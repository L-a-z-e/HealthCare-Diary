package gachon.example.p_project.activity;

import android.content.Context;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gachon.example.p_project.R;
import gachon.example.p_project.adapter.GalleryAdapter;
import gachon.example.p_project.galleryimage;
import gachon.example.p_project.session;

public class GalleryActivity extends AppCompatActivity {
    Context context;
    GridLayoutManager mGridLayoutManager;
    RecyclerView recyclerView;
    ArrayList<galleryimage> galleryimages=new ArrayList<>();
    String userid;
    int month,day,year;

    session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        month=getIntent().getIntExtra("month",0);
        day=getIntent().getIntExtra("day",0);
        year=getIntent().getIntExtra("year",0);

        session=(session)getApplicationContext();
        userid=session.getUserid();
        mGridLayoutManager = new GridLayoutManager(GalleryActivity.this, 4);
        recyclerView=(RecyclerView)findViewById(R.id.exercise_gallery);
        recyclerView.setLayoutManager(mGridLayoutManager);

        new JSONTask().execute("http://"+getString(R.string.ip)+":65000/userrecipe/getdiary?userid="+userid+"&year="+year+"&month="+month+"&day="+day);
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
                galleryimages.clear();
                JSONObject object;
                while (count<jsonArray.length()){
                    object=jsonArray.getJSONObject(count);
                    //System.out.println(object);
                    galleryimage galleryimage1=new galleryimage();
                    galleryimage galleryimage2=new galleryimage();
                    if(object.getString("image1").equals("NoImage")){

                    }else{
                    galleryimage1.setImageurl(object.getString("image1"));
                        galleryimages.add(galleryimage1);}
                    if(object.getString("image2").equals("NoImage")){

                    }else{
                    galleryimage2.setImageurl(object.getString("image2"));
                        galleryimages.add(galleryimage2);}




                    count++;
                }

                GalleryAdapter adapter=new GalleryAdapter(context,galleryimages);
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}
