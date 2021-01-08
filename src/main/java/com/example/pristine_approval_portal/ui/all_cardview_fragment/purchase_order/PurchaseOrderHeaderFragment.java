package com.example.pristine_approval_portal.ui.all_cardview_fragment.purchase_order;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pristine_approval_portal.Dashboard;
import com.example.pristine_approval_portal.R;
import com.example.pristine_approval_portal.allapilinks.AllApiLinks;
import com.example.pristine_approval_portal.globalization.LoadingDialog;
import com.example.pristine_approval_portal.retrofithit.Http_Handler.AsyModel;
import com.example.pristine_approval_portal.retrofithit.Http_Handler.GlobalPostingMethod;
import com.example.pristine_approval_portal.retrofithit.Http_Handler.HttpHandlerModel;
import com.example.pristine_approval_portal.sessionManagement.SessionManagement;
import com.example.pristine_approval_portal.ui.allModel.purchaseOrderModel.PurchaseOrderHeaderModel;
import com.example.pristine_approval_portal.ui.allModel.purchaseOrderModel.PurchaseOrderLineModel;
import com.example.pristine_approval_portal.ui.all_cardview_fragment.purchase_order.purchase_order_adapter.PurchaseOrderLineAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URL;
import java.util.List;


public class PurchaseOrderHeaderFragment extends Fragment {
    SessionManagement sessionManagement;
    LoadingDialog loadingDialog;
    TextView tv_doc_type_for_po_header,tv_vendor_num_for_po_header,tv_vendor_name_for_po_header,tv_city_for_po_header,tv_location_for_po_header,tv_contact_num_for_po_header,tv_buy_from_address_for_po_header,
            tv_ship_to_address_for_po_header;
    ListView listview_purchase_order_header_line;
    public List<PurchaseOrderHeaderModel.AllValuesForPOHeader> purchaseOrderHeaderModels;
    public List<PurchaseOrderLineModel.AllValue> purchaseOrderLineModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_purchase_order_header, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        //todo set initial values for po header
        setInitialValuesForPOHeader(view);



    }

    private void setInitialValuesForPOHeader(View view) {
        //todo textview for header
        tv_doc_type_for_po_header = view.findViewById(R.id.tv_doc_type_for_po_header);
        tv_vendor_num_for_po_header = view.findViewById(R.id.tv_vendor_num_for_po_header);
        tv_vendor_name_for_po_header = view.findViewById(R.id.tv_vendor_name_for_po_header);
        tv_city_for_po_header = view.findViewById(R.id.tv_city_for_po_header);
        tv_location_for_po_header = view.findViewById(R.id.tv_location_for_po_header);
        tv_contact_num_for_po_header = view.findViewById(R.id.tv_contact_num_for_po_header);
        tv_buy_from_address_for_po_header = view.findViewById(R.id.tv_buy_from_address_for_po_header);
        tv_ship_to_address_for_po_header = view.findViewById(R.id.tv_ship_to_address_for_po_header);
        //todo listview
        listview_purchase_order_header_line = view.findViewById(R.id.listview_purchase_order_header_line);

        //todo api hit for PO header
        bindApiHitForPOHeader();
    }

    private void bindApiHitForPOHeader() {
        try {
            AsyModel objAsy = new AsyModel(AllApiLinks.PurchaseOrderHeader,null,"PurchaseOrderHeaderApiHit");
            new HitToServer().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,objAsy);

        }catch (Exception e){

        }
    }
    public class HitToServer extends AsyncTask<AsyModel,Void, HttpHandlerModel>{
        private GlobalPostingMethod hitObj = new GlobalPostingMethod(sessionManagement);
        private String flagOfAction;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog = new LoadingDialog();
            loadingDialog.showLoadingDialog(getActivity());
        }

        @Override
        protected HttpHandlerModel doInBackground(AsyModel... asyModels) {
            try {
                URL createPostingUrl = hitObj.createUrl(asyModels[0].postingUrl);
                flagOfAction = asyModels[0].getFlagOfAction();
                if(asyModels[0].getPostingJson()== null) {
                    return hitObj.getHttpRequest(createPostingUrl);
                } else {
                    return hitObj.postHttpRequest(createPostingUrl,asyModels[0].getPostingJson());
                }

            }catch (Exception e){
                return hitObj.setReturnMessage(false,"Problem retrieving the user JSON results.",e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(HttpHandlerModel result) {
            super.onPostExecute(result);
            loadingDialog.dismissLoading();
            bindResponse(result,flagOfAction);
        }
    }
    PurchaseOrderLineAdapter purchaseOrderLineAdapter;
    private void bindResponse(HttpHandlerModel result, String flagOfAction) {
        try{
            if(result.isConnectStatus()){
                if(flagOfAction.equalsIgnoreCase("PurchaseOrderHeaderApiHit")){
                    PurchaseOrderHeaderModel tempPOHeaderModel = new Gson().fromJson(result.getJsonResponse(),new TypeToken<PurchaseOrderHeaderModel>(){}
                            .getType());
                    if(tempPOHeaderModel!=null){
                        purchaseOrderHeaderModels = tempPOHeaderModel.value;
                        assignValuesForPoHeader();
//                        purchaseOrderHeaderAdapter = new PurchaseOrderHeaderAdapter(getActivity(),tempPOHeaderModel);
//                        listview_purchase_order_header.setAdapter(purchaseOrderHeaderAdapter);

                    }else {
                        Toast.makeText(getActivity(),"No record found",Toast.LENGTH_LONG).show();

                    }

                } else if(flagOfAction.equalsIgnoreCase("PurchaseOrderLineApiHit")){
                    PurchaseOrderLineModel tempPOLineModel = new Gson().fromJson(result.getJsonResponse(),new TypeToken<PurchaseOrderLineModel>(){}
                            .getType());
                    if(tempPOLineModel!=null){
                        purchaseOrderLineModel = tempPOLineModel.value;
                        purchaseOrderLineAdapter = new PurchaseOrderLineAdapter(getActivity(),tempPOLineModel);
                        listview_purchase_order_header_line.setAdapter(purchaseOrderLineAdapter);

                    }else {
                        Toast.makeText(getActivity(),"No record found",Toast.LENGTH_LONG).show();

                    }

                }

            }else {
                Toast.makeText(getActivity(),result.getJsonResponse(),Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){

        }
    }

    private void assignValuesForPoHeader() {
        tv_doc_type_for_po_header.setText(purchaseOrderHeaderModels.get(0).Document_Type);
        tv_vendor_num_for_po_header.setText(purchaseOrderHeaderModels.get(0).Buy_from_Vendor_No);
        tv_vendor_name_for_po_header.setText(purchaseOrderHeaderModels.get(0).Buy_from_Vendor_Name);
        tv_city_for_po_header.setText(purchaseOrderHeaderModels.get(0).Buy_from_City);
        tv_location_for_po_header.setText(purchaseOrderHeaderModels.get(0).Location_Code);
        tv_contact_num_for_po_header.setText(purchaseOrderHeaderModels.get(0).Buy_from_Contact);
        tv_buy_from_address_for_po_header.setText(purchaseOrderHeaderModels.get(0).Buy_from_Address);
        tv_ship_to_address_for_po_header.setText(purchaseOrderHeaderModels.get(0).Ship_to_Address);

        try {
            AsyModel objAsy = new AsyModel(AllApiLinks.PurchaseOrderLine+purchaseOrderHeaderModels.get(0).No+"&document_type="+"Purchase Order Line",null,"PurchaseOrderLineApiHit");
            new HitToServer().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,objAsy);

        }catch (Exception e){

        }
    }

    public void loadFragment(int id, String fragmentName,Bundle bundle){
        NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
        navController.navigate(id,bundle);
        getActivity().setTitle(fragmentName);
    }
    @Override
    public void onResume() {
        super.onResume();
        Dashboard.tv_header_name.setText("Purchase Order Header");
    }
}