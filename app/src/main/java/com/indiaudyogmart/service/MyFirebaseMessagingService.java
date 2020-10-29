package com.indiaudyogmart.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.indiaudyogmart.R;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.utils.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


/**
 *
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onNewToken(String refreshedToken) {
        super.onNewToken(refreshedToken);
        Log.e("NEW_TOKEN", refreshedToken);
        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);
    }

    private void storeRegIdInPref(String token) {
        Log.d("device token ", token);
        CommonFunctions.setPreference(getApplicationContext(), Constants.device_token, token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());
        notificationUtils = new NotificationUtils(getApplicationContext());
        if (remoteMessage == null)
            return;
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                Map<String, String> params = remoteMessage.getData();
                JSONObject object = new JSONObject(params);
                // JSONObject json = new JSONObject(object);
                handleDataMessage(object);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

        // Check if message contains a notification payload.
        else if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification());
        }

        // Check if message contains a data payload.

    }

    private void handleNotification(RemoteMessage.Notification message) {
        try {
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message.getBody());
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();


            } else {
                // If the app is in background, firebase itself handles the notification


            }

            final String title = message.getTitle().equals("") ?getApplicationContext().getString(R.string.app_name): message.getTitle() ;
            //   final String message = json.has("body") ? json.getString("body") : getApplicationContext().getString(R.string.app_name);
            final String messagebody = message.getBody().equals("") ?getApplicationContext().getString(R.string.app_name): message.getBody() ;

         /*   Intent resultIntent = new Intent(getApplicationContext(), DashboardActivity.class);
            resultIntent.putExtra("message", messagebody);
            showNotificationMessage(getApplicationContext(), title, messagebody, "", resultIntent);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            final String title = json.has("title") ? json.getString("title") : getApplicationContext().getString(R.string.app_name);
         //   final String message = json.has("body") ? json.getString("body") : getApplicationContext().getString(R.string.app_name);
            final String message = json.has("message") ? json.getString("message") : getApplicationContext().getString(R.string.app_name);
            /*if (json.getString("notification_type").equalsIgnoreCase(Constants.order_status)) {
                //if (title.contains("order") || title.contains("Order")) {
                String orderid = json.has(Constants.order_id) ? json.getString(Constants.order_id) : "";
                Intent resultIntent = null;
                if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                    resultIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                } else {
                    resultIntent = new Intent(getApplicationContext(), SplashActivity.class);
                }
                resultIntent.putExtra(Constants.from, Constants.PUSH_NOTIFICATION);
                resultIntent.putExtra(Constants.order_id, orderid);
                resultIntent.putExtra(Constants.showorderdetail, true);
                showNotificationMessageWithBigImage(getApplicationContext(), title, message, "", resultIntent, "", true);
            } else {
                String imageUrl = json.has("picture") ? json.getString("picture") : "";
                String product_category = json.has("product_category") ? json.getString("product_category") : "";
                String product_category_name = json.has("category_name") ? json.getString("category_name") : "";
                imageUrl = MandiSavingConfig.WEBURL + MandiSavingConfig.NotificationImageURL + imageUrl;
                Intent resultIntent = null;
                if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                    resultIntent = new Intent(getApplicationContext(), SplashActivity.class);
                } else {
                    resultIntent = new Intent(getApplicationContext(), SplashActivity.class);
                }
                resultIntent.putExtra(Constants.frorm, Constants.PUSH_NOTIFICATION);
                resultIntent.putExtra(Constants.notification_product_category, product_category);
                resultIntent.putExtra(Constants.notification_product_category_name, product_category_name);
                if (!product_category.equalsIgnoreCase(""))
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, "", resultIntent, imageUrl, false);

            }*/

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
                showNotificationMessage(getApplicationContext(), title, message, "", pushNotification);
            } else {
                // app is in background, show the notification in notification tray
               /* Intent resultIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                resultIntent.putExtra("message", message);
                showNotificationMessage(getApplicationContext(), title, message, "", resultIntent);*/
                // check for image attachment
                /*if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, "", resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, "", resultIntent, imageUrl);
                }*/
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
        notificationUtils.playNotificationSound();
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl, boolean isbigtext) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl, isbigtext);
        notificationUtils.playNotificationSound();
    }
}
