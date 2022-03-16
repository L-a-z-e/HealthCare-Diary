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

public class SeafoodFragment extends Fragment {
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

        ingredientitem v1=new ingredientitem(R.drawable.s1,"고등어");
        ingredientitem v2=new ingredientitem(R.drawable.s2,"오징어");
        ingredientitem v3=new ingredientitem(R.drawable.s3,"연어");
        ingredientitem v4=new ingredientitem(R.drawable.s4,"꽁치");
        ingredientitem v5=new ingredientitem(R.drawable.s5,"동태");
        ingredientitem v6=new ingredientitem(R.drawable.s6,"문어");
        ingredientitem v7=new ingredientitem(R.drawable.s7,"새우");
        ingredientitem v8=new ingredientitem(R.drawable.s8,"조개");
        ingredientitem v9=new ingredientitem(R.drawable.s9,"바지락");
        ingredientitem v10=new ingredientitem(R.drawable.s10,"굴");
        ingredientitem v11=new ingredientitem(R.drawable.s11,"홍합");
        ingredientitem v12=new ingredientitem(R.drawable.s12,"전복");
        ingredientitem v13=new ingredientitem(R.drawable.s13,"골뱅이");

        ingredientitem v15=new ingredientitem(R.drawable.s15,"미역");
        ingredientitem v16=new ingredientitem(R.drawable.s16,"다시마");
        ingredientitem v17=new ingredientitem(R.drawable.s17,"톳");




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
        ingredientitems.add(v11);;
        ingredientitems.add(v12);
        ingredientitems.add(v13);

        ingredientitems.add(v15);
        ingredientitems.add(v16);
        ingredientitems.add(v17);




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