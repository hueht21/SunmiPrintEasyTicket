# sunmi_printer_easyticket

# I have implemented a lot of other features described below, the typeface is bolder
# I have updated version 1.0.7 with more features
# Important:
**THIS PACKAGE WILL WORK ONLY IN ANDROID!**
- [x] Jump (n) lines
- [x] Bold mode on/off
- [x] Adjustable font size
- [x] Can print qrcode
- [x] Can print table
- [x] Use SignikaNegative-Bold font as large font
- [x] Use OpenSans-Bold font as large font
- [x] Set font size 20 as center font size to separate two typefaces
- [x] Cut paper - Dedicated method just to cut the line

## Tested Devices

Sunmi V2 Pro

## import packages
import 'package:easy_ticket_b08/features/printer_pos/service_printer_pos/sunmi_printer.dart';
// all method from sunmi printer need to async await

await SunmiPrinter.bindPrinterService(); // Initialize the printer
await SunmiPrinter.startPrinter(); // start printer
await SunmiPrinter.printLine(3); // Jump (3) lines
await SunmiPrinter.cutPaper(); //
await SunmiPrinter.unbindPrinterService(); // unbind printer
 ```
## Example of printing a parking ticket
    await SunmiPrinter.startPrinter();
    await SunmiPrinter.printText(text: AppConst.nameCompany, bold: true, size: 20);
    await SunmiPrinter.printText(text: "${AppConst.taxCodeName} ${AppConst.taxCodeCustomer}", bold: false, size: 20); // size =20 : font size printer
    await SunmiPrinter.setAlignment(1); // 0 : Left align , 1 : Center align, 2 : Right align
    await SunmiPrinter.printLine(3); // Jump (3) lines
    await SunmiPrinter.cutPaper(); // Dedicated method just to cut the line

## Example of printing invoice table
    await SunmiPrinter.printTable(size: 21, cols: [
        ColumnMaker(text: 'Name', width: 10, align: 0), // width: 10 Width of column , 0 - LEFT, 1 - CENTER, 2 - RIGHT  
        ColumnMaker(text: 'Qty', width: 6, align: 1),
        ColumnMaker(text: 'UN', width: 10, align: 2),
        ColumnMaker(text: 'TOT', width: 10, align: 2),
    ]);
    await SunmiPrinter.printTable(cols: [
        ColumnMaker(text: 'Sản phẩm A', width: 10, align: 0),
        ColumnMaker(text: '4x', width: 6, align: 1),
        ColumnMaker(text: '30.00000', width: 10, align: 2),
        ColumnMaker(text: '120.00000', width: 10, align: 2),
    ]);
    class ColumnMaker {
        String text;
        int width;
        int align;
        ColumnMaker({
            this.text = '',
            this.width = 2,
            this.align = 0,
        });
        }
    }
## Example of printing qrcode
    await SunmiPrinter.setAlignment(1); // Adjust the qrcode in the center position
    await SunmiPrinter.printQr(
        dataQRCode:"https://github.com/hueht21",
        modulesize: 5,
        errorlevel: 2);
