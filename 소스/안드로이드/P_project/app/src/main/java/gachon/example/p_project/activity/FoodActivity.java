package gachon.example.p_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import gachon.example.p_project.R;

public class FoodActivity extends AppCompatActivity {
Button recipe_btn,ingredient_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        recipe_btn=(Button)findViewById(R.id.recipe_btn);
        recipe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recipe_i=new Intent(FoodActivity.this,RecipeActivity.class);

                startActivity(recipe_i);
            }
        });

        ingredient_btn=(Button)findViewById(R.id.ingredient_btn);
        ingredient_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recipe_i=new Intent(FoodActivity.this,IngredientActivity.class);

                startActivity(recipe_i);
                
            }
        });





    }
}
