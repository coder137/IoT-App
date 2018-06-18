package com.sprucecube.homeautomation.misc;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AndroidFileHandler
{
    //TODO, Write data to file
    public static boolean writeDataToFile(Context context, String filename, String fileContents)
    {
        //File file = new File(context.getFilesDir(), filename);
        FileOutputStream outputStream;

        try
        {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();

            return true;
        } catch (IOException e)
        {
            //e.printStackTrace();
            return false;
        }

        //return false;
    }

    //TODO, Write data to file and append
    public static boolean writeDataToFileAppend(Context context, String filename, String fileContents)
    {
        FileOutputStream outputStream;

        try
        {
            outputStream = context.openFileOutput(filename, Context.MODE_APPEND);
            outputStream.write(fileContents.getBytes());
            outputStream.close();

            return true;
        } catch (IOException e)
        {
            //e.printStackTrace();
            return false;
        }

        //return false;
    }

    //TODO, Read data from file
    public static String readDataFromFile(Context context, String filename)
    {
        FileInputStream inputStream;
        StringBuffer buffer;
        try {
            inputStream = context.openFileInput(filename);

            int c;
            //thread safe, use
            buffer = new StringBuffer();
            //StringBuilder sb = new StringBuilder();
            while( (c = inputStream.read()) != -1){
                buffer.append((char)c);
            }

            inputStream.close();

            return buffer.toString();

        }
        catch (IOException e)
        {
            //e.printStackTrace();
            return null;
        }

        //return null;
    }
}
