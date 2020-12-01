package com.fjj.eletro.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.fjj.eletro.R;
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
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
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
                try {
                    //String jsonData = HttpUtils.get("https://raw.githubusercontent.com/allenbyus/ibsc/master/uploads/a.json", "UTF-8", null);
                    String jsonData = HttpUtils.post("http://10.1.2.5:9078/load_data", "UTF-8", null, "", HttpURLConnection.HTTP_CREATED);
                    Log.i(TAG, "jsonData Length: "+jsonData.length());
                    ParserJson.updateDataSet(jsonData);
                    for(String name: fragmentSet) {
                        runOnUiThread(() -> {
                            ((FragmentI)fragmentMap.get(name)).updateData();
                        });
                    }
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
            if (currentFragmentName != null) fragmentTransaction.hide(fragmentMap.get(currentFragmentName));
            fragmentTransaction.show(Objects.requireNonNull(fragmentMap.get(name)));
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
        currentFragmentName = name;
        return 1;
    }

    private void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }
}