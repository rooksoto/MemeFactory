package rooksoto.c4q.nyc.memefactory.View.MemeFragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import rooksoto.c4q.nyc.memefactory.R;
import rooksoto.c4q.nyc.memefactory.util.StickerImageView;

/**
 * Created by ashiquechowdhury on 1/21/17.
 */

public class StickerMemeFragment extends Fragment {
    public static final String VAN_PAGE = "VANILLA PAGE NUM";
    public static final String VAN_TITLE = "VANILLA TITLE";
    ImageView memeImageView;
    private int page;
    private String title;
    private Uri uri;
    private float dY;

    // newInstance constructor for creating fragment with arguments
    public static StickerMemeFragment newInstance(Uri uri, int page, String title) {
        StickerMemeFragment stickerMemeFragment = new StickerMemeFragment();
        stickerMemeFragment.uri = uri;
        Bundle args = new Bundle();
        args.putInt(VAN_PAGE, page);
        args.putString(VAN_TITLE, title);
        stickerMemeFragment.setArguments(args);
        return stickerMemeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stickers, container, false);
        memeImageView = (ImageView) view.findViewById(R.id.meme_sticker_imageview);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.with(getContext()).load(uri).fit().into(memeImageView);


        FrameLayout canvas = (FrameLayout) view.findViewById(R.id.my_sticker_frame);

        StickerImageView iv_sticker = new StickerImageView(getActivity());
        iv_sticker.setImageDrawable(getResources().getDrawable(R.drawable.c10));
        canvas.addView(iv_sticker);

    }

}
