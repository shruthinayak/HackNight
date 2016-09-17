package hackday.com.carhack;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by shruthi on 17/9/16.
 */

public class ServerAsyncTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
        // we use the OkHttp library from https://github.com/square/okhttp
        OkHttpClient client = new OkHttpClient();
        Request request =
                new Request.Builder()
                        .url(urls[0])
                        .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Download failed";
    }

    @Override
    protected void onPostExecute(String result) {
        Log.i("FILTEREXECUTE", result);
    }
}