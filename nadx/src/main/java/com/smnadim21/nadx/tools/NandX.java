package com.smnadim21.nadx.tools;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.smnadim21.nadx.R;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class NandX {

    static int RETRY_LIMIT = 3;
    public static final String TAG = "tagsByNadim";

    /*        <color name="s_green">#5cb85c</color>
     *//*   <color name="s_red">#d9534f</color>*/
    public static final String COLOR_RED = "#d9534f";
    public static final String COLOR_GREEN = "#5cb85c";
    public static final String COLOR_BLUE = "#17A2B8";
    public static final String COLOR_YELLOW = "#FFB822";



/*
    private static void sendMessageToActivity(Context context, boolean loginStatus) {
        Intent intent = new Intent("LoginUpdates");
        intent.putExtra("loginStatus", loginStatus);
        // You can also include some extra data.
       *//* intent.putExtra("Status", msg);
        Bundle b = new Bundle();
        b.putParcelable("Location", l);*//*
        //intent.putExtra("Location", b);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }*/

   /* public static void sendMessageToActivity(Context context, String msg) {
        Intent intent = new Intent(INTENT_GEO_UPDATE);
        intent.putExtra(INTENT_EXTRA_GEO_UPDATE, msg);
        // You can also include some extra data.
       *//* intent.putExtra("Status", msg);
        Bundle b = new Bundle();
        b.putParcelable("Location", l);*//*
        //intent.putExtra("Location", b);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }*/

    public static void hideKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public static void print(String str) {
        Log.e(">>>>> ", str);
    }

    public static void printJson(Object obj) {
        Log.e(">>>>> ", getPrettyJson(obj));
    }

    public static void log(String str) {
        Log.e(">>>>> ", str);
    }

    public static void sendMessageToActivity(Context context, String nIntent, String nExtra, String msg) {
        Intent intent = new Intent(nIntent);
        intent.putExtra(nExtra, msg);
        // You can also include some extra data.
       /* intent.putExtra("Status", msg);
        Bundle b = new Bundle();
        b.putParcelable("Location", l);*/
        //intent.putExtra("Location", b);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


    public static boolean checkGPSStatus(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

/*    public static void goHome(Activity activity) {
        Intent intent = new Intent(activity, NetworkActivity.class);
        activity.finish();
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        activity.startActivity(intent);
    }*/

    public static void goTO(Activity activity, Class cls) {
        Intent intent = new Intent(activity, cls);
        activity.finish();
        activity.startActivity(intent);
    }

/*    public static void goTO(Activity activity, Class cls, String errMsg) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra(ERR_CODE, errMsg);
        activity.finish();
        activity.startActivity(intent);
    }

    public static void goToError(Activity activity, String errorMsg) {
        Intent intent = new Intent(activity, Error.class);
        intent.putExtra(ERROR_MSG, errorMsg);
        activity.finish();
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        activity.startActivity(intent);
    }*/

/*    public static void goToError(Context activity, String errorMsg) {
        Intent intent = new Intent(activity, Error.class);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ERROR_MSG, errorMsg);
        activity.startActivity(intent);
    }*/

    public static void showSnackbar(Activity activity, final String mainTextStringId, final String actionStringId,
                                    View.OnClickListener listener) {
        Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainTextStringId,
                Snackbar.LENGTH_SHORT)
                .setActionTextColor(Color.WHITE)
                .setAction(actionStringId, listener).show();
    }

/*    public static void toast(String msg) {
        Toast.makeText(GariSeba.getContext(), msg, Toast.LENGTH_LONG).show();
    }*/

    public static void showSnackbar(Activity activity, final String mainTextStringId, final String actionStringId, int LENGTH,
                                    View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainTextStringId,
                LENGTH)
                .setActionTextColor(Color.WHITE)
                .setAction(actionStringId, listener);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.green_900));
        snackbar.show();
    }

    public static void showSnackbarWithColor(
            Activity activity,
            final String mainTextString,
            final String actionString,
            int LENGTH,
            View.OnClickListener listener, int color) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainTextString,
                LENGTH)
                .setActionTextColor(Color.WHITE)
                .setAction(actionString, listener);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, color));
        snackbar.show();
    }

    public static void snackErr(Activity activity,
                                final String mainTextString,
                                View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainTextString,
                Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction("OK", listener);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.red_900));
        snackbar.show();
    }

    public static void snackOK(Activity activity,
                               final String mainTextStringId,
                               View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(
                activity.findViewById(android.R.id.content),
                mainTextStringId,
                Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction("OK", listener);

        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.green_900));
        snackbar.show();
    }

    public static void makeToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundColor(Color.parseColor("#d32f2f"));
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setTextColor(Color.parseColor("#ffffff"));
        /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
        toast.show();
    }

    public static String getPrettyJson(String text) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(text));
    }

    private static String getPrettyJson(Object obj) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(obj);
    }


    void showBottonSnack(Activity activity, String bodyString, String actionLeftString) {
        final BottomSheetDialog dialog = new BottomSheetDialog(activity);
        View snackview = LayoutInflater.from(activity).inflate(R.layout.bottom_snack, null);

        AppCompatTextView bodytext = snackview.findViewById(R.id.bodyText);
        AppCompatButton actionLeft = snackview.findViewById(R.id.actionLeft);

        bodytext.setText(bodyString);
        bodytext.setMaxLines(10);
        actionLeft.setText(actionLeftString);
        actionLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*dialog.dismiss();
                ((Activity) context).findViewById(R.id.ll_viewpager).setVisibility(View.GONE);
                ((Activity) context).findViewById(R.id.container_body).setVisibility(View.VISIBLE);
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container_body, new CouponWinHistoryFragment()).commit();*/
            }
        });
        dialog.setContentView(snackview);
        dialog.setCancelable(true);
        dialog.show();
    }

/*



    public static void getOnlineStatusFromServer(final Context context) {

        ApiClient
                .getApi()
                .create(Routes.class)
                .getOnlineStatus(SP.getApiTokenID(), SP.getApiToken())
                .enqueue(new Callback<OnlineStatus>() {
                    @Override
                    public void onResponse(Call<OnlineStatus> call, Response<OnlineStatus> response) {
                        Log.e("getOnlineStatus", response.toString());
                        Log.e("getOnlineStatus", new Gson().toJson(response.body()));

                        OnlineStatus onlineStatus = response.body();

                        if (onlineStatus != null) {
                            setOnlineStatus(onlineStatus.getOnline());
                            if (onlineStatus.getOnline()) {
                                startGeoFenceService(context);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<OnlineStatus> call, Throwable t) {
                        setOnlineStatus(false);
                    }
                });
    }*/


    /*public static void setKeepMeAlive(Context context, int mills) {
        Log.e("setKeepMeAlive", "set");
        setKeepMeAliveStatus(true);
        Intent alarmIntent = new Intent(context, KeepMeAliveReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, KEEP_ME_ALIVE_FLAG, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        *//*
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 1);*//*
        setLastKeepMeAliveTime(System.currentTimeMillis());
        manager.setRepeating(AlarmManager.RTC_WAKEUP, getLastKeepMeAliveTime(), mills, pendingIntent);
    }
*/
    /*public static void cancelKeepMeAlive(Context context) {
        setKeepMeAliveStatus(false);
        Log.e("cancelcancelKeepMeAlive", "cancelled");
        Intent alarmIntent = new Intent(context, KeepMeAliveReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, KEEP_ME_ALIVE_FLAG, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }
*/

    /*public static AuthData.Doctor getSession() {
        //loginData = new Gson().fromJson(LocalData.getEXAMPLE(),LoginData.class);
        //return NandroidX.loginData;
        return new Gson().fromJson(LocalData.getEXAMPLE(), AuthData.class).getDoctor();
    }
*/
    /*public static AuthData getAuth() {
        //loginData = new Gson().fromJson(LocalData.getEXAMPLE(),LoginData.class);
        //return NandroidX.loginData;

        return new Gson().fromJson(LocalData.getEXAMPLE(), AuthData.class);
    }
*/

   /* public static void getCurrentLocation(Context context) {
        final FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        final long time = 1000;
        fusedLocationProviderClient.requestLocationUpdates(new LocationRequest()
                        .setFastestInterval(time)
                        .setInterval(time)
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                , new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // super.onLocationResult(locationResult);

                        if (locationResult != null) {
                            Location location = locationResult.getLastLocation();
                            Log.e("LOCATION Updt", location.toString());
                            // Log.e("LOCATION diff", meterDistanceBetweenPoints((float) location.getLatitude(), (float) location.getLongitude()) + "");

                            fusedLocationProviderClient.removeLocationUpdates(this);
                        } else {
                            Log.e("LOCATION Updt", "no updates");
                        }

                        //mLastLocation = locationResult.getLastLocation();

                    }

                    @Override
                    public void onLocationAvailability(LocationAvailability locationAvailability) {
                        super.onLocationAvailability(locationAvailability);
                    }
                }, null);
    }
*/


/*    private static double meterDistanceBetweenPoints(double lat_a, double lng_a) {

        AuthData authData = new Gson().fromJson(LocalData.getEXAMPLE(), AuthData.class);
        double lat_b = authData.getDoctor().getLatitude();
        double lng_b = authData.getDoctor().getLongitude();

        double r = authData.getDoctor().getChamber_radius();

        float pk = (float) (180.f / Math.PI);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000 * tt;
    }*/

    /*private static double meterDistanceBetweenPoints(double lat_a, double lng_a, double lat_b, double lng_b) {

        AuthData authData = new Gson().fromJson(LocalData.getEXAMPLE(), AuthData.class);
        *//*double lat_b = authData.getDoctor().getLatitude();
        double lng_b = authData.getDoctor().getLongitude();*//*

        double r = authData.getDoctor().getChamber_radius();

        float pk = (float) (180.f / Math.PI);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000 * tt;
    }
*/

   /* private static int insideChamber(Location location) {

        for (AuthData.Doctor.Chamber chamber : getSession().getChambers()) {
            Log.e("LOCATION diff", " >>insideChamber>> " + chamber.getChamberName() + " >> " + meterDistanceBetweenPoints(location.getLatitude(), location.getLongitude(), chamber.getChamber_latitude(), chamber.getChamber_longitude()) + "");
            if (meterDistanceBetweenPoints(location.getLatitude(), location.getLongitude(), chamber.getChamber_latitude(), chamber.getChamber_longitude()) <= chamber.getChamber_radious()) {
               return chamber.getChamberId();
            }
        }

        return -1;
        //return meterDistanceBetweenPoints(location.getLatitude(), location.getLongitude()) <= getSession().getChamber_radius();
    }
*/


    //TODO ADD GOOGLE PLAY SERVIECE
/*    public static boolean isGooglePlayServicesAvailable(Activity context) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            Dialog dialog = googleApiAvailability.getErrorDialog(context, resultCode, 0);
            if (dialog != null) {
                dialog.show();
            }
            return false;
        }
        return true;
    }*/

/*    public static void sendNotification(Context context, String msg, Integer id) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "");
        Intent resultIntent = new Intent(context, Home.class);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);

        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round));
        mBuilder.setContentTitle("");
        mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(msg));
        mBuilder.setContentText(msg);
        mBuilder.setSound(defaultSoundUri);
        mBuilder.setVibrate(new long[]{1000, 0, 1000, 0});
        mBuilder.setContentIntent(resultPendingIntent);
        // mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("1000", "abc", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            //notificationChannel.setVibrationPattern(new long[]{1000,0, 1000,0});
            assert mNotificationManager != null;
            mBuilder.setChannelId("1000");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        mNotificationManager.notify(id, mBuilder.build());

    }*/


   /* public static void getOnlineStatus(final Context context, final int chamberId) {
        ApiClient
                .getApi()
                .create(Routes.class)
                .getOnlineStatus(String.valueOf(getSession().getId()), getSession().getApiToken())
                .enqueue(new Callback<OnlineStatus>() {
                    @Override
                    public void onResponse(Call<OnlineStatus> call, Response<OnlineStatus> response) {
                        Log.e("getOnlineStatus", ">>onResponse>> " + response.toString());
                        Log.e("getOnlineStatus", ">>onResponse>> " + new Gson().toJson(response.body()));

                        OnlineStatus onlineStatus = response.body();

                        if (response.body() != null && response.code() == 200 && onlineStatus != null) {
                            // setOnlineStatus(onlineStatus.getOnline());
                            //TODO ChamberID Needed Here
                            changeOnlineStatus(onlineStatus.getOnline(), chamberId, context);
                        } else {
                            Log.e("getOnlineStatus", ">>onResponse>> " + "RESPONSE_NULL " + response.message());
                            sendMessageToActivity(context, INTENT_LOGIN_UPDATES, INTENT_EXTRA_LOGIN_UPDATES, DOC_OFFLINE);

                        }

                    }

                    @Override
                    public void onFailure(Call<OnlineStatus> call, Throwable t) {
                        //setOnlineStatus(false);
                        sendMessageToActivity(context, INTENT_LOGIN_UPDATES, INTENT_EXTRA_LOGIN_UPDATES, ERROR_INSIDE_CHAMBER);
                        sendNotification(context, NTF_ENTER_ERROR, Geofence.GEOFENCE_TRANSITION_ENTER);
                        cancelKeepMeAlive(context);
                        Log.e("getOnlineStatus", ">>onFailure>> " + t.toString());
                    }
                });
    }
*/

    public static ProgressDialog pdialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait a while...");
        progressDialog.setCancelable(false);
        return progressDialog;

   /*     boolean isWorking = (PendingIntent.getBroadcast(context, 1001, intent, PendingIntent.FLAG_NO_CREATE) != null);//just changed the flag
        Log.d(TAG, "alarm is " + (isWorking ? "" : "not") + " working...");*/
    }

 /*   public static Boolean getKeepMeAliveStatus(Context context) {
        return (PendingIntent.getBroadcast(context, KEEP_ME_ALIVE_FLAG, new Intent(context, KeepMeAliveReceiver.class), PendingIntent.FLAG_NO_CREATE) != null);
    }*/

}
