package com.rosen.jambo.views.articles;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rosen.jambo.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.fail;

/**
 * Created by Derick W on 05,April,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@RunWith(AndroidJUnit4.class)
public class ArticleDetailsTest {

    @Rule
    public ActivityTestRule<ArticleDetails> activityDetailRule = new ActivityTestRule<ArticleDetails>(ArticleDetails.class, true, true){
        @Override
        protected Intent getActivityIntent() {
            /* added predefined intent data */
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

            Intent intent = new Intent(targetContext, ArticleDetails.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", "Test");
            bundle.putString("content", "Test");
            bundle.putString("description", "Test");
            bundle.putString("author", "Test");
            bundle.putString("timestamp", "Test");
            bundle.putString("url", "https://google.com");
            bundle.putString("image", "https://www.google.com/search?q=earth&safe=active&rlz=1C5CHFA_enUG799UG799&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiT64iKoLnhAhVhA2MBHVhpDiwQ_AUIDigB&biw=1680&bih=939#");
            intent.putExtra("data", bundle);
            return intent;
        }
    };

    @Test
    public void testArticleDetails(){

        sleepThread(5000);

        onView(withId(R.id.title)).check(matches(withText("Test")));
        onView(withId(R.id.description)).check(matches(withText("Test")));
        onView(withId(R.id.content)).check(matches(withText("Test")));
        onView(withId(R.id.author)).check(matches(withText("Test")));
        onView(withId(R.id.htmlUrl)).check(matches(withText("https://google.com")));
        onView(withId(R.id.publishedat)).check(matches(withText("Test")));
        onView(withId(R.id.profile_image)).check(matches(isDisplayed()));
        onView(withId(R.id.htmlUrl)).perform(click());


        sleepThread(4000);

        try {
            Espresso.pressBack();
//            fail("Should have thrown NoActivityResumedException");
        } catch (NoActivityResumedException expected) {
        }


    }

    @Test
    public void testShareButtonClick(){

        sleepThread(1000);

        onView(withId(R.id.share)).perform(click());

    }

    @Test
    public void testBackPressClick(){

        sleepThread(1000);

        try {
            Espresso.pressBack();
//            fail("Should have thrown NoActivityResumedException");
        } catch (NoActivityResumedException expected) {
        }

    }

    @Test
    public void testCopyArticle(){

        sleepThread(5000);


        onView(withId(R.id.action_copy)).perform(click());

    }

    private void sleepThread(int seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
