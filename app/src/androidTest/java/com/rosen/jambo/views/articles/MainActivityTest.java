package com.rosen.jambo.views.articles;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

import com.rosen.jambo.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");

    @Before
    public void setUp(){
        toggleWifiOn();
    }

    @Before
    public void unlockScreen() {
        final MainActivity activity = mActivityTestRule.getActivity();
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        activity.runOnUiThread(wakeUpDevice);
    }

    @Test
    public void articleListDisplayed() {
        sleepThread(2000);

        onView(withId(R.id.articles_list)).check(matches(isDisplayed()));

        onView(withId(R.id.articles_list)).perform(RecyclerViewActions.scrollToPosition(0));

        onView(withId(R.id.articles_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.title)).check(matches(isDisplayed()));

        onView(withId(R.id.description)).check(matches(isDisplayed()));

        onView(withId(R.id.content)).check(matches(isDisplayed()));

        onView(withId(R.id.share)).check(matches(isDisplayed()));

        onView(withId(R.id.htmlUrl)).check(matches(isDisplayed()));

    }

    @Test
    public void articleOptionTextStyle() {
        sleepThread(2000);
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Font size")).perform(click());
    }

    @Test
    public void articleOptionsTextSize() {
        sleepThread(2000);
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Text style")).perform(click());
        sleepThread(2000);
    }

    @Test
    public void articleOptionsList() {
        sleepThread(2000);
        onView(withId(R.id.action_list)).perform(click());
    }

    @Test
    public void articleOptionsQuilt() {
        sleepThread(2000);
        onView(withId(R.id.action_quilt)).perform(click());
    }

    @Test
    public void testOfflineAccess() {
        toggleWifiOff();
        sleepThread(2000);
        onView(withId(R.id.articles_list)).check(matches(isDisplayed()));
    }

    private void toggleWifiOff(){
        WifiManager wifi = (WifiManager) mActivityTestRule.getActivity().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);
    }

    private void toggleWifiOn(){
        WifiManager wifi = (WifiManager) mActivityTestRule.getActivity().getSystemService(Context.WIFI_SERVICE);
        Objects.requireNonNull(wifi).setWifiEnabled(true);
    }

    @Test
    public void clickOnMenuItems() {
        sleepThread(1000);
        onView(withContentDescription("Open navigation drawer")).perform(click());
        onView(withText("Share")).perform(click());
        onView(withContentDescription("Open navigation drawer")).perform(click());

        ViewInteraction duoOptionView = onView(
                childAtPosition(
                        allOf(withId(R.id.duo_view_menu_options_layout),
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0)),
                        3));
        duoOptionView.perform(scrollTo(), click());

        onView(withContentDescription("Open navigation drawer")).perform(click());
        ViewInteraction currentlocation = onView(
                childAtPosition(
                        allOf(withId(R.id.duo_view_menu_options_layout),
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0)),
                        5));
        currentlocation.perform(scrollTo(), click());

        mActivityTestRule.getActivity().setCurrentLocationLatitude(32.5825);
        mActivityTestRule.getActivity().setCurrentLocationLongitude(0.3476);
        onView(withId(R.id.rlPickLocation)).perform(click());
        sleepThread(1000);
        onView(withId(R.id.rlPickLocation)).perform(click());
        onView(withId(R.id.btnLocation)).perform(click());
        sleepThread(1000);

    }

    public void sleepThread(int seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}
