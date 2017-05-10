package ryanwang.com.mockitotestingcodelab;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkManager {
	private Context context;

	public NetworkManager(Context context) {
		this.context = context;
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}
}
