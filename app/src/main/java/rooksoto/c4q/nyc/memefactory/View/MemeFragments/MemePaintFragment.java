package rooksoto.c4q.nyc.memefactory.View.MemeFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import rooksoto.c4q.nyc.memefactory.R;

/**
 * Created by rook on 1/9/17.
 */

// Use this fragment as a template for yours

public class MemePaintFragment extends Fragment implements View.OnTouchListener {

    File file;
    Float dX;
    Float dY;

    public MemePaintFragment() {
    }

    // The newInstance code isn't really necessary
    public MemePaintFragment newInstance(File file) {
        MemePaintFragment memePaintFragment = new MemePaintFragment();
        this.file = file;
        return memePaintFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meme_paint, container, false);

        // Place views here

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set listeners here
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:

                view.animate()
                        .x(event.getRawX() + dX)
                        .y(event.getRawY() + dY)
                        .setDuration(0)
                        .start();
                break;
            default:
                return false;
        }
        return true;
    }
}
