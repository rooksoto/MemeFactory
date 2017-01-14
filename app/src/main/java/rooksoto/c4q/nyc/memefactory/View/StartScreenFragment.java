package rooksoto.c4q.nyc.memefactory.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import rooksoto.c4q.nyc.memefactory.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ashiquechowdhury on 1/12/17.
 */

public class StartScreenFragment extends Fragment {
    public static final String IMAGEURI = "nyc.c4q.ashiquechowdhury.IMAGEURI";
    private static final String IMAGEFILENAME = "CAMERAIMAGE.jpg";
    private static final int GALLERY_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private File output = null;

    private ImageView memeLogoIView;
    private ImageView cameraIView;
    private ImageView galleryIView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_screen, container, false);
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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Saves File to Internal Storage
                File imageLocation = getActivity()
                        .getApplicationContext()
                        .getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                output = new File(imageLocation, IMAGEFILENAME);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(
                        getContext(),
                        getContext().getApplicationContext().getPackageName() + ".provider", output));
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
        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            Uri imageUri = data.getData();

            Intent intent = new Intent(getContext(), PhotoActivity.class);
            intent.putExtra(IMAGEURI, imageUri);
            startActivity(intent);
        }

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
            Toast.makeText(getContext(), "Saved to Gallery", Toast.LENGTH_LONG).show();
        }
    }
}
