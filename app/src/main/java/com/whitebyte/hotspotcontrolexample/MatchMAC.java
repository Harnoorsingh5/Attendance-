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

package com.whitebyte.hotspotcontrolexample;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.whitebyte.hotspotclients.R;
import com.whitebyte.wifihotspotutils.ClientScanResult;
import com.whitebyte.wifihotspotutils.FinishScanListener;
import com.whitebyte.wifihotspotutils.Student;
import com.whitebyte.wifihotspotutils.Teacher;
import com.whitebyte.wifihotspotutils.WifiApManager;
import com.whitebyte.wifihotspotutils.WifiDatabaseHandler;

public class MatchMAC extends Activity {
	TextView textView1;
	WifiApManager wifiApManager;
	List<Student> students;
	WifiDatabaseHandler db;
	String selectedClass;
	String latestColumnName;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_mac);

		textView1 = (TextView) findViewById(R.id.textView1);
		wifiApManager = new WifiApManager(this);

		db = new WifiDatabaseHandler(this);

		Intent intent = getIntent();
		selectedClass = intent.getStringExtra(DetailClass.EXTRA_MESSAGE2);
		latestColumnName = intent.getStringExtra(DetailClass.EXTRA_MESSAGE2a);


		/**
		          * CRUD Operations * */

		// Reading all Records
		Log.d("Reading: ", "Reading all contacts..");
		students = db.getAllMacOfStudent(selectedClass);

		scan();

		students = db.getAllStudentRecord(selectedClass);


		for(Student sd : students) {
			String log = "Mac: "+sd.getMAC()+ " ,Name: "+ sd.getName() + " ,UID: " + sd.getUID() +",Class: "+sd.getStudentClass()+" ,Atten: " + sd.getAttendance();
			// Writing Contacts to log
			Log.d("Name: ", log);
		}
	}

	private void scan() {
		wifiApManager.getClientList(false, new FinishScanListener() {

			@Override
			public void onFinishScan(final ArrayList<ClientScanResult> clients) {

				textView1.setText("WifiApState: " + wifiApManager.getWifiApState() + "\n\n");
				textView1.append("Clients: \n");
				for (ClientScanResult clientScanResult : clients) {
					textView1.append("####################\n");
					textView1.append("IpAddr: " + clientScanResult.getIpAddr() + "\n");
					textView1.append("Device: " + clientScanResult.getDevice() + "\n");
					textView1.append("HWAddr: " + clientScanResult.getHWAddr() + "\n");
					textView1.append("isReachable: " + clientScanResult.isReachable() + "\n");

					for(Student sd : students) {
						if(clientScanResult.getHWAddr().equals(sd.getMAC())) {
							db.markAttendance(selectedClass, latestColumnName, sd.getMAC());

							List<Student> tempStudent = db.tempCheckAtt(selectedClass, sd.getMAC());
							for(Student sd1 : tempStudent) {
								String log = "Mac: "+sd1.getMAC()+ " ,Name: "+ sd1.getName() + " ,UID: " + sd1.getUID() +",Class: "+sd1.getStudentClass()+" ,Atten: " + sd1.getAttendance();
								// Writing Contacts to log
								Log.d("@@@@@@Name: ", log);

							}


							Toast.makeText(getApplicationContext(), sd.getName() + " oye hoye kya baat ae ", Toast.LENGTH_SHORT).show();
						}
						else {
							Toast.makeText(getApplicationContext(), sd.getName() + " Registration ta kralo kanjro ", Toast.LENGTH_SHORT).show();
						}

					}
				}

			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "Get Clients");
		menu.add(0, 1, 0, "Open AP");
		menu.add(0, 2, 0, "Close AP");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			scan();
			break;
//		case 1:
//			wifiApManager.setWifiApEnabled(null, true);
//			break;
//		case 2:
//			wifiApManager.setWifiApEnabled(null, false);
//			break;
		}

		return super.onMenuItemSelected(featureId, item);
	}
}
