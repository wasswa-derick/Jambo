package com.rosen.jambo;


import com.rosen.jambo.views.articles.Article;
import com.rosen.jambo.views.articles.ArticlesViewModel;
import com.rosen.jambo.views.bookmarks.Bookmark;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    List<Bookmark> bookmarks;
    ArticlesViewModel mockedServiceClient;
    Article article, article1;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        article = new Article("author", "company", "message", "google.com", "image", "timestamp", "24", "111");
        article1 = new Article("author", "company", "message", "google.com", "image", "timestamp", "24", "111");
        articles = new ArrayList<>();
        articles.add(article);
        articles.add(article1);

        Bookmark bookmark1 = new Bookmark();
        bookmark1.setId(1);
        bookmark1.setArticleID("company");
        bookmarks = new ArrayList<>();
        bookmarks.add(bookmark1);

        mockedServiceClient = Mockito.mock(ArticlesViewModel.class);
    }



    @Test
    public void testFetchOnlineArticles(){
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
        when(mockedServiceClient
                .getOfflineArticlesByTag(anyString()))
                .thenReturn(articles);

        List<Article> articleList = mockedServiceClient.getOfflineArticlesByTag("Test");
        assert(articleList.size() == 2);
    }

    @Test
    public void testGetAllBookMarks(){
        when(mockedServiceClient
                .getAllBookmarks())
                .thenReturn(bookmarks);


        List<Bookmark> bookmarkList = mockedServiceClient.getAllBookmarks();
        assert(bookmarkList.size() == 1);
    }

    @Test
    public void testGetAllBookMarksEmpty(){
        when(mockedServiceClient
                .getAllBookmarks())
                .thenReturn(new ArrayList<>());


        List<Bookmark> bookmarkList = mockedServiceClient.getAllBookmarks();
        assert(bookmarkList.size() == 0);
    }

    @Test
    public void testGetArticleByID() {
        when(mockedServiceClient
                .getArticleByID(anyString()))
                .thenReturn(article);

        Article fetchArticle = mockedServiceClient.getArticleByID("1");
        Assert.assertEquals(fetchArticle, article);
    }

    @Test
    public void testFetchOfflineArticlesReturnsNothing() {
        when(mockedServiceClient
                .getOfflineArticlesByTag(anyString()))
                .thenReturn(new ArrayList<>());

        List<Article> articleList = mockedServiceClient.getOfflineArticlesByTag("Test");
        assert(articleList.size() == 0);
    }

    @Test
    public void testFetchOnlineArticlesReturnsNothing(){
        when(mockedServiceClient
                .getAllNewsArticles(anyString(), anyString()))
                .thenReturn(Observable.just(new ArrayList<>()));

        mockedServiceClient.getAllNewsArticles("Test", "Test")
                .test()
                .assertValue(new ArrayList<>())
                .dispose();
    }



}
