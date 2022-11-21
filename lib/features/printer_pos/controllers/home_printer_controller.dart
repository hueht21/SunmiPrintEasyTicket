
import '../../../consts/const.dart';
import '../service_printer_pos/sunmi_printer.dart';

class HomePrinterController {

  Future<void> configPrinter() async {
    await SunmiPrinter.startPrinter();
    await SunmiPrinter.printText(
        text: AppConst.nameCompany, bold: true, size: 20);
    await SunmiPrinter.printText(
        text: AppConst.addressConpany, bold: false, size: 18, underLine: false);
    await SunmiPrinter.printText(
        text: "${AppConst.taxCodeName} ${AppConst.taxCodeCustomer}", bold: false, size: 20);
    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text: AppConst.nameTicket, bold: false, size: 27);
    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text: "${AppConst.fareTicket} ${AppConst.moneyTicket} đồng",
        bold: false,
        size: 25);
    await SunmiPrinter.setAlignment(1);
    //giờ vào
    await SunmiPrinter.printText(
        text:
            "${AppConst.ticketStartingDateHP} ${DateTime.now().hour} h ${DateTime.now().minute} p",
        bold: false,
        size: 20);
    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text:
            "${AppConst.day} ${DateTime.now().day} ${AppConst.month} ${DateTime.now().month} ${AppConst.year} ${DateTime.now().year}",
        bold: false,
        size: 19);
    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text:
            "${AppConst.ncc} ${AppConst.nameCompanyNCC} - ${AppConst.nameTaxCode} ${AppConst.taxCode} \n \t ${AppConst.custommerService} ${AppConst.phoneCustomerService}",
        bold: true,
        size: 17);
    await SunmiPrinter.printLine(3);
    await SunmiPrinter.cutPaper();
  }
  Future<void> printTicket_ThanhHoa() async{

    await SunmiPrinter.startPrinter();
    await SunmiPrinter.printText(
        text: AppConst.nameCompany2, bold: true, size: 20);
    await SunmiPrinter.printText(
        text: AppConst.addressConpany2, bold: false, size: 18, underLine: false);
    await SunmiPrinter.printText(
        text: "${AppConst.taxCodeName} ${AppConst.taxCodeCustomer}", bold: false, size: 21);
    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text: AppConst.nameTicket2, bold: false, size: 30);
    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text: AppConst.location, bold: false, size: 27);
    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text: "${AppConst.fareTicket} ${AppConst.moneyTicket2} đồng",
        bold: false,
        size: 25);

    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text: "${AppConst.ticketStartingDate} ${DateTime.now().hour} h ${DateTime.now().minute} p ",
        bold: false,
        size: 20);
    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text:
        "${AppConst.day} ${DateTime.now().day} ${AppConst.month} ${DateTime.now().month} ${AppConst.year} ${DateTime.now().year}",
        bold: false,
        size: 19);
    await SunmiPrinter.setAlignment(1);
    await SunmiPrinter.printText(
        text:
        "${AppConst.ncc} ${AppConst.nameCompanyNCC} - ${AppConst.nameTaxCode} ${AppConst.taxCode} \n \t ${AppConst.custommerService} ${AppConst.phoneCustomerService}",
        bold: true,
        size: 17);

    await SunmiPrinter.printLine(3);
    await SunmiPrinter.cutPaper();
}
}
