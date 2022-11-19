import 'package:flutter/services.dart';
import 'package:get/get.dart';

class PrintBluetooth {




  static const platform = MethodChannel('easyticket_b08/method_channel');

  static Future<bool> connectBluetooth() async {
    
    bool checkConect = await platform.invokeMethod("CONNECT_BLUETOOTH");
    return checkConect;

}

}