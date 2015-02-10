package arudra.mycompany.com.assignment3;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import java.io.IOException;


/**
 * Created by Abhishek on 2/3/2015.
 */
public class Preview extends SurfaceView implements SurfaceHolder.Callback
{
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Activity activity;

    public Preview (Activity activity, Context context, Camera camera)
    {
        super(context);
        this.activity = activity;
        mCamera = camera;

        //Install a SurfaceHolder.Callback so we get notified when surface is created/destroyed
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder)
    {
        //Surface Created -> camera should draw the preview
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {  }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h)
    {
        //preview surface not created
        if (mHolder.getSurface() == null) { return; }

        //Rotate Preview + Camera
        Camera.Parameters parameters = mCamera.getParameters();
        //Get Supported Preview Sizes
        //List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
        Camera.Size previewSize = parameters.getPreviewSize();//previewSizes.get(2);
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Display display = ((WindowManager)activity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Camera.getCameraInfo(0,cameraInfo);
        int rotation = display.getRotation();

        switch(rotation) {
            case Surface.ROTATION_0:
                parameters.setPreviewSize(previewSize.height,previewSize.width);
                mCamera.setDisplayOrientation(90);
                break;
            case Surface.ROTATION_90:
                parameters.setPreviewSize(previewSize.width,previewSize.height);
                break;
            case Surface.ROTATION_180:
                parameters.setPreviewSize(previewSize.height,previewSize.width);
                break;
            case Surface.ROTATION_270:
                parameters.setPreviewSize(previewSize.width,previewSize.height);
                mCamera.setDisplayOrientation(180);
                break;
        }
        Log.d("Preview","Width:" + previewSize.width  + " Height:" + previewSize.height);
        Log.d("Preview","Camera orientation : " + cameraInfo.orientation);

        //Restart Preview
        try {
            mCamera.setParameters(parameters);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch(Exception e)
        {
            e.printStackTrace();
            Log.d("Preview","Preview change failed");
        }
    }

}
