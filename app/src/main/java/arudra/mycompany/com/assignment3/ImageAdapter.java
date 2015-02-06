package arudra.mycompany.com.assignment3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by arudra on 06/02/15.
 */
public class ImageAdapter extends BaseAdapter
{
    private Context mContext;
    private PictureInfo info = PictureInfo.getInstance();
    private ArrayList<Drawable> thumbImage;

    public ImageAdapter(Context c)
    {
        mContext = c;

        thumbImage = new ArrayList<>();
        Bitmap image;
        for(int i = 0; i < info.GetSize(); i++)
        {
            image = BitmapFactory.decodeFile(info.ReadFile(i)); //Convert images from JPEG to BMP to display
            //Bitmap scaled = Bitmap.createScaledBitmap(original, newWidth, newHeight, true);
            thumbImage.add(new BitmapDrawable(mContext.getResources(), image));
        }

    }

    public int getCount() {
        return info.GetSize();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
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
