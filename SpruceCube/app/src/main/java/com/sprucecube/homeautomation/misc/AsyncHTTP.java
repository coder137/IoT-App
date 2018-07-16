package com.sprucecube.homeautomation.misc;

import android.os.AsyncTask;
import android.util.Log;

/**
 * DONE, Do this for GET and POST
 */
public class AsyncHTTP extends AsyncTask<String, Void, String> {

    private static final String TAG = "AsyncHTTP";

    private String method;
    private CallbackReceived callbackReceived;

    public static final String GET_METHOD = "get";
    public static final String POST_METHOD = "post";

    public AsyncHTTP(String method, CallbackReceived callbackReceived)
    {
        //this.activityWeakReference = new WeakReference<>(activity);
        this.method = method;
        this.callbackReceived = callbackReceived;
    }

    @Override
    protected String doInBackground(String... urls)
    {
        if(method.equals(GET_METHOD))
        {
            return HTTPMethods.getRequest(urls[0]);
        }
        else if(method.equals(POST_METHOD))
        {
            return HTTPMethods.postRequest(urls[0]);
        }

        return null;
    }

    /**
     * Make this so that a notification can be fired from here (Interfaces)
     * https://stackoverflow.com/questions/9273989/how-do-i-retrieve-the-data-from-asynctasks-doinbackground
     */
    @Override
    protected void onPostExecute(String data)
    {
        super.onPostExecute(data);
        Log.d(TAG, String.valueOf(data));
        callbackReceived.onResponseReceived(data);
    }

    /**
     * Interface to be used for AsyncHTTP Task
     */
    public interface CallbackReceived
    {
        void onResponseReceived(String result); //TODO, This can be an object
    }

}
