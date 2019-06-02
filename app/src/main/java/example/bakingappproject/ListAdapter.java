package example.bakingappproject;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Recipe> {

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<Recipe> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_textview_layout_item, null);
        }

        Recipe recipe = getItem(position);

        TextView textView=v.findViewById(R.id.recipeItemTextView);
        ImageView imageView= v.findViewById(R.id.recipeItemImageView);

        textView.setText(recipe.getName());

        Picasso.with(v.getContext()).load(Uri.parse(recipe.getImage())).into(imageView);

        return v;
    }

}
