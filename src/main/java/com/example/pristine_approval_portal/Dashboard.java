package com.example.pristine_approval_portal;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pristine_approval_portal.customExpandableMenu.CustomExpandableListAdapter;
import com.example.pristine_approval_portal.customExpandableMenu.ExpandableDumData;
import com.example.pristine_approval_portal.retrofithit.retrofitApi.NetworkInterface;
import com.example.pristine_approval_portal.retrofithit.retrofitApi.RetrofitApiUtils;
import com.example.pristine_approval_portal.sessionManagement.SessionManagement;
import com.example.pristine_approval_portal.sql_lite_process.dao.ExceptionDao;
import com.example.pristine_approval_portal.sql_lite_process.database.PristineDatabase;
import com.example.pristine_approval_portal.sql_lite_process.model.ExceptionTableModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.pristine_approval_portal.sql_lite_process.database.PristineDatabase.databaseWriteExecutor;

public class Dashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    NavigationView navigationView;
    SessionManagement sessionManagement;
    ImageView imge_menu,imge_logout;
    public static TextView tv_header_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sessionManagement=new SessionManagement(this);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        imge_menu = findViewById(R.id.imge_menu);
        tv_header_name = findViewById(R.id.tv_header_name);
        imge_logout = findViewById(R.id.imge_logout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        imge_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeSidePanel();
            }
        });
        imge_logout.setOnClickListener(view -> {
            logOutMethod();
        });
        Expandiblelistbind();

//        AppExceptionSendToTheServer();
        bindSideMenuHeader();

    }



    private void closeSidePanel() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
       }else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    TextView tv_userName,tv_user_Email;
    void bindSideMenuHeader(){
        View headerLayout = navigationView.getHeaderView(0);
        tv_userName = headerLayout.findViewById(R.id.tv_header_tittle_person_name);
        tv_user_Email = headerLayout.findViewById(R.id.tv_header_tittle_person_email);
        tv_userName.setText(sessionManagement.getUserName());
        tv_user_Email.setText(sessionManagement.getUserEmail());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }
    private void logOutMethod() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Confirm...")
                .setMessage("Do you really want to Logout?")
                .setIcon(R.drawable.ic_baseline_add_alert_gray_24dp)
                .setPositiveButton("Confirm", (dialogInterface, i1) -> {
                    sessionManagement.ClearSession();
                    Intent mainIntent = new Intent(this, SplashActivity.class);
                    startActivity(mainIntent);
                    finish();
                })
                .setNegativeButton("Cancel", (dialogInterface, i1) -> {

                })
                .show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
//        if (item.getItemId() == R.id.action_Logout) {
//
//            return true;
//        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    //todo bind Expandible
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    Map<String, List<String>> expandableListDetail;
    public int selectedchildPosition = 0;
    public String groupName = "";

    public void Expandiblelistbind() {
        expandableListView = findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableDumData.getData(getApplicationContext());
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupClickListener((expandableListView1, view, groupPosition, l) -> {
//            Toast.makeText(getApplicationContext(), expandableListTitle.get(groupPosition) + " List Click.", Toast.LENGTH_SHORT).show();
            if (expandableListView.isGroupExpanded(groupPosition))
                expandableListView.collapseGroup(groupPosition);
            else
                expandableListView.expandGroup(groupPosition);
            if (expandableListAdapter.getChildrenCount(groupPosition) <= 0) {
                SelectGroupNavigation(expandableListTitle.get(groupPosition));
            }
            return true;
        });
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            for (int g = 0; g < expandableListAdapter.getGroupCount(); g++) {
                if (g != groupPosition) {
                    expandableListView.collapseGroup(g);
                }
            }
            expandableListView.setItemChecked(selectedchildPosition, expandableListTitle.get(groupPosition).equalsIgnoreCase(groupName) ? true : false);
        });
        expandableListView.setOnGroupCollapseListener(groupPosition -> {
//              Toast.makeText(getApplicationContext(),expandableListTitle.get(groupPosition) + " List Collapsed.", Toast.LENGTH_SHORT).show();
        });
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            // Toast.makeText(getApplicationContext(),expandableListTitle.get(groupPosition) + " -> "+ expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
            int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
            parent.setItemChecked(index, true);
            groupName = expandableListTitle.get(groupPosition);
            selectedchildPosition = index;
            SelectedChildNavigationExpandible(expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition));
            return false;
        });
    }

    void SelectedChildNavigationExpandible(String selectedChild) {
        switch (selectedChild) {
            case "Dashboard": {
//                 Bundle args = new Bundle();
                loadFragments(R.id.nav_home, "Dashboard");
                break;
            }

        }
    }


    void SelectGroupNavigation(String GroupName) {
        switch (GroupName) {
            case "Without Gallery": {
                loadFragments(R.id.nav_gallery, "Without Gallery");
                break;
            }
        }
    }
    private void loadFragments(int id, String fragmentName) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigateUp();
        navController.navigate(id);
        closeSidePanel();
        this.setTitle(fragmentName);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}