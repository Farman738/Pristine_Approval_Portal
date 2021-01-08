package com.example.pristine_approval_portal.Model;
import java.util.List;

public class LoginModel {
    public boolean condition;
    public String message;
    public String name;
    public String email;
    public String password;
    public String roleId;
    public String locationId;
    public String location_name;
    public String shiftID;
    public String workType;
    public String printerIP;
    public String printerPort;
    public String gateentry;
    public String barcode;
    public String shipment;
    public String pick;

    public List<LoginMenu> menu;

    public class LoginMenu{
        public String id;
        public String title;
        public String translate;
        public String type;
        public String icon;
        public List<SubMenuChild> children;
    }
    public class SubMenuChild {
        public String id;
        public String title;
        public String type;
        public String url;
    }
}
