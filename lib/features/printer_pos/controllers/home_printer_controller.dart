import 'package:get/get.dart';
import '../../../consts/const.dart';
import '../service_printer_pos/sunmi_printer.dart';

class HomePrinterController extends GetxController {

  Future<void> configPrinter() async {
    await SunmiPrinter.startPrinter();
    await SunmiPrinter.printText(
        text: AppConst.nameCompany, bold: true, size: 20);
    await SunmiPrinter.printText(
        text: AppConst.addressConpany, bold: true, size: 18, underLine: false);
    await SunmiPrinter.printText(
        text: "${AppConst.taxCodeName} ${AppConst.taxCodeCustomer}", bold: true, size: 17);
    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text: AppConst.nameTicket, bold: true, size: 25);
    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text: "${AppConst.fareTicket} ${AppConst.moneyTicket} đồng",
        bold: true,
        size: 23);
    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text:
            "${AppConst.ticketStartingDateHP} ${DateTime.now().hour} h ${DateTime.now().minute} p",
        bold: true,
        size: 21);
    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text:
            "${AppConst.day} ${DateTime.now().day} ${AppConst.month} ${DateTime.now().month} ${AppConst.year} ${DateTime.now().year}",
        bold: true,
        size: 20);
    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text:
            "${AppConst.ncc} ${AppConst.nameCompanyNCC} - ${AppConst.nameTaxCode} ${AppConst.taxCode} \n \t ${AppConst.custommerService} ${AppConst.phoneCustomerService}",
        bold: true,
        size: 20);
    await SunmiPrinter.printLine(3);
    await SunmiPrinter.cutPaper();
  }
}
