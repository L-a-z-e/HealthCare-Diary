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

public class GroupMakeActivity extends AppCompatActivity {
    EditText group_name,group_passwd1,group_passwd2;
    Button make;
    String name,passwd1,passwd2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_make);
        group_name=(EditText)findViewById(R.id.group_name);
        group_passwd1=(EditText)findViewById(R.id.password1);
        group_passwd2=(EditText)findViewById(R.id.password2);
        make=(Button)findViewById(R.id.make);
        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTask().execute("http://"+getString(R.string.ip)+":65000/users/groupmake");
            }
        });
    }

    class JSONTask extends AsyncTask<String, String, String> {
        @Override
        public String doInBackground(String... urls) {
            name = ((EditText) (findViewById(R.id.group_name))).getText().toString();

            passwd1 = ((EditText) (findViewById(R.id.password1))).getText().toString();
            passwd2 = ((EditText) (findViewById(R.id.password2))).getText().toString();


            HttpURLConnection con = null;
            BufferedReader reader = null;
            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("name", name);

                jsonObject.accumulate("password1", passwd1);
                jsonObject.accumulate("password2", passwd2);
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
                Toast toast = Toast.makeText(GroupMakeActivity.this, "그룹이 생성 되었습니다.", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(GroupMakeActivity.this, GroupRegisterActivity.class);
                intent.putExtra("groupname",name);
                startActivity(intent);
                finish();

            }
            else if(test.equals("error")){
                Toast toast = Toast.makeText(GroupMakeActivity.this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT);
                toast.show();
            }


        }
    }
}
