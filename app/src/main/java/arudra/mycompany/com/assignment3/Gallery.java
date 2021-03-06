package arudra.mycompany.com.assignment3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Gallery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Gallery extends android.support.v4.app.Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final PictureInfo info = PictureInfo.getInstance();
    private Context context;
    private Activity activity;

    // TODO: Rename and change types and number of parameters
    public static Gallery newInstance(String param1, String param2)
    {
        Gallery fragment = new Gallery();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Gallery() { }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
        context = activity.getApplicationContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.gallery_fragment, container, false);

        //Read Files and setup Drawable array
        ArrayList<Drawable> thumbImage = new ArrayList<>();
        Bitmap image;
        for(int i = 0; i < info.GetSize(); i++)
        {
            image = BitmapFactory.decodeFile(info.ReadFile(i)); //Convert images from JPEG to BMP to display
            thumbImage.add(new BitmapDrawable(context.getResources(), image));
        }

        //Set Adapter
        ImageAdapter imageAdapter = new ImageAdapter(context, thumbImage);
        GridView gridView = (GridView) rootView.findViewById(R.id.grid);

        try {
            gridView.setAdapter(imageAdapter);
        } catch(Exception e) { e.printStackTrace(); }

        //Delete an image
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                new AlertDialog.Builder(activity)
                        .setTitle("Delete Image")
                        .setMessage("Do you want to delete this image?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Delete the image
                                info.DeleteFile(position);
                                android.support.v4.app.Fragment fragment = new Main();
                                getFragmentManager()
                                        .beginTransaction().replace(R.id.fragment_container,fragment)
                                        .commit();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { /*Do nothing*/ }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).show();

            }
        });
        return rootView;
    }


}
