package com.example.pristine_approval_portal.ui.all_cardview_fragment.indent.indentHeaderAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pristine_approval_portal.R;
import com.example.pristine_approval_portal.ui.allModel.purchaseOrderModel.PurchaseOrderHeaderModel;

import java.util.List;

public class IndentHeaderAdapter extends BaseAdapter {
    public Activity activity;
    public PurchaseOrderHeaderModel listDataIndentHeader;

    public IndentHeaderAdapter(Activity activity,PurchaseOrderHeaderModel listDataIndentHeader){
        this.activity = activity;
        this.listDataIndentHeader = listDataIndentHeader;

    }

    @Override
    public int getCount() {
        return listDataIndentHeader.value.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataIndentHeader.value.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.indent_header_list_layout,null);

        TextView tv_doc_num_indent_header = convertView.findViewById(R.id.tv_doc_num_indent_header);
        TextView tv_vendor_num_header = convertView.findViewById(R.id.tv_vendor_num_header);
        TextView tv_status_header = convertView.findViewById(R.id.tv_status_header);
        TextView tv_date_header = convertView.findViewById(R.id.tv_date_header);
        ImageView img_arrrow_forward_header = convertView.findViewById(R.id.img_arrrow_forward_header);

        tv_doc_num_indent_header.setText(listDataIndentHeader.value.get(position).No);
        tv_vendor_num_header.setText(listDataIndentHeader.value.get(position).Buy_from_Vendor_No);
        tv_status_header.setText(listDataIndentHeader.value.get(position).Status);
        tv_date_header.setText(listDataIndentHeader.value.get(position).Posting_Date);

        return convertView;
    }
}
