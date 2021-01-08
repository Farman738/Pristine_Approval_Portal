package com.example.pristine_approval_portal.globalization;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class CustomDatePicker {
    public Activity activity;
    public DatePickerDialog datePickerDialog;

    public CustomDatePicker(Activity activity){ this.activity = activity;}

    public void showDatePickerDialog(TextInputEditText editText){
//todo date formate shoud be 5-12-2013
        if(datePickerDialog==null || !datePickerDialog.isShowing()){
            String passDate = editText.getText().toString();
            int day =0,month=0,year=0;
          if(passDate!=null && !passDate.equalsIgnoreCase("")){
              String[] split = passDate.split("-");
              day = Integer.valueOf(split[0]);
              month = Integer.valueOf(split[1])-1;
              year = Integer.valueOf(split[2]);

          }else {
              Calendar calendar = Calendar.getInstance();
              year = calendar.get(Calendar.YEAR);
              month = calendar.get(Calendar.MONTH);
              day = calendar.get(Calendar.DAY_OF_MONTH);
          }
          DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
              @Override
              public void onDateSet(DatePicker datePicker, int year_this, int monthOfYear, int dayOfMonth) {
           editText.setText((monthOfYear+1)+"-"+dayOfMonth+"-"+year_this);

              }
          };
          datePickerDialog = new DatePickerDialog(activity,dateSetListener,day,month,year);
            datePickerDialog.setCancelable(false);
            datePickerDialog.show();


        }
    }
}
