
import 'dart:convert';

import 'package:flutter/services.dart';

import 'column_maker.dart';

class SunmiPrinter {

  static const platform = MethodChannel('sunmi_print_easyticket_b08/method_channel');

  static Future<bool?> bindPrinterService() async { // Khởi tạo máy in
    final bool? status =  await platform.invokeMethod('BIND_PRINTER_SERVICE');
    return status;
  }
  static Future<bool?> unbindPrinterService() async { // Tắt máy in
    final bool? status = await platform.invokeMethod('UNBIND_PRINTER_SERVICE');
    return status;
  }
  static Future<bool?> startPrinter() async {// start máy in
    final bool? status = await platform.invokeMethod('INIT_PRINTER');
    return status;
  }

  static Future<void> startPrinterExam() async { // in ví dụ
  await platform.invokeMethod('PRINTER_EXAMPLE');
  }

  static Future<void> cutPaper() async { // cắt giấy
    await platform.invokeMethod('CUT_PAPER');
  }
  static Future<void> printLine(int line) async {
    Map<String , dynamic> arguments = <String, dynamic> {"lines" : line};
    await platform.invokeMethod('LINE_WRAP',arguments);
  }

  static Future<void> printText({required String text, int? size, bool? bold, bool? underLine, String? typeface}) async {
    underLine ??= false;
    bold ??= false;
    size ??=10;
    Map<String, dynamic> arguments = <String, dynamic>{"text": '$text\n', "size" : size, "bold" : bold ,"under_line" : underLine};
    await platform.invokeMethod("PRINT_TEXT", arguments);
  }
  static Future<void> setAlignment(int value) async {
    Map<String, dynamic> arguments = <String, dynamic>{"alignment": value};
    await platform.invokeMethod("SET_ALIGNMENT", arguments);
  }
  static Future<String> getPrinterVersion() async {
    return await platform.invokeMethod("PRINTER_VERSION");
  }
  static Future<String> getPrinterSerialNo() async {
    return await platform.invokeMethod("PRINTER_SERIALNO");
  }
  static Future<void> printBarCode({required String dataBarCode, required int symbology, required int height, required int width, required int textposition}) async {
    Map<String, dynamic> arguments = <String, dynamic>{"data": dataBarCode , "symbology" : symbology, "height" : height ,"width" : width , "textposition" : textposition};
    await platform.invokeMethod("PRINT_BARCODE", arguments);
  }
  static Future<void> printQr({required String dataQRCode, required int modulesize, required int errorlevel}) async {
    Map<String, dynamic> arguments = <String, dynamic>{"data": dataQRCode , "modulesize" : modulesize, "errorlevel" : errorlevel};
    await platform.invokeMethod("PRINT_QRCODE", arguments);
  }
  static Future<void> printTable({required List<ColumnMaker> cols, int? size}) async {
    size ??=20;
    final _jsonCols = List<Map<String, String>>.from(
        cols.map<Map<String, String>>((ColumnMaker col) => col.toJson()));
    Map<String, dynamic> arguments = <String, dynamic>{
      "cols": json.encode(_jsonCols), "size" : size
    };
    await platform.invokeMethod("PRINT_TABLE", arguments);
  }



}