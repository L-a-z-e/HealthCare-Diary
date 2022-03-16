package gachon.example.p_project.adapter;

import android.content.Context;
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
import gachon.example.p_project.recipeitem;

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {
    ArrayList<recipeitem> recipeitems=new ArrayList<recipeitem>();
Context context;

    public InformationAdapter(){}
    public InformationAdapter(ArrayList<recipeitem> recipeitems){
        this.recipeitems=recipeitems;
    }




    public class ViewHolder extends  RecyclerView.ViewHolder {
     ImageView orederimage;
     TextView recipeorder;

        public ViewHolder(View view) {
            super(view);
                orederimage=(ImageView)view.findViewById(R.id.orderimage);
                recipeorder=(TextView)view.findViewById(R.id.recipeorder);


        }



    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.informationadapter, viewGroup, false);
        context=viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

     ViewHolder myviewholder=(ViewHolder) viewHolder;
    myviewholder.recipeorder.setText(recipeitems.get(position).getOrder());
        Picasso.with(context)
                .load("http://"+context.getString(R.string.ip)+":65000"+recipeitems.get(position).getImage())
                .resize(120,120)
                .into(myviewholder.orederimage);

    }

    @Override
    public int getItemCount() {
        return recipeitems.size();
    }
}
