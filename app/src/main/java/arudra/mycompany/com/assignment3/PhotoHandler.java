package arudra.mycompany.com.assignment3;

import android.content.Context;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Abhishek on 2/3/2015.
 */
public class PhotoHandler implements Camera.PictureCallback
{
    private final Context context;

    public PhotoHandler(Context context) { this.context = context; }

    @Override
    public void onPictureTaken(byte[] data, Camera camera)
    {
        camera.startPreview();  //Restart Preview
        File picFileDir = context.getExternalFilesDir(null);
        if (picFileDir == null || (!picFileDir.exists() && !picFileDir.mkdirs()))
        {
            Log.d("File Dir", "Couldn't create File Directory");
            Toast.makeText(context, "Can't create directory to save images!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Create File for Image
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
            String date = dateFormat.format(new Date());
            String photoFile = "Picture_" + date + ".jpg";
            String filename = picFileDir.getAbsolutePath() + File.separator + photoFile;
            File picFile = new File(filename);

            //Save File path + GPS to ArrayLists
            PictureInfo info = PictureInfo.getInstance();
            info.AddFile(filename);
            info.AddLocation("default");

            try {
                FileOutputStream fos = new FileOutputStream(picFile);
                fos.write(data);
                fos.close();
                Toast.makeText(context, "Image saved: " + photoFile, Toast.LENGTH_LONG).show();
                Log.d("Image", "The Image was saved to " + filename);
            } catch (Exception e) {
                Toast.makeText(context, "Can't save image!", Toast.LENGTH_LONG).show();
                Log.e("Image", "Image FAILED to save");
            }
        }
    }
}
