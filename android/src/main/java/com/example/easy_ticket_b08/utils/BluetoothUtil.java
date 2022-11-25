package com.example.easy_ticket_b08.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

//import com.example.easy_ticket_b08.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 *  Simple package for connecting a sunmi printer via Bluetooth
 */
public class BluetoothUtil {

    private static final UUID PRINTER_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static final String Innerprinter_Address = "00:11:22:33:44:55";

    public static boolean isBlueToothPrinter = false;

    private static BluetoothSocket bluetoothSocket;

    private static BluetoothAdapter getBTAdapter() {
        return BluetoothAdapter.getDefaultAdapter();
    }

    private static BluetoothDevice getDevice(BluetoothAdapter bluetoothAdapter, Activity activity) {
        BluetoothDevice innerprinter_device = null;
        if (ContextCompat.checkSelfPermission( activity,android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
//            ActivityCompat.requestPermissions(
//                    this,
//                    new String [] { android.Manifest.permission.ACCESS_COARSE_LOCATION },
//                    LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION
//            );
        }
        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : devices) {
            if (device.getAddress().equals(Innerprinter_Address)) {
                innerprinter_device = device;
                break;
            }
        }
        return innerprinter_device;
    }

    private static BluetoothSocket getSocket(BluetoothDevice device, Activity activity) throws IOException {
        BluetoothSocket socket;
        if (ContextCompat.checkSelfPermission( activity,android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
//            ActivityCompat.requestPermissions(
//                    this,
//                    new String [] { android.Manifest.permission.ACCESS_COARSE_LOCATION },
//                    LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION
//            );
        }
        socket = device.createRfcommSocketToServiceRecord(PRINTER_UUID);
        socket.connect();
        return  socket;
    }

    /**
     * connect bluetooth
     */
    public static boolean connectBlueTooth(Context context,Activity activity) {
        if (bluetoothSocket == null) {
            if (getBTAdapter() == null) {
//                Toast.makeText(context,  R.string.toast_3, Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!getBTAdapter().isEnabled()) {
//                Toast.makeText(context, R.string.toast_4, Toast.LENGTH_SHORT).show();
                return false;
            }
            BluetoothDevice device;
            if ((device = getDevice(getBTAdapter(),activity)) == null) {
//                Toast.makeText(context, R.string.toast_5, Toast.LENGTH_SHORT).show();
                return false;
            }

            try {
                bluetoothSocket = getSocket(device,activity);
            } catch (IOException e) {
//                Toast.makeText(context, R.string.toast_6, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    /**
     * disconnect bluethooth
     */
    public static void disconnectBlueTooth(Context context) {
        if (bluetoothSocket != null) {
            try {
                OutputStream out = bluetoothSocket.getOutputStream();
                out.close();
                bluetoothSocket.close();
                bluetoothSocket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  send esc cmd
     */
    public static void sendData(byte[] bytes) {
        if (bluetoothSocket != null) {
            OutputStream out = null;
            try {
                out = bluetoothSocket.getOutputStream();
                out.write(bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            //TODO handle disconnect event
        }
    }
}
