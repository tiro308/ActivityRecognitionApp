package com.example.atrt01admin.meinexample03;

        import android.app.IntentService;
        import android.content.Intent;
        import android.support.v4.content.LocalBroadcastManager;

        import com.google.android.gms.location.ActivityRecognitionResult;
        import com.google.android.gms.location.DetectedActivity;

        import java.util.ArrayList;

public class ActivitiesIntentService extends IntentService {

    private static final String TAG = "ActivitiesIntentService";

    public ActivitiesIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //get the update
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

        // Get the most probable activity from the list of activities in the update
        DetectedActivity mostProbableActivity = result.getMostProbableActivity();
        // Get the type of activity
        int activityType = mostProbableActivity.getType();


        Intent i = new Intent(Constants.STRING_ACTION);

        ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();

        i.putExtra(Constants.STRING_EXTRA, detectedActivities);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }
}
