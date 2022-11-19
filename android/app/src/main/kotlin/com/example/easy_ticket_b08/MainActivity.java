package com.example.easy_ticket_b08;

import androidx.annotation.NonNull;

import com.example.easy_ticket_b08.utils.SunmiPrintHelper;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;


public class MainActivity extends FlutterActivity {
    private String CHANNEL = "easyticket_b08/method_channel";
    String font = "SignikaNegative-Bold.ttf";
    String font2 = "OpenSans-Bold.ttf";
    String typeFont ="";

    SunmiPrintHelper sunmiPrintHelper = new SunmiPrintHelper();
    private void checkFont(int size, boolean isbool){
        // lấy 20 làm size giữa
        if(size > 20 && isbool == false){
            typeFont = font;
        }else if(size == 20 && isbool == true){
            typeFont = font;
        }else if(size < 20 && isbool == false){
            typeFont = font2;
        }else if(size < 20 && isbool == true){
            typeFont = font2;
        }else if(size ==20 && isbool == false){
            typeFont = font2;
        }
    }


    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler(
                        (call, result) -> {
                            switch (call.method){
                                case "BIND_PRINTER_SERVICE" :
                                    sunmiPrintHelper.initSunmiPrinterService(MainActivity.this);
                                    result.success(true);
                                    break;
                                case "ENTER_PRINTER_BUFFER":
                                    sunmiPrintHelper.printExample(MainActivity.this);
                                    result.success(true);
                                    break;
                                case "INIT_PRINTER":
                                    sunmiPrintHelper.initPrinter();
                                    result.success(true);
                                    break;
                                case "PRINT_TEXT":
                                    String text = call.argument("text");
                                    boolean bold = call.argument("bold");
                                    boolean underLine = call.argument("under_line");
                                    int size = call.argument("size");
                                    checkFont(size,bold);
                                    sunmiPrintHelper.printText(text, size,bold,underLine,typeFont);
                                    result.success(true);
                                    break;
                                case  "CUT_PAPER":
                                    sunmiPrintHelper.cutpaper();
                                    result.success(true);
                                    break;
                                case "SET_ALIGNMENT":
                                    int align = call.argument("alignment");
                                    sunmiPrintHelper.setAlign(align);
                                    result.success(true);
                                    break;
                                case "LINE_WRAP":
                                    int lines = call.argument("lines");
                                    sunmiPrintHelper.printLine(lines);
                                    result.success(true);
                                    break;
                            }
                        }
                );
    }
}
