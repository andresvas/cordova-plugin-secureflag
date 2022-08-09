package cordova.plugin.secureflag;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class add secure flag when applications is in background. (ActivityLifecycleCallbacks)
 */
public class SecureFlag extends CordovaPlugin implements Application.ActivityLifecycleCallbacks {

    private static boolean activeFlag = false;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        activeFlag = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            cordova.getActivity().registerActivityLifecycleCallbacks(this);
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("secureFlag")) {
            activeFlag = true;
            cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cordova.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
                }
            });
            callbackContext.success("success");
            return true;
        }
        return false;
    }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            cordova.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        //cordova.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void onPause(boolean multitasking) {
        // if (activeFlag) {
        cordova.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        // }
        super.onPause(multitasking);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPrePaused(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

    }


    @Override
    public void onActivityPaused(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void onActivityPostResumed(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
