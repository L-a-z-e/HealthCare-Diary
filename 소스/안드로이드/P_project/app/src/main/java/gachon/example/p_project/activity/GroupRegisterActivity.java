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

public class GroupRegisterActivity extends AppCompatActivity {
    EditText group_name,group_passwd;
    Button register,make;
    String name,passwd,userid;
    session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_register);
        group_name=(EditText)findViewById(R.id.group_name);
        group_passwd=(EditText)findViewById(R.id.group_passwd);

        register=(Button)findViewById(R.id.group_register);
        make=(Button)findViewById(R.id.group_make);
        session=(session)getApplicationContext();
        userid=session.getUserid();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTask().execute("http://"+getString(R.string.ip)+":65000/users/groupregister");
            }
        });

        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GroupRegisterActivity.this,GroupMakeActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }
    class JSONTask extends AsyncTask<String, String, String> {
        @Override
        public String doInBackground(String... urls) {
            name= ((EditText) (findViewById(R.id.group_name))).getText().toString();
            passwd = ((EditText) (findViewById(R.id.group_passwd))).getText().toString();

            HttpURLConnection con = null;
            BufferedReader reader = null;
            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("name",name);
                jsonObject.accumulate("passwd",passwd);
                jsonObject.accumulate("userid",userid);
                URL url = new URL(urls[0]);

                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
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
            //System.out.println("?????????????????? :" + s);

                //System.out.println(jsonArray.length());

                String test=s;
                if(s.equals("ok")){
                    Toast toast = Toast.makeText(GroupRegisterActivity.this, "????????? ?????? ???????????????.", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
                    intent.putExtra("groupname",name);
                    startActivity(intent);
                    finish();
                }
                else if(s.equals("error")){
                    Toast toast = Toast.makeText(GroupRegisterActivity.this, "???????????? ?????? ??????????????? ????????????.", Toast.LENGTH_SHORT);
                    toast.show();
                }









        }
    }
}
