package rooksoto.c4q.nyc.memefactory.View.MemeFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import rooksoto.c4q.nyc.memefactory.R;
import rooksoto.c4q.nyc.memefactory.View.MemeFragments.rviewsticker.StickerAdapter;
import rooksoto.c4q.nyc.memefactory.util.StickerImageView;

import static android.content.ContentValues.TAG;
import static rooksoto.c4q.nyc.memefactory.View.MemeFragments.DogeFragment.PIC_URI;

/**
 * Created by ashiquechowdhury on 1/21/17.
 */

public class StickerMemeFragment extends Fragment implements StickerAdapter.Listener {
//    private ImageButton savedButton;
    public static final String VAN_PAGE = "STICKER PAGE NUM";
    public static final String VAN_TITLE = "STICKER TITLE";
    StickerAdapter.Listener mListener;
    ImageView memeImageView;
    private FrameLayout canvas;
    private Uri uri;
    private RecyclerView stickerRecycler;
    private StickerImageView iv_sticker;
    private ImageButton stickerShareButton;

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
        stickerShareButton = (ImageButton) view.findViewById(R.id.sticker_share_button);
//        savedButton = (ImageButton) view.findViewById(R.id.sticker_save_button);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadMemeImage();
        loadStickerRecycler();

        iv_sticker = new StickerImageView(getActivity());

        stickerShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage();
            }
        });
//        Picasso.with(getContext()).load(R.drawable.save).resize(64, 64).into(savedButton);
//        savedButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                storeMeme(getBitmapFromView(canvas));
//            }
//        });

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

    private void shareImage() {
        try {
            Bitmap bitmap = getBitmapFromView(canvas);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(getContext(), bitmap));
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, "Share image using "));
        }catch (Exception e){
            e.getMessage();
        }
    }

    public void storeMeme(Bitmap bm) {
        try {
            MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bm, "", "");

            Toast.makeText(this.getContext(), "Saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "Error saving.", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap getBitmapFromView(View view) {
        try {
            view.setDrawingCacheEnabled(true);

            view.buildDrawingCache();
            //Define a bitmap with the same size as the view
            Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            //Bind a canvas to it
            Canvas canvas = new Canvas(returnedBitmap);
            //Get the view's background
            Drawable bgDrawable = view.getBackground();
            if (bgDrawable != null) {
                //has background drawable, then draw it on the canvas
                bgDrawable.draw(canvas);
            } else {
                //does not have background drawable, then draw white background on the canvas
                canvas.drawColor(Color.WHITE);
            }
            // draw the view on the canvas
            view.draw(canvas);
            //return the bitmap
            return returnedBitmap;
        }catch (Exception e){
            Log.e(TAG, "getBitmapFromView: ", e);
        }
        return null;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                    inImage, "", "");
            return Uri.parse(path);
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }
}