package gachon.example.p_project.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gachon.example.p_project.R;
import gachon.example.p_project.adapter.InformationAdapter;
import gachon.example.p_project.recipeitem;
import gachon.example.p_project.session;


public class RecipeInformationActivity extends AppCompatActivity {
    String menuname,menunum,image;
    int recipenum;
    Button favorite_plus;
    String favorite;
    TextView information_name,information_ingredient;
    RecyclerView recyclerView;
    ImageView information_image;
    ArrayList<recipeitem> recipeitems=new ArrayList<recipeitem>();

    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeinformation);
        menuname=getIntent().getStringExtra("menuname");
        menunum=getIntent().getStringExtra("menunum");
        image=getIntent().getStringExtra("image");
        recipenum= Integer.parseInt(getIntent().getStringExtra("recipenum"));
        session session=(session)getApplicationContext();
        userid=session.getUserid();
        information_name=(TextView)findViewById(R.id.information_name);
        information_ingredient=(TextView)findViewById(R.id.information_ingredient);
        information_name.setText(menuname);

        information_image=(ImageView)findViewById(R.id.information_img);
        recyclerView = (RecyclerView)findViewById(R.id.informationlist);
        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Picasso.with(this)
                .load("http://"+getString(R.string.ip)+":65000"+image)
                .resize(300,200)
                .into(information_image);
        System.out.println("서버연결 시도...");
        new JSONTask().execute("http://"+getString(R.string.ip)+":65000/userrecipe/detail?menunum="+menunum);

      favorite_plus=(Button)findViewById(R.id.favorite_plus);
      favorite_plus.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(userid.equals("로그인해주세요")) {
                  Toast toast = Toast.makeText(getApplicationContext(), "로그인이 필요한 기능입니다", Toast.LENGTH_SHORT);
                  toast.show();}
          else{

              //즐겨찾기 추가 기능
              System.out.println("즐겨찾기 서버연결 시도...");
              new JSONTask2().execute("http://"+getString(R.string.ip)+":65000/userrecipe/favorite?userid="+userid);



          }
          }
      });



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
                //System.out.println(jsonArray);
                //System.out.println(jsonArray.length());

                recipeitems.clear();
                JSONObject object = jsonArray.getJSONObject(0);
                ;
                //System.out.println(object);
                System.out.println("레시피횟수 :" + recipenum);
                information_ingredient.setText("요리재료 :" + object.getString("meterial"));
                for (int i = 0; i < recipenum; i++) {
                    recipeitem recipeitem = new recipeitem();
                    String str1 = "recipeorder" + (i + 1);
                    String str2 = "orderimage" + (i + 1);
                    recipeitem.setOrder(object.getString(str1));
                    recipeitem.setImage(object.getString(str2));
                    recipeitems.add(recipeitem);
                    System.out.println("조리순서   ---" + recipeitems.get(i).getOrder());
                    //System.out.println("카운트 :" + count);

                }
                for (int j = 0; j < recipenum; j++) {
                    System.out.println("어레이리스트 : " + recipeitems.get(j).getOrder());
                }
                InformationAdapter adapter = new InformationAdapter(recipeitems);
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }// JSonTask()
    class JSONTask2 extends AsyncTask<String, String, String> {
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
                JSONObject object = jsonArray.getJSONObject(0);

                System.out.println(object);
                favorite = object.getString("favorite");
                System.out.println(favorite);
                int i = 0;
                if(favorite.equals("null")){
                    favorite = "";
                    favorite = favorite + menunum + ",";
                    System.out.println(favorite);
                    new JSONTask3().execute("http://"+getString(R.string.ip)+":65000/userrecipe/favorite");
                }else{
                    for(i= 0; i < favorite.split(",").length; i++) {
                        if(favorite.split(",")[i].equals(menunum)) {
                            Toast toast = Toast.makeText(getApplicationContext(), "이미 등록된 메뉴입니다 ", Toast.LENGTH_SHORT);
                            toast.show();
                            break;
                        }
                    }

                    if(i == favorite.split(",").length){
                        favorite = favorite + menunum + ",";
                        new JSONTask3().execute("http://"+getString(R.string.ip)+":65000/userrecipe/favorite");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }// JsonTask2()
    class JSONTask3 extends AsyncTask<String, String, String> {
        @Override
        public String doInBackground(String... urls) {
            HttpURLConnection con = null;
            BufferedReader reader = null;
            try {
                session session=(session)getApplicationContext();
                 userid=session.getUserid();

                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("favorite", favorite);
                jsonObject.accumulate("userid", userid);


                URL url = new URL(urls[0]);

                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("PUT");
                con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                con.setDoInput(true);                         // 서버에서 읽기 모드 지정
                con.setDoOutput(true);                       // 서버로 쓰기 모드 지정
                con.connect();
                //서버로 보내기위해서 스트림 만듬
                OutputStream outStream = con.getOutputStream();
                //버퍼를 생성하고 넣음
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();//버퍼를 받아줌

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
            } catch (JSONException e) {
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
            System.out.println(s);
            if(s.equals("OK"))
            {
                Toast toast = Toast.makeText(getApplicationContext(), "즐겨찾기에 등록되었습니다", Toast.LENGTH_SHORT);
                toast.show();

            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(), "즐겨찾기 등록에 실패했습니다", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }
}
