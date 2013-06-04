/**
 * 
 */
package com.half.keyboard.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.half.keyboard.R;
import com.half.keyboard.adapter.RecentlyUsedHostAdapter;
import com.half.keyboard.service.Service;
import com.half.keyboard.service.Service.Binder;
import com.half.keyboard.utils.Log;

public class ConnectFragment extends Fragment {
    public static final String TAG = "ConnectFragment";
    public static final String KEY_RECENTLY_USED_HOSTS = "key_recently_used_hosts";
    public static final String KEY_LASTEST_USED_HOST = "KEY_LASTEST_USED_HOST";
    private EditText etIp;
    private Button btnConnect;
    private ListView lvRecentlyUsedHosts;
    private List<String> mRecentlyUsedHosts;
    private RecentlyUsedHostAdapter mAdapter;
    private Service mService;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(getActivity(), Service.class);
        getActivity().bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connect, null);
        if (view != null) {
            etIp = (EditText) view.findViewById(R.id.et_ip);
            btnConnect = (Button) view.findViewById(R.id.btn_connect);
            lvRecentlyUsedHosts = (ListView) view.findViewById(R.id.lv_connect_recently_used_host);
            lvRecentlyUsedHosts.setOnItemClickListener(itemClickListener);
            btnConnect.setOnClickListener(connectClickListener);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences sp = getActivity().getSharedPreferences(getActivity().getPackageName(), 0);
        String strLastestUsedHost = sp.getString(KEY_LASTEST_USED_HOST,"");
        if(!TextUtils.isEmpty(strLastestUsedHost)){
           etIp.setText(strLastestUsedHost);
        }
        String strRecentlyUsedHosts = sp.getString(KEY_RECENTLY_USED_HOSTS, "");
        if (!TextUtils.isEmpty(strRecentlyUsedHosts)) {
            String[] list = strRecentlyUsedHosts.split("_");
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    if (mRecentlyUsedHosts == null) {
                        mRecentlyUsedHosts = new ArrayList<String>();
                    }
                    mRecentlyUsedHosts.add(list[i]);
                }
            }
        }
        if (mRecentlyUsedHosts != null) {
            if (mAdapter == null) {
                mAdapter = new RecentlyUsedHostAdapter(getActivity(), mRecentlyUsedHosts);
                lvRecentlyUsedHosts.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        getActivity().unbindService(conn);
    }

    OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            String ip = mRecentlyUsedHosts.get(arg2);
            if (!TextUtils.isEmpty(ip)) {
                etIp.setText(ip);
            }
        }

    };

    OnClickListener connectClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            String ip = etIp.getText().toString();
            if (TextUtils.isEmpty(ip)) {
                Toast.makeText(getActivity(), getString(R.string.connect_ip_empty), Toast.LENGTH_SHORT).show();
                return;
            }

            if (!ip.matches("^[0-9]{1,4}\\.[0-9]{1,4}\\.[0-9]{1,4}\\.[0-9]{1,4}$")) {
                Toast.makeText(getActivity(), getString(R.string.connect_ip_unvalid), Toast.LENGTH_SHORT).show();
                return;
            }
            if (mService != null) {
                mService.connect(ip);
            }
        }
    };


    ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Binder binder = (Binder) service;
            if (binder != null) {
                mService = binder.getService();
            }
        }
    };

}
