package com.example.venky.tictactoe;


import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


import static android.os.ParcelFileDescriptor.MODE_WORLD_READABLE;

/**
 * Created by venky on 3/28/18.
 */

public class FileUtil {

    private static final String TAG = FileUtil.class.getSimpleName();

    //util file cant create objects
    private FileUtil(){}

    /**
     * Save data entry to file
     * @param file
     * @param data
     */
    public static void Save(File file, LogEntry data)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file,true);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try
        {
            try
            {
                    fos.write(data.toString().getBytes());

            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }

    /**
     * Read data from file
     * @param file
     * @return String of file content
     */
    public static String Load(File file)
    {
        FileInputStream fis = null;

        String ret = "";

        try {
                fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                ret = stringBuilder.toString();
        }
        catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        }
        System.out.print(ret);
        return ret;
    }

}
