package gachon.example.p_project.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gachon.example.p_project.IngredientDialog;
import gachon.example.p_project.R;
import gachon.example.p_project.activity.IngredientResultActivity;
import gachon.example.p_project.adapter.IngredientAdapter;
import gachon.example.p_project.ingredientitem;

public class MeatFragment extends Fragment {
    Context context;
    Button ingredient_selected,ingredient_result;
    GridLayoutManager mGridLayoutManager;
    RecyclerView recyclerView;
    ArrayList<ingredientitem> ingredientitems;
    gachon.example.p_project.session session;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View v=inflater.inflate(R.layout.vegetablefragment, container, false);
        context=container.getContext();
        session=(gachon.example.p_project.session)context.getApplicationContext();
        ingredientitems=new ArrayList<ingredientitem>();

        ingredientitems=new ArrayList<ingredientitem>();

        ingredientitem v1=new ingredientitem(R.drawable.m1,"소고기");
        ingredientitem v2=new ingredientitem(R.drawable.m2,"차돌박이");
        ingredientitem v3=new ingredientitem(R.drawable.m3,"안심");
        ingredientitem v4=new ingredientitem(R.drawable.m4,"등심");
        ingredientitem v5=new ingredientitem(R.drawable.m5,"사골");
        ingredientitem v6=new ingredientitem(R.drawable.m6,"돼지고기");
        ingredientitem v7=new ingredientitem(R.drawable.m7,"삼겹살");
        ingredientitem v8=new ingredientitem(R.drawable.m8,"수육");
        ingredientitem v9=new ingredientitem(R.drawable.m9,"돼지껍데기");
        ingredientitem v10=new ingredientitem(R.drawable.m10,"목심");
        ingredientitem v11=new ingredientitem(R.drawable.m11,"닭고기");

        ingredientitem v13=new ingredientitem(R.drawable.m13,"계란");
        ingredientitem v14=new ingredientitem(R.drawable.m14,"훈제오리");



        ingredientitems.add(v1);
        ingredientitems.add(v2);
        ingredientitems.add(v3);
        ingredientitems.add(v4);
        ingredientitems.add(v5);
        ingredientitems.add(v6);
        ingredientitems.add(v7);
        ingredientitems.add(v8);
        ingredientitems.add(v9);
        ingredientitems.add(v10);
        ingredientitems.add(v11);

        ingredientitems.add(v13);
        ingredientitems.add(v14);




        mGridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView=(RecyclerView)v.findViewById(R.id.vegetable_gridview);
        recyclerView.setLayoutManager(mGridLayoutManager);
        IngredientAdapter adapter=new IngredientAdapter(context,ingredientitems);
        recyclerView.setAdapter(adapter);

        ingredient_selected=(Button)v.findViewById(R.id.ingredient_selectedlist);
        ingredient_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IngredientDialog ingredientDialog=new IngredientDialog(context);
                ingredientDialog.callFunction();

            }
        });
        ingredient_result=(Button)v.findViewById(R.id.ingredient_result);
        ingredient_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session.selected_ingredients.size()==0){
                    Toast toast = Toast.makeText(context, "선택한 재료가 없습니다 ", Toast.LENGTH_SHORT);
                    toast.show();
                }

                else{

                    Intent selected_ingredient_result_i=new Intent(context, IngredientResultActivity.class);
                    startActivity(selected_ingredient_result_i);}
            }

        });






        return v;
    }
}