package com.example.pristine_approval_portal.sessionManagement;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.pristine_approval_portal.globalization.AESUtils;

public class SessionManagement {

    SharedPreferences sp;

    public SessionManagement(Context context) {
        sp = context.getSharedPreferences("Pristine Approval Portal", Context.MODE_PRIVATE);
    }

//todo set sessison
    public void setUsername(String username) { setSharedPreferences("name",username); }
    public void setUserEmail(String userEmail) { setSharedPreferences("email",userEmail); }
    public void setMenu(String menu) { setSharedPreferences("menu",menu); }

    //todo get sessison
    public String getUserName()  { return getDataFromSharedPreferences("name"); }
    public String getUserEmail()  { return getDataFromSharedPreferences("email"); }
    public String getMenu()  { return getDataFromSharedPreferences("menu"); }

    public void setSharedPreferences(String key, String value){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public String getDataFromSharedPreferences(String key) {
  try{
      String returnString = sp.getString(key,null);
      return (returnString);
  }catch (Exception e){
  return "";
  }
    }

    public void ClearSession(){
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

}
