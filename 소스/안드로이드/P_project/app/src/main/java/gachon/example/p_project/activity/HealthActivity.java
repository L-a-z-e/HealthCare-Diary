package gachon.example.p_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import gachon.example.p_project.LoginDialog;
import gachon.example.p_project.R;
import gachon.example.p_project.session;

public class HealthActivity extends AppCompatActivity {
Button weighttraining_btn,cardio_btn,weight_diary_btn,body_history_btn;
session session;
String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        weighttraining_btn=(Button)findViewById(R.id.weighttraining_btn);
         cardio_btn=(Button)findViewById(R.id.cardio_btn);
         weight_diary_btn=(Button)findViewById(R.id.weight_diary_btn);
        body_history_btn=(Button)findViewById(R.id.body_history_btn);
        session=(session)getApplicationContext();
        userid=session.getUserid();

         weighttraining_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i=new Intent(HealthActivity.this,WeightTrainingActivity.class);
                 startActivity(i);
             }
         });

         cardio_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i=new Intent(HealthActivity.this,CardioActivity.class);
                 startActivity(i);
             }
         });
        weight_diary_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if(userid.equals("로그인해주세요")) {
                     LoginDialog customDialog=new LoginDialog(HealthActivity.this);
                     customDialog.callFunction();

                 }else{
                     if(session.getAge()==0){
                     HealthDialog customDialog=new HealthDialog(HealthActivity.this);
                     customDialog.callFunction();}
                     else{
                 Intent i=new Intent(HealthActivity.this,DiaryActivity.class);
                 startActivity(i);}}
             }
         });
        body_history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userid.equals("로그인해주세요")) {
                    LoginDialog customDialog=new LoginDialog(HealthActivity.this);
                    customDialog.callFunction();

                }else{


                        Intent i=new Intent(HealthActivity.this,BodyHistoryActivity.class);
                        startActivity(i);}}

        });


    }
}
