package com.livetv.android.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.livetv.android.listeners.StringRequestListener;
import com.livetv.android.model.User;
import com.livetv.android.utils.networking.NetManager;
import com.livetv.android.utils.networking.WebConfig;
import org.json.JSONObject;

public class Tracking implements StringRequestListener, OnClickListener {
    private static Activity mActivity = null;
    private static Tracking mInstance = null;
    private String action = "IDLE";
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    /* access modifiers changed from: private */
    public boolean isTracking = false;
    private Runnable trackingThread = new Runnable() {
        public void run() {
            Tracking.this.track();
            if (Tracking.this.isTracking) {
                Tracking.this.handler.postDelayed(this, 900000);
                return;
            }
            try {
                finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    };
    private String usr = "";

    public static Tracking getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new Tracking();
        }
        mActivity = activity;
        return mInstance;
    }

    public void onStart() {
        String theUser = DataManager.getInstance().getString("theUser", "");
        if (!TextUtils.isEmpty(theUser)) {
            this.usr = ((User) new Gson().fromJson(theUser, User.class)).getName();
        }
        if (!this.isTracking) {
            this.isTracking = true;
            this.handler.post(this.trackingThread);
        }
    }

    public void onStop() {
        this.isTracking = false;
    }

    /* access modifiers changed from: private */
    public void track() {
        String url = WebConfig.trackingURL.replace("{USER}", this.usr).replace("{MOVIE}", this.action);
        Log.d("DNLS", "Tracking URL: " + url);
        NetManager.getInstance().makeStringRequest(url, this);
    }

    public void setAction(String action2) {
        this.action = action2;
    }

    public void onError() {
        Log.d("DNLS", "Tracking ERROR");
    }

    public void onCompleted(String response) {
        Log.d("DNLS", "Tracking response: " + response);
        try {
            JSONObject messageJson = new JSONObject(response);
            if (!messageJson.isNull("message") && !TextUtils.isEmpty(messageJson.getString("message"))) {
                Dialogs.showOneButtonDialog(mActivity, "Atención", messageJson.getString("message"), (OnClickListener) this);
            }
        } catch (Exception e) {
        }
    }

    public void onClick(DialogInterface dialog, int which) {
        String url = WebConfig.deleteMessageURL.replace("{USER}", this.usr);
        Log.d("DNLS", "Tracking URL: " + url);
        NetManager.getInstance().makeStringRequest(url, this);
    }
}
