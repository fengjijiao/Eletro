package com.fjj.eletro.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.blankj.utilcode.constant.RegexConstants;
import com.blankj.utilcode.util.RegexUtils;
import com.fjj.eletro.R;
import com.fjj.eletro.dataSet.Moniter;
import com.fjj.eletro.dataSet.ParserJson;
import com.fjj.eletro.fragment.BuildingStatisticsFragment;
import com.fjj.eletro.fragment.DetailFragment;
import com.fjj.eletro.fragment.ElectricityConsumptionRankingFragment;
import com.fjj.eletro.fragment.FragmentI;
import com.fjj.eletro.fragment.PowerListFragment;
import com.fjj.eletro.fragment.PowerRankingFragment;
import com.fjj.eletro.utils.HttpUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private FragmentManager fragmentManager;
    private BottomNavigationView bottom_navigation;
    private HashMap<String, Fragment> fragmentMap;
    private HashSet<String> fragmentSet;
    private String currentFragmentName;
    private final String FRAGMENT_NAME_DE = "detail", FRAGMENT_NAME_PO = "power",  FRAGMENT_NAME_PR = "powerranking", FRAGMENT_NAME_EC = "electricityconsumptionranking", FRAGMENT_NAME_BS = "buildingstatistics", FRAGMENT_NAME_PL = "powerlist";
    private final Handler mHandler = new Handler();
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            new Thread(() -> {
                String thresholdStr = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString(getString(R.string.notification_threshold_key), "5.8");
                new Thread(() -> {
                    if(RegexUtils.isMatch(RegexConstants.REGEX_POSITIVE_FLOAT, thresholdStr)) {
                        Moniter.TriggerMoniterPower(MainActivity.this, Double.parseDouble(thresholdStr));
                    }
                }).start();
                try {
                    String jsonData = HttpUtils.post("http://10.1.2.5:9078/load_data", "UTF-8", null, "", HttpURLConnection.HTTP_CREATED);
                    Log.i(TAG, "jsonData Length: "+jsonData.length());
                    ParserJson.updateDataSet(jsonData);
                    for(String name: fragmentSet) {
                        runOnUiThread(() -> {
                            ((FragmentI) Objects.requireNonNull(fragmentMap.get(name))).updateData();
                        });
                    }
                    new Thread(() -> {
                        if(RegexUtils.isMatch(RegexConstants.REGEX_POSITIVE_FLOAT, thresholdStr)) {
                            Moniter.TriggerMoniterPower(MainActivity.this, Double.parseDouble(thresholdStr));
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            mHandler.postDelayed(mRunnable, 10*1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragmentManager();
        initFragmentList();
        mHandler.postDelayed(mRunnable, 3*1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if( item.getItemId() == R.id.main_menu_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void initFragmentManager() {
        fragmentManager = getSupportFragmentManager();
    }

    public void initFragmentList() {
        fragmentMap = new HashMap<>();
        fragmentSet = new HashSet<>();
        fragmentMap.put(FRAGMENT_NAME_PR, new PowerRankingFragment());
        fragmentMap.put(FRAGMENT_NAME_EC, new ElectricityConsumptionRankingFragment());
        fragmentMap.put(FRAGMENT_NAME_BS, new BuildingStatisticsFragment());
        fragmentMap.put(FRAGMENT_NAME_PL, new PowerListFragment());
        fragmentMap.put(FRAGMENT_NAME_DE, new DetailFragment());
        switchFragment(FRAGMENT_NAME_PR);
        switchFragment(FRAGMENT_NAME_EC);
        switchFragment(FRAGMENT_NAME_BS);
        switchFragment(FRAGMENT_NAME_PL);
        switchFragment(FRAGMENT_NAME_DE);
    }

    public void initView() {
        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.page_1) Log.i(TAG, "switchFragment Code: " + switchFragment(FRAGMENT_NAME_DE));
            if(item.getItemId() == R.id.page_2) Log.i(TAG, "switchFragment Code: " + switchFragment(FRAGMENT_NAME_PR));
            if(item.getItemId() == R.id.page_3) Log.i(TAG, "switchFragment Code: " + switchFragment(FRAGMENT_NAME_EC));
            if(item.getItemId() == R.id.page_4) Log.i(TAG, "switchFragment Code: " + switchFragment(FRAGMENT_NAME_BS));
            if(item.getItemId() == R.id.page_5) Log.i(TAG, "switchFragment Code: " + switchFragment(FRAGMENT_NAME_PL));
            return true;
        });
    }

    public int switchFragment(String name) {
        if(currentFragmentName != null && currentFragmentName.equals(name)) return -1;
        if (fragmentMap.isEmpty()) return -2;
        if (!fragmentMap.containsKey(name)) return -3;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(!fragmentSet.contains(name)) {
            fragmentTransaction.add(R.id.fragment, Objects.requireNonNull(fragmentMap.get(name)), name);
            fragmentSet.add(name);
        }else {
            if (currentFragmentName != null) hideAllFragment(fragmentTransaction);
            fragmentTransaction.show(Objects.requireNonNull(fragmentMap.get(name)));
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
        currentFragmentName = name;
        return 1;
    }

    public void hideAllFragment(FragmentTransaction fragmentTransaction) {
        for(Map.Entry<String, Fragment> entry: fragmentMap.entrySet()) {
            fragmentTransaction.hide(Objects.requireNonNull(entry.getValue()));
        }
    }

    private void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }
}