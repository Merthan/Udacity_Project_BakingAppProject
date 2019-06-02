package example.bakingappproject;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FetchRecipes extends AsyncTask<Void,Void,Void> {


    private AsyncTaskHelperInterface athi;
    private List<Recipe> recipes;

    FetchRecipes(final AsyncTaskHelperInterface athi){
        this.athi=athi;
    }
    @Override
    protected Void doInBackground(Void... myAdapters) {
        recipes = new ArrayList<>();
        Uri uri = Uri.parse(athi.URL);


        try {

          JSONArray recipeJsonArray = new JSONArray(getResponseFromUrl(new URL(uri.toString())));

          for(int i=0;i<recipeJsonArray.length();i++){
              recipes.add(new Recipe(recipeJsonArray.getJSONObject(i)));
          }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        athi.onFinished(recipes);
    }

    public String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
        String s="";
        try{

            Scanner scanner=new Scanner(urlConnection.getInputStream());
            while (scanner.hasNextLine()){
                s+=scanner.nextLine();//i know this isnt optimal, but it works
            }
        }finally {
            urlConnection.disconnect();
        }
        return s;
    }
}
