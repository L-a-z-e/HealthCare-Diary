package gachon.example.p_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import gachon.example.p_project.R;
import gachon.example.p_project.fragment.BackFragment;
import gachon.example.p_project.fragment.ChestFragment;
import gachon.example.p_project.fragment.CoreFragment;
import gachon.example.p_project.fragment.LegFragment;
import gachon.example.p_project.fragment.ShoulderFragment;

public class WeightTrainingActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager pager;
    ArrayList<Fragment> fraglist=new ArrayList<>();
    ArrayList<String> titlelist=new ArrayList<>();
    Button action_home;
    FragmentManager manager;

    ChestFragment sub1=new ChestFragment();
    BackFragment sub2=new BackFragment();
    ShoulderFragment sub3=new ShoulderFragment();
    LegFragment sub4=new LegFragment();
    CoreFragment sub5=new CoreFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);


        tabLayout.addTab(tabLayout.newTab().setText("가슴"));
        tabLayout.addTab(tabLayout.newTab().setText("등"));
        tabLayout.addTab(tabLayout.newTab().setText("어깨"));
        tabLayout.addTab(tabLayout.newTab().setText("하체"));
        tabLayout.addTab(tabLayout.newTab().setText("코어"));


        manager = getSupportFragmentManager();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, sub1).commit();


        setCustomActionbar();
        action_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent action_home_i = new Intent(WeightTrainingActivity.this, HomeActivity.class);
                action_home_i.putExtra("home", "home");
                startActivity(action_home_i);


            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition(); //탭 버튼의 index 참조

                Fragment selected = null;

                /* 선택된 탭에 따라 화면에 보이는 프래그먼트 변경 */
                switch (position) {
                    case 0:
                        selected = sub1;
                        break;
                    case 1:
                        selected = sub2;
                        break;
                    case 2:
                        selected = sub3;
                        break;
                    case 3:
                        selected = sub4;
                        break;
                    case 4:
                        selected = sub5;
                        break;
                    default:
                        break;
                }
                WeightTrainingActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }


    public void setCustomActionbar() {
        ActionBar actionBar = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.


        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.

        View actionbarview = LayoutInflater.from(this).inflate(R.layout.actionbarlayout,null);
        actionBar.setCustomView(actionbarview);
        action_home=(Button)actionbarview.findViewById(R.id.home_btn);

        //액션바 양쪽 공백 없애기
        Toolbar parent = (Toolbar)actionbarview.getParent();
        parent.setContentInsetsAbsolute(0,0);

        ActionBar.LayoutParams params=new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(actionbarview,params);


    }







}