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
public class FragmentModelTest {

    FragmentModel fragmentModel;

    @Before
    public void setUp() {
        fragmentModel = new FragmentModel();
        fragmentModel.setEmptyList(true);
        fragmentModel.setLoadingArticles(false);
    }


    @Test
    public void testFragmentModel(){
        assertThat(fragmentModel.getEmptyList(), is(true));
        assertThat(fragmentModel.getLoadingArticles(), is(false));
    }
}
