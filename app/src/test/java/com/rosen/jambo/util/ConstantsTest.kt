package com.rosen.jambo.util

import com.rosen.jambo.utils.Constants
import org.junit.Assert
import org.junit.Test

/**
 * Created by Derick W on 04,May,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
class ConstantsTest {

    @Test
    fun testApiUrlReturned() {
        // Set connection to true.
        val constant = Constants
        Assert.assertEquals(constant.API_URL, "https://newsapi.org/v2/")
    }

}
