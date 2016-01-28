package com.example.atrt01admin.meinexample03;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class DisplayHistory extends AppCompatActivity {

    public static MyDBHandler db;
    private TextView tv;
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new MyDBHandler(this, null, null, 1);
        tv  = (TextView) findViewById((R.id.output));

        //auslesen des Intents der Activity, welche diese Activity aufrief. Inhalt der message kommt in den TextView
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.STRING_EXTRA);
        tv.setText(message);

        //Linechart DAtenset
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(getCountStillYesterday(), 0));
        entries.add(new Entry(getCountStillTwoDaysBefore(), 1));
        entries.add(new Entry(getCountStillThreeDaysBefore(), 2));

        ArrayList<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(getCountWalkingYesterday(), 0));
        entries2.add(new Entry(getCountWalkingTwoDaysBefore(), 1));
        entries2.add(new Entry(getCountWalkingThreeDaysBefore(), 2));

        ArrayList<Entry> entries3 = new ArrayList<>();
        entries3.add(new Entry(getCountRunningYesterday(), 0));
        entries3.add(new Entry(getCountRunningTwoDaysBefore(), 1));
        entries3.add(new Entry(getCountRunningThreeDaysBefore(), 2));

        //create Dataset out of entries
        LineDataSet dataset = new LineDataSet(entries, "still records");
        dataset.setColor(Color.rgb(240, 180, 70));
        dataset.setLineWidth(5f);
        LineDataSet dataset2= new LineDataSet(entries2, "walking records");
        dataset2.setColor(Color.rgb(0,230,30));
        dataset2.setLineWidth(5f);
        LineDataSet dataset3= new LineDataSet(entries3, "running records");
        dataset3.setColor(Color.rgb(50, 80, 240));
        dataset3.setLineWidth(5f);

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataset);
        dataSets.add(dataset2);
        dataSets.add(dataset3);

        //x-achsen Labels
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Yesterday");
        labels.add("2 days ago");
        labels.add("3 days ago");

        //create blank chart
        lineChart = new LineChart(this);
        //setContentView;
        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        LineData data = new LineData(labels,dataSets);
        lineChart.setData(data);
        lineChart.invalidate();
        //description of chart
        lineChart.setDescription("3 days history");

    }

    public float getCountWalkingYesterday(){
        return db.countActivityWalkingYesterday();
    }
    public float getCountWalkingTwoDaysBefore(){
        return db.countActivityWalkingTwoDaysBefore();
    }
    public float getCountWalkingThreeDaysBefore(){
        return db.countActivityWalkingThreeDaysBefore();
    }

    public float getCountRunningYesterday(){
        return db.countActivityRunningYesterday();
    }
    public float getCountRunningTwoDaysBefore(){
        return db.countActivityRunningTwoDaysBefore();
    }
    public float getCountRunningThreeDaysBefore(){
        return db.countActivityRunningThreeDaysBefore();
    }

    public float getCountStillYesterday(){
        return db.countActivityStillYesterday();
    }
    public float getCountStillTwoDaysBefore(){
        return db.countActivityStillTwoDaysBefore();
    }
    public float getCountStillThreeDaysBefore(){
        return db.countActivityStillThreeDaysBefore();
    }

}
