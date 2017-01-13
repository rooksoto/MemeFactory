package rooksoto.c4q.nyc.memefactory.Presenter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by rook on 1/9/17.
 */

public class FileMaker {

    private final String IMAGE_FORMAT = "PNG";
    private static File memePic;

    public File makeMeme(View view, String fileName) {

        Bitmap image = Bitmap.
                createBitmap(
                        view.getWidth(),
                        view.getHeight(),
                        Bitmap.Config.RGB_565
                );
        view.draw(new Canvas(image));

        try {
            memePic = new File(
                    Environment.getExternalStorageDirectory().toString(),
                    fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(memePic);
            image.compress(Bitmap.CompressFormat.valueOf(IMAGE_FORMAT), 90, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(view.getContext(), "Failed to create file", Toast.LENGTH_SHORT).show();
        }
        return memePic;
    }
}