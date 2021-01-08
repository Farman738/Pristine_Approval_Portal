package com.example.pristine_approval_portal.ui.home;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.pristine_approval_portal.Dashboard;
import com.example.pristine_approval_portal.R;
import com.example.pristine_approval_portal.globalization.LoadingDialog;
import com.example.pristine_approval_portal.sessionManagement.SessionManagement;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    SessionManagement sessionManagement;
    LoadingDialog loadingDialog;
    ListView listview_for_dashboard;
    BarChart barchart,barchart1;
//    LinearLayout linearlayout_show_all_cardview;
//    ImageView imageView_dashboard_purchase_order,imageView_dashboard_indent,imageView_dashboard_purchase_invoice,imageView_dashboard_purchase_request,
//            imageView_dashboard_purchase_receipt;
//    TextView tv_count_indent,tv_count_po_request,tv_count_purchase_order,tv_count_purchase_receipt,tv_count_purchase_invoice,tv_count_batch_workflow;

    public View onCreateView( LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }


    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManagement = new SessionManagement(getActivity());
        loadingDialog = new LoadingDialog();
        setInitialValues(view);
        bindBarchart(view);
        bindBarchart1(view);

    }


    private void setInitialValues(View view) {
        //todo List view
//        listview_for_dashboard = view.findViewById(R.id.listview_for_dashboard);


    }

    void bindBarchart(View view){
        barchart = view.findViewById(R.id.barchart);
        ArrayList<BarEntry> visitor = new ArrayList<>();
        visitor.add(new BarEntry(2f, 170));
        visitor.add(new BarEntry(4f, 270));
        visitor.add(new BarEntry(6f, 320));
        visitor.add(new BarEntry(8f, 340));
        BarDataSet barDataSet = new BarDataSet(visitor, "Visitor");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(8f);
        BarData barData = new BarData(barDataSet);
        barchart.setFitBars(true);
        barchart.setData(barData);
        barchart.getDescription().setText("Bar Chart Example");
        barchart.animateY(1000);
        barchart.setScaleEnabled(false);
    }
    void bindBarchart1(View view){
        barchart1 = view.findViewById(R.id.barchart1);
        ArrayList<BarEntry> visitor = new ArrayList<>();
        visitor.add(new BarEntry(2f, 170));
        visitor.add(new BarEntry(4f, 270));
        visitor.add(new BarEntry(6f, 320));
        visitor.add(new BarEntry(8f, 340));
        BarDataSet barDataSet = new BarDataSet(visitor, "Visitor");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(8f);
        BarData barData = new BarData(barDataSet);
        barchart1.setFitBars(true);
        barchart1.setData(barData);
        barchart1.getDescription().setText("Bar Chart Example");
        barchart1.animateY(1000);
        barchart1.setScaleEnabled(false);
    }



    public void loadFragment(int id, String fragmentName,Bundle bundle){
        NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
        navController.navigate(id,bundle);
        getActivity().setTitle(fragmentName);
    }

    @Override
    public void onResume() {
        super.onResume();
        Dashboard.tv_header_name.setText("Dashboard");
    }
}