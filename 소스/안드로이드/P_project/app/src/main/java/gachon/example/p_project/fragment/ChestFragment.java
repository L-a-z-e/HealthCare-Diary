package gachon.example.p_project.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gachon.example.p_project.R;
import gachon.example.p_project.adapter.ExerciseAdapter;
import gachon.example.p_project.exerciseitem;

public class ChestFragment extends Fragment {
    Context context;
    String title;
    ArrayList<exerciseitem> exerciseitems=new ArrayList<exerciseitem>();

    RecyclerView recyclerView;

    public ChestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v1=inflater.inflate(R.layout.exercisefragment, container, false);
        context=container.getContext();

        recyclerView = v1.findViewById(R.id.exercise_list);
        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);




                    System.out.println("서버연결 시도...");
                    new JSONTask().execute("http://"+getString(R.string.ip)+":65000/userrecipe/exercise?part=가슴");



        return v1;
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

               ExerciseAdapter adapter=new ExerciseAdapter(exerciseitems);
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }}
