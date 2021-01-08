package com.example.pristine_approval_portal.ui.home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pristine_approval_portal.R;
import com.example.pristine_approval_portal.ui.allModel.purchaseOrderModel.PurchaseOrderHeaderModel;

public class HomeListViewAdapter extends BaseAdapter {


    public interface OnItemClickListener{
       void onItemClick(String item_no, String flag);
    }
    private final OnItemClickListener listener;
    public PurchaseOrderHeaderModel listDataHome;
    public Activity activity;
    public HomeListViewAdapter(Activity activity, PurchaseOrderHeaderModel listDataHome,OnItemClickListener listener){
        this.listener = listener;
        this.activity = activity;
        this.listDataHome = listDataHome;
    }

    @Override
    public int getCount() {
        int size_per = listDataHome.value.size()%2;
        if(size_per>0)
            return (listDataHome.value.size()/2)+1;
        else
        return (listDataHome.value.size()/2);
    }

    @Override
    public Object getItem(int position) {
        return listDataHome.value.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        LayoutInflater inflater = activity.getLayoutInflater();
        convertView = inflater.inflate(R.layout.home_recyclerview_list_layout,null);



        return convertView;
    }
}
