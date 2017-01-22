package rooksoto.c4q.nyc.memefactory.View.MemeFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import rooksoto.c4q.nyc.memefactory.Presenter.FileMaker;
import rooksoto.c4q.nyc.memefactory.R;
import rooksoto.c4q.nyc.memefactory.View.Custom.DrawableCanvasView;

import static android.content.ContentValues.TAG;

/**
 * Created by rook on 1/9/17.
 */

public class MemePaintFragment extends Fragment implements View.OnClickListener {

    private static final String MPF_PAGE = "MPF PAGE NUM";
    private static final String MPF_TITLE = "MPF TITLE";
    public static final String PIC_URI = "PICTURE URI";

    private Uri uri;

    private DrawableCanvasView drawableCanvasView;
    private View rootView;
    private RelativeLayout rlExportMeme;
    private EditText tvFileName;
    private ImageView imageColor;
    private ImageView imageStroke;
    private ImageView imageSave;

    public Dialog dialogInterface;
    public boolean isDialogVisible = false;

    private CardView cvColor;
    private CardView cvStroke;
    private CardView cvSave;

    private int page;
    private String title;
    private ImageButton shareButton;

    public MemePaintFragment() {
    }

    public static MemePaintFragment newInstance(Uri uri, int page, String title) {
        MemePaintFragment memePaintFragment = new MemePaintFragment();
        memePaintFragment.uri = uri;
        Bundle args = new Bundle();
        args.putInt(MPF_PAGE, page);
        args.putString(MPF_TITLE, title);
        args.putParcelable(PIC_URI, uri);
        memePaintFragment.setArguments(args);
        return memePaintFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(MPF_PAGE, 0);
        title = getArguments().getString(MPF_TITLE);
        uri = getArguments().getParcelable(PIC_URI);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_meme_paint, container, false);
        loadViews();
        return rootView;
    }

    private void loadViews() {
        rlExportMeme = (RelativeLayout) rootView.findViewById(R.id.rl_export_meme);
        tvFileName = (EditText) rootView.findViewById(R.id.tv_file_name);
        shareButton = (ImageButton) rootView.findViewById(R.id.paint_share_button);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage();
            }
        });

        drawableCanvasView = (DrawableCanvasView) rootView.findViewById(R.id.dcv_drawable_canvas);
        Picasso.with(rootView.
                getContext()).
                load(uri).
                into(drawableCanvasView)
        ;

        cvColor = (CardView) rootView.findViewById(R.id.cv_color);
        imageColor = (ImageView) rootView.findViewById(R.id.iv_button_color);
        Picasso.with(rootView.getContext()).load(R.drawable.palette).resize(64, 64).centerInside().into(imageColor);

        cvStroke = (CardView) rootView.findViewById(R.id.cv_stroke);
        imageStroke = (ImageView) rootView.findViewById(R.id.iv_button_stroke);
        Picasso.with(rootView.getContext()).load(R.drawable.brush).resize(64, 64).centerInside().into(imageStroke);

        cvSave = (CardView) rootView.findViewById(R.id.cv_save);
        imageSave = (ImageView) rootView.findViewById(R.id.iv_button_save);
        Picasso.with(rootView.getContext()).load(R.drawable.save).resize(64, 64).centerInside().into(imageSave);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cvColor.setOnClickListener(this);
        cvStroke.setOnClickListener(this);
        cvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_color:
                showColorDialog();
                break;
            case R.id.cv_stroke:
                showStrokeDialog();
                break;
            case R.id.cv_save:
                FileMaker fileMaker = new FileMaker();
                fileMaker.makeMeme(drawableCanvasView, tvFileName.getText() + ".jpg");
                break;
            case R.id.tv_select_color:
                SeekBar sbAlpha = (SeekBar) dialogInterface.findViewById(R.id.sb_alpha);
                SeekBar sbRed = (SeekBar) dialogInterface.findViewById(R.id.sb_red);
                SeekBar sbGreen = (SeekBar) dialogInterface.findViewById(R.id.sb_green);
                SeekBar sbBlue = (SeekBar) dialogInterface.findViewById(R.id.sb_blue);
                drawableCanvasView.setColor(Color.argb(
                        sbAlpha.getProgress(),
                        sbRed.getProgress(),
                        sbGreen.getProgress(),
                        sbBlue.getProgress()));
                isDialogVisible = false;
                dialogInterface.hide();
                break;
            case R.id.tv_select_width:
                SeekBar sbStrokeWidth = (SeekBar) dialogInterface.findViewById(R.id.sb_stroke_width);
                drawableCanvasView.setStrokeWidth(sbStrokeWidth.getProgress());
                isDialogVisible = false;
                dialogInterface.hide();
            default:
                break;
        }
    }

    public SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            View colorPreview = dialogInterface.findViewById(R.id.v_color_preview);
            SeekBar sbAlpha = (SeekBar) dialogInterface.findViewById(R.id.sb_alpha);
            SeekBar sbRed = (SeekBar) dialogInterface.findViewById(R.id.sb_red);
            SeekBar sbGreen = (SeekBar) dialogInterface.findViewById(R.id.sb_green);
            SeekBar sbBlue = (SeekBar) dialogInterface.findViewById(R.id.sb_blue);
            colorPreview.setBackgroundColor(Color.argb(
                    sbAlpha.getProgress(),
                    sbRed.getProgress(),
                    sbGreen.getProgress(),
                    sbBlue.getProgress()))
            ;
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private void showColorDialog() {
        dialogInterface = new Dialog(rootView.getContext());
        dialogInterface.setContentView(R.layout.dialog_color_dialog);
        dialogInterface.setTitle("Change Stroke Color");
        dialogInterface.setCancelable(true);

        TextView selectColor = (TextView) dialogInterface.findViewById(R.id.tv_select_color);

        SeekBar sbAlpha = (SeekBar) dialogInterface.findViewById(R.id.sb_alpha);
        SeekBar sbRed = (SeekBar) dialogInterface.findViewById(R.id.sb_red);
        SeekBar sbGreen = (SeekBar) dialogInterface.findViewById(R.id.sb_green);
        SeekBar sbBlue = (SeekBar) dialogInterface.findViewById(R.id.sb_blue);

        sbAlpha.setOnSeekBarChangeListener(seekBarChangeListener);
        sbRed.setOnSeekBarChangeListener(seekBarChangeListener);
        sbGreen.setOnSeekBarChangeListener(seekBarChangeListener);
        sbBlue.setOnSeekBarChangeListener(seekBarChangeListener);

        int color = drawableCanvasView.getColor();
        sbAlpha.setProgress(Color.alpha(color));
        sbRed.setProgress(Color.red(color));
        sbGreen.setProgress(Color.green(color));
        sbBlue.setProgress(Color.blue(color));

        selectColor.setOnClickListener(this);

        isDialogVisible = true;
        dialogInterface.show();
    }

    private void showStrokeDialog() {
        dialogInterface = new Dialog(rootView.getContext());
        dialogInterface.setContentView(R.layout.dialog_width_dialog);
        dialogInterface.setCancelable(true);
        dialogInterface.setTitle("Select Stroke Width");

        TextView tvSetWidth = (TextView) dialogInterface.findViewById(R.id.tv_select_width);
        tvSetWidth.setOnClickListener(this);

        float strokeWidth = drawableCanvasView.getStrokeWidth();

        SeekBar sbStrokeWidth = (SeekBar) dialogInterface.findViewById(R.id.sb_stroke_width);
        sbStrokeWidth.setProgress((int) strokeWidth);
        sbStrokeWidth.setOnSeekBarChangeListener(seekBarWidthListener);

        isDialogVisible = true;
        dialogInterface.show();
    }

    public SeekBar.OnSeekBarChangeListener seekBarWidthListener = new SeekBar.OnSeekBarChangeListener() {

        Bitmap bitmap = Bitmap.createBitmap(400, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);


        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            ImageView ivWidthPreview = (ImageView) dialogInterface.findViewById(R.id.iv_stroke_width_preview);
            Paint paint = new Paint();
            paint.setColor(drawableCanvasView.getColor());
            paint.setStrokeWidth(drawableCanvasView.getStrokeWidth());

            bitmap.eraseColor(Color.WHITE);
            canvas.drawLine(50, 50, 370, 50, paint);
            ivWidthPreview.setImageBitmap(bitmap);
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private void shareImage() {
        try {
            Bitmap bitmap = getBitmapFromView(rlExportMeme);
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
