package example.bakingappproject;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private double servings;
    private String image;
    //Lists of Json array
    private List<Ingredient> ingredients;
    private List<Step> steps;


    public void setupRecipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, double servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", servings=" + servings +
                ", image='" + image + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }

    public Recipe(JSONObject recipeJson){



        try {
            //Getting the ingredients
            List<Ingredient> ingredients = new ArrayList<>();
            JSONArray ingredientsJsonArray = recipeJson.getJSONArray("ingredients");

            for(int j=0;j<ingredientsJsonArray.length();j++){
                JSONObject ingredientJson=ingredientsJsonArray.getJSONObject(j);
                ingredients.add(new Ingredient(ingredientJson.getDouble("quantity"),ingredientJson.getString("measure"),ingredientJson.getString("ingredient")));
            }
            //Getting the steps
            List<Step> steps = new ArrayList<>();
            JSONArray stepsJsonArray = recipeJson.getJSONArray("steps");

            for(int k=0;k<stepsJsonArray.length();k++){
                JSONObject stepsJson=stepsJsonArray.getJSONObject(k);
                steps.add(new Step(stepsJson.getInt("id"),stepsJson.getString("shortDescription"),stepsJson.getString("description"),stepsJson.getString("videoURL"),stepsJson.getString("thumbnailURL")));
            }


            setupRecipe(recipeJson.getInt("id"),recipeJson.getString("name"),ingredients,steps,recipeJson.getDouble("servings"),recipeJson.getString("image"));// int id, String name, List<Ingredient> ingredients, List<Step> steps, double servings, String image) {
            Log.d(RECIPE_LOG,"added: " + this.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(RECIPE_LOG,"ERROR: " + e.getMessage());
        }

    }
    public static final String RECIPE_LOG="RecipeLog";


    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public double getServings() {
        return servings;
    }

    public void setServings(double servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
