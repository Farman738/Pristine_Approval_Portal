package com.example.pristine_approval_portal.ui.all_cardview_fragment.purchase_order.approvalforalldata;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pristine_approval_portal.Dashboard;
import com.example.pristine_approval_portal.R;
import com.example.pristine_approval_portal.globalization.LoadingDialog;
import com.example.pristine_approval_portal.retrofithit.retrofitApi.NetworkInterface;
import com.example.pristine_approval_portal.retrofithit.retrofitApi.RetrofitApiUtils;
import com.example.pristine_approval_portal.sessionManagement.SessionManagement;
import com.example.pristine_approval_portal.ui.allModel.purchaseOrderModel.ApprovalModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApprovalDataFragment extends Fragment {

     SessionManagement sessionManagement;
     public ListView listview_for_dashboard_approval;
    LoadingDialog loadingDialog;
     public String document_id="";
     LinearLayout linearlayout_show_error_message_layout;
     TextView tv_error_message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_approval_data, container, false);
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         sessionManagement = new SessionManagement(getActivity());
         loadingDialog = new LoadingDialog();
        //todo set initalvalues for approval
        setinitialValesForApproval(view);
        document_id = getArguments().getString("GetDocumentIdForPoInvoice");

        try {
            if(!loadingDialog.getLoadingState()) {
                NetworkInterface mApiServiceApproval = RetrofitApiUtils.getPristineAPIService();
                Call<List<ApprovalModel>> approval = mApiServiceApproval.getApproval(document_id, sessionManagement.getUserName());
                loadingDialog.showLoadingDialog(getActivity());
                approval.enqueue(new Callback<List<ApprovalModel>>() {
                    @Override
                    public void onResponse(Call<List<ApprovalModel>> call, Response<List<ApprovalModel>> response) {
                        try {
                            if (response.isSuccessful()) {
                                List<ApprovalModel> getApprovalDataModel = response.body();
                                if (getApprovalDataModel.size() > 0 && getApprovalDataModel.get(0).condition) {
                                    approvalListDataAdapter = new ApprovalListDataAdapter(getActivity(), getApprovalDataModel);
                                    listview_for_dashboard_approval.setAdapter(approvalListDataAdapter);

                                } else {
//                                    Toast.makeText(getActivity(), getApprovalDataModel.get(0).message, Toast.LENGTH_LONG).show();
                                    linearlayout_show_error_message_layout.setVisibility(View.VISIBLE);
                                    tv_error_message.setText(getApprovalDataModel.get(0).message);
                                    listview_for_dashboard_approval.setVisibility(View.GONE);
                                }

                            } else {

                                Toast.makeText(getActivity(), response.message(), Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {

                        } finally {
                            loadingDialog.dismissLoading();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<ApprovalModel>> call, Throwable t) {
                        loadingDialog.dismissLoading();
                    }
                });

            }
        }catch (Exception e){

        }


    }

    private void setinitialValesForApproval(View view) {
        //todo listview
        listview_for_dashboard_approval = view.findViewById(R.id.listview_for_dashboard_approval);

        //todo linearlayout
        linearlayout_show_error_message_layout = view.findViewById(R.id.linearlayout_show_error_message_layout);
        tv_error_message = view.findViewById(R.id.tv_error_message);
        
        //todo method for hit All Api
        ApiHitMethod();
    }

    ApprovalListDataAdapter approvalListDataAdapter;
    private void ApiHitMethod() {
        //todo get api hit for approval order list


    }
    public void loadFragment(int id, String fragmentName){
        NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
        navController.navigate(id);
        getActivity().setTitle(fragmentName);
    }
    @Override
    public void onResume() {
        super.onResume();
        Dashboard.tv_header_name.setText("Approval List");
    }
}