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

public class RegisterActivity extends AppCompatActivity {

    Button RegistButton;
    String userid, password1,password2, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RegistButton = (Button)findViewById(R.id.registbutton);
        RegistButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("서버연결 시도...");
                new JSONTask().execute("http://"+getString(R.string.ip)+":65000/users/signup");

            }
        });

    }
    class JSONTask extends AsyncTask<String, String, String> {
        @Override
        public String doInBackground(String... urls) {
            userid = ((EditText) (findViewById(R.id.userid))).getText().toString();
            email = ((EditText) (findViewById(R.id.email))).getText().toString();
            password1 = ((EditText) (findViewById(R.id.password1))).getText().toString();
            password2 = ((EditText) (findViewById(R.id.password2))).getText().toString();


            HttpURLConnection con = null;
            BufferedReader reader = null;
            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("email", email);
                jsonObject.accumulate("password1", password1);
                jsonObject.accumulate("password2", password2);
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


            if(test.equals("OK"))
            {
                Toast toast = Toast.makeText(getApplicationContext(), "회원가입 되었습니다.", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
            else if(test.equals("emailerror")){
                Toast toast = Toast.makeText(getApplicationContext(), "이메일을 정확히 입력해주세요", Toast.LENGTH_SHORT);
                toast.show();}

            else if(test.equals("passwderror")){
                Toast toast = Toast.makeText(getApplicationContext(), "비밀번호 확인을 다시해주세요", Toast.LENGTH_SHORT);
                toast.show();}

            else{
            Toast toast = Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT);
            toast.show();}

    }
        }

    }



