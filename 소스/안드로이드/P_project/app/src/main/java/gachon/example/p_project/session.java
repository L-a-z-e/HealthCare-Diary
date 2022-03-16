package gachon.example.p_project;

import android.app.Application;

import java.util.ArrayList;

public class session extends Application {
    private String userid,sex;




    private int height,weight,age;
    private double BMR;


    public double getBMR(){
        if(sex.equals("m")){
            BMR=10*weight+6.25*height-5*age+5;
        }else{
            BMR=10*weight+6.25*height-5*age-161;

        }
        return BMR;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }





    public ArrayList<SelectedIngredientItem> selected_ingredients=new ArrayList<>();

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


}
