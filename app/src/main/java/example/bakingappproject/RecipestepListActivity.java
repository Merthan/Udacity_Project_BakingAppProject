package example.bakingappproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import example.bakingappproject.widget_related.ListAppWidgetProvider;

import static example.bakingappproject.SelectRecipeActivity.EXTRA_POSITION;

/**
 * An activity representing a list of Recipe steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipestepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipestepListActivity extends AppCompatActivity {


        //TODO: REPLACE THE DUMMYDATA WITH REAL DATA;;;Ö;:;:;:;:;:;:;:;:;:;:;:;:

    private void showIngredientsDialog(List<Ingredient> ingredients){


        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.alertdialog_ingredients_text);

        String message="";
        for (Ingredient i : ingredients) {
            message+="\n"+i.getIngredient()+" "+i.getQuantity()+i.getMeasure()+"\n";
        }
        final String finalMessage =message;

        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             dialog.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.show_in_widget), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                editor.putString(CURRENT_INGREDIENT_LIST,finalMessage);
                editor.apply();
                ListAppWidgetProvider.refreshWidget(RecipestepListActivity.this);
            }
        });

        alertDialogBuilder.show();

    }

public static final String CURRENT_INGREDIENT_LIST="current_ingredient_list";


    private boolean mTwoPane;

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipestep_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        recipe=SelectRecipeActivity.recipes.get(getIntent().getIntExtra(EXTRA_POSITION,0));


        //So it is only run the first time oncreate is called
        if(savedInstanceState==null)
            showIngredientsDialog(recipe.getIngredients());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showIngredientsDialog(recipe.getIngredients());

            }
        });
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.recipestep_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.recipestep_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, recipe.getSteps(), mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final AppCompatActivity mParentActivity;
        private final List<?> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
        public static final String TAG="MyTag";
        SimpleItemRecyclerViewAdapter(AppCompatActivity parent, List<?> items, boolean twoPane) {
            Log.d(TAG, "SimpleItemRecyclerViewAdapter: const");
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d(TAG, "SimpleItemRecyclerViewAdapter: ocvh");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipestep_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Log.d(TAG, "SimpleItemRecyclerViewAdapter: onbind");
            holder.mIdView.setText(""+(position+1));
            if(mValues.get(position) instanceof Step){
                holder.mContentView.setText(((Step)(mValues.get(position))).getShortDescription());

            }
            else if(mValues.get(position) instanceof String){
                holder.mContentView.setText((String)(mValues.get(position)));

            }


            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                /*
                "id": 0,
        "shortDescription": "Recipe Introduction",
        "description": "Recipe Introduction",
        "videoURL": "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdae8_-intro-cheesecake/-intro-cheesecake.mp4",
        "thumbnailURL": ""*/
                @Override
                public void onClick(View view) {

                    if(mValues.get(0) instanceof Step){
                        Step item = (Step) view.getTag();
                        ArrayList<String> extras= new ArrayList<>(Arrays.asList(item.getId()+"",item.getShortDescription(),item.getDescription(),item.getVideoURL(),item.getThumbnailURL()));

                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putStringArrayList(RecipestepDetailFragment.ARG_ITEM_LIST, extras);
                            RecipestepDetailFragment fragment = new RecipestepDetailFragment();
                            fragment.setArguments(arguments);
                            mParentActivity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.recipestep_detail_container, fragment)
                                    .commit();
                        } else {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, RecipestepDetailActivity.class);
                            intent.putStringArrayListExtra(RecipestepDetailFragment.ARG_ITEM_LIST, extras);

                            context.startActivity(intent);
                        }

                    }else if(mValues.get(0) instanceof String){
                        Intent i=new Intent(mParentActivity,RecipestepListActivity.class);
                        i.putExtra(EXTRA_POSITION,holder.getAdapterPosition());
                        mParentActivity.startActivity(i);

                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "SimpleItemRecyclerViewAdapter: count"+mValues.size());
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
