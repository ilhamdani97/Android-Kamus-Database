package com.example.ilhamramadani.kamus;

import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        MaterialSearchBar.OnSearchActionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.nav_view)
    NavigationView nav_view;

    @BindView(R.id.search_bar)
    MaterialSearchBar search_bar;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private KamusHelper kamusHelper;
    private SearchAdapter adapter;

    private ArrayList<KamusModel> list = new ArrayList<>();
    private boolean isEnglish = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);
        search_bar.setOnSearchActionListener(this);

        kamusHelper = new KamusHelper(this);

        setupList();
        loadData();
        nav_view.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_english_indonesia) {
            isEnglish = true;
            loadData();
        }

        if (id == R.id.nav_indonesia_english) {
            isEnglish = false;
            loadData();
        }


        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Invoked when SearchBar opened or closed
     *
     * @param enabled
     */
    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        loadData(String.valueOf(text));
    }


    @Override
    public void onButtonClicked(int buttonCode) {

    }

    private void setupList() {
        adapter = new SearchAdapter();
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(adapter);
    }

    private void loadData(String search) {
        try {
            kamusHelper.open();
            if (search.isEmpty()) {
                list = kamusHelper.getAllData(isEnglish);
            } else {
                list = kamusHelper.getDataByName(search, isEnglish);
            }

            if (isEnglish) {
                getSupportActionBar().setSubtitle(getResources().getString(R.string.english_indonesia));
            } else {
                getSupportActionBar().setSubtitle(getResources().getString(R.string.indonesia_english));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            kamusHelper.close();
        }
        adapter.replaceAll(list);
    }

    private void loadData() {
        loadData("");
    }
}
