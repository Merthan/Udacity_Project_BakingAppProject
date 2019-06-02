package example.bakingappproject;

import java.util.List;

public interface AsyncTaskHelperInterface {
    public void onFinished(List<Recipe> recipes);
    public static final String URL="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
}
