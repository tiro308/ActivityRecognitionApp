package com.example.atrt01admin.meinexample03;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class DisplayWorkingArea extends AppCompatActivity {

    public static MyDBHandler db;
    private TextView tv;
    BarChart barChart; //bardataset für Barchart

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_working_area);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new MyDBHandler(this, null, null, 1);
        tv  = (TextView) findViewById((R.id.output));

        //auslesen des Intents der Activity, welche diese Activity aufrief. Inhalt der message kommt in den TextView
        Intent intent = getIntent();
        String message = intent.getStringExtra(Constants.STRING_EXTRA);
        tv.setText(message);

        //barchart DAtenset
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(getCountWalking(), 0));
        entries.add(new BarEntry(getCountRunning(), 1));
        entries.add(new BarEntry(getCountStill(), 2));

        //create Dataset out of entries
        BarDataSet dataset = new BarDataSet(entries, "Activities");

        //x-achsen Labels
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Walking");
        labels.add("Running");
        labels.add("Still");

        //create blank chart
        barChart = new BarChart(this);
        //setContentView(barChart);
        BarChart barChart = (BarChart) findViewById(R.id.chart);
        BarData data = new BarData(labels,dataset);
        barChart.setData(data);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateXY(2000, 2000);
        barChart.invalidate();
        //description of chart
        barChart.setDescription("activities working area");

    }

    public float getCountWalking(){
        return db.countActivityWalkingWorkingArea();
    }

    public float getCountRunning(){
        return db.countActivityRunningWorkingArea();
    }

    public float getCountStill(){
        return db.countActivityStillWorkingArea();
    }

}
