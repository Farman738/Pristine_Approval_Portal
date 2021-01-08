package com.example.pristine_approval_portal.retrofithit.retrofitApi;

import com.example.pristine_approval_portal.Model.LoginModel;
import com.example.pristine_approval_portal.ui.allModel.purchaseOrderModel.ApprovalModel;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetworkInterface {

    @POST("/api/User/Login") //todo login api hit
    Call<List<LoginModel>> postLogin(@Body JsonObject jsonObject);

    @GET("/api/ApprovalEntries/GetApprovalEntriesData")//todo for approval api hit?document_type=2&approver_id=RAVINDER ASWAL"
    Call<List<ApprovalModel>> getApproval(@Query("document_type") String documentType,@Query ("approver_id") String approverId);


}
