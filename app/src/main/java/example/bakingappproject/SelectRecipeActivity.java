package example.bakingappproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import example.bakingappproject.widget_related.ListAppWidgetProvider;

public class SelectRecipeActivity extends AppCompatActivity {

    RecyclerView listView;

    public static List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipe);
        ListAppWidgetProvider.refreshWidget(this);

        listView = findViewById(R.id.newRecyclerView);
        listView.setLayoutManager(new LinearLayoutManager(SelectRecipeActivity.this));

        ListAppWidgetProvider.refreshWidget(this);

    }
    public static final String EXTRA_RV_STATE="state";

    public static final String EXTRA_POSITION = "EP";
    RecipestepListActivity.SimpleItemRecyclerViewAdapter listAdapter;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_RV_STATE,listView.getLayoutManager().onSaveInstanceState());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(listView!=null)listView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(EXTRA_RV_STATE));
    }

    @Override
    protected void onResume() {
        super.onResume();

        //In onresume so we can disable it when the list is set
        if (recipes != null) {

            String[] arr = new String[recipes.size()];
            for (int i = 0; i < recipes.size(); i++)
                arr[i] = recipes.get(i).getName();
            listAdapter=new RecipestepListActivity.SimpleItemRecyclerViewAdapter(SelectRecipeActivity.this, Arrays.asList(arr), false);
            listView.setAdapter(listAdapter);
            return;
        }
        new FetchRecipes(new AsyncTaskHelperInterface() {
            @Override
            public void onFinished(List<Recipe> recipes) {
                if(listView==null)return;


                SelectRecipeActivity.recipes = recipes;
                String[] arr = new String[recipes.size()];
                for (int i = 0; i < recipes.size(); i++)
                    arr[i] = recipes.get(i).getName();




                //listAdapter = new ListAdapter(SelectRecipeActivity.this,R.layout.custom_textview_layout_item,recipes);

                listAdapter=new RecipestepListActivity.SimpleItemRecyclerViewAdapter(SelectRecipeActivity.this, Arrays.asList(arr), false);
                listView.setAdapter(listAdapter);

            }
        }).execute();
    }

}


