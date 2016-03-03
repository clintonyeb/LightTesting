package com.example.holys.light.FetchData;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.holys.light.DATABASE_DIR.NewsContract;
import com.example.holys.light.NEWS_DIR.NewsFacade;
import com.example.holys.light.Tabs.TabsFragment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class FetchNewsDataJSON extends AsyncTask<TabsFragment, Void, Void> {
    TabsFragment _fragment;

    @Override
    protected Void doInBackground (TabsFragment... params) {
        Log.i("CALLED BACKGROUND", "Here");
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;
        //CastToCorrectType(params[0]);
        _fragment = params[0];

        try {

            final String baseUri = "http://content.guardianapis.com/search?";
            Uri uriBuilder = Uri.parse(baseUri)
                    .buildUpon()
                    .appendQueryParameter("section", "world|opinion|media|society|australia-news|us-news|uk-news|politics|law|")
                    .appendQueryParameter("order-by", "newest")
                    .appendQueryParameter("use-date", "published")
                    .appendQueryParameter("show-fields", "trailText,thumbnail")
                    .appendQueryParameter("page", String.valueOf(TabsFragment.pageSize))
                    .appendQueryParameter("page-size", "10")
                    .appendQueryParameter("api-key", "255d0959-a40e-4fb5-98ae-4d3914d5009a")
                    .build();
            URL url = new URL(uriBuilder.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
            PassJSONForData passer = new PassJSONForData();
            if(_fragment.CLEAR){DeleteRows();}
            InsertIntoTable(passer.PassJSON(forecastJsonStr));
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return null;
    }
        private byte[] EncodeImage(Bitmap bitmap)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }

    private void DeleteRows()
    {
        _fragment.mResolver.delete(NewsContract.CONTENT_URI_WORLD, null, null);
    }

    private void InsertIntoTable(List<NewsFacade> data) {
        for (NewsFacade facade :
                data) {
            ContentValues values = new ContentValues();
            values.put(NewsContract.DataContract.COLUMN_NAME_DATE, facade.getDate());
            values.put(NewsContract.DataContract.COLUMN_NAME_CONTENT, facade.getText());
            values.put(NewsContract.DataContract.COLUMN_NAME_TAG, facade.getTag());
            byte[] image = EncodeImage(facade.getThumb());
            values.put(NewsContract.DataContract.COLUMN_NAME_THUMB, image);
            values.put(NewsContract.DataContract.COLUMN_NAME_TITLE, facade.getTitle());
            values.put(NewsContract.DataContract.COLUMN_NAME_WEBADDRESS, facade.getWebAddress());
            _fragment.mResolver.insert(NewsContract.CONTENT_URI_WORLD, values);


        }
    }
}
