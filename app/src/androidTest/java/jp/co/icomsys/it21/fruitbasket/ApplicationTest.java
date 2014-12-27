package jp.co.icomsys.it21.fruitbasket;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class ApplicationTest {

    @Before
    public void setup() {

    }

    @After
    public void teardrop() {

    }

    @Test
    public void testSample() {
        Assert.assertEquals("a", "a");

    }

}