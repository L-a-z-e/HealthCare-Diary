package gachon.example.p_project;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import gachon.example.p_project.activity.HomeActivity;
import gachon.example.p_project.activity.LoginActivity;

public class LoginDialog {
    private Context context;

    HomeActivity activity=(HomeActivity) HomeActivity.homeactivity;

    public LoginDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction() {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.logindialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final TextView dialog_text = (TextView) dlg.findViewById(R.id.dialog_text);
        final Button dialog_login = (Button) dlg.findViewById(R.id.dialog_login);
        final Button dialog_cancle = (Button) dlg.findViewById(R.id.dialog_cancle);

        dialog_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '확인' 버튼 클릭시 메인 액티비티에서 설정한 main_label에
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                Intent login_i = new Intent(context, LoginActivity.class);
                context.startActivity(login_i);
                dlg.dismiss();
                ((Activity)context).finish();
                 activity.finish();


            }
        });
        dialog_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dlg.dismiss();
            }
        });
    }
}
