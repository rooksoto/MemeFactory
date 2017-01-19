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
    private static int PAGE_COUNT = 2;
    private Uri uri;

    public MemePagerAdapter(FragmentManager fm, Uri uri) {
        super(fm);
        this.uri = uri;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DogeFragment.newInstance(uri, 0, "Doge");
            case 1:
                return VanillaMemeFragment.newInstance(uri, 1, "Vanilla");
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Doge";
            case 1:
                return "Vanilla";
            default:
                return "Page" + position;
        }
    }
}
