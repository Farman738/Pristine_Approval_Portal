package com.example.pristine_approval_portal.retrofithit.retrofitApi;

public class RetrofitApiUtils {

    public RetrofitApiUtils(){

    }
    //todo main url of pristine company or domain name
    public static String BASE_Main_URL = "https://pristinefulfil.com";

    public static NetworkInterface getPristineAPIService(){
        return NetworkClient.getClient(BASE_Main_URL).create(NetworkInterface.class);
    }
}
