package gachon.example.p_project;

public class categoryitem {
    private String menuname;

    private String difficulty;
    private String image;
    private String menunumber;
    private String recipenum;
    private String uploader;

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getRecipenum() {
        return recipenum;
    }

    public void setRecipenum(String recipenum) {
        this.recipenum = recipenum;
    }

    public String getMenunumber() {
        return menunumber;
    }

    public void setMenunumber(String menunumber) {
        this.menunumber = menunumber;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }



    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
