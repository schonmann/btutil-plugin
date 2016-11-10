package schonmann.btutil;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

import org.apache.cordova.CordovaInterface;

/**
 * Created by Anton on 04-Nov-16.
 */
public class BTStarter implements Runnable {

	public static final int REQUEST_BLUETOOTH = 1;

	private Callback c;
	private Fallback f;

	public BTStarter(Callback callback, Fallback fallback){
		this.c = callback;
		this.f = fallback;
	}

	@Override
	public void run() {
		BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();

		if(BTAdapter == null) f.fallback();

		if (!BTAdapter.isEnabled()) {
			Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			BTUtilPlugin.getCordovaInterface().getActivity().startActivityForResult(enableBT, REQUEST_BLUETOOTH);
		}

		c.callback();
	}
}
