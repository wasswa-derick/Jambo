package com.rosen.jambo.views.articles;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;

import com.rosen.jambo.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by Derick W on 04,May,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CurrentLocationOffline {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

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
    public void clickOnCurrentLocationWithNullLocation() {
        WifiManager wifi = (WifiManager) mActivityTestRule.getActivity().getSystemService(Context.WIFI_SERVICE);
        Objects.requireNonNull(wifi).setWifiEnabled(false);

        sleepThread(1000);
        onView(withContentDescription("Open navigation drawer")).perform(click());
        ViewInteraction currentlocation = onView(
                childAtPosition(
                        allOf(withId(R.id.duo_view_menu_options_layout),
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0)),
                        5));
        currentlocation.perform(scrollTo(), click());

        mActivityTestRule.getActivity().setCurrentLocationLatitude(0.0);
        mActivityTestRule.getActivity().setCurrentLocationLongitude(0.0);

        onView(withId(R.id.rlPickLocation)).perform(click());
        sleepThread(1000);
        onView(withId(R.id.rlPickLocation)).perform(click());
        onView(withId(R.id.btnLocation)).perform(click());
        sleepThread(1000);
        wifi.setWifiEnabled(true);
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

    public void sleepThread(int seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
