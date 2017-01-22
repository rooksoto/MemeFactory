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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import rooksoto.c4q.nyc.memefactory.util.GenericTextWatcher;
import rooksoto.c4q.nyc.memefactory.R;

import static android.content.ContentValues.TAG;
import static rooksoto.c4q.nyc.memefactory.View.MemeFragments.DogeFragment.PIC_URI;

public class VanillaMemeFragment extends Fragment implements View.OnTouchListener {

    public static final String VAN_PAGE = "VANILLA PAGE NUM";
    public static final String VAN_TITLE = "VANILLA TITLE";
    //
    private View rootView;
    private ImageView memeImageView;
    private TextView topTextView;
    private TextView bottomTextView;
    private EditText topTextEditor;
    private EditText bottomTextEditor;
    private ImageButton saveButton;
    private ImageButton shareButton;
    private RelativeLayout layoutToShare;
    //
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
        rootView = inflater.inflate(R.layout.vanilla_meme_fragment, container, false);
        //
        initViews();
        //
        topTextEditor.addTextChangedListener(new GenericTextWatcher(topTextView, topTextEditor));
        bottomTextEditor.addTextChangedListener(new GenericTextWatcher(bottomTextView, bottomTextEditor));

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage();
            }
        });

        Picasso.with(getContext()).load(uri).error(R.drawable.doge).into(memeImageView);

        return rootView;
    }

    private void initViews() {
        memeImageView = (ImageView) rootView.findViewById(R.id.meme_imageview);
        topTextView = (TextView) rootView.findViewById(R.id.top_text_view);
        bottomTextView = (TextView) rootView.findViewById(R.id.bottom_text_view);
        topTextEditor = (EditText) rootView.findViewById(R.id.top_text_editor);
        bottomTextEditor = (EditText) rootView.findViewById(R.id.bottom_text_editor);
        saveButton = (ImageButton) rootView.findViewById(R.id.save_button);
        layoutToShare = (RelativeLayout) rootView.findViewById(R.id.vanilla_viewgroup);
        shareButton = (ImageButton) rootView.findViewById(R.id.vanilla_share_button);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topTextView.setOnTouchListener(this);
        bottomTextView.setOnTouchListener(this);
        Picasso.with(getContext()).load(R.drawable.save).resize(90, 90).into(saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeMeme(getBitmapFromView(layoutToShare));
            }
        });
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

    private void shareImage() {
        try {
            Bitmap bitmap = getBitmapFromView(layoutToShare);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(getContext(), bitmap));
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, "Share image using "));
        }catch (Exception e){
            e.getMessage();
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
                canvas.drawColor(Color.parseColor("#FF757575"));
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

    public void storeMeme(Bitmap bm) {
        try {
            MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bm, "", "");

            Toast.makeText(this.getContext(), "Saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "Error saving.", Toast.LENGTH_SHORT).show();
        }
    }
}