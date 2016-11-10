package schonmann.btutil;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.telecom.Call;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Antonio Schönmann on 29/06/2016.
 */

public class BTUtilPlugin extends CordovaPlugin{
	public static final String TAG = "BTUtilPlugin";
	private static final String SCAN = "scan";

	public BTUtilPlugin() {
	}

	private static CordovaInterface cordovaInterface = null;

	public static CordovaInterface getCordovaInterface(){
		return cordovaInterface;
	}

	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		cordovaInterface = cordova;
	}

	public boolean execute(final String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {

		final Context context = cordova.getActivity().getApplicationContext();

		Callback onBtUp = new Callback() {
			@Override
			public void callback() {
				routeExecution(action, args, callbackContext, context);
			}
		};

		Fallback onBtFail = new Fallback() {
			@Override
			public void fallback() {
				callbackContext.error("Could not setup bluetooth adapter.");
			}
		};

		Runnable BTStarter = new BTStarter(onBtUp, onBtFail);
		cordova.getThreadPool().execute(BTStarter);

		return true;
	}

	private void routeExecution(final String action, final JSONArray args,
	                            final CallbackContext callbackContext, Context context){
		if(SCAN.equals(action)){
			ScanTask scanTask = new ScanTask(callbackContext);
			scanTask.execute(args);
		}
	}
}