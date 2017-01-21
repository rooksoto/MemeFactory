package rooksoto.c4q.nyc.memefactory.View.MemeFragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import rooksoto.c4q.nyc.memefactory.R;

public class VanillaMemeFragment extends Fragment implements View.OnTouchListener {

    public static final String VAN_PAGE = "VANILLA PAGE NUM";
    public static final String VAN_TITLE = "VANILLA TITLE";
    private TextView topTextView;
    private TextView bottomTextView;

    private int page;
    private String title;
    private Uri uri;
    private float dY;

    // newInstance constructor for creating fragment with arguments
    public static VanillaMemeFragment newInstance(Uri uri, int page, String title) {
        VanillaMemeFragment vanillaMemeFragment = new VanillaMemeFragment();
        vanillaMemeFragment.uri = uri;
        Bundle args = new Bundle();
        args.putInt(VAN_PAGE, page);
        args.putString(VAN_TITLE, title);
        vanillaMemeFragment.setArguments(args);
        return vanillaMemeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(VAN_PAGE, 0);
        title = getArguments().getString(VAN_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vanilla_meme_fragment, container, false);

        ImageView memeImageView = (ImageView) view.findViewById(R.id.meme_imageview);

        topTextView = (TextView) view.findViewById(R.id.top_text_view);
        bottomTextView = (TextView) view.findViewById(R.id.bottom_text_view);

        EditText topTextEditor = (EditText) view.findViewById(R.id.top_text_editor);
        EditText bottomTextEditor = (EditText) view.findViewById(R.id.bottom_text_editor);

        Picasso.with(getContext()).load(uri).error(R.drawable.doge).into(memeImageView);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topTextView.setOnTouchListener(this);
        bottomTextView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dY = view.getY() - event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                view.animate()
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
