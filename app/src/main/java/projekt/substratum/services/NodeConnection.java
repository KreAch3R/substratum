package projekt.substratum.services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import node.substratum.INodeService;

/**
 * Created by KreAch3R on 06.04.2017.
 */

public class NodeConnection extends Activity {
    private INodeService service;
    private RemoteServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectService();
        try {
            if (service != null) {
                service.testLog("I am calling from substratum");
                Toast.makeText(this, "Interfacer method called!", Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(this, "Interfacer is not connected", Toast.LENGTH_LONG)
                        .show();
            }
        } catch (Exception e) {

        }
    }

    private void connectService() {
        serviceConnection = new RemoteServiceConnection();
        Intent i = new Intent("interfacer.substratum.services.NodeService");
        i.setPackage("interfacer.substratum");
        boolean ret = bindService(i, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    class RemoteServiceConnection implements ServiceConnection {

        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = INodeService.Stub.asInterface((IBinder) boundService);
            Toast.makeText(NodeConnection.this, "Interfacer connection succeeded.", Toast.LENGTH_LONG)
                    .show();
        }

        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Toast.makeText(NodeConnection.this, "Service disconnected", Toast.LENGTH_LONG)
                    .show();
        }
    }
}
