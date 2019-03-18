package com.rosen.jambo.views.articles;

import android.content.Context;
import android.os.Parcel;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static com.rosen.jambo.views.articles.Article.CREATOR;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by Derick W on 05,April,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@RunWith(AndroidJUnit4.class)
public class ArticleModelTest {

    Article article;

    @Before
    public void setUp() {
        article = new Article("test", "test", "test", "google.com", "google.com", "test", "test", "test");
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.rosen.jambo", appContext.getPackageName());
    }

    @Test
    public void testArticleParcelable(){

        Parcel parcel = Parcel.obtain();
        this.article.writeToParcel(parcel, article.describeContents());
        parcel.setDataPosition(0);

        Article createdFromParcel = CREATOR.createFromParcel(parcel);
        Article [] newArray = CREATOR.newArray(1);
        assertThat(createdFromParcel.getTitle(), is("test"));
        assertThat(createdFromParcel.getContent(), is("test"));
        assertThat(createdFromParcel.getAuthor(), is("test"));
        assertThat(createdFromParcel.getUrlToImage(), is("google.com"));
        assertThat(createdFromParcel.getUrl(), is("google.com"));
        assertThat(createdFromParcel.getPublishedAt(), is("test"));
        assertThat(createdFromParcel.getDescription(), is("test"));
        assertEquals(newArray.length, 1);
        assertEquals(Article.getCREATOR(), CREATOR);
    }


    @Test
    public void testArticleConstructorWithSource(){

        Source source = new Source("test", "test");
        Article article = new Article("test", "test", "test", "google.com", "google.com", "test", "test", source);
        article.setSource(source);

        assertThat(article.getSource(), is(source));
    }

    @Test
    public void testArticleConstructorWithID(){

        Article article = new Article("test", "test", "test", "google.com", "google.com", "test", "test", "test");

        assertThat(article.getId(), is("test"));
    }

}
