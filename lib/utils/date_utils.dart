

import 'package:intl/intl.dart';
const String PATTERN_1 = "dd/MM/yyyy";
const String PATTERN_DD = "dd";
const String PATTERN_MM = "MM";
const String PATTERN_YY = "yyyy";
const String PATTERN_2 = "dd/MM";
const String PATTERN_3 = "yyyy-MM-dd'T'HHmmss";
const String PATTERN_4 = "h:mm a dd/MM";
const String PATTERN_5 = "yyyy-MM-dd HH:mm:ss";
const String PATTERN_6 = "dd/MM/yyyy HH:mm";
const String PATTERN_7 = "HH:mm dd/MM/yyyy";
const String PATTERN_8 = "yyyy-MM-ddTHH:mm:ss";
const String PATTERN_9 = "HH:mm - dd/MM/yyyy";
const String PATTERN_10 = "dd/MM/yyyy HH:mm:ss";
const String PATTERN_11 = "HH:mm";
const String PATTERN_DEFAULT = "yyyy-MM-dd";

String convertDateToString(DateTime dateTime, String pattern) {
  return DateFormat(pattern).format(dateTime);
}