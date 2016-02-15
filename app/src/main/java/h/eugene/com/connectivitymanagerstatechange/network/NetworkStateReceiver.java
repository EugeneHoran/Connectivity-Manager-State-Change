package h.eugene.com.connectivitymanagerstatechange.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStateReceiver extends BroadcastReceiver {

    /**
     * Connection Status Interface
     * <p/>
     * isConnected()
     * notConnected()
     */
    private OnNetworkChangeListener networkChangeListener;

    public void netWorkChanged(OnNetworkChangeListener networkChangeListener) {
        this.networkChangeListener = networkChangeListener;
    }

    /**
     * @param context Context
     * @param intent  new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
            if (activeInfo != null && activeInfo.isConnected()) {
                if (networkChangeListener != null) {
                    networkChangeListener.isConnected();
                }
            } else {
                if (networkChangeListener != null) {
                    networkChangeListener.notConnected();
                }
            }
        }
    }
}