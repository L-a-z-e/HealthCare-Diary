package gachon.example.p_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import gachon.example.p_project.R;
import gachon.example.p_project.exerciseitem;

public class CardioAdapter extends RecyclerView.Adapter<CardioAdapter.ViewHolder>{
    Context context;
    ArrayList<exerciseitem> exerciseitems=new ArrayList<exerciseitem>();



    public CardioAdapter(ArrayList<exerciseitem> exerciseitems){
        this.exerciseitems=exerciseitems;
    }



    // oncreateviewholder 에서 넘겨준 뷰를 받아서 아이템을 참조한다
    public class ViewHolder extends  RecyclerView.ViewHolder {
        ImageView exercise_img;
        TextView exerciser_name,kcal;
        EditText weight,runtime;
        Button calc;

        public ViewHolder(View view) {
            super(view);

            exercise_img=(ImageView)view.findViewById(R.id.exercise_img);
            exerciser_name=(TextView)view.findViewById(R.id.exercise_name);
            weight=(EditText)view.findViewById(R.id.kg);
            runtime=(EditText)view.findViewById(R.id.min);
            calc=(Button)view.findViewById(R.id.calc);
            kcal=(TextView)view.findViewById(R.id.kcal);

        }


    }
    @NonNull
    @Override
    // 리사이클러뷰의 행의 레이아웃을 정하는 함수
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardioadapter, viewGroup, false);
        context=viewGroup.getContext();
        return new ViewHolder(view);
    }
    // viewholder 에서 참조한 컴퓨넌트들에 설정해주는곳 position은 리스트뷰 인덱스
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final ViewHolder myviewHolder=(ViewHolder) viewHolder;
        final int position=i;





        Picasso.with(context)
                .load(exerciseitems.get(i).getImgurl())
                .resize(200,250)
                .into(myviewHolder.exercise_img);
        myviewHolder.exerciser_name.setText(exerciseitems.get(i).getName());

        myviewHolder.calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            int kg=Integer.parseInt(myviewHolder.weight.getText().toString());
              int min=Integer.parseInt(myviewHolder.runtime.getText().toString());


                if(position==0){
                    double kcal_n=kg*min*0.07;
                    String kcal_s = String.format("%.2f", kcal_n);
                    myviewHolder.kcal.setText(kcal_s+"kacl");
                }
                if(position==1){
                    double kcal_n=kg*min*0.12;
                    String kcal_s = String.format("%.2f", kcal_n);
                    myviewHolder.kcal.setText(kcal_s+"kacl");
                }
                if(position==2){
                    double kcal_n=kg*min*0.12;
                    String kcal_s = String.format("%.2f", kcal_n);
                    myviewHolder.kcal.setText(kcal_s+"kacl");
                }
                if(position==3){
                    double kcal_n=kg*min*0.14;
                    String kcal_s = String.format("%.2f", kcal_n);
                    myviewHolder.kcal.setText(kcal_s+"kacl");
                }
                if(position==4){
                    double kcal_n=kg*min*0.16;
                    String kcal_s = String.format("%.2f", kcal_n);
                    myviewHolder.kcal.setText(kcal_s+"kacl");
                }
                if(position==5){
                    double kcal_n=kg*min*0.14;
                    String kcal_s = String.format("%.2f", kcal_n);
                    myviewHolder.kcal.setText(kcal_s+"kacl");
                }
                if(position==6){
                    double kcal_n=kg*min*0.16;
                    String kcal_s = String.format("%.2f", kcal_n);
                    myviewHolder.kcal.setText(kcal_s+"kacl");
                }


            }
        });










    }


    //리사이클러뷰 행개수 리턴
    @Override
    public int getItemCount() {
        return exerciseitems.size();
    }
}
