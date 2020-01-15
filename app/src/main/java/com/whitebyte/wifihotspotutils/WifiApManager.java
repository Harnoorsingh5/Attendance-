/*
 * Copyright 2013 WhiteByte (Nick Russler, Ahmet Yueksektepe).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.whitebyte.wifihotspotutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;

public class WifiApManager {
	public final WifiManager mWifiManager;
	private Context context;

	public WifiApManager(Context context) {
		this.context = context;
		mWifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
	}

	/**
	 * Start AccessPoint mode with the specified
	 * configuration. If the radio is already running in
	 * AP mode, update the new configuration
	 * Note that starting in access point mode disables station
	 * mode operation
	 *
	 * @param wifiConfig SSID, security and channel details as part of WifiConfiguration
	 * @return {@code true} if the operation succeeds, {@code false} otherwise
	 */
	public boolean setWifiApEnabled(WifiConfiguration wifiConfig, boolean enabled) {
		try {
			if (enabled) { // disable WiFi in any case
				mWifiManager.setWifiEnabled(false);
			}

			Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
			return (Boolean) method.invoke(mWifiManager, wifiConfig, enabled);
		} catch (Exception e) {
			Log.e(this.getClass().toString(), "", e);
			return false;
		}
	}

	/**
	 * Gets the Wi-Fi enabled state.
	 *
	 * @return {@link WIFI_AP_STATE}
	 * @see #//isWifiApEnabled()
	 */
	public WIFI_AP_STATE getWifiApState() {
		try {
			Method method = mWifiManager.getClass().getMethod("getWifiApState");

			int tmp = ((Integer) method.invoke(mWifiManager));

			// Fix for Android 4
			if (tmp >= 10) {
				tmp = tmp - 10;
			}
			return WIFI_AP_STATE.class.getEnumConstants()[tmp];
		} catch (Exception e) {
			Log.e(this.getClass().toString(), "", e);
			return WIFI_AP_STATE.WIFI_AP_STATE_FAILED;
		}
	}

	/**
	 * Return whether Wi-Fi AP is enabled or disabled.
	 * @return {@code true} if Wi-Fi AP is enabled
	 * @see #getWifiApState()
	 *
	 * @hide Dont open yet
	 */
//	public boolean isWifiApEnabled() {
//		return getWifiApState() == WIFI_AP_STATE.WIFI_AP_STATE_ENABLED;
//	}

	/**
	 * Gets the Wi-Fi AP Configuration.
	 *
	 * @return AP details in {@link WifiConfiguration}
	 */
	public WifiConfiguration getWifiApConfiguration() {
		try {
			Method method = mWifiManager.getClass().getMethod("getWifiApConfiguration");
			return (WifiConfiguration) method.invoke(mWifiManager);
		} catch (Exception e) {
			Log.e(this.getClass().toString(), "", e);
			return null;
		}
	}

	/**
	 * Sets the Wi-Fi AP Configuration.
	 *
	 * @return {@code true} if the operation succeeded, {@code false} otherwise
	 */
	public boolean setWifiApConfiguration(WifiConfiguration wifiConfig) {
		try {
			Method method = mWifiManager.getClass().getMethod("setWifiApConfiguration", WifiConfiguration.class);
			return (Boolean) method.invoke(mWifiManager, wifiConfig);
		} catch (Exception e) {
			Log.e(this.getClass().toString(), "", e);
			return false;
		}
	}

	/*

		REMOVE NETWORK

	 */

//	public void getNetworkID() {
//
//		List<WifiConfiguration> list = mWifiManager.getConfiguredNetworks();
//		for (WifiConfiguration i : list) {
//
//			Log.v("#######NETWORK ID  ", String.valueOf(i.networkId) + "  ");
//			if(i.networkId == mWifiManager.getConnectionInfo().getNetworkId() ) {
//				mWifiManager.removeNetwork(i.networkId);
//				mWifiManager.saveConfiguration();
//			}
//		}
//			int networkid = mWifiManager.getConnectionInfo().getNetworkId();
//		removeNetwork(networkid);
//
//	public void removeNetwork(int netID) {
//		mWifiManager.removeNetwork(netID);
//	}


	/**
	 * Gets a list of the clients connected to the Hotspot, reachable timeout is 300
	 *
	 * @param onlyReachables  {@code false} if the list should contain unreachable (probably disconnected) clients, {@code true} otherwise
	 * @param finishListener, Interface called when the scan method finishes
	 */
	public void getClientList(boolean onlyReachables, FinishScanListener finishListener) {
		getClientList(onlyReachables, 300, finishListener);
	}

	/**
	 * Gets a list of the clients connected to the Hotspot
	 *
	 * @param onlyReachables   {@code false} if the list should contain unreachable (probably disconnected) clients, {@code true} otherwise
	 * @param reachableTimeout Reachable Timout in miliseconds
	 * @param finishListener,  Interface called when the scan method finishes
	 */
	public void getClientList(final boolean onlyReachables, final int reachableTimeout, final FinishScanListener finishListener) {


		Runnable runnable = new Runnable() {
			public void run() {

				BufferedReader br = null;
				final ArrayList<ClientScanResult> result = new ArrayList<ClientScanResult>();

				try {
					br = new BufferedReader(new FileReader("/proc/net/arp"));
					String line;
					while ((line = br.readLine()) != null) {
						String[] splitted = line.split(" +");
						Log.v("###arp  ", line);
						if ((splitted != null) && (splitted.length >= 4)) {
							// Basic sanity check
							String mac = splitted[3];

							if (mac.matches("..:..:..:..:..:..")) {
								boolean isReachable = InetAddress.getByName(splitted[0]).isReachable(reachableTimeout);

								if (!onlyReachables || isReachable) {
									result.add(new ClientScanResult(splitted[0], splitted[3], splitted[5], isReachable));
								}
							}
						}
					}
				} catch (Exception e) {
					Log.e(this.getClass().toString(), e.toString());
				} finally {
					try {
						br.close();
					} catch (IOException e) {
						Log.e(this.getClass().toString(), e.getMessage());
					}
				}

				// Get a handler that can be used to post to the activity_match_mac thread
				Handler mainHandler = new Handler(context.getMainLooper());
				Runnable myRunnable = new Runnable() {
					@Override
					public void run() {
						finishListener.onFinishScan(result);
					}
				};
				mainHandler.post(myRunnable);
			}
		};

		Thread mythread = new Thread(runnable);
		mythread.start();
	}

	public void removeDevice(String ip) {

//		try {
		//Runtime.getRuntime().exec("/arp/chmod 777");
//			Process process = Runtime.getRuntime().exec("arp -d " + ip);
//			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
//			process.getInputStream();
//			Log.v("&&&&&", String.valueOf(in));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		BufferedReader	br = null;
//
//		String line;
//		try {
//			br = new BufferedReader(new FileReader("/proc/net/arp"));
//			while ((line = br.readLine()) != null) {
//				Log.v("###arp  ", line);
//		}
//		List<WifiConfiguration> list = this.mWifiManager.getConfiguredNetworks();
//		setWifiApConfiguration((WifiConfiguration) list);
//		WifiConfiguration wf = getWifiApConfiguration();
//		if(wf!=null) {
//			Log.e("removed : hoooaaaa ", String.valueOf(wf));
//
//			for (WifiConfiguration i : wf) {
//				mWifiManager.removeNetwork(i.networkId);
//				Log.e("removed : ", String.valueOf(i.networkId));
//				mWifiManager.saveConfiguration();
		//}


		try {
//			String fileName = "mewFile";
			File inFile = new File("/proc/net/arp");
			//Construct the new file that will later be renamed to the original filename.
//			File tempFile = new File(inFile.getAbsolutePath() + fileName + ".tmp");

//			if(tempFile==null)
//				tempFile.createNewFile();
//
			File outputDir = context.getCacheDir(); // context being the Activity pointer
			File outputFile = File.createTempFile("arpNewFile", "tmp", outputDir);

			BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
			PrintWriter pw = new PrintWriter(new FileWriter(outputFile));

			BufferedReader outbr = new BufferedReader((new FileReader(outputFile)));

			String line;
			String IP;
			String str;
			while ((line = br.readLine()) != null) {
				Log.v("####", line);
				String[] splitted = line.split(" +");

				IP = splitted[0];
				if (IP.equals(ip)) {
					continue;
				} else {
					pw.println(line);
					//Log.v("####ARP ",line);
					pw.flush();
				}

			}
			while ((line = outbr.readLine()) != null) {
				Log.e("####OUT", line);
			}

			pw.close();
			br.close();

			File appDir = new File("/proc/net");
			if (appDir.exists()) {
				String[] children = appDir.list();
				for (String c : children) {
					File f = new File(c);
					Log.e("file check to delete: ",c);
					if (c.equals("arp")) {
						//f.delete();
						f = new File(c + "pppp");
						f.createNewFile();
						Log.e("deleted arp ", c);
						break;
					}
				}
			}
			if (appDir.exists()) {
				String[] children = appDir.list();
				for (String c : children) {
					File f = new File(c);
					Log.e("file check to delete: ",c);
//					if (c.equals("arp")) {
//						f.delete();
//						Log.e("deleted arp ", c);
//						break;
//					}
				}
			}
				//Delete the original file
//				if (!inFile.delete()) {
//					Log.v("Could not delete file", "####");
//					//return;
//				}

				//Rename the new file to the filename the original file had.
				if (!outputFile.renameTo(inFile))
					Log.v("Could not rename file", "###");

				outbr = new BufferedReader((new FileReader(inFile)));


				while ((line = outbr.readLine()) != null) {
					Log.e("####OUT_AFTER", line);
				}


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
