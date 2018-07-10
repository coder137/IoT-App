package com.sprucecube.homeautomation.misc;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPMethods
{
    private static final String TAG = "HTTPMethods";

    public static String getRequest(String url)
    {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        String data = null;
        try
        {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful())
            {
                data = response.body().string();
                Log.d(TAG, String.valueOf(data));
            }
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            Log.wtf(TAG, "Error Occured: getRequest");
        }

        return data;
    }

    public static String postRequest(String url)
    {
        final MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");
        String text = "Test";
        RequestBody body = RequestBody.create(TEXT, text);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "text/plain")
                .build();

        String data = null;
        try
        {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful())
            {
                data = response.body().string();
                Log.d(TAG, String.valueOf(data));
            }
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            Log.wtf(TAG, "Error Occured: postRequest");
        }

        return data;
    }

    public static void postRequestEnqueue(String url, Callback responseCall)
    {
        final MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");
        String text = "Test";
        RequestBody body = RequestBody.create(TEXT, text);
        Log.d(TAG, body.contentType().toString());

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "text/plain")
                .build();

        client.newCall(request).enqueue(responseCall);
    }

    public static void getRequestEnqueue(String url, Callback responseCall)
    {

    }
}
