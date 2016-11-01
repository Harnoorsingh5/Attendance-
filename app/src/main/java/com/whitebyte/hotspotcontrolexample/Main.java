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
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.whitebyte.hotspotclients.R;
import com.whitebyte.wifihotspotutils.ClientScanResult;
import com.whitebyte.wifihotspotutils.FinishScanListener;
import com.whitebyte.wifihotspotutils.Student;
import com.whitebyte.wifihotspotutils.Teacher;
import com.whitebyte.wifihotspotutils.WifiApManager;
import com.whitebyte.wifihotspotutils.WifiDatabaseHandler;

public class Main extends Activity {
	TextView textView1;
	WifiApManager wifiApManager; 

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		textView1 = (TextView) findViewById(R.id.textView1);
		wifiApManager = new WifiApManager(this);

		WifiDatabaseHandler db = new WifiDatabaseHandler(this);
		/**
		          * CRUD Operations * */

		// Inserting Student Records
//		Log.d("Insert: ", "Inserting ..");
//		db.addStudentRecord(new Student("34:fc:ef:c5:c5:26","Harry","14BCS1530","CSE-5","P"));
//		db.addStudentRecord(new Student("70:77:81:8E:E3:DF","Jassi","14BCS1538","CSE-4","A"));
//		db.addStudentRecord(new Student("24:fc:ef:c5:c5:27","Joshi","14BCS1528","CSE-6","P"));
//
//		//db.sDeleteRecord(new Student("34:fc:ef:c5:c5:27"));

		// Reading all Records
		Log.d("Reading: ", "Reading all contacts..");
		List<Student> students = db.getAllStudentRecord();

		for(Student sd : students) {
			String log = "Mac: "+sd.getMAC()+ " ,Name: "+ sd.getName() + " ,UID: " + sd.getUID() +",Class: "+sd.getStudentClass()+" ,Atten: " + sd.getAttendance();
			// Writing Contacts to log
			Log.d("Name: ", log);
		}

//		 Inserting Teacher Records
//	    Log.d("Insert: ", "Inserting ..");
//		db.addTeacherRecord(new Teacher("E2231","abc","CSE-6","qwertyuiop"));
//		db.addTeacherRecord(new Teacher("E2341","def","CSE-5","asdfghjkl"));
//		db.addTeacherRecord(new Teacher("E2342","ghi","CSE-4","zxcvbnm"));

		// Reading all Records
		Log.d("Reading: ", "Reading all contacts..");
		List<Teacher> teachers = db.getAllTeacherRecord();

		for(Teacher teacher : teachers) {
			String log = "T_UID: "+teacher.getT_uid()+" ,Name: " + teacher.getT_name() + " ,Class: " + teacher.getT_classteach()+", Password: "+teacher.getT_password();
			// Writing Contacts to log
			Log.d("Name: ", log);
		}

		scan();

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
