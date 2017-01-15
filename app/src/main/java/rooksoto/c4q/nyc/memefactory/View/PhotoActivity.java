package rooksoto.c4q.nyc.memefactory.View;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.IOException;

import rooksoto.c4q.nyc.memefactory.Presenter.MemePagerAdapter;
import rooksoto.c4q.nyc.memefactory.R;

/**
 * Created by ashiquechowdhury on 1/12/17.
 */
public class PhotoActivity extends AppCompatActivity{
    private ImageView memePhotoImageView;
    private MemePagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vpager_photo);

        ViewPager vPager = (ViewPager) findViewById(R.id.vpager_photo);
        adapterViewPager = new MemePagerAdapter(getSupportFragmentManager());
        vPager.setAdapter(adapterViewPager);

//        memePhotoImageView = (ImageView) findViewById(R.id.meme_photo_IView);

        Uri memeImageUri = getIntent().getParcelableExtra(StartScreenFragment.IMAGEURI);
        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), memeImageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

//
    }
}
