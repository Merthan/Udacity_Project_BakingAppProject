package example.bakingappproject;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {




    @Rule
    public ActivityTestRule<SelectRecipeActivity> activityActivityTestRule = new ActivityTestRule<SelectRecipeActivity>(SelectRecipeActivity.class);

    @Test
    public void flowTest() {
        Intents.init();

        onView(withText("Brownies")).perform(click());
        intended(hasComponent(RecipestepListActivity.class.getName()));

        onView(withText(R.string.alertdialog_ingredients_text)).check(matches(isDisplayed()));

    }
}
