package com.rosen.jambo.views.articles;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Derick W on 05,April,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@RunWith(AndroidJUnit4.class)
public class ArticlesModelTest {

    Articles articles;
    List<Article> articleList;

    @Before
    public void setUp() {

        articles = new Articles();
        articleList = new ArrayList<>();
        Article article = new Article("author", "company", "message", "google.com", "image", "timestamp", "24", "111");
        Article article1 = new Article("author", "company", "message", "google.com", "image", "timestamp", "24", "111");
        articleList.add(article);
        articleList.add(article1);

        articles.setArticles(articleList);
        articles.setStatus("test");
        articles.setTotalResults(1);
    }


    @Test
    public void testFragmentModel(){
        assertThat(articleList.size(), is(2));
        assertThat(articles.getStatus(), is("test"));
        assertThat(articles.getTotalResults(), is(1));
    }
}
