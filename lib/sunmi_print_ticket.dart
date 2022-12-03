import 'dart:convert';

import 'package:flutter/services.dart';

class SunmiPrinter {
  static const platform =
      MethodChannel('sunmi_print_easyticket_b08/method_channel');

  static Future<bool?> bindPrinterService() async {
    // Khởi tạo máy in
    final bool? status = await platform.invokeMethod('BIND_PRINTER_SERVICE');
    return status;
  }

  static Future<bool?> unbindPrinterService() async {
    // Tắt máy in
    final bool? status = await platform.invokeMethod('UNBIND_PRINTER_SERVICE');
    return status;
  }

  static Future<bool?> initPrinter() async {
    // start máy in
    final bool? status = await platform.invokeMethod('INIT_PRINTER');
    return status;
  }

  ///*startTransactionPrint*
  ///
  ///If you want to print in one transaction, you can start the transaction, build your print commands without send to the buffer
  static Future<void> startTransactionPrint([bool clear = false]) async {
    Map<String, dynamic> arguments = <String, dynamic>{"clearEnter": clear};
    await platform.invokeMethod("ENTER_PRINTER_BUFFER", arguments);
  }

  ///*submitTransactionPrint*
  ///
  ///This method will submit your transaction to the bufffer
  static Future<void> submitTransactionPrint() async {
    await platform.invokeMethod("COMMIT_PRINTER_BUFFER");
  }

  ///*exitTransactionPrint*
  ///
  ///This method will close the transaction

  static Future<void> exitTransactionPrint([bool clear = true]) async {
    Map<String, dynamic> arguments = <String, dynamic>{"clearExit": clear};
    await platform.invokeMethod("EXIT_PRINTER_BUFFER", arguments);
  }

  ///*resetFontSize*
  ///
  ///This method will reset the font size to the medium (default) size
  static Future<void> resetFontSize() async {
    Map<String, dynamic> arguments = <String, dynamic>{"size": 24};
    await platform.invokeMethod("FONT_SIZE", arguments);
  }

  static Future<void> startPrinterExam() async {
    // in ví dụ
    await platform.invokeMethod('PRINTER_EXAMPLE');
  }

  static Future<void> cutPaper() async {
    // cắt giấy
    await platform.invokeMethod('CUT_PAPER');
  }

  static Future<void> printLine(int line) async {
    Map<String, dynamic> arguments = <String, dynamic>{"lines": line};
    await platform.invokeMethod('LINE_WRAP', arguments);
  }

  ///*line*
  ///
  ///With this method you can draw a line to divide sections.
  static Future<void> line({
    String ch = '-',
    int len = 31,
  }) async {
    resetFontSize();
    await printText(text: List.filled(len, ch[0]).join());
  }

  ///*line*
  ///
  ///With this method you can draw a line to divide sections.
  static Future<void> lineDash({
    String ch = '- ',
    int len = 18,
  }) async {
    resetFontSize();
    await printText(text: List.filled(len, ch).join());
  }

  static Future<void> printText({
    required String text,
    int size = 24,
    bool bold = false,
    bool underLine = false,
    String? typeface,
    bool isLight = false,
    bool isExtra = false,
  }) async {
    Map<String, dynamic> arguments = <String, dynamic>{
      "text": '$text\n',
      "size": size,
      "bold": bold,
      "under_line": underLine,
      "is_light": isLight,
      "is_extra": isExtra
    };
    await platform.invokeMethod("PRINT_TEXT", arguments);
  }

  static Future<void> setAlignment(int value) async {
    Map<String, dynamic> arguments = <String, dynamic>{"alignment": value};
    await platform.invokeMethod("SET_ALIGNMENT", arguments);
  }

  static Future<String> getPrinterVersion() async {
    return await platform.invokeMethod("PRINTER_VERSION");
  }
  static Future<void> printImage(Uint8List img) async {
    Map<String, dynamic> arguments = <String, dynamic>{};
    arguments.putIfAbsent("bitmap", () => img);
    await platform.invokeMethod("PRINT_IMAGE", arguments);
  }

  static Future<String> getPrinterSerialNo() async {
    return await platform.invokeMethod("PRINTER_SERIALNO");
  }
  static Future<void> printDrawRow() async {
    await platform.invokeMethod("PRINT_DRAW_ROW");
  }

  static Future<void> printBarCode(
      {required String dataBarCode,
      required int symbology,
      required int height,
      required int width,
      required int textposition}) async {
    Map<String, dynamic> arguments = <String, dynamic>{
      "data": dataBarCode,
      "symbology": symbology,
      "height": height,
      "width": width,
      "textposition": textposition
    };
    await platform.invokeMethod("PRINT_BARCODE", arguments);
  }

  static Future<void> printQr(
      {required String dataQRCode,
      required int modulesize,
      required int errorlevel}) async {
    Map<String, dynamic> arguments = <String, dynamic>{
      "data": dataQRCode,
      "modulesize": modulesize,
      "errorlevel": errorlevel
    };
    await platform.invokeMethod("PRINT_QRCODE", arguments);
  }

  static Future<void> printTable(
      {required List<ColumnMaker> cols, int? size}) async {
    size ??= 20;
    final _jsonCols = List<Map<String, String>>.from(
        cols.map<Map<String, String>>((ColumnMaker col) => col.toJson()));
    Map<String, dynamic> arguments = <String, dynamic>{
      "cols": json.encode(_jsonCols),
      "size": size
    };
    await platform.invokeMethod("PRINT_TABLE", arguments);
  }
  static Future<String> printStatus() async {
    return await platform.invokeMethod("PRINT_STATUS");
  }
  static Future<String> getPrintPaper() async {
    return await platform.invokeMethod("PRINT_PAPER");
  }
  static Future<void> feedPaper() async {
    await platform.invokeMethod("FEED_PAPER");
  }
  static Future<bool> getBackLabelMode() async {
    return await platform.invokeMethod("BACK_LABEL_MODE");
  }
  static Future<bool> getLabelModel() async {
    return await platform.invokeMethod("LABEL_MODEL");
  }
  static Future<void> printTrans() async {
    await platform.invokeMethod("PRINT_TRANS");
  }
  static Future<void> controlLCD(int flag) async {
    Map<String, dynamic> arguments = <String, dynamic>{"flag": flag};
    await platform.invokeMethod("CONTROL_LCD", arguments);
  }
  static Future<void> sentTextLCD() async {
    await platform.invokeMethod("SEND_TEXT_TOLCD");
  }
  static Future<void> sentTextsLCD() async {
    await platform.invokeMethod("SEND_TEXTS_TOLCD");
  }
  static Future<void> printMultiLabel(int num) async {
    Map<String, dynamic> arguments = <String, dynamic>{"num": num};
    await platform.invokeMethod("PRINT_MULTILABEL", arguments);
  }
  static Future<void> printeHead() async {
    await platform.invokeMethod("PRINTE_HEAD");
  }
  static Future<void> printeDistance() async {
    await platform.invokeMethod("PRINTE_DISTANCE");
  }


}

class ColumnMaker {
  String text;
  int width;
  int align;
  ColumnMaker({
    this.text = '',
    this.width = 2,
    this.align = 0,
  });
  //Convert to json
  Map<String, String> toJson() {
    int value = 0;
    switch (align) {
      case 0:
        value = 0;
        break;
      case 1:
        value = 1;
        break;
      case 2:
        value = 2;
        break;
      default:
        value = 0;
    }
    return {
      "text": text,
      "width": width.toString(),
      "align": value.toString(),
    };
  }
}
