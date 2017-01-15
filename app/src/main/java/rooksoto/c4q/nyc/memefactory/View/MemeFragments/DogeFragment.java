package rooksoto.c4q.nyc.memefactory.View.MemeFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

import rooksoto.c4q.nyc.memefactory.R;

/**
 * Created by huilin on 1/14/17.
 */

public class DogeFragment extends Fragment implements View.OnTouchListener {

    File file;
    Float dX;
    Float dY;

    private String title;
    private int page;
    private ImageView imageView;


    // The newInstance code isn't really necessary
    public DogeFragment newInstance(/*File file,*/ int page, String title) {
        DogeFragment dogeFragment = new DogeFragment();
//        this.file = file;
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        dogeFragment.setArguments(args);
        return dogeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_meme_photo, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.meme_photo_IView);
        imageView.setImageResource(R.drawable.doge);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView.setOnTouchListener(this);

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
