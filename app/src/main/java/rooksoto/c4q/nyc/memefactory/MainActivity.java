package rooksoto.c4q.nyc.memefactory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rooksoto.c4q.nyc.memefactory.Presenter.FileMaker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileMaker fileMaker = new FileMaker();
    }
}
