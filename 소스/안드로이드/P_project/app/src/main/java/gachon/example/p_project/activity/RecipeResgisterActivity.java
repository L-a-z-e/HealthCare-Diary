package gachon.example.p_project.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import gachon.example.p_project.R;

public class RecipeResgisterActivity extends AppCompatActivity {
    Button clipboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciperegisterpage);

        clipboard = (Button)findViewById(R.id.clipboard);
        clipboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("복사할 데이터","ps_tr@naver.com");
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getApplicationContext(),"클립보드에 이메일주소를 복사하였습니다.",Toast.LENGTH_SHORT).show();


            }
        });
    }
}