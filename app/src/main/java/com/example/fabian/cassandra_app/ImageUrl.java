package com.example.fabian.cassandra_app;

/**
 * Created by Fabian on 24/04/2015.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.net.URL;

public class ImageUrl extends AsyncTask<Void, Void, Bitmap>
{
    private String url;

    public ImageUrl(String url)
    {
        this.url = url;
    }

    @Override
    protected Bitmap doInBackground(Void... params)
    {
        try
        {
            URL imageURL = new URL(url);
            Main.initialTime = System.nanoTime();
            Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openStream());
            Main.finalTime = System.nanoTime();
            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result)
    {
        super.onPostExecute(result);
    }
}

