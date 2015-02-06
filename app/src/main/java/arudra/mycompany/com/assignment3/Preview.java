package arudra.mycompany.com.assignment3;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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
        Log.d("Surface","Surface was created");
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {
        Log.d("Surface","Surface was destroyed");
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h)
    {
        //preview surface not created
        if (mHolder.getSurface() == null) { return; }

        //Stop Preview
        /*
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace(); //started a preview that didn't exist
        } */

        //Rotate Preview + Camera
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(0,cameraInfo);
        int rotation = this.activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch(rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                parameters.setPreviewSize(h,w);
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                parameters.setPreviewSize(w,h);
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                parameters.setPreviewSize(h,w);
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                parameters.setPreviewSize(w,h);
                break;
        }
        int result;
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT ) {
            result = (cameraInfo.orientation + degrees) %360;
            result = (360 - result) % 360;
        }
        else {
            result = (cameraInfo.orientation - degrees + 360) % 360;
        }

        //Restart Preview
        try {
            //mCamera.setPreviewDisplay(mHolder);
            mCamera.setDisplayOrientation(result);
            //mCamera.setParameters(parameters);
            mCamera.startPreview();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
