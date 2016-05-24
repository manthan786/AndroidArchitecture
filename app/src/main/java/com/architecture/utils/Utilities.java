package com.architecture.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.client.methods.HttpGet;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

/**
 * Created by Hemu on 12/26/2014.
 */
public class Utilities {

    private static final String TAG = Utilities.class.getSimpleName();
    private static Toast toast;
    private static TextView toastMsgTxt;
    private static final String SHARED_PREF_NAME = "stor_shared_pref";


    public static HttpGet addDefaultHeaders(HttpGet httpGet)
    {
        Map<String, String> httpDefaultHeaders = getHTTPDefaultHeaders();
        for(String headerKey : httpDefaultHeaders.keySet()){
            httpGet.addHeader(headerKey, httpDefaultHeaders.get(headerKey));
        }
        return httpGet;
    }

    public static boolean checkCameraHardware(Context paramContext)
    {
        return paramContext.getPackageManager().hasSystemFeature("android.hardware.camera");
    }

    public static void clearCache(Activity activity)
    {
        SharedPreferences.Editor localEditor = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit();
        localEditor.clear();
        if (Build.VERSION.SDK_INT > 8) {
            localEditor.apply();
        }else{
            localEditor.commit();
        }
    }

    public static void dialNumber(Activity activity, String phoneNoStr)
    {
        try
        {
            activity.startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + phoneNoStr)));
            return;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
            //showToast(activity, "Unable to dial number because no call application exists.");
        }
    }

    public static String getAndroidID(Context context)
    {
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap)
    {
        if (bitmap == null) {
            return new byte[0];
        }
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, localByteArrayOutputStream);
        return localByteArrayOutputStream.toByteArray();
    }

    public static boolean getBooleanFromSharedPreferances(Context context, String key, boolean defaultValue)
    {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
    }

    public static String getBuildDate(Context context)
    {
        String str = "";
        try
        {
            ZipFile zipFile = new ZipFile(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir);
            long time = zipFile.getEntry("classes.dex").getTime();
            str = new SimpleDateFormat("yyyyMMdd").format(new Date(time));
            zipFile.close();
            return str;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return str;
    }

    public static int getDPEquivalentPixels(Context context, float dp)
    {
        return (int)(0.5F + dp * context.getResources().getDisplayMetrics().density);
    }

    public static String getDeviceBrand()
    {
        return Build.BRAND;
    }

    public static String getDeviceModel()
    {
        return Build.MODEL;
    }

    public static String getDeviceSerialNumber()
    {
        String str = Build.SERIAL;
        if ((str == null) || ("".equals(str))) {}
        try
        {
            Class localClass = Class.forName("android.os.SystemProperties");
            str = (String)localClass.getMethod("get", new Class[] { String.class, String.class }).invoke(localClass, "ro.serialno", "unknown");
            return str;
        }
        catch (Exception localException)
        {
            handleException(localException, true, TAG, "Error getting device serial number ");
        }
        return str;
    }

    public static Map<String, String> getHTTPDefaultHeaders()
    {
        Map<String, String> defaultHeaderMap = new HashMap();
        defaultHeaderMap.put("x-hp50-debug", "h4uyp$*50)r%");
        return defaultHeaderMap;
    }

    public static String getAppVersion(Context context)
    {
        try
        {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return str;
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
            localNameNotFoundException.printStackTrace();
        }
        return null;
    }

    public static String getStringFromSharedPreferances(Context context, String key, String defaultValue)
    {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).getString(key, defaultValue);
    }

    public static int getIntFromSharedPreferances(Context context, String key, int defaultValue)
    {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).getInt(key, defaultValue);
    }

    public static String getOSVersion()
    {
        return Build.VERSION.RELEASE;
    }

    public static <T> T getObjectFromSharedPreferances(Context context, String key, Class<T> classType)
    {
        Gson gson = new Gson();
        String str = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).getString(key, null);
        Object localObject = null;
        if (str != null) {
            localObject = gson.fromJson(str, classType);
        }
        return classType.cast(localObject);
    }

    public static String getUniqueDeviceId(Context context)
    {
        String deviceId = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).getString(Global.KEY_DEVICE_ID, null);

        if ((deviceId == null) || ("".equals(deviceId)))
        {
            String serialNumber = getDeviceSerialNumber();
            if ((serialNumber != null) && (!"".equals(serialNumber)) && (!"unknown".equalsIgnoreCase(serialNumber))) {
                deviceId = getMD5EncryptedString(serialNumber);
            }
            if ((deviceId == null) || ("".equals(deviceId)))
            {
                String androidID = getAndroidID(context);
                if(androidID != null & !("".equals(androidID))){
                    deviceId = getMD5EncryptedString(androidID);
                }
            }
            if ((deviceId == null) || ("".equals(deviceId)))
            {
                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                boolean wifiEnabled = wifiManager.isWifiEnabled();
                if (!wifiEnabled) {
                    wifiManager.setWifiEnabled(true);
                }
                String macAddress = wifiManager.getConnectionInfo().getMacAddress();
                if (wifiEnabled) {
                    wifiManager.setWifiEnabled(false);
                }
                if(macAddress != null & !("".equals(macAddress))){
                    deviceId = getMD5EncryptedString(macAddress);
                }
            }
        }

        if (deviceId != null) {
             setStringInSharedPreferances(context, Global.KEY_DEVICE_ID, deviceId + "");
        }

        return deviceId;
    }

    public static String getMD5EncryptedString(String encTarget){
        MessageDigest mdEnc = null;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception while encrypting to md5");
            e.printStackTrace();
        } // Encryption algorithm
        mdEnc.update(encTarget.getBytes(), 0, encTarget.length());
        String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
        while ( md5.length() < 32 ) {
            md5 = "0"+md5;
        }
        return md5;
    }

    public static void handleException(Exception ex, boolean shouldReport, String tag, String msg)
    {
        Log.e(tag, msg, ex);
        if (shouldReport) {

        }

    }

    public static boolean isInteger(String str)
    {
        try
        {
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException localNumberFormatException) {}
        return false;
    }

    public static boolean isLocationServiceEnabled(Context context)
    {
        LocationManager localLocationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean isLocationServiceEnabled = false;
        try
        {
            boolean gpsEnabled = localLocationManager.isProviderEnabled("gps");
            isLocationServiceEnabled = gpsEnabled;
        }
        catch (Exception localException1)
        {
            isLocationServiceEnabled = false;
        }
        if (!isLocationServiceEnabled) {
            try {
                boolean isNetworkLocationEnabled = localLocationManager.isProviderEnabled("network");
                isLocationServiceEnabled =  isNetworkLocationEnabled;
            } catch (Exception localException2) {
                isLocationServiceEnabled = false;
            }
        }
        return isLocationServiceEnabled;
    }

    public static boolean isValidEmailAddress(String emailAddr)
    {
        if ((emailAddr == null) || (StringUtils.EMPTY.equals(emailAddr))) {
            return false;
        }
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(emailAddr).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNoStr)
    {
        return phoneNoStr.matches("^[0-9]{10}$");
    }

    public static void openURLInBrowser(Activity activity, String url)
    {
        try
        {
            activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            return;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
            //showToast(activity, "Unable to open URL because no browser application exists.");
        }
    }

    public static double parseDouble(Object obj, double defaultValue)
    {
        try
        {
            double d = Double.parseDouble(String.valueOf(obj));
            return d;
        }
        catch (Exception localException)
        {
            handleException(localException, true, "Parse Int", localException.getMessage());
        }
        return defaultValue;
    }

    public static double parseDouble(String str, double defaultValue)
    {
        try
        {
            double d = Double.parseDouble(str);
            return d;
        }
        catch (Exception localException)
        {
            handleException(localException, true, "Parse Int", localException.getMessage());
        }
        return defaultValue;
    }

    /**
     *
     * @param obj to be converted into int
     * @return
     */
    public static int parseInt(Object obj)
    {
        try
        {
            int i = Integer.parseInt(String.valueOf(obj));
            return i;
        }
        catch (Exception localException)
        {
            handleException(localException, true, "Parse Int", localException.getMessage());
        }
        return -1;
    }

    /**
     *
      * @param obj String object to be converted to Integer
     * @param defaultValue
     * @return
     */
    public static int parseInt(Object obj, int defaultValue)
    {
        try
        {
            int i = Integer.parseInt(String.valueOf(obj));
            return i;
        }
        catch (Exception localException)
        {
            handleException(localException, true, "Parse Int", localException.getMessage());
        }
        return defaultValue;
    }

    /**
     * Converts string to int
     * @param str String to be converted into integer
     * @return
     */
    public static int parseInt(String str)
    {
        try
        {
            int i = Integer.parseInt(str);
            return i;
        }
        catch (Exception localException)
        {
            handleException(localException, true, "Parse Int", localException.getMessage());
        }
        return -1;
    }

    /**
     * Converts string to int
     * @param str String to be converted into integer
     * @param defaultValue
     * @return Converted Integer value
     */
    public static int parseInt(String str, int defaultValue)
    {
        try
        {
            int i = Integer.parseInt(str);
            return i;
        }
        catch (Exception localException)
        {
            handleException(localException, true, "Parse Int", localException.getMessage());
        }
        return defaultValue;
    }

    /**
     * Converts String to Long
     * @param str String objected to be converted into Integer
     * @return Converted Long value
     */
    public static long parseLong(String str)
    {
        try
        {
            long l = Long.parseLong(str);
            return l;
        }
        catch (Exception localException)
        {
            handleException(localException, true, "Parse Int", localException.getMessage());
        }
        return -1L;
    }

    /**
     * Converts String to Long
     * @param str
     * @param defaultValue
     * @return Converted Long value or Default Value
     */
    public static long parseLong(String str, int defaultValue)
    {
        try
        {
            long l2 = Long.parseLong(str);
            return l2;
        }
        catch (Exception localException)
        {
            long l1 = defaultValue;
            handleException(localException, true, "Parse Int", localException.getMessage());
            return l1;
        }
    }

    /**
     * Serialize object into JSON String & write into shared preferences
     * @param context Context
     * @param key key for the shared preference
     * @param obj Object to be put into shared preference
     */
    public static void putObjectInSharedPreferances(Context context, String key, Object obj)
    {
        setStringInSharedPreferances(context, key, new Gson().toJson(obj));
    }

    /**
     * Removes object from shared preferences
     * @param context Context
     * @param key key to identify object in Shared Preference
     */
    public static void removeObjectFromSharedPreferances(Context context, String key)
    {
        SharedPreferences.Editor localEditor = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit();
        localEditor.remove(key);
        localEditor.commit();
    }

    /**
     * Rounds the double value up to 2 decimal points
     * @param dVal
     * @return Rounded value
     */
    public static double roundTwoDecimals(double dVal)
    {
        return Double.valueOf(new DecimalFormat("#.##").format(dVal)).doubleValue();
    }

    /**
     * Sets the boolean value in shared preference
     * @param context Context Object
     * @param key key for value
     * @param boolVal boolean value
     */
    public static void setBooleanInSharedPreferances(Context context, String key, boolean boolVal)
    {
        SharedPreferences.Editor localEditor = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit();
        localEditor.putBoolean(key, boolVal);
        localEditor.commit();
    }

    /**
     *
     * @param context Context Object
     * @param key key for value
     * @param intVal Integer Value to be set in shared preference
     */
    public static void setIntInSharedPreferances(Context context, String key, int intVal)
    {
        SharedPreferences.Editor localEditor = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit();
        localEditor.putInt(key, intVal);
        localEditor.commit();
    }

    /**
     *
     * @param context Context Object
     * @param key key for the value
     * @param longVal Long Value
     */
    public static void setLongInSharedPreferances(Context context, String key, Long longVal)
    {
        SharedPreferences.Editor localEditor = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit();
        localEditor.putLong(key, longVal.longValue());
        localEditor.commit();
    }

    /**
     *
     * @param context Context Object
     * @param key Key for the String Value
     * @param strVal String value to be put in Shared Preference
     */
    public static void setStringInSharedPreferances(Context context, String key, String strVal)
    {
        SharedPreferences.Editor localEditor = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit();
        localEditor.putString(key, strVal);
        if (Build.VERSION.SDK_INT > 8)
        {
            localEditor.apply();
            return;
        }
        localEditor.commit();
    }

    /**
     *
     * @param str String to be converted into Integer
     * @param defaultVal Default value if fails
     * @return converted int value or default  value
     */
    public static int valueOf(String str, int defaultVal)
    {
        try
        {
            int i = Integer.valueOf(str).intValue();
            return i;
        }
        catch (NumberFormatException localNumberFormatException) {}
        return defaultVal;
    }


    public static String removeLastChar(String str) {
        return str.substring(0,str.length()-1);
    }




    public static  boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
