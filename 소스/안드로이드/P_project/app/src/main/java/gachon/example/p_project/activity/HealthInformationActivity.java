package gachon.example.p_project.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import androidx.appcompat.app.AppCompatActivity;
import gachon.example.p_project.R;
import gachon.example.p_project.session;

public class HealthInformationActivity extends AppCompatActivity {
    Button RegistButton;
    String userid,age,height,weight,sex;
    session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_information);

        session=(session)getApplicationContext();
        userid=session.getUserid();

        RegistButton = (Button)findViewById(R.id.registbutton);
        RegistButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("서버연결 시도...");
                new JSONTask().execute("http://"+getString(R.string.ip)+":65000/users/health");

            }
        });
    }

    class JSONTask extends AsyncTask<String, String, String> {
        @Override
        public String doInBackground(String... urls) {

            age = ((EditText) (findViewById(R.id.age))).getText().toString();
            height = ((EditText) (findViewById(R.id.height))).getText().toString();
            weight = ((EditText) (findViewById(R.id.weight))).getText().toString();
            if(((EditText) (findViewById(R.id.sex))).getText().toString().equals("남자")){
                sex="m";
            }else if((((EditText) (findViewById(R.id.sex))).getText().toString().equals("여자"))) {
                sex="f";
            }else{
                sex="0";
            }


            HttpURLConnection con = null;
            BufferedReader reader = null;
            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("age", age);
                jsonObject.accumulate("height", height);
                jsonObject.accumulate("weight", weight);
                jsonObject.accumulate("sex", sex);
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


            if(test.equals("ok"))
            {
                Toast toast = Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("userid",userid);
                intent.putExtra("home","home");
                intent.putExtra("height",height);
                intent.putExtra("weight",weight);
                intent.putExtra("age",age);
                intent.putExtra("sex",sex);

                startActivity(intent);
                finish();

            }

            else{
                Toast toast = Toast.makeText(getApplicationContext(), "등록 실패", Toast.LENGTH_SHORT);
                toast.show();}

        }
    }

}
