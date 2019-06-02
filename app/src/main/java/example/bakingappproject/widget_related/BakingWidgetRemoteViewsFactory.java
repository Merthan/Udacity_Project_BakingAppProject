package example.bakingappproject.widget_related;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Binder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import example.bakingappproject.R;
import example.bakingappproject.RecipestepListActivity;

public class BakingWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;


    public BakingWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        preferences= PreferenceManager.getDefaultSharedPreferences(mContext);
    }
    SharedPreferences preferences;
    List<String> ingredientList;
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {


        final long identityToken = Binder.clearCallingIdentity();
       String result=preferences.getString(RecipestepListActivity.CURRENT_INGREDIENT_LIST,mContext.getString(R.string.default_list_text));
       if(result.equals(mContext.getString(R.string.default_list_text))){
           ingredientList=Arrays.asList( result);//Default element (Message only)
       }else{
           ingredientList=Arrays.asList(result.split("\n "));

       }
        Log.d("WIdget displaying:",ingredientList.toString());

        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientList == null ? 0 : ingredientList.size();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION || ingredientList == null) {
            return null;
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.list_widget_list_item);
        rv.setTextViewText(R.id.widgetItemTaskNameLabel, ingredientList.get(position));


        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(ListAppWidgetProvider.EXTRA_INDEX, ingredientList.get(position));//Decided against really using this, as the other way is probably enough as well
        rv.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }



}
