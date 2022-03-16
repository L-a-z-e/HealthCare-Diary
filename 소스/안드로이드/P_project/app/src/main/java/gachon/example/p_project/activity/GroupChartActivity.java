package gachon.example.p_project.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import gachon.example.p_project.R;
import gachon.example.p_project.chartitem;
import gachon.example.p_project.session;

public class GroupChartActivity extends AppCompatActivity{

    int month,year;
    session session;
    TextView title,average;
    Button bmr,recommed;
    private GraphicalView mChartView;
    ArrayList<chartitem> chartitems=new ArrayList<>();
    XYSeries totalkcal=new XYSeries("칼로리");
    XYSeries totaltime=new XYSeries("운동시간(분)");
    ArrayList<Integer> x=new ArrayList<>();
    AlertDialog alertDialog;
    String userid;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_chart);
        month=getIntent().getIntExtra("month",0);
        year=getIntent().getIntExtra("year",0);
        session=(gachon.example.p_project.session)getApplicationContext();
        title=(TextView)findViewById(R.id.diarytitle);

        userid=getIntent().getStringExtra("userid");
        title.setText(userid+" 님의 "+month+" 월 차트");
        bmr=(Button)findViewById(R.id.bmr);
        recommed=(Button)findViewById(R.id.recommend);
        average=(TextView)findViewById(R.id.average);


        new JSONTask().execute("http://"+getString(R.string.ip)+":65000/userrecipe/getchart?userid="+userid+"&year="+year+"&month="+month);



    }


    private void drawChart1() {






        // Creating an XYSeries for Income



        // Creating an XYSeries for Expense



        // Adding data to Income and Expense Series




        // Creating a dataset to hold each series

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        // Adding Income Series to the dataset

        dataset.addSeries(totalkcal);

        // Adding Expense Series to dataset




        // Creating XYSeriesRenderer to customize incomeSeries

        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();

        incomeRenderer.setColor(Color.GREEN); //color of the graph set to cyan

        incomeRenderer.setFillPoints(true);

        incomeRenderer.setLineWidth(300);

        incomeRenderer.setDisplayChartValues(true);
        incomeRenderer.setChartValuesTextSize(30);
        incomeRenderer.setChartValuesTextAlign(Paint.Align.RIGHT);

        // Creating XYSeriesRenderer to customize expenseSeries



        // Creating a XYMultipleSeriesRenderer to customize the whole chart

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        multiRenderer.setXLabels(0);






        /***

         * Customizing graphs

         */

        //setting text size of the title

        multiRenderer.setChartTitleTextSize(40);

        multiRenderer.setAxisTitleTextSize(40);

        //setting text size of the graph lable

        multiRenderer.setLabelsTextSize(35);

        //setting zoom buttons visiblity

        multiRenderer.setZoomButtonsVisible(false);

        //setting pan enablity which uses graph to move on both axis

        multiRenderer.setPanEnabled(false, false);

        //setting click false on graph

        multiRenderer.setClickEnabled(false);

        //setting zoom to false on both axis

        multiRenderer.setZoomEnabled(false, false);

        //setting lines to display on y axis

        multiRenderer.setShowGridY(false);

        //setting lines to display on x axis

        multiRenderer.setShowGridX(false);

        //setting legend to fit the screen size

        multiRenderer.setFitLegend(true);

        //setting displaying line on grid

        multiRenderer.setShowGrid(false);

        //setting zoom to false

        multiRenderer.setZoomEnabled(false);

        //setting external zoom functions to false

        multiRenderer.setExternalZoomEnabled(false);

        //setting displaying lines on graph to be formatted(like using graphics)

        multiRenderer.setAntialiasing(true);

        //setting to in scroll to false

        multiRenderer.setInScroll(false);

        //setting to set legend height of the graph

        multiRenderer.setLegendHeight(5);

        //setting x axis label align

        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);

        //setting y axis label to align

        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);

        //setting text style

        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);

        //setting no of values to display in y axis

        multiRenderer.setYLabels(10);

        // setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.

        // if you use dynamic values then get the max y value and set here

        multiRenderer.setYAxisMax(1000);
        multiRenderer.setYAxisMin(100);
        //setting used to move the graph on xaxiz to .5 to the right

        multiRenderer.setXAxisMin(-0.5);

        //setting max values to be display in x axis

        multiRenderer.setXAxisMax(31);

        //setting bar size or space between two bars

        multiRenderer.setBarSpacing(1);

        //Setting background color of the graph to transparent

        multiRenderer.setBackgroundColor(Color.TRANSPARENT);

        //Setting margin color of the graph to transparent

        multiRenderer.setMarginsColor(Color.WHITE);

        multiRenderer.setApplyBackgroundColor(true);

        //setting the margin size for the graph in the order top, left, bottom, right

        multiRenderer.setMargins(new int[]{50, 50, 50, 50});

        for (int i = 1; i < 32; i++) {

            multiRenderer.addXTextLabel(i-1, i+" 일");

        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer

        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer

        // should be same

        multiRenderer.addSeriesRenderer(incomeRenderer);



        //this part is used to display graph on the xml

        LinearLayout layout = (LinearLayout) findViewById(R.id.chart_bar1);
        layout.removeAllViews();
        //remove any views before u paint the chart



        //drawing bar chart

        mChartView = ChartFactory.getBarChartView(this, dataset, multiRenderer, BarChart.Type.DEFAULT);


        layout.addView(mChartView, new LinearLayout.LayoutParams(5000,

                LinearLayout.LayoutParams.MATCH_PARENT));

    }

    private void drawChart2() {






        // Creating an XYSeries for Income



        // Creating an XYSeries for Expense



        // Adding data to Income and Expense Series




        // Creating a dataset to hold each series

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        // Adding Income Series to the dataset



        // Adding Expense Series to dataset

        dataset.addSeries(totaltime);


        // Creating XYSeriesRenderer to customize incomeSeries

        //setting chart value distance


        // Creating XYSeriesRenderer to customize expenseSeries

        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();

        expenseRenderer.setColor(Color.RED);

        expenseRenderer.setFillPoints(true);

        expenseRenderer.setLineWidth(300);

        expenseRenderer.setDisplayChartValues(true);
        expenseRenderer.setChartValuesTextSize(30);
        expenseRenderer.setChartValuesTextAlign(Paint.Align.RIGHT);
        // Creating a XYMultipleSeriesRenderer to customize the whole chart

        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();

        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        multiRenderer.setXLabels(0);








        /***

         * Customizing graphs

         */

        //setting text size of the title

        multiRenderer.setChartTitleTextSize(40);

        multiRenderer.setAxisTitleTextSize(40);

        //setting text size of the graph lable

        multiRenderer.setLabelsTextSize(35);

        //setting zoom buttons visiblity

        multiRenderer.setZoomButtonsVisible(false);

        //setting pan enablity which uses graph to move on both axis

        multiRenderer.setPanEnabled(false, false);

        //setting click false on graph

        multiRenderer.setClickEnabled(false);

        //setting zoom to false on both axis

        multiRenderer.setZoomEnabled(false, false);

        //setting lines to display on y axis

        multiRenderer.setShowGridY(false);

        //setting lines to display on x axis

        multiRenderer.setShowGridX(false);

        //setting legend to fit the screen size

        multiRenderer.setFitLegend(true);

        //setting displaying line on grid

        multiRenderer.setShowGrid(false);

        //setting zoom to false

        multiRenderer.setZoomEnabled(false);

        //setting external zoom functions to false

        multiRenderer.setExternalZoomEnabled(false);

        //setting displaying lines on graph to be formatted(like using graphics)

        multiRenderer.setAntialiasing(true);

        //setting to in scroll to false

        multiRenderer.setInScroll(false);

        //setting to set legend height of the graph

        multiRenderer.setLegendHeight(5);

        //setting x axis label align

        multiRenderer.setXLabelsAlign(Paint.Align.CENTER);

        //setting y axis label to align

        multiRenderer.setYLabelsAlign(Paint.Align.LEFT);

        //setting text style

        multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);

        //setting no of values to display in y axis

        multiRenderer.setYLabels(12);

        // setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.

        // if you use dynamic values then get the max y value and set here

        multiRenderer.setYAxisMax(120);
        multiRenderer.setYAxisMin(10);
        //setting used to move the graph on xaxiz to .5 to the right

        multiRenderer.setXAxisMin(-0.5);

        //setting max values to be display in x axis

        multiRenderer.setXAxisMax(31);

        //setting bar size or space between two bars

        multiRenderer.setBarSpacing(1);

        //Setting background color of the graph to transparent

        multiRenderer.setBackgroundColor(Color.TRANSPARENT);

        //Setting margin color of the graph to transparent

        multiRenderer.setMarginsColor(Color.WHITE);

        multiRenderer.setApplyBackgroundColor(true);

        //setting the margin size for the graph in the order top, left, bottom, right

        multiRenderer.setMargins(new int[]{50, 50, 50, 50});

        for (int i = 1; i < 32; i++) {

            multiRenderer.addXTextLabel(i-1, i+" 일");

        }

        // Adding incomeRenderer and expenseRenderer to multipleRenderer

        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer

        // should be same



        multiRenderer.addSeriesRenderer(expenseRenderer);

        //this part is used to display graph on the xml

        LinearLayout layout = (LinearLayout) findViewById(R.id.chart_bar2);
        layout.removeAllViews();
        //remove any views before u paint the chart



        //drawing bar chart

        mChartView = ChartFactory.getBarChartView(this, dataset, multiRenderer, BarChart.Type.DEFAULT);


        layout.addView(mChartView, new LinearLayout.LayoutParams(5000,

                LinearLayout.LayoutParams.MATCH_PARENT));

    }
    class JSONTask extends AsyncTask<String, String, String> {
        @Override
        public String doInBackground(String... urls) {

            HttpURLConnection con = null;
            BufferedReader reader = null;
            try {

                URL url = new URL(urls[0]);
                System.out.println(url);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                //서버로 보내기위해서 스트림 만듬
                InputStream stream = con.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) buffer.append(line);
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            //System.out.println("넘어온데이터 :" + s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                System.out.println(jsonArray);
                //System.out.println(jsonArray.length());
                int count=0;
                long i=0;
                long hap1=0;
                long hap2=0;
                long avg=0;
                JSONObject object;
                while (count<jsonArray.length()){
                    object=jsonArray.getJSONObject(count);
                    //System.out.println(object);

                    totalkcal.add(object.getInt("day")-1,object.getInt("totalkcal")-0);
                    totaltime.add(object.getInt("day")-1,object.getInt("totaltime")-0);
                    hap1=hap1+object.getInt("totalkcal");
                    hap2=hap2+object.getInt("totaltime");


                    i++;

                    count++;}
                if(i==0){
                    average.setText("평균 소모 칼로리(kcal) "+avg+"\n평균 운동 시간(분) "+avg);
                }else{
                    average.setText("평균 소모 칼로리(kcal) "+(hap1/i)+"\n평균 운동 시간(분) "+(hap2/i));}


                drawChart1();
                drawChart2();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}
