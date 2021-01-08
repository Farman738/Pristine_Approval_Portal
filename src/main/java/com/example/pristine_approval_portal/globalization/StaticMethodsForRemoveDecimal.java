package com.example.pristine_approval_portal.globalization;

import java.text.DecimalFormat;

public class StaticMethodsForRemoveDecimal {
 public static String removeDecimal(float number){
     DecimalFormat decimalFormat = new DecimalFormat("###.##");
   return decimalFormat.format(number);
 }
public static String removeDecimalKG(double number){
     DecimalFormat decimalFormat = new DecimalFormat("###.###");
     return decimalFormat.format(number);
}
public static String removeDecimal(String number){
     float decimalvalue = Float.parseFloat(number);
    DecimalFormat decimalFormat = new DecimalFormat("###.##");
    return decimalFormat.format(decimalvalue);
}
}
