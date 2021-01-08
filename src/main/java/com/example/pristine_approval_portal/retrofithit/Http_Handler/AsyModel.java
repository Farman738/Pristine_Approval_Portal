package com.example.pristine_approval_portal.retrofithit.Http_Handler;

import org.json.JSONObject;

public class AsyModel {

    public String postingUrl;
    public JSONObject postingJson;
    public String flagOfAction;

    public AsyModel(String postingUrl, JSONObject postingJson,String flagOfAction){
        this.postingUrl = postingUrl;
        this.flagOfAction = flagOfAction;
        this.postingJson = postingJson;

    }

    public String getPostingUrl() {
        return postingUrl;
    }

    public String getFlagOfAction() {
        return flagOfAction;
    }

    public JSONObject getPostingJson() {
        return postingJson;
    }
}

