package arudra.mycompany.com.assignment3;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Abhishek on 2/3/2015.
 */
public class PictureInfo
{
    //Arraylist to hold Picture File Path
    private static ArrayList<String> FilePath;

    //ArrayList to hold Picture GPS info
    private static ArrayList<String> Location;


    private static PictureInfo ourInstance = new PictureInfo();

    public static PictureInfo getInstance() { return ourInstance; }

    private PictureInfo()
    {
        FilePath = new ArrayList<String>();
        Location = new ArrayList<String>();
    }

    public void AddFile(String file)
    {
        FilePath.add(file);
        //Log.d("File Added", "file: " + file);
    }

    public void AddLocation (String location)
    {
        Location.add(location);
        //Log.d("Location Added", "location: " + location);
    }

    public int GetSize() { return FilePath.size(); }

    public String ReadFile(int index) { return FilePath.get(index); }

    public String ReadLocation(int index) { return Location.get(index); }



}
