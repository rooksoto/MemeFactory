package rooksoto.c4q.nyc.memefactory.View.MemeFragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rooksoto.c4q.nyc.memefactory.Presenter.FileMaker;
import rooksoto.c4q.nyc.memefactory.R;

/**
 * Created by huilin on 1/14/17.
 */

public class DogeFragment extends Fragment implements View.OnTouchListener, View.OnClickListener, View.OnLongClickListener {

    public static final String DOGE_PAGE = "DOGE PAGE NUM";
    public static final String DOGE_TITLE = "DOGE TITLE";
    public static final String PIC_URI = "PICTURE URI";
    Uri uri;
    Float dX;
    Float dY;

    private String title;
    private int page;
    private int fontColor = Color.WHITE;
    private ImageView imageView;
    private View rootView;
    private List<EditText> captionTvs = new ArrayList<>();
    private RelativeLayout rootLayout;
    private EditText tvFileName;
    private CardView colorCV;
    private ImageView fontColorIV;
    private Dialog dialogInterface;
    private boolean isDialogVisible;
    private CardView cvSave;
    private ImageView imageSave;


    public static DogeFragment newInstance(Uri uri, int page, String title) {
        DogeFragment dogeFragment = new DogeFragment();
        dogeFragment.uri = uri;
        Bundle args = new Bundle();
        args.putInt(DOGE_PAGE, page);
        args.putString(DOGE_TITLE, title);
        args.putParcelable(PIC_URI, uri);
        dogeFragment.setArguments(args);
        return dogeFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(DOGE_PAGE, 0);
        title = getArguments().getString(DOGE_TITLE);
        uri = getArguments().getParcelable(PIC_URI);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_doge, container, false);
        tvFileName = (EditText) rootView.findViewById(R.id.tv_file_name);
        imageView = (ImageView) rootView.findViewById(R.id.meme_photo_IView);
        Picasso.with(rootView.getContext()).load(uri).error(R.drawable.doge).into(imageView);

        colorCV = (CardView) rootView.findViewById(R.id.cv_color);
        fontColorIV = (ImageView) rootView.findViewById(R.id.iv_button_color);
        Picasso.with(rootView.getContext()).load(R.drawable.palette).resize(64, 64).centerInside().into(fontColorIV);

        cvSave = (CardView) rootView.findViewById(R.id.cv_save);
        imageSave = (ImageView) rootView.findViewById(R.id.iv_button_save);
        Picasso.with(rootView.getContext()).load(R.drawable.save).resize(64, 64).centerInside().into(imageSave);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView.setOnLongClickListener(this);
        colorCV.setOnClickListener(this);
        cvSave.setOnClickListener(this);
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
    public boolean onLongClick(View view) {
        addCaption(view);
        return true;
    }

    private void addCaption(View view) {
        rootLayout = (RelativeLayout) rootView.findViewById(R.id.layout_iv);
        Typeface typeface = Typeface.createFromAsset(view.getContext().getAssets(), "LDFComicSans.ttf");

        EditText captionText = new EditText(view.getContext());
        createCaption(view, typeface, captionText);
        captionText.setOnTouchListener(this);

        rootLayout.addView(captionText);

        captionTvs.add(captionText);
    }

    private void createCaption(View view, Typeface typeface, EditText captionText) {
        captionText.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        captionText.setBackgroundColor(Color.TRANSPARENT);
        captionText.setTypeface(typeface);
        captionText.setText(" ");
        captionText.setTextColor(fontColor);
        captionText.requestFocus();
        ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_FORCED,
                        InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

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

        int color = fontColor;
        sbAlpha.setProgress(Color.alpha(color));
        sbRed.setProgress(Color.red(color));
        sbGreen.setProgress(Color.green(color));
        sbBlue.setProgress(Color.blue(color));

        selectColor.setOnClickListener(this);


        isDialogVisible = true;
        dialogInterface.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_color:
                showColorDialog();
                break;
            case R.id.cv_save:
                FileMaker fileMaker = new FileMaker();
                fileMaker.makeMeme(rootView, tvFileName.getText() + ".jpg");
                break;
            case R.id.tv_select_color:
                SeekBar sbAlpha = (SeekBar) dialogInterface.findViewById(R.id.sb_alpha);
                SeekBar sbRed = (SeekBar) dialogInterface.findViewById(R.id.sb_red);
                SeekBar sbGreen = (SeekBar) dialogInterface.findViewById(R.id.sb_green);
                SeekBar sbBlue = (SeekBar) dialogInterface.findViewById(R.id.sb_blue);
                fontColor = Color.argb(
                        sbAlpha.getProgress(),
                        sbRed.getProgress(),
                        sbGreen.getProgress(),
                        sbBlue.getProgress());
                isDialogVisible = false;
                dialogInterface.hide();
                break;
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
}
