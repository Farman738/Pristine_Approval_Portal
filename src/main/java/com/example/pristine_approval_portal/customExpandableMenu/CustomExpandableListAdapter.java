package com.example.pristine_approval_portal.customExpandableMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pristine_approval_portal.R;
import java.util.List;
import java.util.Map;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    public Context context;
    public List<String> expandableListTittle;
    public Map<String,List<String>> expandableListSubTittle;

    public CustomExpandableListAdapter(Context context, List<String> expandableListTittle,Map<String,List<String>> expandableListSubTittle){
        this.context = context;
        this.expandableListSubTittle = expandableListSubTittle;
        this.expandableListTittle = expandableListTittle;
    }
    @Override
    public int getGroupCount() {
        return expandableListTittle.size();
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListSubTittle.get(this.expandableListTittle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTittle.get(listPosition);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListSubTittle.get(this.expandableListTittle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }



    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        String listTittle = (String) getGroup(listPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_group,null);

        }
        TextView tv_menu_title_name = convertView.findViewById(R.id.tv_menu_title_name);
        tv_menu_title_name.setText(listTittle);
        bindGroupImage(listTittle,(ImageView) convertView.findViewById(R.id.img_list_group));
        ImageView img_arrow_up_down = convertView.findViewById(R.id.img_arrow_up_down);

        if(isExpanded){
            img_arrow_up_down.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_pink_24dp));
        }else {
            img_arrow_up_down.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_pink_24dp));

        }


        return convertView;
    }

    private void bindGroupImage(String listTittle, ImageView viewById) {
        if(listTittle.equalsIgnoreCase("DASHBOARD")){
            viewById.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_dashboard_pink_24dp));
        }
    }

    @Override
    public View getChildView(int listPosition, int expandedListPosition, boolean isLastChild, View convertView, ViewGroup viewGroup) {
        String childTittle = (String) getChild(listPosition,expandedListPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_item,null);
        }
        TextView expandedListItem = convertView.findViewById(R.id.expandedListItem);
        expandedListItem.setText(childTittle);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
}
