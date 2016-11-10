package schonmann.btutil;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

public class ScanTask extends AsyncTask<JSONArray, Void, Void>{

	private static final String TAG = "ScanTask";

	private CallbackContext callback;

	public ScanTask(CallbackContext callback){
		this.callback = callback;
	}

	@Override
	protected Void doInBackground(JSONArray... params) {

		BluetoothAdapter bluetoothAdapter
				= BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices
				= bluetoothAdapter.getBondedDevices();

		try {
			JSONArray devices = new JSONArray();
			if (pairedDevices.size() > 0) {
				for (BluetoothDevice d : pairedDevices) {
					JSONObject dev = new JSONObject();
					dev.put("name", d.getName());
					dev.put("address", d.getAddress());
					devices.put(dev);
				}
			}
			callback.success(devices);
		}catch(JSONException jse){
			Log.e(TAG,"Json exception.");
		}

		return null;
	}
}
