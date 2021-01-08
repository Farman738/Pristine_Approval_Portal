package com.example.pristine_approval_portal.retrofithit.Http_Handler;

public class HttpHandlerModel {

    public boolean connectStatus;
    public String jsonResponse;
    public String localFileUrl;
    public String printer_message;

    public HttpHandlerModel(){
        this.setConnectStatus(false);
        this.setJsonResponse("");
    }

    public boolean isConnectStatus() {
        return connectStatus;
    }
    public void setConnectStatus(boolean connectStatus){ this.connectStatus = connectStatus;}

    public String getJsonResponse() {
        return jsonResponse;
    }

    public void setJsonResponse(String jsonResponse) {
        this.jsonResponse = jsonResponse;
    }

    public String getLocalFileUrl() {
        return localFileUrl;
    }

    public void setLocalFileUrl(String localFileUrl) {
        this.localFileUrl = localFileUrl;
    }

    public String getPrinter_message() {
        return printer_message;
    }

    public void setPrinter_message(String printer_message) {
        this.printer_message = printer_message;
    }
}
