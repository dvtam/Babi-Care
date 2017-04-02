package com.fihou.babicare.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.fihou.babicare.Adapter.ChuongtrinhAdapter;
import com.fihou.babicare.Data.ChitietChuongtrinh;
import com.fihou.babicare.Data.Chuongtrinh;
import com.fihou.babicare.Data.Save;
import com.fihou.babicare.DiaLog.PassWordChange;
import com.fihou.babicare.R;
import com.fihou.babicare.Utility.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvusername, tvemail;
    private static boolean isshowfragment = false;
    PassWordChange passWordChange = new PassWordChange();
    ArrayList<ChitietChuongtrinh> listchitiet = new ArrayList<>();
    ChuongtrinhAdapter adapter;
    StaggeredGridView gvChuongtrinh;
    public AVLoadingIndicatorView avi;
    TextView tvtitle_Chuongtrinh, tvtime;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(2).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        tvusername = (TextView) header.findViewById(R.id.tvuser);
        tvemail = (TextView) header.findViewById(R.id.tvemail);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.show();
        if (Save.userInfos.getTentaikhoan().toString() != null && Save.userInfos.getEmail().toString() != null) {
            tvusername.setText(Save.userInfos.getTentaikhoan());
            tvemail.setText(Save.userInfos.getEmail());
        } else {
            tvusername.setText("");
            tvemail.setText("");
        }

        gvChuongtrinh = (StaggeredGridView) findViewById(R.id.lvChuongtrinh);
        getSupportActionBar().setTitle("Babi care");

//        listchitiet = Save.listchitietchuongtrinh;
        adapter = new ChuongtrinhAdapter(LogInActivity.listchitiet, this);
        adapter.setData(LogInActivity.listchitiet);
        adapter.notifyDataSetChanged();
        gvChuongtrinh.setAdapter(adapter);
        gvChuongtrinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this, ""+LogInActivity.listchitiet.get(i).getId(), Toast.LENGTH_SHORT).show();
                id = i;
                Intent chitiet = new Intent(MainActivity.this, ChuongtrinhActivity.class);
                chitiet.putExtra("id", i);
                startActivity(chitiet);
            }
        });
        tvtitle_Chuongtrinh.setText(LogInActivity.chuongtrinh.getChude().toUpperCase());
        tvtime.setText("Hôm nay thứ " + getnameofDay() + ", Ngày " +getcurrentDay());
    }

    private void initView() {
        tvtitle_Chuongtrinh = (TextView) findViewById(R.id.tvtitle_Chuongtrinh);
        tvtime = (TextView) findViewById(R.id.tvtime);
    }
    private String getnameofDay() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(calendar.getTime());

        String[] days = new String[]{"8", "2", "3", "4", "5", "6", "7"};

        String day = days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        return day;
    }
    private String getcurrentDay() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.DAY_OF_WEEK-1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(calendar.getTime());
    return date;
    }

    public void setdataChitietCT(ArrayList<ChitietChuongtrinh> data) {
        listchitiet = data;
        if (adapter == null) {
            adapter = new ChuongtrinhAdapter(LogInActivity.listchitiet, getApplicationContext());
        } else {
            adapter.setData(data);
        }
        adapter.notifyDataSetChanged();
        gvChuongtrinh.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.mnsearch);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(OnTextChanged);
        return true;
    }

    private SearchView.OnQueryTextListener OnTextChanged = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            ArrayList<ChitietChuongtrinh> chitiet = new ArrayList<>();

            for (ChitietChuongtrinh item : LogInActivity.listchitiet) {
                if (item.getThoigian().toLowerCase().contains(newText.toLowerCase())) {
                    chitiet.add(item);
                } else if (item.getNoidung().toLowerCase().contains(newText.toLowerCase())) {
                    chitiet.add(item);
                }
            }
            adapter = new ChuongtrinhAdapter(chitiet, getApplicationContext());
            gvChuongtrinh.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mnuserinfor) {
            Intent userinfo = new Intent(MainActivity.this, UserInfosActivity.class);
            startActivity(userinfo);
        } else if (id == R.id.mnchuongtrinh) {

        } else if (id == R.id.mnthucdon) {

        } else if (id == R.id.mnbe) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.mndoipass) {
            passWordChange.showDialog(this, Save.userInfos);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    private void callFragment(Fragment fragment, String title) {
//        getSupportActionBar().setTitle(title);
//        FragmentManager fm = getSupportFragmentManager();
//        if (fm.findFragmentById(R.id.frameContent) == null) {
//            fm.beginTransaction().replace(R.id.frameContent, fragment)
//                    .setTransition(FragmentTransaction.TRANSIT_NONE)
//                    .addToBackStack(null)
//                    .commit();
//            isshowfragment = true;
//        }
//
//    }
}
