package rooksoto.c4q.nyc.memefactory.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import rooksoto.c4q.nyc.memefactory.Presenter.FileMaker;
import rooksoto.c4q.nyc.memefactory.R;

public class MainActivity extends AppCompatActivity {
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileMaker fileMaker = new FileMaker();

        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_holder, new StartScreenFragment()).commit();
    }
}
