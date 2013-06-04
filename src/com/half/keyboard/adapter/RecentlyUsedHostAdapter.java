package com.half.keyboard.adapter;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.half.keyboard.R;
import com.half.keyboard.fragment.ConnectFragment;

public class RecentlyUsedHostAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<String> mList;
	private int mSelectedPosition = -1;
	private Context mContext;

	public void setSelectedPosition(int selectedPosition) {
		if (mSelectedPosition != selectedPosition) {
			this.mSelectedPosition = selectedPosition;
			notifyDataSetChanged();
		}
	}

	public RecentlyUsedHostAdapter(Context context, List<String> list) {
		this.mInflater = LayoutInflater.from(context);
		this.mList = list;
		mContext = context;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public final class ViewHolder {
		public TextView tvIp;
		public ImageView ivSelected;
		public Button btnDelete;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String ip = mList.get(position);
		ViewHolder holder = null;
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_recently_used_hosts, null);
			holder.tvIp = (TextView) convertView.findViewById(R.id.tv_ip);
			holder.ivSelected = (ImageView) convertView.findViewById(R.id.iv_selected);
			holder.btnDelete = (Button) convertView.findViewById(R.id.btn_delete);
			holder.btnDelete.setOnClickListener(deleteClickListener);
			convertView.setTag(holder);
		}
		if (mSelectedPosition == position) {
			holder.ivSelected.setVisibility(View.VISIBLE);
		} else {
			holder.ivSelected.setVisibility(View.INVISIBLE);
		}
		holder.tvIp.setText(ip);
		holder.btnDelete.setTag(position);
		return convertView;
	}

	OnClickListener deleteClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int position = Integer.valueOf(v.getTag().toString());
			if (position >= 0 && position < mList.size()) {
				String ip = mList.get(position);
				SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(), 0);
				String strRecentlyUsedHosts = sp.getString(ConnectFragment.KEY_RECENTLY_USED_HOSTS, "");
				if (!TextUtils.isEmpty(strRecentlyUsedHosts)) {
					if (!strRecentlyUsedHosts.contains(ip)) {
						strRecentlyUsedHosts = strRecentlyUsedHosts.replace(ip, "");
						strRecentlyUsedHosts = strRecentlyUsedHosts.replace("__", "");
					}
				}
				Editor editor = sp.edit();
				editor.putString(ConnectFragment.KEY_RECENTLY_USED_HOSTS, strRecentlyUsedHosts);
				editor.commit();

				mList.remove(position);
				notifyDataSetChanged();
			}
		}
	};
}
