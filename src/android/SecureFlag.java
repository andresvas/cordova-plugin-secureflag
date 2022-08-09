package cordova.plugin.secureflag;

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
public class SecureFlag extends CordovaPlugin {

    private static boolean activeFlag = false;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        activeFlag = false;
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
    public void onPause(boolean multitasking) {
        if (activeFlag) {
            cordova.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        super.onPause(multitasking);
    }

}
