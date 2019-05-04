package com.rosen.jambo.views.bookmarks;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Derick W on 03,May,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class BookmarkModelTest {

    Bookmark bookmark;

    @Before
    public void setUp() {
        bookmark = new Bookmark();
        bookmark.setId(1);
        bookmark.setArticleID("1");
    }


    @Test
    public void testArticleSource(){

        Bookmark bookmark = this.bookmark;
        assertThat(bookmark.getId(), is(1));
        assertThat(bookmark.getArticleID(), is("1"));
    }
}
