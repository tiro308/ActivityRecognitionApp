package com.example.atrt01admin.meinexample03;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status>, LocationListener,Serializable {

    private static final String TAG = "MainActivity";
    private GoogleApiClient mGoogleApiClient;
    private TextView mDetectedActivityTextView;
    private ActivityDetectionBroadcastReceiver mBroadcastReceiver;
    private LocationRequest mLocationRequest;
    private String mLastUpdateTime;
    private double latitude;
    private double longitude;
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;
    private TextView showDataTextView;
    private Context context;
    public List<RecordItem> recordItemList;
    public static MyDBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordItemList = new ArrayList<>();
        context = getApplicationContext();

        db = new MyDBHandler(this, null, null, 1);
        //printDatabase();

        mLatitudeTextView = (TextView) findViewById((R.id.latitude_textview));
        mLongitudeTextView = (TextView) findViewById((R.id.longitude_textview));
        showDataTextView = (TextView) findViewById(R.id.showDataTextView);
        showDataTextView.setMovementMethod(new ScrollingMovementMethod());
        mDetectedActivityTextView = (TextView) findViewById(R.id.detected_activities_textview);

        final Button button = (Button) findViewById(R.id.show_records);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getAllRecords();
            }

        });


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(ActivityRecognition.API)
                .addApi(LocationServices.API)
                .build();

        mBroadcastReceiver = new ActivityDetectionBroadcastReceiver();
    }

    public void openNewActivity(View view) {
        String message = "default area";

        switch (view.getId()) {
            case R.id.working_area:
                message = "Working Area";
                break;
            case R.id.uni_area:
                message = "University Area";
                break;
            case R.id.home_area:
                message = "Home Area";
                break;
        }

        Intent intent = new Intent(this, DisplayActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
        intent.putExtra(Constants.STRING_EXTRA, message);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override //aus Connectioncallbacks
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Connected");

        //Teil: periodic location updates
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override //aus Connectioncallbacks
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override //aus ConnectionFailedListener
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("void onStart()");
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("void onStop()");
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
    }

    public String getDetectedActivity(int detectedActivityType) {
        Resources resources = this.getResources();
        switch (detectedActivityType) {
            case DetectedActivity.IN_VEHICLE:
                return resources.getString(R.string.in_vehicle);
            case DetectedActivity.ON_BICYCLE:
                return resources.getString(R.string.on_bicycle);
            case DetectedActivity.ON_FOOT:
                return resources.getString(R.string.on_foot);
            case DetectedActivity.RUNNING:
                return resources.getString(R.string.running);
            case DetectedActivity.WALKING:
                return resources.getString(R.string.walking);
            case DetectedActivity.STILL:
                return resources.getString(R.string.still);
            case DetectedActivity.TILTING:
                return resources.getString(R.string.tilting);
            case DetectedActivity.UNKNOWN:
                return resources.getString(R.string.unknown);
            default:
                return resources.getString(R.string.unidentifiable_activity, detectedActivityType);
        }
    }

    public void requestActivityUpdates(View view) {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, "GoogleApiClient not yet connected", Toast.LENGTH_SHORT).show();
        } else {
            ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mGoogleApiClient, 0, getActivityDetectionPendingIntent()).setResultCallback(this);
        }
    }

    public void removeActivityUpdates(View view) {
        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(mGoogleApiClient, getActivityDetectionPendingIntent()).setResultCallback(this);
    }

    private PendingIntent getActivityDetectionPendingIntent() {
        Intent intent = new Intent(this, ActivitiesIntentService.class);

        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void onResult(Status status) {
        if (status.isSuccess()) {
            Log.e(TAG, "Successfully added activity detection.");

        } else {
            Log.e(TAG, "Error: " + status.getStatusMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume()");
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, new IntentFilter(Constants.STRING_ACTION));
    }

    @Override
    protected void onPause() {
        System.out.println("onPause()");
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
        super.onPause();
    }

    @Override // von Interface LocationListener
    public void onLocationChanged(Location location) {
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        System.out.println(DateFormat.getTimeInstance().format(new Date())); //test
        mLatitudeTextView.setText(String.valueOf(location.getLatitude()));
        mLongitudeTextView.setText(String.valueOf(location.getLongitude()));
        Toast.makeText(this, "Updated: " + mLastUpdateTime, Toast.LENGTH_SHORT).show();

        latitude = location.getLatitude();
        longitude = location.getLongitude();

    }

    public class ActivityDetectionBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<DetectedActivity> detectedActivities = intent.getParcelableArrayListExtra(Constants.STRING_EXTRA);
            String activityString = "";
            String activityToFile = "";
            for (DetectedActivity activity : detectedActivities) {
                if (activity.getType() == DetectedActivity.RUNNING || activity.getType() == DetectedActivity.WALKING || activity.getType() == DetectedActivity.STILL) {
                    activityString = "Activity: " + getDetectedActivity(activity.getType()) + ", Confidence: " + activity.getConfidence() + "%\n"; // += für mehrere activities

                    activityToFile = getDetectedActivity(activity.getType());

                    //create new recordItem
                    Date dNow = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                    //System.out.println("Current Date: " + ft.format(dNow));

                    RecordItem recordItem = new RecordItem(activityToFile, latitude, longitude, ft.format(dNow));
                    addRecordToDB(recordItem);
                }

            }
            mDetectedActivityTextView.setText(activityString);
        }
    }

    public void addRecordToDB(RecordItem recordItem) {

        db.addRecord(recordItem);
        System.out.println("\n addRecordToDB:" + recordItem.recordItemToString());
    }

    public void getAllRecords() {
        //System.out.println(db.getTableAsString());

//        List<RecordItem> recordItemList = db.getAllRecordItems();
//        for (RecordItem recordItem : recordItemList) {
//            System.out.println(recordItem.recordItemToString());
//        }
        //test, getcounted "walking" entries
        System.out.println("\n countWalking " + db.countActitiyWalkinginAllRecords());
        System.out.println("\n countRunning " + db.countActitiyRunninginAllRecords());
        System.out.println("\n countStill " + db.countActitiyStillinAllRecords());

        showDataTextView.setText("Walking: " + db.countActitiyWalkinginAllRecords()
        + "Running: " + db.countActitiyRunninginAllRecords() +
                "Still: " + db.countActitiyStillinAllRecords());
    }

    public float getCountWalking(){
        return db.countActitiyWalkinginAllRecords();
    }
}