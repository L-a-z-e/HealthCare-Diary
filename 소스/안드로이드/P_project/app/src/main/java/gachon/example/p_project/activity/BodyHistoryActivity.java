package gachon.example.p_project.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import gachon.example.p_project.R;
import gachon.example.p_project.adapter.BodyAdapter;
import gachon.example.p_project.bodyitem;

public class BodyHistoryActivity extends AppCompatActivity {
    ViewPager pager;
    Context context;
    ArrayList<bodyitem> bodyitems;
    gachon.example.p_project.session session;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_history);
        pager= (ViewPager)findViewById(R.id.pager);
        context=getApplicationContext();
        bodyitems=new ArrayList<>();
        session=(gachon.example.p_project.session)getApplicationContext();
        userid=session.getUserid();
        //ViewPager에 설정할 Adapter 객체 생성

        //ListView에서 사용하는 Adapter와 같은 역할.

        //다만. ViewPager로 스크롤 될 수 있도록 되어 있다는 것이 다름

        //PagerAdapter를 상속받은 CustomAdapter 객체 생성

        //CustomAdapter에게 LayoutInflater 객체 전달


        new JSONTask().execute("http://"+getString(R.string.ip)+":65000/userrecipe/getbody?userid="+userid+"&day=0000");


    }



    //onClick속성이 지정된 View를 클릭했을때 자동으로 호출되는 메소드

    public void mOnClick(View v){



        int position;



        switch( v.getId() ){

            case R.id.btn_previous://이전버튼 클릭



                position=pager.getCurrentItem();//현재 보여지는 아이템의 위치를 리턴



                //현재 위치(position)에서 -1 을 해서 이전 position으로 변경

                //이전 Item으로 현재의 아이템 변경 설정(가장 처음이면 더이상 이동하지 않음)

                //첫번째 파라미터: 설정할 현재 위치

                //두번째 파라미터: 변경할 때 부드럽게 이동하는가? false면 팍팍 바뀜

                pager.setCurrentItem(position-1,true);



                break;



            case R.id.btn_next://다음버튼 클릭



                position=pager.getCurrentItem();//현재 보여지는 아이템의 위치를 리턴



                //현재 위치(position)에서 +1 을 해서 다음 position으로 변경

                //다음 Item으로 현재의 아이템 변경 설정(가장 마지막이면 더이상 이동하지 않음)

                //첫번째 파라미터: 설정할 현재 위치

                //두번째 파라미터: 변경할 때 부드럽게 이동하는가? false면 팍팍 바뀜

                pager.setCurrentItem(position+1,true);



                break;

        }



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
                bodyitems.clear();
                JSONObject object;
                while (count<jsonArray.length()){
                    object=jsonArray.getJSONObject(count);
                    //System.out.println(object);
                  bodyitem bodyitem=new bodyitem();
                  bodyitem.setImage(object.getString("image"));
                  bodyitem.setYear(object.getInt("year"));
                  bodyitem.setMonth(object.getInt("month"));
                  bodyitem.setDay(object.getInt("day"));
                  bodyitems.add(bodyitem);


                    count++;
                }
                BodyAdapter adapter= new BodyAdapter(getLayoutInflater(),bodyitems,context);
                //ViewPager에 Adapter 설정

                pager.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}




