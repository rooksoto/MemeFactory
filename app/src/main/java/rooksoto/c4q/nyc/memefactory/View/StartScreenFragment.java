package rooksoto.c4q.nyc.memefactory.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import rooksoto.c4q.nyc.memefactory.R;

/**
 * Created by ashiquechowdhury on 1/12/17.
 */

public class StartScreenFragment extends Fragment {
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
                Toast.makeText(view.getContext(), "Clicked Camera", Toast.LENGTH_LONG).show();
            }
        };
    }

    private View.OnClickListener galleryClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Clicked Gallery", Toast.LENGTH_LONG).show();
            }
        };
    }
}
