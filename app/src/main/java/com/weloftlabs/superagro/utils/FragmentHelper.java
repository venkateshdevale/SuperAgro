package com.weloftlabs.superagro.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by Venkatesh Devale on 12-09-2015.
 */
public class FragmentHelper {


    /**
     * Replace the fragment and add to the back stack
     *
     * @param context
     * @param containerId
     * @param contentFragment
     */
    public static void replaceAndAddToStack(FragmentActivity context, int containerId, Fragment contentFragment) {

        FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(containerId, contentFragment).
                addToBackStack(contentFragment.getClass().getSimpleName()).commitAllowingStateLoss();

    }

    /**
     * Replace the fragment and add to the back stack, with user defined animations.
     *
     * @param context
     * @param containerId
     * @param contentFragment
     * @param entryAnimation
     * @param exitAnimation
     */
    public static void replaceAndAddToStack(FragmentActivity context, int containerId,
                                            Fragment contentFragment, int entryAnimation, int exitAnimation) {

        FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(entryAnimation, exitAnimation,
//                R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        fragmentTransaction.replace(containerId, contentFragment).
                addToBackStack(contentFragment.getClass().getSimpleName()).commitAllowingStateLoss();

    }

    /**
     * Replace the fragment, set a tag to the fragment and add to the back stack
     *
     * @param context
     * @param containerId
     * @param contentFragment
     * @param fragmentTag
     */
    public static void replaceAndAddToStack(FragmentActivity context, int containerId, Fragment contentFragment, String fragmentTag) {

        FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.support.design.R.anim.abc_fade_in,
                android.support.design.R.anim.abc_fade_out);
        fragmentTransaction.replace(containerId, contentFragment).addToBackStack(contentFragment.getClass().getSimpleName()).commitAllowingStateLoss();
        Log.d("tony", contentFragment.getClass().getSimpleName());
    }

    /**
     * Replace the fragment
     *
     * @param context
     * @param containerId
     * @param contentFragment
     */
    public static void replace(FragmentActivity context, int containerId, Fragment contentFragment) {

        FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.support.design.R.anim.abc_fade_in, android.support.design.R.anim.abc_fade_out);
        fragmentTransaction.replace(containerId, contentFragment).commitAllowingStateLoss();

    }

    /**
     * Replace the fragment
     *
     * @param context
     * @param containerId
     * @param contentFragment
     */
    public static void replace(FragmentActivity context, int containerId, Fragment contentFragment, int entryAnimation, int exitAnimation) {

        FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(entryAnimation, exitAnimation,
//                R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        fragmentTransaction.replace(containerId, contentFragment).commitAllowingStateLoss();

    }

}