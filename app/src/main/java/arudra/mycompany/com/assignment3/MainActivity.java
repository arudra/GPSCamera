package arudra.mycompany.com.assignment3;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;

import java.io.File;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .build();

        //Read file Directory and fill up global array
        FillPictureInfo();

        //Start Main Fragment
        Main NewFragment = new Main();
        NewFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,NewFragment).commit();
    }

    //Adding All Image file names to PictureInfo
    private void FillPictureInfo()
    {
        PictureInfo info = PictureInfo.getInstance();
        File folder = this.getExternalFilesDir(null);

        if (folder == null || (!folder.exists() && !folder.mkdirs()))
        {
            Log.d("File Dir", "Couldn't find File Directory");
            Toast.makeText(this, "Can't find directory to read images!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            for (final File fileEntry : folder.listFiles())
            {
                info.AddFile(fileEntry.getName());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClickPictureMode (View view)
    {
        //Start Picture Fragment
        Picture NewFragment = new Picture();
        NewFragment.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,NewFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onClickGalleryMode (View view)
    {
        //Start Gallery Fragment
        Gallery NewFragment = new Gallery();
        NewFragment.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,NewFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
