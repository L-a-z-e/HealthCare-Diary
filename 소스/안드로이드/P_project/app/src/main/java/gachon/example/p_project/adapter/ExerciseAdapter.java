package gachon.example.p_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import gachon.example.p_project.R;
import gachon.example.p_project.activity.ExerciseInformationActivity;
import gachon.example.p_project.exerciseitem;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder>{
    Context context;
    ArrayList<exerciseitem> exerciseitems=new ArrayList<exerciseitem>();



    public ExerciseAdapter(ArrayList<exerciseitem> exerciseitems){
        this.exerciseitems=exerciseitems;
    }



    // oncreateviewholder 에서 넘겨준 뷰를 받아서 아이템을 참조한다
    public class ViewHolder extends  RecyclerView.ViewHolder {
        ImageView exercise_img;
        TextView exerciser_name;

        public ViewHolder(View view) {
            super(view);

            exercise_img=(ImageView)view.findViewById(R.id.exercise_img);
            exerciser_name=(TextView)view.findViewById(R.id.exercise_name);
        }


    }
    @NonNull
    @Override
    // 리사이클러뷰의 행의 레이아웃을 정하는 함수
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exerciseadapter, viewGroup, false);
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
        myviewHolder.exercise_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ExerciseInformationActivity.class);
                intent.putExtra("explain",exerciseitems.get(position).getExplain());
                intent.putExtra("videourl",exerciseitems.get(position).getVideourl());
                intent.putExtra("name",exerciseitems.get(position).getName());
                intent.putExtra("imgurl",exerciseitems.get(position).getImgurl());
                 context.startActivity(intent);
            }
        });






    }


    //리사이클러뷰 행개수 리턴
    @Override
    public int getItemCount() {
        return exerciseitems.size();
    }
}