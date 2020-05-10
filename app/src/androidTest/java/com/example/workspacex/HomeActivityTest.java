package com.example.workspacex;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.example.workspacex.ui.home.ScanActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<HomeActivity>(HomeActivity.class);
    private HomeActivity mActivity;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ScanActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchOfSecondActivityOnButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.fab));
        onView(withId(R.id.fab)).perform(click());
        Activity scannerActivity =  getInstrumentation().waitForMonitorWithTimeout(monitor,5000000);
        assertNotNull(scannerActivity);
        scannerActivity.finish();
    }


    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}