package gachon.example.p_project.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Future;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import gachon.example.p_project.R;
import gachon.example.p_project.session;

public class DiaryRegisterActivity extends AppCompatActivity {
      ImageView exe_img1,exe_img2;
      TextView  exe_start,exe_finish,exe_total_time,exe_kcal,exe_memo;
      EditText exe_name,exe_time,h1s,m1s,h2s,m2s;
      Date start,finish;
      Button exe_calc,registebtn,timecalc,hm1,hm2;
      double kcal_n=0;
      int year,month,day;
      String userid,exercisertime;
      String kcal_s,path1,path2,memo_string="";
    AlertDialog alertDialog;
    int weight,flag1=0,flag2=0;
      session session;
      Spinner spinner;
      ArrayList<String> exercise=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    long min=0,diff;
    DiaryActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_register);
        activity=(DiaryActivity)DiaryActivity.diaryactivity;
        exe_img1=(ImageView)findViewById(R.id.exe_img1);
        exe_img2=(ImageView)findViewById(R.id.exe_img2);
        exe_start=(TextView)findViewById(R.id.exe_start);
        exe_finish=(TextView)findViewById(R.id.exe_finish);
        exe_total_time=(TextView)findViewById(R.id.exe_total_time);
        exe_name=(EditText)findViewById(R.id.exe_name);
        exe_time=(EditText)findViewById(R.id.exe_time);
        exe_calc=(Button)findViewById(R.id.exe_clac);
        exe_kcal=(TextView)findViewById(R.id.exe_kcal);
        exe_memo=(TextView)findViewById(R.id.exe_memo);
        registebtn=(Button)findViewById(R.id.registbutton);
        spinner=(Spinner)findViewById(R.id.exe_name_spinner);
        month=getIntent().getIntExtra("month",0);
        day=getIntent().getIntExtra("day",0);
        year=getIntent().getIntExtra("year",0);
        h1s=(EditText)findViewById(R.id.h1s);
        h2s=(EditText)findViewById(R.id.h2s);
        m1s=(EditText)findViewById(R.id.m1s);
        m2s=(EditText)findViewById(R.id.m2s);
        timecalc=(Button)findViewById(R.id.timecalc);
        hm1=(Button)findViewById(R.id.hm1);
        hm2=(Button)findViewById(R.id.hm2);

        session=(session)getApplicationContext();
        userid=session.getUserid();
        weight=session.getWeight();

        new JSONTask2().execute("http://"+getString(R.string.ip)+":65000/userrecipe/exercise?part=1");
        Ion.getDefault(this).configure().setLogging("ion-sample", Log.DEBUG);

        exe_img1.setOnClickListener(new View.OnClickListener() {
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

        exe_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent fintent = new Intent(Intent.ACTION_PICK);
                fintent.setType("image/*");
                try {
                    startActivityForResult(fintent, 200);
                } catch (ActivityNotFoundException e) {


            }}
        });

        exe_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memo_string = exe_name.getText().toString()+"  "+exe_time.getText().toString()+" 분\n";
                exe_memo.setText(memo_string);
                if(exe_name.getText().toString().equals("걷기")){
                    int min=Integer.parseInt(exe_time.getText().toString());

                    kcal_n=weight*min*0.07;
                    kcal_s = String.format("%.2f", kcal_n);
                    exe_kcal.setText("소모 칼로리 : "+kcal_s);
                }
                else if(exe_name.getText().toString().equals("계단")){
                    int min=Integer.parseInt(exe_time.getText().toString());
                    kcal_n=weight*min*0.12;
                    kcal_s = String.format("%.2f", kcal_n);
                    exe_kcal.setText("소모 칼로리 : "+kcal_s);
                }
                else if(exe_name.getText().toString().equals("달리기")){
                    int min=Integer.parseInt(exe_time.getText().toString());
                    kcal_n=weight*min*0.12;
                    kcal_s = String.format("%.2f", kcal_n);
                    exe_kcal.setText("소모 칼로리 : "+kcal_s);
                }
                else if(exe_name.getText().toString().equals("등산")){
                    int min=Integer.parseInt(exe_time.getText().toString());
                    kcal_n=weight*min*0.14;
                    kcal_s = String.format("%.2f", kcal_n);
                    exe_kcal.setText("소모 칼로리 : "+kcal_s);
                }
                else if(exe_name.getText().toString().equals("수영")){
                    int min=Integer.parseInt(exe_time.getText().toString());
                    kcal_n=weight*min*0.16;
                    kcal_s = String.format("%.2f", kcal_n);
                    exe_kcal.setText("소모 칼로리 : "+kcal_s);
                }
                else if(exe_name.getText().toString().equals("자전거")){
                    int min=Integer.parseInt(exe_time.getText().toString());
                    kcal_n=weight*min*0.14;
                    kcal_s = String.format("%.2f", kcal_n);
                    exe_kcal.setText("소모 칼로리 : "+kcal_s);
                }
                else if(exe_name.getText().toString().equals("줄넘기")){
                    int min=Integer.parseInt(exe_time.getText().toString());
                    kcal_n=weight*min*0.16;
                    kcal_s = String.format("%.2f", kcal_n);
                    exe_kcal.setText("소모 칼로리 : "+kcal_s);
                }
                else{
                    kcal_n=0;
                    kcal_s = String.format("%.2f", kcal_n);
                    exe_kcal.setText("소모 칼로리:"+kcal_s);
                }
            }
        });

        registebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DiaryRegisterActivity.this);

                builder.setMessage("                       등록 중입니다.");

                 alertDialog = builder.create();

                alertDialog.show();
                new JSONTask().execute("http://"+getString(R.string.ip)+":65000/userrecipe/insertdiary");





            }
        });

       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(exercise.get(position).equals("직접입력")){
                   exe_name.setText("");
                   exe_name.setHint("운동이름 입력");


               }
               else{

               exe_name.setText(exercise.get(position).toString());
           }
           }


           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
       hm1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String time=h1s.getText().toString()+":"+m1s.getText().toString()+":00";
               exe_start.setText("운동 시작 시간 "+time);

               try {
                   start=formatter.parse(time);
               } catch (ParseException e) {
                   e.printStackTrace();
               }
           }
       });
       hm2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String time=h2s.getText().toString()+":"+m2s.getText().toString()+":00";
               exe_finish.setText("운동 끝 시간 "+time);
               try {
                  finish=formatter.parse(time);
               } catch (ParseException e) {
                   e.printStackTrace();

           }}
       });


       timecalc.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               diff=finish.getTime()-start.getTime();
               min=diff/60000;
               if(min<=0){
                   Toast.makeText(getApplicationContext(),"시간 순서가 맞지 않습니다", Toast.LENGTH_SHORT).show();
               }
               else{
               exercisertime=String.valueOf(min);
               exe_time.setText(exercisertime);
               exe_total_time.setText("운동시간  "+min+"분");}
           }
       });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    path1 = getPathFromURI(data.getData());
                    exe_img1.setImageURI(data.getData());
                    flag1=1;
                    File f = new File(path1);


                    String s=formatter.format(f.lastModified());
                    try {
                        start=formatter.parse(s);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    exe_start.setText("운동 시작 시간 "+s);


                    break;}

             case 200:
                if (resultCode == RESULT_OK) {
                  path2 = getPathFromURI(data.getData());
                  exe_img2.setImageURI(data.getData());
                    flag2=1;
                    File f = new File(path2);




                    String s=formatter.format(f.lastModified());
                    try {
                        finish=formatter.parse(s);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    exe_finish.setText("운동 끝 시간 "+s);}



        }
    }


    private String getPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    void postimage1(){
        File f = new File(path1);

        Future uploading = Ion.with(DiaryRegisterActivity.this)
                            .load("http://"+getString(R.string.ip)+":65000/userrecipe/image1")
                            .setMultipartFile("image", f)
                            .asString()
                            .withResponse()
                            .setCallback(new FutureCallback<Response<String>>() {
                                @Override
                                public void onCompleted(Exception e, Response<String> result) {
                                    try {
                                        JSONObject jobj = new JSONObject(result.getResult());
                                        if(jobj.getString("response").equals("ok")){
                                            postimage2();
                                        }

                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }

                                }
                });

    }
    void postimage2(){
        File f = new File(path2);

        Future uploading = Ion.with(DiaryRegisterActivity.this)
                .load("http://"+getString(R.string.ip)+":65000/userrecipe/image2")
                .setMultipartFile("image", f)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        try {
                            JSONObject jobj = new JSONObject(result.getResult());
                            if(jobj.getString("response").equals("ok")){
                                alertDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"등록 되었습니다", Toast.LENGTH_SHORT).show();
                                activity.finish();
                                Intent intent=new Intent(DiaryRegisterActivity.this,DiaryActivity.class);
                                startActivity(intent);
                                finish();

                            }

                        } catch (JSONException e1) {
                            e1.printStackTrace();
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


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("userid",userid);
                jsonObject.accumulate("year",year);
                jsonObject.accumulate("month",month);
                jsonObject.accumulate("day",day);
                jsonObject.accumulate("exercisetime",min);
                jsonObject.accumulate("exercisekcal",kcal_n);
                jsonObject.accumulate("exercisememo",memo_string);
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
            if(s.equals("ok")){
                if(flag1==1&&flag2==1){
                    postimage1();
                }else if(flag1==1&&flag2==0){
                    File f = new File(path1);

                    Future uploading = Ion.with(DiaryRegisterActivity.this)
                            .load("http://"+getString(R.string.ip)+":65000/userrecipe/image1")
                            .setMultipartFile("image", f)
                            .asString()
                            .withResponse()
                            .setCallback(new FutureCallback<Response<String>>() {
                                @Override
                                public void onCompleted(Exception e, Response<String> result) {
                                    try {
                                        JSONObject jobj = new JSONObject(result.getResult());
                                        if(jobj.getString("response").equals("ok")){
                                            alertDialog.dismiss();
                                            Toast.makeText(getApplicationContext(),"등록 되었습니다", Toast.LENGTH_SHORT).show();

                                            Intent intent=new Intent(DiaryRegisterActivity.this,DiaryActivity.class);

                                            startActivity(intent);
                                            finish();
                                        }

                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }

                                }
                            });

                }else if(flag1==0&&flag2==1){
                    File f = new File(path2);

                    Future uploading = Ion.with(DiaryRegisterActivity.this)
                            .load("http://"+getString(R.string.ip)+":65000/userrecipe/image2")
                            .setMultipartFile("image", f)
                            .asString()
                            .withResponse()
                            .setCallback(new FutureCallback<Response<String>>() {
                                @Override
                                public void onCompleted(Exception e, Response<String> result) {
                                    try {
                                        JSONObject jobj = new JSONObject(result.getResult());
                                        if(jobj.getString("response").equals("ok")){
                                            alertDialog.dismiss();
                                            Toast.makeText(getApplicationContext(),"등록 되었습니다", Toast.LENGTH_SHORT).show();
                                            activity.finish();
                                            Intent intent=new Intent(DiaryRegisterActivity.this,DiaryActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }

                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }

                                }
                            });
                }else{
                    alertDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"등록 되었습니다", Toast.LENGTH_SHORT).show();
                    activity.finish();
                    Intent intent=new Intent(DiaryRegisterActivity.this,DiaryActivity.class);
                    startActivity(intent);
                    finish();
                }





            }


        }
    }

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
                int count=0;
                exercise.clear();
                exercise.add("직접입력");
                JSONObject object;
                while (count<jsonArray.length()){
                    object=jsonArray.getJSONObject(count);
                    //System.out.println(object);

                    exercise.add(object.getString("name"));
                    count++;
                }
                arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        exercise);
                spinner.setAdapter(arrayAdapter);





            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}
