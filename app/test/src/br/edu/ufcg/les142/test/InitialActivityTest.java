package br.edu.ufcg.les142.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import br.edu.ufcg.les142.*;
import com.parse.ParseUser;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class br.edu.ufcg.les142.InitialActivityTest \
 * br.edu.ufcg.les142.tests/android.test.InstrumentationTestRunner
 */
public class InitialActivityTest extends ActivityInstrumentationTestCase2<InitialActivity> {

    private InitialActivity mActivity;

    public InitialActivityTest() {
        super(InitialActivity.class);


    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(false);


        mActivity = getActivity();
    }

    @SmallTest
    public void testShouldCreateAnUser(){
        assertTrue(mActivity.setupUser("Lucas", "qwerty123").getUsername().equals("Lucas"));
    }

    @SmallTest
    public void testShouldLoginUser(){
        new Runnable() {
            @Override
            public void run() {
                ParseUser hominho =  mActivity.setupUser("Rodrigo", "123senha");
                mActivity.login();

                assertTrue(hominho.isAuthenticated());
            }
        };
    }

    @SmallTest
    public void testShouldConnectLocationClientOnStart(){
        new Runnable() {
            @Override
            public void run() {
                mActivity.onStart();
                assertTrue(mActivity.getLocationClient().isConnected());
            }
        };
    }

    @SmallTest
    public void testShouldDisconnectLocationClientOnStop(){
        new Runnable() {
            @Override
            public void run() {
                mActivity.onStart();
                mActivity.onStop();
                assertFalse(mActivity.getLocationClient().isConnected());
            }
        };
    }

    @LargeTest
    public void testShouldGetLocation(){
        new Runnable() {
            @Override
            public void run() {
                assertTrue(mActivity.getLocation() != null);
            }
        };
    }
}
