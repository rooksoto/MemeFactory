package rooksoto.c4q.nyc.memefactory.View.MemeFragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import rooksoto.c4q.nyc.memefactory.GenericTextWatcher;
import rooksoto.c4q.nyc.memefactory.R;

import static rooksoto.c4q.nyc.memefactory.View.MemeFragments.DogeFragment.PIC_URI;

public class VanillaMemeFragment extends Fragment implements View.OnTouchListener {

    public static final String VAN_PAGE = "VANILLA PAGE NUM";
    public static final String VAN_TITLE = "VANILLA TITLE";
    private ImageView memeImageView;
    private TextView topTextView;
    private TextView bottomTextView;
    private EditText topTextEditor;
    private EditText bottomTextEditor;
    private ImageButton paletteButton;

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
        args.putParcelable(PIC_URI, uri);
        vanillaMemeFragment.setArguments(args);
        return vanillaMemeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(VAN_PAGE, 0);
        title = getArguments().getString(VAN_TITLE);
        uri = getArguments().getParcelable(PIC_URI);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vanilla_meme_fragment, container, false);

        memeImageView = (ImageView) view.findViewById(R.id.meme_imageview);

        topTextView = (TextView) view.findViewById(R.id.top_text_view);
        bottomTextView = (TextView) view.findViewById(R.id.bottom_text_view);

        topTextEditor = (EditText) view.findViewById(R.id.top_text_editor);
        bottomTextEditor = (EditText) view.findViewById(R.id.bottom_text_editor);

        topTextEditor.addTextChangedListener(new GenericTextWatcher(topTextView, topTextEditor));
        bottomTextEditor.addTextChangedListener(new GenericTextWatcher(bottomTextView, bottomTextEditor));

        paletteButton = (ImageButton) view.findViewById(R.id.palette_button);

        Picasso.with(getContext()).load(uri).error(R.drawable.doge).into(memeImageView);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topTextView.setOnTouchListener(this);
        bottomTextView.setOnTouchListener(this);
//        paletteButton
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("memeImageUri", uri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            Uri memeImageUri = (Uri) savedInstanceState.get("memeImageUri");
            Picasso.with(getContext()).load(memeImageUri).into(memeImageView);
        }
    }
}
