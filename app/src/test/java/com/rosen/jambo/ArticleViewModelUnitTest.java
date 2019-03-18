package com.rosen.jambo;

import android.app.Application;

import com.rosen.jambo.domain.data.repository.impl.NewsRepositoryImpl;
import com.rosen.jambo.views.articles.Article;
import com.rosen.jambo.views.articles.ArticlesViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Derick W on 18,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@RunWith(MockitoJUnitRunner.class)
public class ArticleViewModelUnitTest {

    List<Article> articles;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        Article article = new Article("author", "company", "message", "google.com", "image", "timestamp", "24", "111");
        Article article1 = new Article("author", "company", "message", "google.com", "image", "timestamp", "24", "111");
        articles = new ArrayList<>();
        articles.add(article);
        articles.add(article1);
    }



    @Test
    public void testFetchOnlineArticles(){
        final ArticlesViewModel mockedServiceClient = Mockito.mock(ArticlesViewModel.class);
        when(mockedServiceClient
                .getAllNewsArticles(anyString(), anyString()))
                .thenReturn(Observable.just(articles));

        mockedServiceClient.getAllNewsArticles("Test", "Test")
                .test()
                .assertValue(articles)
                .dispose();
    }


    @Test
    public void testFetchOfflineArticles(){

        final ArticlesViewModel mockedServiceClient = Mockito.mock(ArticlesViewModel.class);
        when(mockedServiceClient
                .getOfflineArticlesByTag(anyString()))
                .thenReturn(articles);

        List<Article> articleList = mockedServiceClient.getOfflineArticlesByTag("Test");
        assert(articleList.size() == 2);
    }


    @Test
    public void testArticlesFromRepository(){
        final ArticlesViewModel mockedServiceClient = Mockito.mock(ArticlesViewModel.class);
        when(mockedServiceClient
                .getArticleList())
                .thenReturn(Observable.just(articles));

        mockedServiceClient.getArticleList()
                .test()
                .assertValue(articles)
                .dispose();
    }


}
