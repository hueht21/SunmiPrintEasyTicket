import 'package:easy_ticket_b08/features/printer_pos/views/home_printer.dart';
import 'package:get/get.dart';
part 'app_routes.dart';

class AppPages {
  AppPages._();

  static const INITIAL = Routes.HOME;

  static final routes = [
    GetPage(
      name: _Paths.HOME,
      page: () => HomePrinterView(),
    ),
  ];
}
