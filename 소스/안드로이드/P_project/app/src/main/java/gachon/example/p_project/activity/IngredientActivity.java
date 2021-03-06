package gachon.example.p_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import gachon.example.p_project.R;
import gachon.example.p_project.fragment.FruitFragment;
import gachon.example.p_project.fragment.MeatFragment;
import gachon.example.p_project.fragment.NutDairyFragment;
import gachon.example.p_project.fragment.SauceFragment;
import gachon.example.p_project.fragment.SeafoodFragment;
import gachon.example.p_project.fragment.VegetableFragment;

public class IngredientActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager pager;
    Button action_home;
    FragmentManager manager;
    ArrayList<Fragment> fraglist=new ArrayList<>();
    ArrayList<String> titlelist=new ArrayList<>();

    MeatFragment sub1=new MeatFragment();
    SeafoodFragment sub2=new SeafoodFragment();
    VegetableFragment sub3=new VegetableFragment();
    FruitFragment sub4=new FruitFragment();
    SauceFragment sub5=new  SauceFragment();
    NutDairyFragment sub6=new NutDairyFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs2);


        setCustomActionbar();
        action_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent action_home_i = new Intent(IngredientActivity.this, HomeActivity.class);
                action_home_i.putExtra("home", "home");
                startActivity(action_home_i);


            }
        });
        titlelist.add("??????");
        titlelist.add("?????????");
        titlelist.add("??????");
        titlelist.add("??????");
        titlelist.add("??????/?????????");
        titlelist.add("?????????/?????????");
        fraglist.add(sub1);
        fraglist.add(sub2);
        fraglist.add(sub3);
        fraglist.add(sub4);
        fraglist.add(sub5);
        fraglist.add(sub6);
         /*tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("??????")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("?????????")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("??????")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("??????")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("??????/?????????")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("?????????/?????????")));*/
        pager = (ViewPager) findViewById(R.id.pager2);
        manager = getSupportFragmentManager();
        pageradapter adapter = new pageradapter(manager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);


    }
  /*private View createTabView(String tabname){
        View tabView=LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab,null);
        TextView txt_name = (TextView) tabView.findViewById(R.id.txt_name);

        txt_name.setText(tabname);
        return tabView;
    }*/

    public void setCustomActionbar() {
        ActionBar actionBar = getSupportActionBar();

        // Custom Actionbar??? ???????????? ?????? CustomEnabled??? true ????????? ?????? ?????? ?????? false ?????????
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //????????? ???????????? ??? ??????????????? ????????? ???????????????.
        actionBar.setDisplayShowTitleEnabled(false);        //???????????? ???????????? ????????? ??????????????? ???????????????.
        actionBar.setDisplayShowHomeEnabled(false);            //??? ???????????? ?????????????????????.


        //layout??? ????????? ?????? actionbar??? ????????? ????????????.

        View actionbarview = LayoutInflater.from(this).inflate(R.layout.actionbarlayout,null);
        actionBar.setCustomView(actionbarview);
        action_home=(Button)actionbarview.findViewById(R.id.home_btn);

        //????????? ?????? ?????? ?????????
        Toolbar parent = (Toolbar)actionbarview.getParent();
        parent.setContentInsetsAbsolute(0,0);

        ActionBar.LayoutParams params=new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(actionbarview,params);


    }
    class pageradapter extends FragmentPagerAdapter {

        public pageradapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fraglist.get(i);
        }

        @Override
        public int getCount() {
            return fraglist.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titlelist.get(position);
        }

    }

}
