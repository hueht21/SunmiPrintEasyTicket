package com.example.easy_ticket_b08.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

//import com.starpos.printerhelper.R;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;

/**
 * Simple package for connecting a starPOS printer via Bluetooth
 */
public class BluetoothUtil {
    private static final String TAG = "BluetoothUtil";

    private static final UUID PRINTER_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static final String sunmi_printer = "00:11:22:33:44:55";
    private static final String starPOS_printer = "11:22:33:44:55:66";

    public static boolean isBlueToothPrinter = true;

    private static BluetoothSocket bluetoothSocket;

    private static BluetoothAdapter getBTAdapter() {
        return BluetoothAdapter.getDefaultAdapter();
    }

//    private static BluetoothDevice getDevice(BluetoothAdapter bluetoothAdapter) {
//        Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
//        for (BluetoothDevice device : devices) {
//            if (device.getAddress().equals(sunmi_printer) || device.getAddress().equals(starPOS_printer)) {
//                return device;
//            }
//        }
//        return null;
//    }

//    private static BluetoothSocket getSocket(BluetoothDevice device) throws IOException {
//        BluetoothSocket socket;
//        socket = device.createRfcommSocketToServiceRecord(PRINTER_UUID);
//        socket.connect();
//        return socket;
//    }

    /**
     * connect bluetooth
     */
//    public static boolean connectBlueTooth(Context context) {
//        if (bluetoothSocket == null) {
//            if (getBTAdapter() == null) {
//                Toast.makeText(context, R.string.toast_3, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//            if (!getBTAdapter().isEnabled()) {
//                Toast.makeText(context, R.string.toast_4, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//            BluetoothDevice device;
//            if ((device = getDevice(getBTAdapter())) == null) {
//                Toast.makeText(context, R.string.toast_5, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            try {
//                bluetoothSocket = getSocket(device);
//                isBlueToothPrinter = true;
//            } catch (IOException e) {
//                Toast.makeText(context, R.string.toast_6, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        }
//        return true;
//    }

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
                isBlueToothPrinter = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**sendData
     *
     *
     * send esc cmd
     */
    public static void sendData(byte[] bytes) {

        // convert byte[] to string
        String s = new String(bytes, StandardCharsets.UTF_8);

//        Log.i(TAG, "==Jimmy== sendData == " + s);
        if (bluetoothSocket != null) {
            try {
                OutputStream out = bluetoothSocket.getOutputStream();
                out.write(bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "BLE socket NULL");
        }
    }
}
