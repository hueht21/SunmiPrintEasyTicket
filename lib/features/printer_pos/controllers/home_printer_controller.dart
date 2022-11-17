
import 'package:get/get.dart';

import '../../../consts/const.dart';
import '../service_printer_pos/sunmi_printer.dart';

class HomePrinterController extends GetxController{

  //static const platform = MethodChannel('easyticket_b08/method_channel');
  RxString printerVesion = "Chưa có".obs;
  RxString printerSerial= "Chưa có".obs;
  @override
  void onInit() async {
    super.onInit();
    await SunmiPrinter.startPrinter();
  }
  Future<void> configPrinter() async{
    await SunmiPrinter.printLine(1);
    //await SunmiPrinter.setAlignment(0);
    await SunmiPrinter.printText(
        text: AppConst.nameCompany, bold: true, size: 19);
    // await SunmiPrinter.printLine(0);
    await SunmiPrinter.printText(
        text: AppConst.addressConpany, bold: true, size: 17,underLine: true);
    await SunmiPrinter.printText(
        text: "Mã số thuế: 20202020", bold: false, size: 19);
    await SunmiPrinter.setAlignment(25);
    await SunmiPrinter.printText(
        text: AppConst.nameTicket, bold: true, size: 30);
    //await SunmiPrinter.setAlignment(15);
    await SunmiPrinter.printText(
        text: "${AppConst.fareTicket} 25,000 đồng", bold: true, size: 25);
    await SunmiPrinter.printText(
        text: "${AppConst.ticketStartingDateHP} ${DateTime.now().hour} h ${DateTime.now().minute}", bold: true, size: 20);
    //await SunmiPrinter.printLine(3);
    await SunmiPrinter.cutPaper();
  }


}