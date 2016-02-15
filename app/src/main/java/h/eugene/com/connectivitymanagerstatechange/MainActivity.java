package h.eugene.com.connectivitymanagerstatechange;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import h.eugene.com.connectivitymanagerstatechange.network.CheckNetwork;
import h.eugene.com.connectivitymanagerstatechange.network.NetworkStateReceiver;
import h.eugene.com.connectivitymanagerstatechange.network.OnNetworkChangeListener;

public class MainActivity extends AppCompatActivity {

    private NetworkStateReceiver mReceiver;

    private TextView mConnectionStatus;
    private TextView mManualCheckStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConnectionStatus = (TextView) findViewById(R.id.txtConnectionStatus);
        mManualCheckStatus = (TextView) findViewById(R.id.txtManualCheck);
        Button mManualCheckConnection = (Button) findViewById(R.id.btnManualCheck);
        mManualCheckConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String manualConnection;
                if (CheckNetwork.isConnected(v.getContext())) {
                    manualConnection = getString(R.string.connected);
                } else {
                    manualConnection = getString(R.string.not_connected);
                }
                mManualCheckStatus.setText(String.format("%s: %s", getString(R.string.manual_check), manualConnection));
            }
        });
    }

    @Override
    protected void onResume() {
        mReceiver = new NetworkStateReceiver();
        registerReceiver(this.mReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        mReceiver.netWorkChanged(new OnNetworkChangeListener() {
            @Override
            public void isConnected() {
                // Connected
                // Handle connection here
                Log.e("Connection", "Connected");

                mConnectionStatus.setText(R.string.connected);
            }

            @Override
            public void notConnected() {
                // Not connected
                // Handle connection loss here
                Log.e("Connection", "No Connection");

                mConnectionStatus.setText(R.string.not_connected);
            }
        });
        super.onResume();
    }

    @Override
    protected void onPause() {
        mReceiver.netWorkChanged(null);
        unregisterReceiver(mReceiver);
        super.onPause();
    }
}