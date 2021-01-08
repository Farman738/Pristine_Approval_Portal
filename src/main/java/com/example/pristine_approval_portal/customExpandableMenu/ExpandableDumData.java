package com.example.pristine_approval_portal.customExpandableMenu;

import android.content.Context;

import com.example.pristine_approval_portal.Model.LoginModel;
import com.example.pristine_approval_portal.sessionManagement.SessionManagement;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExpandableDumData {
    public static Map<String, List<String>> getData(Context context) {
        Map<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();
        try {
            List<LoginModel.LoginMenu> menu = new Gson().fromJson(new SessionManagement(context).getMenu(), new TypeToken<List<LoginModel.LoginMenu>>() {
            }.getType());
            for (int i = 0; i < menu.size(); i++) {
                List<String> menulist = new ArrayList<String>();
                for (int j = 0; j < menu.get(i).children.size(); j++) {
                    menulist.add(menu.get(i).children.get(j).title);
                }
                expandableListDetail.put(menu.get(i).title, menulist);
            }
            return expandableListDetail;
        }catch (Exception e){
            return expandableListDetail;
        }
    }
}
