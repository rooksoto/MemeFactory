package rooksoto.c4q.nyc.memefactory.Presenter;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import rooksoto.c4q.nyc.memefactory.View.MemeFragments.DogeFragment;
import rooksoto.c4q.nyc.memefactory.View.MemeFragments.MemePaintFragment;
import rooksoto.c4q.nyc.memefactory.View.MemeFragments.VanillaMemeFragment;

/**
 * Created by huilin on 1/14/17.
 */

public class MemePagerAdapter extends FragmentPagerAdapter {
    private static int PAGE_COUNT = 3;
    private Uri uri;
    private DogeFragment dogeFrag;
    private VanillaMemeFragment vanillaFrag;
    private MemePaintFragment paintFrag;

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
                dogeFrag = DogeFragment.newInstance(uri, 0, "Doge");
                return dogeFrag;
            case 1:
                vanillaFrag = VanillaMemeFragment.newInstance(uri, 1, "Vanilla");
                return vanillaFrag;
            case 2:
                paintFrag = MemePaintFragment.newInstance(uri, 2, "Paint!");
                return paintFrag;
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
            case 2:
                return "Paint!";
            default:
                return "Page" + position;
        }
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
//        switch (position) {
//            case 0:
//                dogeFrag = (DogeFragment) createdFragment;
//                break;
//            case 1:
//                vanillaFrag = (VanillaMemeFragment) createdFragment;
//                break;
//            case 2:
//                paintFrag = (MemePaintFragment) createdFragment;
//        }
//        return createdFragment;
//    }
}
