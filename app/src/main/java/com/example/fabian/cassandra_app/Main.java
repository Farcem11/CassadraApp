package com.example.fabian.cassandra_app;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class Main extends ActionBarActivity
{
    static long initialTime;
    static long finalTime;
    double size = 1487458;
    String Url = "https://scontent-mia.xx.fbcdn.net/hphotos-xpa1/v/t35.0-12/11187877_920513957971377_1198075127_o.jpg?oh=45be3ccbd639375a427a5f266d0caf6c&oe=553D2ECA";
    ImageView imageView;
    TextView latitudeView;
    TextView longitudeView;
    TextView timeView;
    TextView sizeView;
    TextView speedView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        imageView = (ImageView) findViewById(R.id.imageView);
        latitudeView = (TextView)findViewById(R.id.latitudeView);
        longitudeView = (TextView)findViewById(R.id.longitudeView);
        timeView = (TextView)findViewById(R.id.timeView);
        sizeView = (TextView)findViewById(R.id.sizeView);
        speedView = (TextView)findViewById(R.id.speedView);
        findViewById(R.id.btnDownload).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    startDownload();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    protected double sizeOf(Bitmap data)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1)
        {
            return (data.getRowBytes() * data.getHeight());
        }
        else
        {
            return data.getByteCount();
        }
    }

    public void startDownload() throws ExecutionException, InterruptedException
    {
        Bitmap bitmap = new ImageUrl(Url).execute().get();
        imageView.setImageBitmap(bitmap);
        double time = (finalTime - initialTime)/1e6/1000;
        size = sizeOf(bitmap);

        timeView.setText(String.format("%.2f", time) + " secs");
        sizeView.setText(String.valueOf(size) + " kb");
        speedView.setText(String.format("%.2f", (size/1024/1024) / time)  + " mb per sec");

        LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location location = manager.getLastKnownLocation(locationProvider);
        if(location != null)
        {
            latitudeView.setText(String.valueOf(location.getLatitude()));
            longitudeView.setText(String.valueOf(location.getLongitude()));
        }
    }
}
