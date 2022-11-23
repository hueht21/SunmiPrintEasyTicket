
import 'package:flutter/material.dart';

import 'features/printer_pos/views/home_printer.dart';

void main() {
  runApp(

    MaterialApp(
      debugShowCheckedModeBanner: false,
      title: "Application",
      home: HomePrinterView(),
    ),
  );
}

