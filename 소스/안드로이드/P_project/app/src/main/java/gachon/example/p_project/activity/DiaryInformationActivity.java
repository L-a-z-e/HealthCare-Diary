package gachon.example.p_project.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Future;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import gachon.example.p_project.R;
import gachon.example.p_project.session;

public class DiaryInformationActivity extends AppCompatActivity {
TextView text_date,exe_time,exe_kcal,exe_expl;
Button gallery,body_btn;
ImageView body_img;
int year,month,day;
String userid;
FloatingActionButton fab;
session session;
String exercisetime="0";
String exercisekcal="0";
String path;
int action=0;
int flag=0;
    int m,d,y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy_information);
        text_date=(TextView)findViewById(R.id.date);
        exe_time=(TextView)findViewById(R.id.exercise_time);
        exe_kcal=(TextView)findViewById(R.id.exercise_kcal);
        exe_expl=(TextView)findViewById(R.id.exercise_expl);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        gallery=(Button)findViewById(R.id.gallery);
        body_btn=(Button)findViewById(R.id.body_btn);
        body_img=(ImageView)findViewById(R.id.body_img);
        session=(session)getApplicationContext();
        userid=session.getUserid();
        month=getIntent().getIntExtra("month",0);
        day=getIntent().getIntExtra("day",0);
        year=getIntent().getIntExtra("year",0);

        d= CalendarDay.today().getDay();
        m=CalendarDay.today().getMonth()+1;
        y=CalendarDay.today().getYear();



        text_date.setText(month+" 월 "+day+" 일");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (d == getIntent().getIntExtra("day", 0) && m == getIntent().getIntExtra("month", 0) && y == getIntent().getIntExtra("year", 0)) {

                    Intent intent = new Intent(DiaryInformationActivity.this, DiaryRegisterActivity.class);
                    intent.putExtra("month", month);
                    intent.putExtra("day", day);
                    intent.putExtra("year", year);
                    startActivity(intent);
                     finish();}


           else{

                Toast.makeText(getApplicationContext(),"오늘만 등록할 수 있습니다", Toast.LENGTH_SHORT).show();

           }}
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   Intent intent=new Intent(DiaryInformationActivity.this,GalleryActivity.class);
                intent.putExtra("month",month);
                intent.putExtra("day",day);
                intent.putExtra("year",year);
                startActivity(intent);

            }
        });

        body_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fintent = new Intent(Intent.ACTION_PICK);
                fintent.setType("image/*");
                try {
                    startActivityForResult(fintent, 100);
                } catch (ActivityNotFoundException e) {

                }
            }
        });
        body_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0){
                    Toast.makeText(getApplicationContext(),"선택한 사진이 없습니다.", Toast.LENGTH_SHORT).show();
                }
               else if (d == getIntent().getIntExtra("day", 0) && m == getIntent().getIntExtra("month", 0) && y == getIntent().getIntExtra("year", 0)) {
                    if(body_btn.getText().toString().equals("등록")){
                        action=1;
                    }else if(body_btn.getText().toString().equals("변경")){
                        action=2;
                    }
                    new JSONTask2().execute("http://"+getString(R.string.ip)+":65000/userrecipe/insertbody?userid="+userid+"&year="+year+"&month="+month+"&day="+day);
               }


                else{

                    Toast.makeText(getApplicationContext(),"오늘만 등록할 수 있습니다", Toast.LENGTH_SHORT).show();

               }

                }
        });

        new JSONTask().execute("http://"+getString(R.string.ip)+":65000/userrecipe/getdiary?userid="+userid+"&year="+year+"&month="+month+"&day="+day);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    path = getPathFromURI(data.getData());
                   body_img.setImageURI(data.getData());
                   flag=1;
                }
    }}
    private String getPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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

                JSONObject object;
                while (count<jsonArray.length()){
                    object=jsonArray.getJSONObject(count);
                    //System.out.println(object);

                    int num1=object.getInt("exercisetime");
                    int num2=Integer.parseInt(exercisetime);
                    int sum1=num1+num2;
                    exercisetime=String.valueOf(sum1);
                    exe_time.setText("총 운동시간(분) :   "+String.valueOf(sum1));
                    exe_expl.append(object.getString("exercisememo"));
                    long knum1=object.getInt("exercisekcal");
                    long knum2=Integer.parseInt(exercisekcal);
                    long sum2=knum1+knum2;
                    exercisekcal=String.valueOf(sum2);
                    exe_kcal.setText("총 소모칼로리(kcal) :   "+String.valueOf(sum2));

                    count++;
                }
                new JSONTask3().execute("http://"+getString(R.string.ip)+":65000/userrecipe/getbody?userid="+userid+"&year="+year+"&month="+month+"&day="+day);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
    class JSONTask2 extends AsyncTask<String, String, String> {
        @Override
        public String doInBackground(String... urls) {



            HttpURLConnection con = null;
            BufferedReader reader = null;
            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("userid",userid);
                jsonObject.accumulate("year",year);
                jsonObject.accumulate("month",month);
                jsonObject.accumulate("day",day);
                jsonObject.accumulate("action",action);

                URL url = new URL(urls[0]);

                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
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
            String test = s;
            System.out.println(s);
            if(s.equals("ok")) {


                File f = new File(path);

                Future uploading = Ion.with(DiaryInformationActivity.this)
                        .load("http://" + getString(R.string.ip) + ":65000/userrecipe/bodyimg")
                        .setMultipartFile("image", f)
                        .asString()
                        .withResponse()
                        .setCallback(new FutureCallback<Response<String>>() {
                            @Override
                            public void onCompleted(Exception e, Response<String> result) {
                                try {
                                    JSONObject jobj = new JSONObject(result.getResult());
                                    if (jobj.getString("response").equals("ok")) {

                                        Toast.makeText(getApplicationContext(), "등록 되었습니다", Toast.LENGTH_SHORT).show();
                                        flag = 0;
                                        body_btn.setText("변경");
                                        action = 2;

                                    }

                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        });


            }

            }


        }
    class JSONTask3 extends AsyncTask<String, String, String> {
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

                JSONObject object;
                while (count<jsonArray.length()){
                    object=jsonArray.getJSONObject(count);
                    //System.out.println(object);
                    Picasso.with(getApplicationContext())
                            .load("http://"+getApplicationContext().getString(R.string.ip)+":65000"+object.getString("image"))
                            .resize(100,200)
                            .into(body_img);
                    body_btn.setText("변경");
                    count++;

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
    }

