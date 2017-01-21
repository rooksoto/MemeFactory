package rooksoto.c4q.nyc.memefactory.View.MemeFragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rooksoto.c4q.nyc.memefactory.R;

/**
 * Created by huilin on 1/14/17.
 */

public class DogeFragment extends Fragment implements View.OnTouchListener, View.OnClickListener {

    public static final String DOGE_PAGE = "DOGE PAGE NUM";
    public static final String DOGE_TITLE = "DOGE TITLE";
    Uri uri;
    Float dX;
    Float dY;

    private String title;
    private int page;
    private ImageView imageView;
    private View rootView;
    private List<EditText> captionTvs = new ArrayList<>();
    private RelativeLayout secondLayout;
    private ImageView penView;
    private RelativeLayout secView;


    public static DogeFragment newInstance(Uri uri, int page, String title) {
        DogeFragment dogeFragment = new DogeFragment();
        dogeFragment.uri = uri;
        Bundle args = new Bundle();
        args.putInt(DOGE_PAGE, page);
        args.putString(DOGE_TITLE, title);
        dogeFragment.setArguments(args);
        return dogeFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(DOGE_PAGE, 0);
        title = getArguments().getString(DOGE_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_meme_photo, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.meme_photo_IView);
        penView = (ImageView) rootView.findViewById(R.id.write_ic_IV);
//        secView = (RelativeLayout) rootView.findViewById(R.id.memeImg_layout);
        Picasso.with(rootView.getContext()).load(uri).error(R.drawable.doge).into(imageView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        penView.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        addCaption(view);

    }

    private void addCaption(View view) {
        secondLayout = (RelativeLayout) rootView.findViewById(R.id.memeImg_layout);
        Typeface typeface = Typeface.createFromAsset(view.getContext().getAssets(), "LDFComicSans.ttf");

        EditText captionText = new EditText(view.getContext());
        createCaption(view, typeface, captionText);
        captionText.setOnTouchListener(this);

        secondLayout.addView(captionText);

        captionTvs.add(captionText);
    }

    private void createCaption(View view, Typeface typeface, EditText captionText) {
        captionText.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        captionText.setBackgroundColor(Color.TRANSPARENT);
        captionText.setTypeface(typeface);
        captionText.setText(" ");
        captionText.requestFocus();
        ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_FORCED,
                        InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
