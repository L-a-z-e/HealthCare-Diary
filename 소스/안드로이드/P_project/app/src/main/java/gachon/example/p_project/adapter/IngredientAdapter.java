package gachon.example.p_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import gachon.example.p_project.R;
import gachon.example.p_project.SelectedIngredientItem;
import gachon.example.p_project.ingredientitem;
import gachon.example.p_project.session;

public class IngredientAdapter extends  RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<ingredientitem> ingredientitems;
    gachon.example.p_project.session session;

    public IngredientAdapter(){}
    public IngredientAdapter(Context context, ArrayList<ingredientitem> items){
        this.context=context;
        this.ingredientitems=items;
        session=(session)context.getApplicationContext();
    }




    public class ViewHolder extends  RecyclerView.ViewHolder {
        ImageView img;
        TextView name;

        public ViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.ingredient_img);
            name = (TextView) view.findViewById(R.id.ingredient_name);


        }



    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingredientadapter, viewGroup, false);
        context=viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        ViewHolder myviewholder=(ViewHolder) viewHolder;

        myviewholder.img.setImageResource(ingredientitems.get(position).getImg());
        myviewholder.name.setText(ingredientitems.get(position).getName());
        final SelectedIngredientItem selectedIngredientItem=new SelectedIngredientItem();
        selectedIngredientItem.setImg(ingredientitems.get(position).getImg());
        selectedIngredientItem.setName(ingredientitems.get(position).getName());
        myviewholder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=0;


                session.selected_ingredients.add(selectedIngredientItem);
                if( session.selected_ingredients.size()>1) {

                    for (int i = 0; i < session.selected_ingredients.size()-1; i++) {

                        if (selectedIngredientItem.getName().equals(session.selected_ingredients.get(i).getName())) {
                            Toast toast1= Toast.makeText(context,ingredientitems.get(position).getName()+ " 이(가) 이미 있습니다.", Toast.LENGTH_SHORT);
                            toast1.show();
                            session.selected_ingredients.remove(session.selected_ingredients.size()-1);
                            flag=1;
                            break;
                        }
                    }


                }
                if(flag==0){
                    Toast toast2 = Toast.makeText(context,ingredientitems.get(position).getName()+ " 이(가) 추가되었습니다.", Toast.LENGTH_SHORT);
                    toast2.show();}




            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientitems.size();
    }
}
