package com.rosen.jambo.views.articles;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Derick W on 05,April,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@RunWith(AndroidJUnit4.class)
public class SourceModelTest {

    Source source;

    @Before
    public void setUp() {
        source = new Source("Test", "Test");
        source.setId("1");
        source.setName("1");
    }


    @Test
    public void testArticleSource(){

        Source source = this.source;
        assertThat(source.getId(), is("1"));
        assertThat(source.getName(), is("1"));
    }
}
