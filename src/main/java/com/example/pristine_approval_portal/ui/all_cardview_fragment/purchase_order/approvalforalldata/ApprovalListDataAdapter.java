package com.example.pristine_approval_portal.ui.all_cardview_fragment.purchase_order.approvalforalldata;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.pristine_approval_portal.R;
import com.example.pristine_approval_portal.ui.allModel.purchaseOrderModel.ApprovalModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ApprovalListDataAdapter extends BaseAdapter {
    public List<ApprovalModel> listDataApproval;
    public Activity activity;

    public ApprovalListDataAdapter(Activity activity, List<ApprovalModel> listDataApproval){
        this.listDataApproval = listDataApproval;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listDataApproval.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataApproval.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.approval_list_layout,null);

        TextView tv_doc_type_approval_line = convertView.findViewById(R.id.tv_doc_type_approval_line);
        TextView tv_approve_id_approval_line = convertView.findViewById(R.id.tv_approve_id_approval_line);
        TextView tv_approve_code_approval_line = convertView.findViewById(R.id.tv_approve_code_approval_line);
        TextView tv_approval_date_po_header_line = convertView.findViewById(R.id.tv_approval_date_po_header_line);

        //todo imageview for arrow forward
        ImageView img_arrrow_forward_approval_line = convertView.findViewById(R.id.img_arrrow_forward_approval_line);

        //todo set textview data into listview
        tv_doc_type_approval_line.setText(listDataApproval.get(position).documnet_type);
        tv_approve_id_approval_line.setText(listDataApproval.get(position).approver_id);
        tv_approve_code_approval_line.setText(listDataApproval.get(position).approval_code);
        tv_approval_date_po_header_line.setText(getDateMMMDDYYYY(listDataApproval.get(position).date_time_sent_for_approval));

        img_arrrow_forward_approval_line.setOnClickListener(view -> {
            if(listDataApproval.get(position).documnet_type.equalsIgnoreCase("1")){
                ShowPurchaseOrder(position);
            }

        });

        return convertView;
    }
    public static String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov",
            "Dec"};
    private String getDateMMMDDYYYY(String value) {
        try {
            if (value == null || value.equalsIgnoreCase("")) {
                return "";

            }
            SimpleDateFormat formatter1 = new SimpleDateFormat("MM-dd-yyyy'T'HH:mm:ss");
            Date date = formatter1.parse(value);
            String month = monthName[date.getMonth()];
            int year = 1900 + date.getYear();
            long day = date.getDate();
            return month + " " + day + ", " + year;
        } catch (Exception e) {
            return value.substring(0, 10);
        }
    }

    private void ShowPurchaseOrder(int position) {
        Bundle args_po = new Bundle();
        loadFragment(R.id.nav_PurchaseOrderHeader_dashboard,"Purchase Order Header",args_po);

    }
  public void loadFragment(int id, String fragmentName,Bundle bundle){
      NavController navController = Navigation.findNavController(activity,R.id.nav_host_fragment);
      navController.navigate(id,bundle);
      activity.setTitle(fragmentName);

  }
}
