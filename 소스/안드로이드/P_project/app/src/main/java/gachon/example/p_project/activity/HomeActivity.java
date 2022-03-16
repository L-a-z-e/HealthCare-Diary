package gachon.example.p_project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import gachon.example.p_project.R;
import gachon.example.p_project.session;

public class HomeActivity extends AppCompatActivity {

    Button exerciser_menu,food_menu,btnOpenDrawer,btnCloseDrawer;
    DrawerLayout drawerLayout;
    View drawerView,header;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView nav_text,nav_userid;
    LayoutInflater inflater;
    MenuItem menuItem;
    private String userid;
    public static Activity homeactivity;
    ActionBarDrawerToggle actionBarDrawerToggle;
    String height,weight,age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeactivity=HomeActivity.this;
        if(getIntent().getStringExtra("home")==null){
            final Intent loading_i=new Intent(HomeActivity.this,LoadingActivity.class);
            startActivity(loading_i);}
        final session session=(session)getApplicationContext();
        session.setUserid("로그인해주세요");



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View nav_header_view = navigationView.getHeaderView(0);
        nav_text= (TextView) nav_header_view.findViewById(R.id.nav_text);
        nav_userid = (TextView) nav_header_view.findViewById(R.id.nav_userid);
        MenuInflater inflater=getMenuInflater();

        if(!(((getIntent().getStringExtra("userid")))==null)){
            nav_text.setText("접속자 : ");
            nav_userid.setText(getIntent().getStringExtra("userid"));
            height=getIntent().getStringExtra("height");
            weight=getIntent().getStringExtra("weight");
            age=getIntent().getStringExtra("age");
            int height_n,weight_n,age_n;
            height_n=Integer.parseInt(height);
            weight_n=Integer.parseInt(weight);
            age_n=Integer.parseInt(age);
            session.setAge(age_n);
            session.setHeight(height_n);
            session.setWeight(weight_n);
            session.setSex(getIntent().getStringExtra("sex"));
            session.setUserid(getIntent().getStringExtra("userid"));

            navigationView.getMenu().findItem(R.id.item1).setTitle("로그아웃");
            navigationView.getMenu().findItem(R.id.item2).setTitle("회원탈퇴");
            navigationView.getMenu().findItem(R.id.item3).setTitle("신체정보 등록");
            navigationView.getMenu().findItem(R.id.item4).setTitle("나의 그룹");
        }
        userid=session.getUserid();
        exerciser_menu=(Button)findViewById(R.id.exercise_menu);
        food_menu=(Button)findViewById(R.id.food_menu);
        food_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent food_i=new Intent(HomeActivity.this,FoodActivity.class);

                startActivity(food_i);
            }
        });
      exerciser_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ingredient_i=new Intent(HomeActivity.this,HealthActivity.class);
                startActivity(ingredient_i);
            }

        });





        InitializeLayout();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.item1:
                        if(menuItem.getTitle().equals("로그인")){

                            Intent login_i=new Intent(HomeActivity.this,LoginActivity.class);
                            startActivity(login_i);
                        }

                        else{
                            Toast.makeText(getApplicationContext(), "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
                            nav_text.setText("");
                            nav_userid.setText("로그인해주세요");
                            menuItem.setTitle("로그인");
                            session.setUserid("로그인해주세요");
                            navigationView.getMenu().findItem(R.id.item2).setTitle("회원가입");
                            navigationView.getMenu().findItem(R.id.item3).setTitle("");
                            navigationView.getMenu().findItem(R.id.item4).setTitle("");
                        }

                        break;
                    case R.id.item2:
                        if(menuItem.getTitle().equals("회원가입")){
                            Intent register_i=new Intent(HomeActivity.this,RegisterActivity.class);
                            startActivity(register_i);}
                        else{
                            Intent withdrawal_i=new Intent(HomeActivity.this,WithdrawalActivity.class);
                            startActivity(withdrawal_i);}

                        break;
                    case R.id.item3:
                        if(menuItem.getTitle().equals("신체정보 등록")){
                                Intent intent=new Intent(HomeActivity.this,HealthInformationActivity.class);
                                startActivity(intent);
                           }
                        break;
                    case R.id.item4:
                        if(menuItem.getTitle().equals("나의 그룹")){

                            new JSONTask().execute("http://"+getString(R.string.ip)+":65000/users/usergroup");


                        }
                        break;

                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });



    }

    public void InitializeLayout()
    {
        //toolBar를 통해 App Bar 생성
         toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //App Bar의 좌측 영영에 Drawer를 Open 하기 위한 Incon 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_format_list_bulleted_black_24dp);
        getSupportActionBar().setTitle("");

        DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

         actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawLayout.addDrawerListener(actionBarDrawerToggle);
    }



    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    class JSONTask extends AsyncTask<String, String, String> {
        @Override
        public String doInBackground(String... urls) {



            HttpURLConnection con = null;
            BufferedReader reader = null;
            try {


                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("userid", userid);                
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
            //System.out.println("넘어온데이터 :" + s);


                System.out.println(s);
                String test=s;
                //System.out.println(jsonArray.length());

                if(s.equals("no")){
                    Toast toast = Toast.makeText(getApplicationContext(), "등록된 그룹이 없습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), GroupRegisterActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
                    intent.putExtra("groupname",test);
                    startActivity(intent);
                }









        }
    }
}