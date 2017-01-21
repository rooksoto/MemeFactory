package rooksoto.c4q.nyc.memefactory.View.MemeFragments.stickerfragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import rooksoto.c4q.nyc.memefactory.R;
import rooksoto.c4q.nyc.memefactory.util.StickerImageView;

import static rooksoto.c4q.nyc.memefactory.View.MemeFragments.DogeFragment.PIC_URI;

/**
 * Created by ashiquechowdhury on 1/21/17.
 */

public class StickerMemeFragment extends Fragment implements StickerAdapter.Listener{
    public static final String VAN_PAGE = "STICKER PAGE NUM";
    public static final String VAN_TITLE = "STICKER TITLE";
    StickerAdapter.Listener mListener;
    ImageView memeImageView;
    private FrameLayout canvas;
    private Uri uri;
    private RecyclerView stickerRecycler;
    private StickerImageView iv_sticker;

    // newInstance constructor for creating fragment with arguments
    public static StickerMemeFragment newInstance(Uri uri, int page, String title) {
        StickerMemeFragment stickerMemeFragment = new StickerMemeFragment();
        stickerMemeFragment.uri = uri;
        Bundle args = new Bundle();
        args.putInt(VAN_PAGE, page);
        args.putString(VAN_TITLE, title);
        args.putParcelable(PIC_URI, uri);
        stickerMemeFragment.setArguments(args);
        return stickerMemeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uri = getArguments().getParcelable(PIC_URI);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stickers, container, false);
        memeImageView = (ImageView) view.findViewById(R.id.meme_sticker_imageview);
        canvas = (FrameLayout) view.findViewById(R.id.my_sticker_frame);
        stickerRecycler = (RecyclerView) view.findViewById(R.id.sticker_recycler);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadMemeImage();
        loadStickerRecycler();

        iv_sticker = new StickerImageView(getActivity());
    }

    private void loadStickerRecycler() {
        stickerRecycler.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false));
        stickerRecycler.setAdapter(new StickerAdapter(this));
    }

    private void loadMemeImage() {
        Picasso.with(getContext()).load(uri).into(memeImageView);
    }

    @Override
    public void onStickerPressed(int stickerDrawable){
        iv_sticker = new StickerImageView(getActivity());
        iv_sticker.setImageDrawable(getResources().getDrawable(stickerDrawable));
        canvas.addView(iv_sticker);
    }
}
