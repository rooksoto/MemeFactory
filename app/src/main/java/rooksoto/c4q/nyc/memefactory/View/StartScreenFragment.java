package rooksoto.c4q.nyc.memefactory.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import rooksoto.c4q.nyc.memefactory.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ashiquechowdhury on 1/12/17.
 */

public class StartScreenFragment extends Fragment {
    public static final String MEME_FILE_NAME = "meme_bitmap.png";
    private static final int GALLERY_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private ImageView memeLogoIView;
    private ImageView cameraIView;
    private ImageView galleryIView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_screen_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        memeLogoIView = (ImageView) view.findViewById(R.id.logo_imageView);
        cameraIView = (ImageView) view.findViewById(R.id.camera_button);
        galleryIView = (ImageView) view.findViewById(R.id.gallery_button);

        Glide.with(this)
                .load(R.raw.memefactory_logo)
                .into(memeLogoIView);

        cameraIView.setOnClickListener(cameraClickListener());
        galleryIView.setOnClickListener(galleryClickListener());

    }


    private View.OnClickListener cameraClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        };
    }

    private View.OnClickListener galleryClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            Bitmap image = (Bitmap) data.getExtras().get("data");

            setupBitmapImage(image);
            Intent intent = new Intent(getContext(), PhotoActivity.class);
            intent.putExtra("image", MEME_FILE_NAME);
            startActivity(intent);
        }

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            Uri selectedImage = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContext().getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap image = BitmapFactory.decodeStream(imageStream);

            setupBitmapImage(image);
            Intent intent = new Intent(getContext(), PhotoActivity.class);
            intent.putExtra("image", MEME_FILE_NAME);
            startActivity(intent);
        }
    }

    private void setupBitmapImage(Bitmap image) {
        try {
            String filename = "meme_bitmap.png";
            FileOutputStream stream = getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);

            stream.close();
            image.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
