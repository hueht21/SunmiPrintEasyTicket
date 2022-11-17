

import 'dart:developer';

import 'package:flutter/services.dart';

class SunmiPrinter {

  static const platform = MethodChannel('easyticket_b08/method_channel');


  static Future<bool?> startPrinter() async {
    final bool? status = await platform.invokeMethod('BIND_PRINTER_SERVICE');
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
    underLine ??= false;
    bold ??= false;
    size ??=10;
    Map<String, dynamic> arguments = <String, dynamic>{"text": '$text\n', "size" : size, "bold" : bold ,"under_line" : underLine  };
    await platform.invokeMethod("PRINT_TEXT", arguments);
    await startInitPrinter();
  }
  static Future<void> printTextNoLine({required String text, int? size, bool? bold, bool? underLine}) async {
    underLine ??= false;
    bold ??= false;
    size ??=10;
    Map<String, dynamic> arguments = <String, dynamic>{"text": "$text", "size" : size, "bold" : bold ,"under_line" : underLine  };
    log(text);
    await platform.invokeMethod("PRINT_TEXT_NO_LINE", arguments);
    await startInitPrinter();
  }
  static Future<void> setAlignment(int value) async {
    Map<String, dynamic> arguments = <String, dynamic>{"alignment": value};
    await platform.invokeMethod("SET_ALIGNMENT", arguments);
  }

}