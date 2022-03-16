package gachon.example.p_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import gachon.example.p_project.R;
import gachon.example.p_project.activity.GroupChartActivity;
import gachon.example.p_project.groupitem;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder>{
    Context context;
    ArrayList<groupitem> groupitems=new ArrayList<groupitem>();
    int month= CalendarDay.today().getMonth()+1;
    int year=CalendarDay.today().getYear();



    public GroupAdapter(ArrayList<groupitem> groupitems){
        this.groupitems=groupitems;

    }



    // oncreateviewholder 에서 넘겨준 뷰를 받아서 아이템을 참조한다
    public class ViewHolder extends  RecyclerView.ViewHolder {

        TextView group_name,group_kcal,group_time;
        Button chart;

        public ViewHolder(View view) {
            super(view);
        group_name=(TextView)view.findViewById(R.id.group_name);
        group_kcal=(TextView)view.findViewById(R.id.group_kcal);
        group_time=(TextView)view.findViewById(R.id.group_time);
        chart=(Button)view.findViewById(R.id.chart);

        }


    }
    @NonNull
    @Override
    // 리사이클러뷰의 행의 레이아웃을 정하는 함수
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.groupadapter, viewGroup, false);
        context=viewGroup.getContext();
        return new ViewHolder(view);
    }
    // viewholder 에서 참조한 컴퓨넌트들에 설정해주는곳 position은 리스트뷰 인덱스
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final ViewHolder myviewHolder=(ViewHolder) viewHolder;
        final int position=i;

        myviewHolder.group_name.setText("이름 : "+groupitems.get(i).getName());
        myviewHolder.group_kcal.setText("이번달 총 소모 칼로리(kcal) : "+groupitems.get(i).getTotalkcal());
        myviewHolder.group_time.setText("이번달 총 운동시간(분) : "+groupitems.get(i).getTotaltime());
        myviewHolder.chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, GroupChartActivity.class);
                intent.putExtra("userid",groupitems.get(i).getName());
                intent.putExtra("month",month);
                intent.putExtra("year",year);
                context.startActivity(intent);

            }
        });









    }


    //리사이클러뷰 행개수 리턴
    @Override
    public int getItemCount() {
        return groupitems.size();
    }
}