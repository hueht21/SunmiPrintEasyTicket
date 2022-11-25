package com.example.easy_ticket_b08;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.easy_ticket_b08.utils.SunmiPrintHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;


public class MainActivity extends FlutterActivity {
    private String CHANNEL = "sunmi_print_easyticket_method_channel";
    String bigFont = "SignikaNegative-Bold.ttf";
    String smallFont = "OpenSans-Bold.ttf";
    String typeFont ="";

    SunmiPrintHelper sunmiPrintHelper = new SunmiPrintHelper();
    private void checkFont(int size, boolean isBold){
        // lấy 20 làm size giữa
        if(size > 20 && !isBold){
            typeFont = bigFont;
        }else if(size == 20 && isBold){
            typeFont = bigFont;
        }else if(size < 20 && !isBold){
            typeFont = smallFont;
        }else if(size < 20 && isBold){
            typeFont = smallFont;
        }else if(size ==20 && !isBold){
            typeFont = smallFont;
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
                                case "UNBIND_PRINTER_SERVICE" :
                                    sunmiPrintHelper.deInitSunmiPrinterService(MainActivity.this);
                                    result.success(true);
                                    break;
                                case "PRINTER_EXAMPLE":
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

                                case "PRINTER_VERSION":
                                    String version = sunmiPrintHelper.getPrinterVersion();
                                    result.success(version);
                                    break;
                                case "PRINTER_SERIALNO":
                                    String serialNo = sunmiPrintHelper.getPrinterSerialNo();
                                    result.success(serialNo);
                                    break;
                                case "PRINT_BARCODE":
                                    String dataBarCode = call.argument("data");
                                    int symbology = call.argument("symbology");
                                    int height = call.argument("height");
                                    int width = call.argument("width");
                                    int textposition = call.argument("textposition");
                                    Log.d("PRINT_BARCODE", dataBarCode + symbology + height + width + textposition + "");
                                    sunmiPrintHelper.printBarCode(dataBarCode,symbology,height,width,textposition);
                                    result.success(true);
                                    break;
                                case "PRINT_QRCODE":
                                    String dataQRCode = call.argument("data");
                                    int modulesize = call.argument("modulesize");
                                    int errorlevel = call.argument("errorlevel");
                                    sunmiPrintHelper.printQr(dataQRCode,modulesize,errorlevel);
                                    result.success(true);
                                    break;
                                case "PRINT_TABLE":
                                    String colsStr = call.argument("cols");
                                    int fontSize = call.argument("size");
                                    try {
                                        JSONArray cols = new JSONArray(colsStr);
                                        String[] colsText = new String[cols.length()];
                                        int[] colsWidth = new int[cols.length()];
                                        int[] colsAlign = new int[cols.length()];
                                        for (int i = 0; i < cols.length(); i++) {
                                            JSONObject col = cols.getJSONObject(i);
                                            String textColumn = col.getString("text");
                                            int widthColumn = col.getInt("width");
                                            int alignColumn = col.getInt("align");
                                            colsText[i] = textColumn;
                                            colsWidth[i] = widthColumn;
                                            colsAlign[i] = alignColumn;
                                        }

                                        sunmiPrintHelper.printTable(colsText, colsWidth, colsAlign,fontSize);
                                        result.success(true);
                                    } catch (Exception err) {
                                        Log.d("SunmiPrinter", err.getMessage());
                                    }
                                    break;
                                case "PRINT_BITMAP":
                                    Bitmap bitmap = call.argument("bitmap");
                                    int orientation = call.argument("orientation");
                                    sunmiPrintHelper.printBitmap(bitmap,orientation);
                                    result.success(true);
                                    break;
                                case "PRINT_STATUS":
                                    sunmiPrintHelper.showPrinterStatus(MainActivity.this);
                                    result.success(true);
                                    break;
                                case "OPEN_CASH_BOX":
                                    sunmiPrintHelper.openCashBox();
                                    result.success(true);
                                    break;
                                case "SENT_RAW_DATA":
                                    byte[] dataRaw = call.argument("data");
                                    sunmiPrintHelper.sendRawData(dataRaw);
                                    result.success(true);
                                    break;
                                case "DEVICE_MODEL":
                                    String deviceModel = sunmiPrintHelper.getDeviceModel();
                                    result.success(deviceModel);
                                    break;
                                case "PRINT_PAPER":
                                    String getPrintPaper = sunmiPrintHelper.getPrinterPaper();
                                    result.success(getPrintPaper);
                                    break;
                                case "FEED_PAPER":
                                    sunmiPrintHelper.feedPaper();
                                    result.success(true);
                                    break;
                                case "BACK_LABEL_MODE":
                                    boolean isBackkLabel = sunmiPrintHelper.isBlackLabelMode();
                                    result.success(isBackkLabel);
                                    break;
                                case "LABEL_MODEL":
                                    boolean isLabelMode = sunmiPrintHelper.isLabelMode();
                                    result.success(isLabelMode);
                                    break;
                                case "PRINT_TRANS":
                                    sunmiPrintHelper.printTrans(MainActivity.this,null);
                                    result.success(true);
                                    break;
                                case "CONTROL_LCD":
                                    int flag = call.argument("flag");
                                    sunmiPrintHelper.controlLcd(flag);
                                    result.success(true);
                                    break;
                                case "SEND_TEXT_TOLCD":
                                    sunmiPrintHelper.sendTextToLcd();
                                    result.success(true);
                                    break;
                                case "SEND_TEXTS_TOLCD":
                                    sunmiPrintHelper.sendTextsToLcd();
                                    result.success(true);
                                    break;
                                case "SEND_PIC_TOLCD":
                                    Bitmap pic = call.argument("pic");
                                    sunmiPrintHelper.sendPicToLcd(pic);
                                    result.success(true);
                                    break;
                                case "PRINT_ONE_LABEL":
                                    sunmiPrintHelper.printOneLabel();
                                    result.success(true);
                                    break;
                                case "PRINT_MULTILABEL":
                                    int num = call.argument("num");
                                    sunmiPrintHelper.printMultiLabel(num);
                                    result.success(true);
                                    break;
                                case "PRINTE_HEAD":
                                    sunmiPrintHelper.getPrinterHead(null);
                                    result.success(true);
                                    break;
                                case "PRINTE_DISTANCE":
                                    sunmiPrintHelper.getPrinterDistance(null);
                                    result.success(true);
                                    break;

                            }
                        }
                );
    }
}
