package gachon.example.p_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import gachon.example.p_project.FavoriteDialog;
import gachon.example.p_project.R;
import gachon.example.p_project.activity.RecipeInformationActivity;
import gachon.example.p_project.categoryitem;
import gachon.example.p_project.session;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    Context context;
    ArrayList<categoryitem> categories=new ArrayList<categoryitem>();


    public FavoriteAdapter(){}
    public FavoriteAdapter(ArrayList<categoryitem> categories){
        this.categories=categories;
    }



    // oncreateviewholder 에서 넘겨준 뷰를 받아서 아이템을 참조한다
    public class ViewHolder extends  RecyclerView.ViewHolder {
        ImageView recipe_img;
        TextView recipe_text1,recipe_text2,recipe_text3;
        Button delete_btn;
        public ViewHolder(View view) {
            super(view);

            recipe_img=(ImageView)view.findViewById(R.id.recipe_img);
            recipe_text1=(TextView)view.findViewById(R.id.recipe_name);
            recipe_text2=(TextView)view.findViewById(R.id.recipe_level);
            recipe_text3=(TextView)view.findViewById(R.id.uploader);
            delete_btn=(Button)view.findViewById(R.id.delete_btn);
        }


    }
    @NonNull
    @Override
    // 리사이클러뷰의 행의 레이아웃을 정하는 함수
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favoritadapter, viewGroup, false);
        context=viewGroup.getContext();
        return new ViewHolder(view);
    }



    // viewholder 에서 참조한 컴퓨넌트들에 설정해주는곳 position은 리스트뷰 인덱스
    @Override
    public void onBindViewHolder(@NonNull final FavoriteAdapter.ViewHolder viewHolder, int i) {
        final ViewHolder myviewHolder=(ViewHolder) viewHolder;
        myviewHolder.recipe_text1.setText(categories.get(i).getMenuname());
        myviewHolder.recipe_text2.setText("조리 난이도  " + categories.get(i).getDifficulty());
        if(categories.get(i).getUploader()!=null){
            myviewHolder.recipe_text3.setText("등록자 : "+categories.get(i).getUploader());
        }
        Picasso.with(context)
                .load("http://"+context.getString(R.string.ip)+":65000"+categories.get(i).getImage())
                .resize(200,250)
                .into(myviewHolder.recipe_img);
        final String menunum=categories.get(i).getMenunumber();
        final String menuname=categories.get(i).getMenuname();
        final String image=categories.get(i).getImage();
        final String recipenum=categories.get(i).getRecipenum();


        myviewHolder.recipe_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recipe_information=new Intent(context, RecipeInformationActivity.class);
                recipe_information.putExtra("menunum",menunum);
                recipe_information.putExtra("image",image);
                recipe_information.putExtra("menuname",menuname);
                recipe_information.putExtra("recipenum",recipenum);
                context.startActivity(recipe_information);
            }
        });
           final session session=(session)context.getApplicationContext();
        myviewHolder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userid=session.getUserid();
               FavoriteDialog favoriteDialog=new FavoriteDialog(context,userid,menunum);
               favoriteDialog.callFunction();
            }
        });




    }


    //리사이클러뷰 행개수 리턴
    @Override
    public int getItemCount() {
        return categories.size();
    }
}