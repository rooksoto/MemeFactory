package rooksoto.c4q.nyc.memefactory.View;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

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
        Uri memeImageUri = getIntent().getParcelableExtra(StartScreenFragment.IMAGEURI);

        ViewPager vPager = (ViewPager) findViewById(R.id.vpager_photo);
        adapterViewPager = new MemePagerAdapter(getSupportFragmentManager(), memeImageUri);
        vPager.setAdapter(adapterViewPager);

//        memePhotoImageView = (ImageView) findViewById(R.id.meme_photo_IView);

//        Bitmap bitmap = null;
//
//        try {
//            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), memeImageUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//
    }
}
