package com.rosen.jambo.views.bookmarks;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.rosen.jambo.R;
import com.rosen.jambo.views.articles.Article;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by Derick W on 03,May,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@LargeTest
public class BookmarkActivityTest {

    List<Article> articles;
    List<Bookmark> bookmarks;
    Article article, article1;

    @Rule
    public ActivityTestRule<BookmarksActivity> mActivityTestRule = new ActivityTestRule<>(BookmarksActivity.class);

    @Before
    public void setUp() {
        article = new Article("author", "company", "message", "google.com", "image", "timestamp", "24", "111");
        article1 = new Article("author", "company1", "message", "google.com", "image", "timestamp", "24", "1112");
        articles = new ArrayList<>();
        articles.add(article);
        articles.add(article1);

        Bookmark bookmark1 = new Bookmark();
        bookmark1.setId(1);
        bookmark1.setArticleID("company");
        bookmarks = new ArrayList<>();
        bookmarks.add(bookmark1);
    }

    @Test
    public void noBookmark() {
        sleepThread(2000);
        onView(withId(R.id.articles_list)).check(matches(isDisplayed()));
    }

    @Test
    public void bookmarkListDisplayed() {
        sleepThread(2000);

        populateBookmarks();
        onView(withId(R.id.articles_list)).check(matches(isDisplayed()));
    }

    @Test
    public void clickBookmark() {
        sleepThread(2000);

        populateBookmarks();

        onView(withId(R.id.articles_list)).check(matches(isDisplayed()));
        onView(withId(R.id.articles_list)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.articles_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void bookmarkOptionsList() {
        sleepThread(2000);
        populateBookmarks();
        onView(withId(R.id.action_list)).perform(click());
    }

    @Test
    public void bookmarkOptionsQuilt() {
        sleepThread(2000);
        populateBookmarks();
        onView(withId(R.id.action_quilt)).perform(click());
    }

    @Test
    public void bookmarkOptionTextSize() {
        sleepThread(2000);
        populateBookmarks();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Font size")).perform(click());

        ViewInteraction appCompatRadioButton6 = onView(
                allOf(withId(R.id.normal), withText("Normal"),
                        childAtPosition(
                                allOf(withId(R.id.text_sizes),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatRadioButton6.perform(click());

        ViewInteraction appCompatRadioButton7 = onView(
                allOf(withId(R.id.small), withText("Small"),
                        childAtPosition(
                                allOf(withId(R.id.text_sizes),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatRadioButton7.perform(click());

        ViewInteraction appCompatRadioButton8 = onView(
                allOf(withId(R.id.medium), withText("Medium"),
                        childAtPosition(
                                allOf(withId(R.id.text_sizes),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatRadioButton8.perform(click());

        ViewInteraction appCompatRadioButton9 = onView(
                allOf(withId(R.id.large), withText("Large"),
                        childAtPosition(
                                allOf(withId(R.id.text_sizes),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatRadioButton9.perform(click());

        ViewInteraction appCompatRadioButton10 = onView(
                allOf(withId(R.id.xlarge), withText("X Large"),
                        childAtPosition(
                                allOf(withId(R.id.text_sizes),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatRadioButton10.perform(click());

    }

    @Test
    public void bookmarkOptionsTextStyles() {
        sleepThread(2000);
        populateBookmarks();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Text style")).perform(click());


        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.journal), withText("Journal"),
                        childAtPosition(
                                allOf(withId(R.id.text_styles),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.lobster), withText("Lobster"),
                        childAtPosition(
                                allOf(withId(R.id.text_styles),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatRadioButton2.perform(click());

        ViewInteraction appCompatRadioButton3 = onView(
                allOf(withId(R.id.dancing), withText("Dancing Script"),
                        childAtPosition(
                                allOf(withId(R.id.text_styles),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatRadioButton3.perform(click());

        ViewInteraction appCompatRadioButton4 = onView(
                allOf(withId(R.id.walkway), withText("Walkway"),
                        childAtPosition(
                                allOf(withId(R.id.text_styles),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatRadioButton4.perform(click());

        ViewInteraction appCompatRadioButton5 = onView(
                allOf(withId(R.id.gooddog), withText("Gooddog"),
                        childAtPosition(
                                allOf(withId(R.id.text_styles),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatRadioButton5.perform(click());


        sleepThread(2000);
    }

    private void populateBookmarks () {
        mActivityTestRule.getActivity().bookmarkList.addAll(bookmarks);
        mActivityTestRule.getActivity().articleList.addAll(articles);
        mActivityTestRule.getActivity().runOnUiThread(() -> mActivityTestRule.getActivity().articlesAdapter.notifyDataSetChanged());
    }


    private void sleepThread(int seconds) {
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
