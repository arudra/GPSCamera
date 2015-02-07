package arudra.mycompany.com.assignment3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by arudra on 06/02/15.
 */
public class ImageAdapter extends ArrayAdapter<Drawable>
{
    private PictureInfo info = PictureInfo.getInstance();
    private ArrayList<Drawable> thumbImage;
    private LayoutInflater inflater;

    public ImageAdapter(Context c, ArrayList<Drawable> thumbImage)
    {
        super(c ,R.layout.gallery_fragment, thumbImage);
        inflater = LayoutInflater.from(c);
        this.thumbImage = thumbImage;
    }


    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View view, ViewGroup parent)
    {
        ImageView picture;
        TextView name;

        if (view == null)
        {  // if it's not recycled, initialize some attributes
            view = inflater.inflate(R.layout.gridview_item, parent, false);
            view.setTag(R.id.picture, view.findViewById(R.id.picture));
            view.setTag(R.id.text, view.findViewById(R.id.text));
            /*
            picture = new ImageView(mContext);
            picture.setLayoutParams(new GridView.LayoutParams(85, 85));
            picture.setScaleType(ImageView.ScaleType.CENTER_CROP);
            picture.setPadding(8, 8, 8, 8);
        } else {
            picture = (ImageView) convertView; */
        }

        picture = (ImageView) view.getTag(R.id.picture);
        name = (TextView) view.getTag(R.id.text);

        picture.setImageDrawable(thumbImage.get(position));
        name.setText(info.ReadLocation(position));

        return view;
    }
}
