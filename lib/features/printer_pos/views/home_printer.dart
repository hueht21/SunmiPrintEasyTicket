
import 'package:easy_ticket_b08/features/printer_pos/controllers/home_printer_controller.dart';
import 'package:easy_ticket_b08/features/printer_pos/service_printer_pos/sunmi_printer.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../../../consts/const.dart';

class HomePrinterView extends StatelessWidget {
  HomePrinterController controller = Get.put(HomePrinterController());

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      body: SafeArea(
        child: Column(
          children: [
            Center(
              child: ElevatedButton(
                onPressed: () async {
                  await controller.configPrinter();
                },
                child: const Text("In vé"),
              ),
            ),
            Center(
              child: ElevatedButton(
                onPressed: () async {
                  await SunmiPrinter.startPrinterExam();
                },
                child: const Text("Ví dụ"),
              ),
            )
          ],
        ),
      ),
    );
  }
}
