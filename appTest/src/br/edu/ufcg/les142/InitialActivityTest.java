package br.edu.ufcg.les142;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

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
    public void testShouldConnectLocationClientOnStart(){
        new Runnable() {
            @Override
            public void run() {
                mActivity.onStart();
                assertTrue(mActivity.getLocationClient().isConnected());
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
