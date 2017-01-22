package rooksoto.c4q.nyc.memefactory.View;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import rooksoto.c4q.nyc.memefactory.Presenter.MemePagerAdapter;
import rooksoto.c4q.nyc.memefactory.R;

/**
 * Created by ashiquechowdhury on 1/12/17.
 */
public class MemeEditorActivity extends AppCompatActivity {
    private MemePagerAdapter adapterViewPager;
    private ViewPager vPager;
    private Uri memeImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vpager_photo);

        memeImageUri = getIntent().getParcelableExtra(StartScreenFragment.IMAGEURI);

        vPager = (ViewPager) findViewById(R.id.vpager_photo);
        adapterViewPager = new MemePagerAdapter(getSupportFragmentManager(), memeImageUri);
        vPager.setAdapter(adapterViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vPager);

        if (savedInstanceState != null) {
            vPager.setCurrentItem(savedInstanceState.getInt("item"));
        }
    }

        @Override
        protected void onSaveInstanceState (Bundle outState){
            super.onSaveInstanceState(outState);
            outState.putInt("item", vPager.getCurrentItem());
            outState.putParcelable("URI", memeImageUri);
        }

        @Override
        public boolean dispatchTouchEvent (MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (v instanceof EditText) {
                    EditText edit = (EditText) v;
                    Rect outRect = new Rect();
                    v.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                        v.clearFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    edit.setCursorVisible(false);
                }
            }
            return super.dispatchTouchEvent(event);
        }
    }
