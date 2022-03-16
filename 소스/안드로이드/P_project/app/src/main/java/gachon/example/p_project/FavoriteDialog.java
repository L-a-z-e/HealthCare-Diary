package gachon.example.p_project;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import gachon.example.p_project.fragment.FavoriteFragment;

public class FavoriteDialog {
    private Context context;
    String userid;
    String menunum;
    String favorite;
    String favorite2;




    public FavoriteDialog(Context context, String userid, String menunum) {

        this.context = context;
        this.menunum=menunum;
        this.userid=userid;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.favoritedialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView favorite_text = (TextView) dlg.findViewById(R.id.favorite_text);
        final Button favorite_ok = (Button) dlg.findViewById(R.id.favorite_ok);
        final Button favorite_cancle = (Button) dlg.findViewById(R.id.favorite_cancle);


        favorite_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // '확인' 버튼 클릭시 메인 액티비티에서 설정한 main_label에
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                // 즐겨찾기 삭제기능

                System.out.println("즐겨찾기 서버연결 시도...");
                new JSONTask1().execute("http://"+context.getString(R.string.ip)+":65000/userrecipe/favorite?userid="+userid);


                dlg.dismiss();


            }
        });
        favorite_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dlg.dismiss();
            }
        });
    }
    class JSONTask1 extends AsyncTask<String, String, String> {
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
                favorite2=favorite.replace(menunum+",","");
                System.out.println(menunum);
                System.out.println("즐겨찾기 서버연결 시도...");
                new JSONTask2().execute("http://"+context.getString(R.string.ip)+":65000/userrecipe/favorite");

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }// JsonTask()
    class JSONTask2 extends AsyncTask<String, String, String> {
        @Override
        public String doInBackground(String... urls) {
            HttpURLConnection con = null;
            BufferedReader reader = null;
            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("favorite", favorite2);
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
                Toast toast = Toast.makeText(context, "즐겨찾기에 삭제되었습니다", Toast.LENGTH_SHORT);
                toast.show();
                FavoriteFragment.btnperform();

            }
            else
            {
                Toast toast = Toast.makeText(context, "즐겨찾기 삭제에 실패했습니다", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }

}
