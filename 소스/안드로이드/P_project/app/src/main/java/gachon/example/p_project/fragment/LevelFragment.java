package gachon.example.p_project.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

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

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gachon.example.p_project.R;
import gachon.example.p_project.adapter.RecipeAdapter;
import gachon.example.p_project.categoryitem;

public class LevelFragment extends Fragment {
Context context;
    String title;
    ArrayList<categoryitem> categories=new ArrayList<categoryitem>();
    ListView list2;
    RecyclerView recyclerView;
    int flag1,flag2,flag3;
    public LevelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v2=inflater.inflate(R.layout.levelfragment, container, false);
        context=container.getContext();
          final Button btn1=v2.findViewById(R.id.level1);
          final Button btn2=v2.findViewById(R.id.level2);
          final Button btn3=v2.findViewById(R.id.level3);
          flag1=0;
          flag2=0;
          flag3=0;
          btn1.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  btn1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.star1c));
                  btn2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.star2));
                  btn3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.star3));
                  if(flag1==0) {
                      System.out.println("서버연결 시도...");
                      new JSONTask().execute("http://"+getString(R.string.ip)+":65000/userrecipe/difficulty?difficulty=1");
                  }
                  flag1=1; flag2=0; flag3=0;


              }
          });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.star1));
                btn2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.star2c));
                btn3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.star3));
                if(flag2==0) {
                    System.out.println("서버연결 시도...");
                    new JSONTask().execute("http://"+getString(R.string.ip)+":65000/userrecipe/difficulty?difficulty=2");
                }
                flag1=0; flag2=1; flag3=0;

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.star1));
                btn2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.star2));
                btn3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.star3c));
                if(flag3==0) {
                    System.out.println("서버연결 시도...");
                    new JSONTask().execute("http://"+getString(R.string.ip)+":65000/userrecipe/difficulty?difficulty=3");
                }
                flag1=0; flag2=0; flag3=1;

            }
        });





        recyclerView = v2.findViewById(R.id.categorylist2);
        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        btn1.performClick();



        return v2;
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
                categories.clear();
                JSONObject object;
                while (count<jsonArray.length()){
                    object=jsonArray.getJSONObject(count);
                    //System.out.println(object);
                    categoryitem categoryitem=new categoryitem();
                    categoryitem.setMenuname(object.getString("menuname"));
                    categoryitem.setDifficulty(object.getString("difficulty"));
                    categoryitem.setImage(object.getString("image"));
                    categoryitem.setMenunumber(object.getString("menunum"));
                    categoryitem.setRecipenum(object.getString("recipenum"));
                    categories.add(categoryitem);
                    count++;
                }
                RecipeAdapter adapter = new RecipeAdapter(categories);
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}
