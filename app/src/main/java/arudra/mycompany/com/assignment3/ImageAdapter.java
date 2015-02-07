package arudra.mycompany.com.assignment3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by arudra on 06/02/15.
 */
public class ImageAdapter extends ArrayAdapter<Drawable>
{
    private Context mContext;
    private PictureInfo info;
    private ArrayList<Drawable> thumbImage;

    public ImageAdapter(Context c, ArrayList<Drawable> thumbImage)
    {
        super(c ,R.layout.gallery_fragment, thumbImage);
        mContext = c;
        this.thumbImage = thumbImage;
    }


    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageDrawable(thumbImage.get(position));
        return imageView;
    }
}
