package com.example.easy_ticket_b08;

import com.example.easy_ticket_b08.utils.starPOSPrintHelper;

import androidx.annotation.NonNull;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;


public class MainActivity extends FlutterActivity {
    private String CHANNEL = "easyticket_b08/method_channel";

    starPOSPrintHelper starPOSPrint = new starPOSPrintHelper();


    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler(
                        (call, result) -> {
                            switch (call.method){
                                case "BIND_PRINTER_SERVICE" :
                                    starPOSPrint.initStarPOSPrinterService(MainActivity.this);
                                    result.success(true);
                                    break;
                                case "ENTER_PRINTER_BUFFER":
                                    starPOSPrint.printExample(MainActivity.this);
                                    result.success(true);
                                    break;
                                case "INIT_PRINTER":
                                    starPOSPrint.initPrinter();
                                    result.success(true);
                                    break;
                                case "PRINT_TEXT":
                                    String text = call.argument("text");
                                    boolean bold = call.argument("bold");
                                    boolean underLine = call.argument("under_line");
                                    int size = call.argument("size");
                                    starPOSPrint.printText(text, size,bold,false);
                                    result.success(true);
                                    break;
                                case  "CUT_PAPER":
                                    starPOSPrint.cutpaper();
                                    result.success(true);
                                    break;
                                case "SET_ALIGNMENT":
                                    int align = call.argument("alignment");
                                    starPOSPrint.setAlign(align);
                                    result.success(true);
                                    break;
                                case "LINE_WRAP":
                                    int lines = call.argument("lines");
                                    starPOSPrint.printLine(lines);
                                    result.success(true);
                                    break;
                            }
                        }
                );
    }
}
