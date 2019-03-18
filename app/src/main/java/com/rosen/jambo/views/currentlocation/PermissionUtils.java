package com.rosen.jambo.views.currentlocation;



import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;


/**
 * Credits to Jaison published on 02/05/17.
 */

public class PermissionUtils
{

    Context context;
    Activity current_activity;

    PermissionResultCallback permissionResultCallback;


    ArrayList<String> permission_list = new ArrayList<>();
    ArrayList<String> listPermissionsNeeded = new ArrayList<>();

    String dialog_content = "";
    int req_code;


    public PermissionUtils(Context context, PermissionResultCallback callback)
    {
        this.context=context;
        this.current_activity= (Activity) context;

        permissionResultCallback= callback;
    }


    /**
     * Check the API Level & Permission
     *
     * @param permissions
     * @param dialog_content
     * @param request_code
     */

    public void checkPermission(ArrayList<String> permissions, String dialog_content, int request_code)
    {
        this.permission_list = permissions;
        this.dialog_content = dialog_content;
        this.req_code = request_code;

        if(Build.VERSION.SDK_INT >= 23)
        {
            if (checkAndRequestPermissions(permissions, request_code))
            {
                permissionResultCallback.PermissionGranted(request_code);
            }
        }
        else
        {
            permissionResultCallback.PermissionGranted(request_code);
        }

    }


    /**
     * Check and request the Permissions
     *
     * @param permissions
     * @param requestCode
     * @return
     */

    private  boolean checkAndRequestPermissions(ArrayList<String> permissions, int requestCode) {

        if(permissions.isEmpty())
        {
            listPermissionsNeeded = new ArrayList<>();

            for(int i=0;i<permissions.size();i++)
            {
                int hasPermission = ContextCompat.checkSelfPermission(current_activity,permissions.get(i));

                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(permissions.get(i));
                }

            }

            if (!listPermissionsNeeded.isEmpty())
            {
                ActivityCompat.requestPermissions(current_activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), requestCode);
                return false;
            }
        }

        return true;
    }


    public interface PermissionResultCallback
    {
        void PermissionGranted(int request_code);
    }
}


