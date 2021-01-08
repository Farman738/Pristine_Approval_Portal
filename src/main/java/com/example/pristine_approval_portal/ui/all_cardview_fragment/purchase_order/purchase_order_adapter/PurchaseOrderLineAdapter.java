package com.example.pristine_approval_portal.ui.all_cardview_fragment.purchase_order.purchase_order_adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pristine_approval_portal.R;
import com.example.pristine_approval_portal.ui.allModel.purchaseOrderModel.PurchaseOrderLineModel;


public class PurchaseOrderLineAdapter extends BaseAdapter {
    public PurchaseOrderLineModel listDataPurchaseOrder;
    public Activity activity;

    public PurchaseOrderLineAdapter(Activity activity, PurchaseOrderLineModel listDataPurchaseOrder){
        this.activity = activity;
        this.listDataPurchaseOrder = listDataPurchaseOrder;
    }
    @Override
    public int getCount() {
        return listDataPurchaseOrder.value.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataPurchaseOrder.value.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.purchase_order_line_list_layout,null);

        TextView tv_doc_num_po_line = convertView.findViewById(R.id.tv_doc_num_po_line);
        TextView tv_qty_po_line = convertView.findViewById(R.id.tv_qty_po_line);
        TextView tv_cost_lcy_po_line = convertView.findViewById(R.id.tv_cost_lcy_po_line);
        TextView tv_type_po_line = convertView.findViewById(R.id.tv_type_po_line);
        TextView tv_line_num_po_line = convertView.findViewById(R.id.tv_line_num_po_line);

        //todo bind textview data into listview
        tv_doc_num_po_line.setText(listDataPurchaseOrder.value.get(position).Document_No);
        tv_qty_po_line.setText(listDataPurchaseOrder.value.get(position).Quantity);
        tv_cost_lcy_po_line.setText(listDataPurchaseOrder.value.get(position).Unit_Cost_LCY);
        tv_type_po_line.setText(listDataPurchaseOrder.value.get(position).Type);
        tv_line_num_po_line.setText(listDataPurchaseOrder.value.get(position).Line_No);

        return convertView;
    }
}
