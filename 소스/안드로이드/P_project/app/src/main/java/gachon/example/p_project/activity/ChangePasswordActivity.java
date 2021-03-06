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

public class ChangePasswordActivity extends AppCompatActivity {
EditText ch_passwd1,ch_passwd2;
Button changebtn;
String passwd1,passwd2,id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepasswd);
        ch_passwd1=(EditText)findViewById(R.id.ch_passwd1);
        ch_passwd2=(EditText)findViewById(R.id.ch_passwd2);
        changebtn=(Button)findViewById(R.id.changebtn);

        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTask().execute("http://"+getString(R.string.ip)+":65000/users/alterpassword");
            }
        });

    }
    class JSONTask extends AsyncTask<String, String, String> {
        @Override
        public String doInBackground(String... urls) {
            passwd1=ch_passwd1.getText().toString();
            passwd2=ch_passwd2.getText().toString();
            id=getIntent().getStringExtra("id");
            HttpURLConnection con = null;
            BufferedReader reader = null;
            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("passwd1", passwd1);
                jsonObject.accumulate("passwd2", passwd2);
                jsonObject.accumulate("userid", id);
                URL url = new URL(urls[0]);

                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("PUT");
                con.setRequestProperty("Cache-Control", "no-cache");//?????? ??????
                con.setRequestProperty("Content-Type", "application/json");//application JSON ???????????? ??????
                con.setDoInput(true);                         // ???????????? ?????? ?????? ??????
                con.setDoOutput(true);                       // ????????? ?????? ?????? ??????
                con.connect();
                //????????? ?????????????????? ????????? ??????
                OutputStream outStream = con.getOutputStream();
                //????????? ???????????? ??????
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();//????????? ?????????

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


            if(test.equals("SUCCESS"))
            { Toast toast = Toast.makeText(getApplicationContext(), "??????????????? ?????????????????????.", Toast.LENGTH_SHORT);
                toast.show();
                Intent change_i=new Intent(ChangePasswordActivity.this,LoginActivity.class);
                startActivity(change_i);


            }
            else
            {
                ch_passwd2.setText("???????????? ????????? ??????????????????");
            }

        }
    }
}
