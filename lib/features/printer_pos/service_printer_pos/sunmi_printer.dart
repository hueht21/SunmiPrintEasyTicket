

import 'dart:developer';

import 'package:flutter/services.dart';
import 'package:sunmi_printer_plus/sunmi_style.dart';

class SunmiPrinter {

  static const platform = MethodChannel('easyticket_b08/method_channel');


  static Future<bool?> startPrinter() async {
    final bool? status = await platform.invokeMethod('BIND_PRINTER_SERVICE');
    log("${status}");
    return status;
  }

  static Future<void> startPrinterExam() async {
  await platform.invokeMethod('ENTER_PRINTER_BUFFER');
  }

  static Future<void> startInitPrinter() async {
    await platform.invokeMethod('INIT_PRINTER');
  }
  static Future<void> cutPaper() async {
    await platform.invokeMethod('CUT_PAPER');
  }
  static Future<void> printLine(int line) async {
    Map<String , dynamic> arguments = <String, dynamic> {"lines" : line};
    await platform.invokeMethod('LINE_WRAP',arguments);
  }

  static Future<void> printText({required String text, int? size, bool? bold, bool? underLine}) async {

    Map<String, dynamic> arguments = <String, dynamic>{"text": '$text\n', "size" : size, "bold" : bold ,"under_line" : false  };
    await platform.invokeMethod("PRINT_TEXT", arguments);
    //await setAlignment(20);
    await startInitPrinter();
  }
  static Future<void> setAlignment(int value) async {
    Map<String, dynamic> arguments = <String, dynamic>{"alignment": value};
    await platform.invokeMethod("SET_ALIGNMENT", arguments);
  }

}