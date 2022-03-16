package gachon.example.p_project.activity;

import android.os.AsyncTask;
import android.os.Bundle;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gachon.example.p_project.R;
import gachon.example.p_project.adapter.CardioAdapter;
import gachon.example.p_project.exerciseitem;

public class CardioActivity extends AppCompatActivity {

    ArrayList<exerciseitem> exerciseitems=new ArrayList<exerciseitem>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio);

        recyclerView =(RecyclerView)findViewById(R.id.exercise_list);
        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        System.out.println("서버연결 시도...");
        new JSONTask().execute("http://" + getString(R.string.ip) + ":65000/userrecipe/exercise?part=유산소");


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
                    exerciseitems.clear();
                    JSONObject object;
                    while (count<jsonArray.length()){
                        object=jsonArray.getJSONObject(count);
                        //System.out.println(object);
                        exerciseitem exerciseitem=new exerciseitem();
                        exerciseitem.setName(object.getString("name"));
                        exerciseitem.setExplain(object.getString("expl"));
                        exerciseitem.setImgurl(object.getString("imgurl"));
                        exerciseitem.setVideourl(object.getString("videourl"));

                        exerciseitems.add(exerciseitem);
                        count++;
                    }

                    CardioAdapter adapter=new CardioAdapter(exerciseitems);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }}}

