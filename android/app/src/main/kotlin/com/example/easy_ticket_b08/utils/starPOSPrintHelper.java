package com.example.easy_ticket_b08.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

//import com.starpos.printerhelper.R;
import com.sunmi.peripheral.printer.ExceptionConst;
import com.sunmi.peripheral.printer.InnerLcdCallback;
import com.sunmi.peripheral.printer.InnerPrinterCallback;
import com.sunmi.peripheral.printer.InnerPrinterException;
import com.sunmi.peripheral.printer.InnerPrinterManager;
import com.sunmi.peripheral.printer.InnerResultCallback;
import com.sunmi.peripheral.printer.SunmiPrinterService;
import com.sunmi.peripheral.printer.WoyouConsts;

/**
 * <pre>
 *      This class is used to demonstrate various printing effects
 *      Developers need to repackage themselves, for details please refer to
 *      http://sunmi-ota.oss-cn-hangzhou.aliyuncs.com/DOC/resource/re_cn/Sunmiprinter%E5%BC%80%E5%8F%91%E8%80%85%E6%96%87%E6%A1%A31.1.191128.pdf
 *  </pre>
 *
 * @author kaltin
 * @since create at 2020-02-14
 */
public class starPOSPrintHelper {

    private final String TAG = starPOSPrintHelper.class.getName();

    public static int NoStarPOSPrinter = 0x00000000;
    public static int CheckStarPOSPrinter = 0x00000001;
    public static int FoundStarPOSPrinter = 0x00000002;
    public static int LostStarPOSPrinter = 0x00000003;

    /**
     *  starPOS means checking the printer connection status
     */
    public int starPOSPrinter = CheckStarPOSPrinter;
    /**
     */
    private SunmiPrinterService mPrinterService;

    private static starPOSPrintHelper helper = new starPOSPrintHelper();

    public starPOSPrintHelper() {}

    public static starPOSPrintHelper getInstance() {
        return helper;
    }

    private InnerPrinterCallback innerPrinterCallback = new InnerPrinterCallback() {
        @Override
        protected void onConnected(SunmiPrinterService service) {
            mPrinterService = service;
            checkStarPOSPrinterService(service);
        }

        @Override
        protected void onDisconnected() {
            mPrinterService = null;
            starPOSPrinter = LostStarPOSPrinter;
        }
    };

    /**
     * init sunmi print service
     */
    public void initStarPOSPrinterService(Context context){
        try {
            boolean ret =  InnerPrinterManager.getInstance().bindService(context,
                    innerPrinterCallback);
            if(!ret){
                starPOSPrinter = NoStarPOSPrinter;
            }
        } catch (InnerPrinterException e) {
            e.printStackTrace();
        }
    }

    /**
     *  deInit starPOS print service
     */
    public void deInitStarPOSPrinterService(Context context){
        try {
            if(mPrinterService != null){
                InnerPrinterManager.getInstance().unBindService(context, innerPrinterCallback);
                mPrinterService = null;
                starPOSPrinter = NoStarPOSPrinter;
            }
        } catch (InnerPrinterException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check the printer connection,
     * like some devices do not have a printer but need to be connected to the cash drawer through a print service
     */
    private void checkStarPOSPrinterService(SunmiPrinterService service){
        boolean ret = false;
        try {
            ret = InnerPrinterManager.getInstance().hasPrinter(service);
            Log.i("PrinterHelp", "== checkStarPOSPrinterService = " + ret);
        } catch (InnerPrinterException e) {
            e.printStackTrace();
        }
        starPOSPrinter = ret?FoundStarPOSPrinter:NoStarPOSPrinter;
    }

    /**
     *  Some conditions can cause interface calls to fail
     *  For example: the version is too low、device does not support
     *  You can see {@link ExceptionConst}
     *  So you have to handle these exceptions
     */
    private void handleRemoteException(RemoteException e){
        //TODO process when get one exception
    }

    /**
     * send esc cmd
     */
    public void sendRawData(byte[] data) {
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }
        try {
            mPrinterService.sendRAWData(data, null);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     *  Printer cuts paper and throws exception on machines without a cutter
     */
    public void cutpaper(){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }
        try {
            mPrinterService.cutPaper(null);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     *  Initialize the printer
     *  All style settings will be restored to default
     */
    public void initPrinter(){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }
        try {
            Log.i("PrinterHelper", "== Init Printer ==");
            mPrinterService.printerInit(null);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     *  paper feed three lines
     *  Not disabled when line spacing is set to 0
     */
    public void print3Line(){

        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }

        try {
            mPrinterService.lineWrap(3, null);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }
    // cái này viết thêm để chỉnh linewap
    public void printLine(int line){

        if(mPrinterService == null){
            return;
        }
        try {
            Log.i("Printer line wrap", line + "");
            mPrinterService.lineWrap(line, null);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     * Get printer serial number
     */
    public String getPrinterSerialNo(){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return "";
        }
        try {
            return mPrinterService.getPrinterSerialNo();
        } catch (RemoteException e) {
            handleRemoteException(e);
            return "";
        }
    }

    /**
     * Get device model
     */
    public String getDeviceModel(){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return "";
        }
        try {
            return mPrinterService.getPrinterModal();
        } catch (RemoteException e) {
            handleRemoteException(e);
            return "";
        }
    }

    /**
     * Get firmware version
     */
    public String getPrinterVersion(){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return "";
        }
        try {
            return mPrinterService.getPrinterVersion();
        } catch (RemoteException e) {
            handleRemoteException(e);
            return "";
        }
    }

    /**
     * Get paper specifications
     */
    public String getPrinterPaper(){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return "";
        }
        try {
            return mPrinterService.getPrinterPaper() == 1?"58mm":"80mm";
        } catch (RemoteException e) {
            handleRemoteException(e);
            return "";
        }
    }
//    public String getHeightMilis() {
//        if(mPrinterService == null){
//            //TODO Service disconnection processing
//            return "";
//        }
//        try {
//            return mPrinterService.lineWrap(1);
//        } catch (RemoteException e) {
//            handleRemoteException(e);
//            return "";
//        }
//    }


    /**
     * Get paper specifications
     * @param callbcak
     */
    public void getPrinterHead(InnerResultCallback callbcak){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }
        try {
             mPrinterService.getPrinterFactory(callbcak);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     * Get printing distance since boot
     * Get printing distance through interface callback since 1.0.8(printerlibrary)
     * @param callback
     */
    public void getPrinterDistance(InnerResultCallback callback){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }
        try {
            mPrinterService.getPrintedLength(callback);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     * Set printer alignment
     */
    public void setAlign(int align){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }
        try {
            mPrinterService.setAlignment(align, null);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     *  Due to the distance between the paper hatch and the print head,
     *  the paper needs to be fed out automatically
     *  But if the Api does not support it, it will be replaced by printing three lines
     */
    public void feedPaper(){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }

        try {
            mPrinterService.autoOutPaper(null);
        } catch (RemoteException e) {
            print3Line();
        }
    }

    /**
     * print text
     * setPrinterStyle Api require V4.2.22 or later, So use esc cmd instead when not supported
     *  More settings reference documentation {@link WoyouConsts}
     */
    public void printText(String content, float size, boolean isBold, boolean isUnderLine) {
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }
        try {
            try {
                mPrinterService.setPrinterStyle(WoyouConsts.ENABLE_BOLD, isBold?
                        WoyouConsts.ENABLE:WoyouConsts.DISABLE);
            } catch (RemoteException e) {
                if (isBold) {
                    mPrinterService.sendRAWData(ESCUtil.boldOn(), null);
                } else {
                    mPrinterService.sendRAWData(ESCUtil.boldOff(), null);
                }
            }
            try {
                mPrinterService.setPrinterStyle(WoyouConsts.ENABLE_UNDERLINE, isUnderLine?
                        WoyouConsts.ENABLE:WoyouConsts.DISABLE);
            } catch (RemoteException e) {
                if (isUnderLine) {
                    mPrinterService.sendRAWData(ESCUtil.underlineWithOneDotWidthOn(), null);
                } else {
                    mPrinterService.sendRAWData(ESCUtil.underlineOff(), null);
                }
            }
            mPrinterService.printTextWithFont(content, null, size, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printTextNoLine(String content, float size, boolean isBold, boolean isUnderLine) {
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }
        //Log.i("text",  content  + "");
        try {
            try {
                mPrinterService.setPrinterStyle(WoyouConsts.ENABLE_BOLD, isBold?
                        WoyouConsts.ENABLE:WoyouConsts.DISABLE);
            } catch (RemoteException e) {
                if (isBold) {
                    mPrinterService.sendRAWData(ESCUtil.boldOn(), null);
                } else {
                    mPrinterService.sendRAWData(ESCUtil.boldOff(), null);
                }
                Log.i("catch", "line 399 " +e);
            }
            try {
                mPrinterService.setPrinterStyle(WoyouConsts.ENABLE_UNDERLINE, isUnderLine?
                        WoyouConsts.ENABLE:WoyouConsts.DISABLE);
            } catch (RemoteException e) {
                if (isUnderLine) {
                    mPrinterService.sendRAWData(ESCUtil.underlineWithOneDotWidthOn(), null);
                } else {
                    mPrinterService.sendRAWData(ESCUtil.underlineOff(), null);
                }
                Log.i("catch", "line 410 " +e);
            }
            Log.i("text",  content  + "");
            mPrinterService.printTextWithFont(content, null, size, null);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.i("catch", "line 415 " +e);
        }
    }

    /**
     * print Bar Code
     */
    public void printBarCode(String data, int symbology, int height, int width, int textposition) {
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }

        try {
            mPrinterService.printBarCode(data, symbology, height, width, textposition, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * print Qr Code
     */
    public void printQr(String data, int modulesize, int errorlevel) {
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }

        try {
            mPrinterService.printQRCode(data, modulesize, errorlevel, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print a row of a table
     */
    public void printTable(String[] txts, int[] width, int[] align) {
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }

        try {
//            mPrinterService.printColumnsText(txts, width, align, null);
            mPrinterService.printColumnsString(txts, width, align, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Print pictures and text in the specified orde
     *  After the picture is printed,
     *  the line feed output needs to be called,
     *  otherwise it will be saved in the cache
     *  In this example, the image will be printed because the print text content is added
     */
    public void printBitmap(Bitmap bitmap, int orientation) {
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }

        try {
            if(orientation == 0){
                mPrinterService.printBitmap(bitmap, null);
//                sunmiPrinterService.printText("横向排列\n", null);
//                sunmiPrinterService.printBitmap(bitmap, null);
//                sunmiPrinterService.printText("横向排列\n", null);
            }else{
                mPrinterService.printBitmap(bitmap, null);
//                sunmiPrinterService.printText("\n纵向排列\n", null);
//                sunmiPrinterService.printBitmap(bitmap, null);
//                sunmiPrinterService.printText("\n纵向排列\n", null);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets whether the current printer is in black mark mode
     */
    public boolean isBlackLabelMode(){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return false;
        }
        try {
            return mPrinterService.getPrinterMode() == 1;
        } catch (RemoteException e) {
            return false;
        }
    }

    /**
     * Gets whether the current printer is in label-printing mode
     */
    public boolean isLabelMode(){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return false;
        }
        try {
            return mPrinterService.getPrinterMode() == 2;
        } catch (RemoteException e) {
            return false;
        }
    }

    /**
     *  Transaction printing:
     *  enter->print->exit(get result) or
     *  enter->first print->commit(get result)->twice print->commit(get result)->exit(don't care
     *  result)
     */
    public void printTrans(Context context, InnerResultCallback callbcak){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }

        try {
            mPrinterService.enterPrinterBuffer(true);
            printExample(context);
            mPrinterService.exitPrinterBufferWithCallback(true, callbcak);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     */
    public void openCashBox(){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }

        try {
            mPrinterService.openDrawer(null);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     * LCD screen control
     * @param flag 1 —— Initialization
     *             2 —— Light up screen
     *             3 —— Extinguish screen
     *             4 —— Clear screen contents
     */
    public void controlLcd(int flag){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }

        try {
            mPrinterService.sendLCDCommand(flag);
        } catch (RemoteException e) {
            handleRemoteException(e);
        }
    }

    /**
     * Display text SUNMI,font size is 16 and format is fill
     * sendLCDFillString(txt, size, fill, callback)
     * Since the screen pixel height is 40, the font should not exceed 40
     */
    public void sendTextToLcd(){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }

        try {
            mPrinterService.sendLCDFillString("STARPOS", 16, true, new InnerLcdCallback() {
                @Override
                public void onRunResult(boolean show) throws RemoteException {
                    //TODO handle result
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * Display two lines and one empty line in the middle
     */
    public void sendTextsToLcd(){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }

        try {
            String[] texts = {"STARPOS", null, "STARPOS"};
            int[] align = {2, 1, 2};
            mPrinterService.sendLCDMultiString(texts, align, new InnerLcdCallback() {
                @Override
                public void onRunResult(boolean show) throws RemoteException {
                    //TODO handle result
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * Display one 128x40 pixels and opaque picture
     */
    public void sendPicToLcd(Bitmap pic){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return;
        }

        try {
            mPrinterService.sendLCDBitmap(pic, new InnerLcdCallback() {
                @Override
                public void onRunResult(boolean show) throws RemoteException {
                    //TODO handle result
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     *  Sample print receipt
     */
    public void printExample(Context context){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return ;
        }

        try {
            int paper = mPrinterService.getPrinterPaper();
            mPrinterService.printerInit(null);
            mPrinterService.setAlignment(1, null);
            mPrinterService.printText("Test sample\n", null);
            //Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sunmi);
            //mPrinterService.printBitmap(bitmap, null);
            mPrinterService.lineWrap(1, null);
            mPrinterService.setAlignment(0, null);
            try {
                mPrinterService.setPrinterStyle(WoyouConsts.SET_LINE_SPACING, 0);
            } catch (RemoteException e) {
                mPrinterService.sendRAWData(new byte[]{0x1B, 0x33, 0x00}, null);
            }
            mPrinterService.printTextWithFont("Note: This is an example of a custom small ticket style, developers can imitate this to build their own\n",
                    null, 12, null);
            if(paper == 1){
                mPrinterService.printText("--------------------------------\n", null);
            }else{
                mPrinterService.printText("------------------------------------------------\n",
                        null);
            }
            try {
                mPrinterService.setPrinterStyle(WoyouConsts.ENABLE_BOLD, WoyouConsts.ENABLE);
            } catch (RemoteException e) {
                mPrinterService.sendRAWData(ESCUtil.boldOn(), null);
            }
            String txts[] = new String[]{"Product", "Price"};
            int width[] = new int[]{1, 1};
            int align[] = new int[]{0, 2};
            mPrinterService.printColumnsString(txts, width, align, null);
            try {
                mPrinterService.setPrinterStyle(WoyouConsts.ENABLE_BOLD, WoyouConsts.DISABLE);
            } catch (RemoteException e) {
                mPrinterService.sendRAWData(ESCUtil.boldOff(), null);
            }
            if(paper == 1){
                mPrinterService.printText("--------------------------------\n", null);
            }else{
                mPrinterService.printText("------------------------------------------------\n",
                        null);
            }
            txts[0] = "Hamburger";
            txts[1] = "17$";
            mPrinterService.printColumnsString(txts, width, align, null);
            txts[0] = "French Fries";
            txts[1] = "10$";
            mPrinterService.printColumnsString(txts, width, align, null);
            txts[0] = "Milk Tea";
            txts[1] = "11";
            mPrinterService.printColumnsString(txts, width, align, null);
            txts[0] = "Fried Chicken";
            txts[1] = "33$";
            mPrinterService.printColumnsString(txts, width, align, null);
            txts[0] = "Apple Juice";
            txts[1] = "10$";
            mPrinterService.printColumnsString(txts, width, align, null);
            if(paper == 1){
                mPrinterService.printText("--------------------------------\n", null);
            }else{
                mPrinterService.printText("------------------------------------------------\n",
                        null);
            }
            mPrinterService.printTextWithFont("Total:          199$\b", null, 40, null);
            mPrinterService.setAlignment(1, null);
            mPrinterService.printQRCode("Thank you", 10, 0, null);
            mPrinterService.setFontSize(36, null);
            mPrinterService.printText("Thank you", null);
            mPrinterService.autoOutPaper(null);
         } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to report the real-time query status of the printer, which can be used before each
     * printing
     */
    public void showPrinterStatus(Context context){
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return ;
        }
        String result = "Interface is too low to implement interface";
        try {
            int res = mPrinterService.updatePrinterState();
            switch (res){
                case 1:
                    result = "printer is running";
                    break;
                case 2:
                    result = "printer found but still initializing";
                    break;
                case 3:
                    result = "printer hardware interface is abnormal and needs to be reprinted";
                    break;
                case 4:
                    result = "printer is out of paper";
                    break;
                case 5:
                    result = "printer is overheating";
                    break;
                case 6:
                    result = "printer's cover is not closed";
                    break;
                case 7:
                    result = "printer's cutter is abnormal";
                    break;
                case 8:
                    result = "printer's cutter is normal";
                    break;
                case 9:
                    result = "not found black mark paper";
                    break;
                case 505:
                    result = "printer does not exist";
                    break;
                default:
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

    /**
     * Demo printing a label
     * After printing one label, in order to facilitate the user to tear the paper, call
     * labelOutput to push the label paper out of the paper hatch
     * 演示打印一张标签
     * 打印单张标签后为了方便用户撕纸可调用labelOutput,将标签纸推出纸舱口
     */
    public void printOneLabel() {
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return ;
        }
        try {
            mPrinterService.labelLocate();
            printLabelContent();
            mPrinterService.labelOutput();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Demo printing multi label
     *
     After printing multiple labels, choose whether to push the label paper to the paper hatch according to the needs
     * 演示打印多张标签
     * 打印多张标签后根据需求选择是否推出标签纸到纸舱口
     */
    public void printMultiLabel(int num) {
        if(mPrinterService == null){
            //TODO Service disconnection processing
            return ;
        }
        try {
            for(int i = 0; i < num; i++){
                mPrinterService.labelLocate();
                printLabelContent();
            }
            mPrinterService.labelOutput();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     *  Custom label ticket content
     *  In the example, not all labels can be applied. In actual use, please pay attention to adapting the size of the label. You can adjust the font size and content position.
     *  自定义的标签小票内容
     *  例子中并不能适用所有标签纸，实际使用时注意要自适配标签纸大小，可通过调节字体大小，内容位置等方式
     */
    private void printLabelContent() throws RemoteException {
        mPrinterService.setPrinterStyle(WoyouConsts.ENABLE_BOLD, WoyouConsts.ENABLE);
        mPrinterService.lineWrap(1, null);
        mPrinterService.setAlignment(0, null);
        mPrinterService.printText("商品         豆浆\n", null);
        mPrinterService.printText("到期时间         12-13  14时\n", null);
        mPrinterService.printBarCode("{C1234567890123456",  8, 90, 2, 2, null);
        mPrinterService.lineWrap(1, null);
    }
}
