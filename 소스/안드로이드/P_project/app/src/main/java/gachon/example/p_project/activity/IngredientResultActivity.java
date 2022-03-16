package gachon.example.p_project.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gachon.example.p_project.R;
import gachon.example.p_project.adapter.RecipeAdapter;
import gachon.example.p_project.categoryitem;
import gachon.example.p_project.session;

public class IngredientResultActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<categoryitem> categories=new ArrayList<categoryitem>();
    gachon.example.p_project.session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientresult);

         session=(session)getApplicationContext();
         recyclerView=(RecyclerView)findViewById(R.id.ingredient_selected_resultlist);
        RecyclerView.LayoutManager layoutManager;
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(IngredientResultActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        String str = "meterial=";
        for(int i=0;i<session.selected_ingredients.size();i++){
            str=str+session.selected_ingredients.get(i).getName();
            if(i<session.selected_ingredients.size()-1){
                str=str+",";
            }
        }
        System.out.println(str);

     new JSONTask().execute("http://"+getString(R.string.ip)+":65000/userrecipe/meterial?"+str);





    }
    class JSONTask extends AsyncTask<String, String, String> {
        @Override
        public String doInBackground(String... urls) {

            HttpURLConnection con = null;
            BufferedReader reader = null;
            try {

                URL url = new URL(urls[0]);
                System.out.println(url);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                //서버로 보내기위해서 스트림 만듬
                InputStream stream = con.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) buffer.append(line);
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("넘어온데이터 :" + s);
            if(s.equals("empty")){

                Toast toast = Toast.makeText(getApplicationContext(), "검색되는 메뉴가 없습니다", Toast.LENGTH_SHORT);
                toast.show();
            }
            try {
                JSONArray jsonArray = new JSONArray(s);
                System.out.println(jsonArray);

                //System.out.println(jsonArray.length());
                int count=0;
                categories.clear();
                JSONObject object;
                while (count<jsonArray.length()) {
                    object = jsonArray.getJSONObject(count);
                    //System.out.println(object);
                    categoryitem categoryitem = new categoryitem();
                    categoryitem.setMenuname(object.getString("menuname"));
                    categoryitem.setDifficulty(object.getString("difficulty"));
                    categoryitem.setImage(object.getString("image"));
                    categoryitem.setMenunumber(object.getString("menunum"));
                    categoryitem.setRecipenum(object.getString("recipenum"));
                    categories.add(categoryitem);
                    count++;
                }


                RecipeAdapter adapter = new RecipeAdapter(categories);
                recyclerView.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}
