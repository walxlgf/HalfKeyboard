/**
 *
 */
package com.half.keyboard.service;

import java.net.InetSocketAddress;

import com.half.keyboard.vo.KeyDatas;
import com.half.keyboard.fragment.ConnectFragment;
import com.half.keyboard.utils.Log;
import org.apache.mina.common.ConnectFuture;
import org.apache.mina.common.DefaultIoFilterChainBuilder;
import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.IBinder;
import android.text.TextUtils;

import org.codehaus.jackson.map.ObjectMapper;

public class Service extends android.app.Service {
	private final static String TAG = "Service";
    public final static String ACTION_SESSION_OPENED = "ACTION_SESSION_OPENED";
    public final static String ACTION_SESSION_CLOSE = "ACTION_SESSION_CLOSE";
	private final static int SERVER_PORT = 8088;
	private String mServerAddress;
	private IoSession mSession;
    ClientHandler mClientHandler;

	private Binder binder = new Binder();
	ObjectMapper mObjectMapper = new ObjectMapper();

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG, "onUnbind");
		return super.onUnbind(intent);
	}

	public class Binder extends android.os.Binder {
		public Service getService() {
			return Service.this;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();

		Log.d(TAG, "onCreate");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy");
		if (mSession != null) {
			mSession.close();
		}
	}

	/**
	 * 连接
	 *
	 * @param address
	 */
	public void connect(String address) {
		mServerAddress = address;
		new ClientAsyncTask().execute();
	}

	public void sendKeyData(KeyDatas keyDatas) {
		if (mSession != null) {
			try {
				mSession.write(mObjectMapper.writeValueAsString(keyDatas));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class ClientAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			// 创建一个tcp/ip 连接
			NioSocketConnector connector = new NioSocketConnector();
			/*---------接收字符串---------*/
			//创建接收数据的过滤器
			DefaultIoFilterChainBuilder chain = connector.getFilterChain();
			TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory();
			textLineCodecFactory.setDecoderMaxLineLength(400 * 1024);
			textLineCodecFactory.setEncoderMaxLineLength(400 * 1024);
			chain.addLast("myChin", new ProtocolCodecFilter(textLineCodecFactory));

			// 设定服务器端的消息处理器:一个 SamplMinaServerHandler 对象,
            mClientHandler = new ClientHandler() ;
			connector.setHandler(mClientHandler);
			// Set connect timeout.
			//	        connector.setConnectTimeoutCheckInterval(30);
			// 连结到服务器:
			ConnectFuture cf = connector.connect(new InetSocketAddress(mServerAddress, SERVER_PORT));
			// Wait for the connection attempt to be finished.
			cf.awaitUninterruptibly();
			cf.getSession().getCloseFuture().awaitUninterruptibly();
			connector.dispose();
			return null;
		}
	}

	class ClientHandler extends IoHandlerAdapter {

		@Override
		public void sessionCreated(IoSession session) throws Exception {
			super.sessionCreated(session);
			Log.d(TAG, "sessionCreated");
		}

		@Override
		public void sessionOpened(IoSession session) throws Exception {
			super.sessionOpened(session);
			mSession = session;
			SharedPreferences sp = getSharedPreferences(getPackageName(), 0);
			String strRecentlyUsedHosts = sp.getString(ConnectFragment.KEY_RECENTLY_USED_HOSTS, "");
			if (TextUtils.isEmpty(strRecentlyUsedHosts)) {
				strRecentlyUsedHosts = mServerAddress;
			} else {
				if (!strRecentlyUsedHosts.contains(mServerAddress)) {
					strRecentlyUsedHosts = strRecentlyUsedHosts + "_" + mServerAddress;
				}
			}
			Editor editor = sp.edit();
			editor.putString(ConnectFragment.KEY_RECENTLY_USED_HOSTS, strRecentlyUsedHosts);
            editor.putString(ConnectFragment.KEY_LASTEST_USED_HOST,mServerAddress);
			editor.commit();

			Intent intent = new Intent(ACTION_SESSION_OPENED);
			sendBroadcast(intent);
		}

		@Override
		public void messageReceived(IoSession session, Object message) throws Exception {
			Log.d(TAG, "messageReceived");
		}

		@Override
		public void sessionClosed(IoSession session) {
			Log.d(TAG, "sessionClosed");
			mSession = null;
			Intent intent = new Intent(ACTION_SESSION_CLOSE);
			sendBroadcast(intent);
		}

		@Override
		public void sessionIdle(IoSession session, IdleStatus status) {
			Log.d(TAG, "sessionIdle");
		}

		@Override
		public void exceptionCaught(IoSession session, Throwable cause) {
			Log.d(TAG, "exceptionCaught:" + session + "cause:" + cause);
			mSession = null;
            mSession.close();
		}

	}
}
