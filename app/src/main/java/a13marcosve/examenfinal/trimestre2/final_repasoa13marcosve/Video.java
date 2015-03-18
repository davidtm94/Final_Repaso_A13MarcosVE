package a13marcosve.examenfinal.trimestre2.final_repasoa13marcosve;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;


public class Video extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        isExternalStorageWritable();
        isExternalStorageReadable();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video, menu);
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

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    //GRAVAR VIDEOS
    static final int REQUEST_VIDEO_CAPTURE = 1;
    int random = 0;
    File ruta;
    File arquivo;

    public void dispatchTakeVideoIntent(View view) {
        random++;
        ruta = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        arquivo = new File(ruta, "video" + random + ".mp4");

        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivo));
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    Toast.makeText(getApplicationContext(),
                            "NON HAI IMAXE/VIDEO A GARDAR", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                ruta = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
                arquivo = new File(ruta, "video" + random + ".mp4");

                if (!arquivo.exists())
                    return; // Non hai video

                android.widget.MediaController media = new android.widget.MediaController(
                        this);

                VideoView vidview = (VideoView) findViewById(R.id.videoView);
                vidview.setVideoURI(Uri.fromFile(arquivo));
                vidview.setMediaController(media);
                vidview.start();

                Toast.makeText(this,
                        "Video gardado en " + arquivo.getAbsolutePath(),
                        Toast.LENGTH_SHORT).show();

            }
        }
    }
}
