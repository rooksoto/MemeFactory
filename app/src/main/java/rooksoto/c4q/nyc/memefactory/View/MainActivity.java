package rooksoto.c4q.nyc.memefactory.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rooksoto.c4q.nyc.memefactory.Presenter.FileMaker;
import rooksoto.c4q.nyc.memefactory.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileMaker fileMaker = new FileMaker();
    }


}
