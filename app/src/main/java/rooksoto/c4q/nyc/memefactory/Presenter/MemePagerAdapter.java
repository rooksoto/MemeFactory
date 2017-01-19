package rooksoto.c4q.nyc.memefactory.Presenter;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import rooksoto.c4q.nyc.memefactory.View.MemeFragments.DogeFragment;
import rooksoto.c4q.nyc.memefactory.View.MemeFragments.VanillaMemeFragment;

/**
 * Created by huilin on 1/14/17.
 */

public class MemePagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;
    private Uri uri;

    public MemePagerAdapter(FragmentManager fm, Uri uri) {
        super(fm);
        this.uri = uri;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DogeFragment.newInstance(uri, 0, "Doge");
            case 1:
                return VanillaMemeFragment.newInstance(uri, 1, "Standard");
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) return "Dodge Meme Editor";
        if (position == 1) return "Standard Meme Editor";
        return "Page" + position;
    }
}
