
// import 'enums.dart';
class ColumnMaker {
  String text;
  int width;
  int align;
  ColumnMaker({
    this.text = '',
    this.width = 2,
    this.align = 0,
  });
  //Convert to json
  Map<String, String> toJson() {
    int value = 0;
    switch (align) {
      case 0:
        value = 0;
        break;
      case 1:
        value = 1;
        break;
      case 2:
        value = 2;
        break;
      default:
        value = 0;
    }
    return {
      "text": text,
      "width": width.toString(),
      "align": value.toString(),
    };
  }
}