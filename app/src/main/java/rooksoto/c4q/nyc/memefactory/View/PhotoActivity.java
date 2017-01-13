package rooksoto.c4q.nyc.memefactory.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import rooksoto.c4q.nyc.memefactory.R;

/**
 * Created by ashiquechowdhury on 1/12/17.
 */
public class PhotoActivity extends AppCompatActivity{
    private ImageView memePhotoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_photo);

        memePhotoImageView = (ImageView) findViewById(R.id.meme_photo_IView);

        Intent intent = getIntent();
        Bitmap memeImage = intent.getParcelableExtra(StartScreenFragment.MEME_PHOTO);
        memePhotoImageView.setImageBitmap(memeImage);
    }
}
