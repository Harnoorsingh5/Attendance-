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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.whitebyte.hotspotclients.R;
import com.whitebyte.wifihotspotutils.ClientScanResult;
import com.whitebyte.wifihotspotutils.FinishScanListener;
import com.whitebyte.wifihotspotutils.Student;
import com.whitebyte.wifihotspotutils.WifiApManager;
import com.whitebyte.wifihotspotutils.WifiDatabaseHandler;

import java.util.ArrayList;
import java.util.List;

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
						if(clientScanResult.getHWAddr().equals(sd.getMAC()) && clientScanResult.isReachable()) {
							db.markAttendance(selectedClass, latestColumnName, sd.getMAC());

							//wifiApManager.removeDevice(clientScanResult.getIpAddr());

							TableRow.LayoutParams  params1=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1.0f);
							TableRow.LayoutParams params2=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
							TableLayout tableLayout=(TableLayout) findViewById(R.id.tableLayout);

							TableRow rowColumnName = new TableRow(getApplicationContext());

							TextView macColumnName = new TextView(getApplicationContext());
							TextView uidColumnName = new TextView(getApplicationContext());
							TextView nameColumnName = new TextView(getApplicationContext());
							TextView attendanceColumnName = new TextView(getApplicationContext());

							macColumnName.setText("MAC ADDRESS "+ "  ");
							uidColumnName.setText("UID " + "  ");
							nameColumnName.setText("NAME " + "  ");
							//attendanceColumnName.setText(DetailClass.columnName + "  ");

							macColumnName.setLayoutParams(params1);
							uidColumnName.setLayoutParams(params1);
							nameColumnName.setLayoutParams(params1);
							//attendanceColumnName.setLayoutParams(params1);

							rowColumnName.addView(macColumnName);
							rowColumnName.addView(uidColumnName);
							rowColumnName.addView(nameColumnName);
							//rowColumnName.addView(attendanceColumnName);


							attendanceColumnName.setText(DetailClass.columnName + "  ");
							attendanceColumnName.setLayoutParams(params1);
							rowColumnName.addView(attendanceColumnName);

							rowColumnName.setLayoutParams(params2);
							tableLayout.addView(rowColumnName);

							List<Student> tempStudent = db.tempCheckAtt(selectedClass, sd.getMAC());
							for(Student sd1 : tempStudent) {
								String log = "Mac: "+sd1.getMAC()+ " ,Name: "+ sd1.getName() + " ,UID: " + sd1.getUID() +",Class: "+sd1.getStudentClass()+" ,Atten: " + sd1.getAttendance();
								// Writing Contacts to log
								Log.d("@@@@@@Name: ", log);

									//Creating new tablerows and textviews
									TableRow row = new TableRow(getApplicationContext());

									TextView mac = new TextView(getApplicationContext());
									TextView uid = new TextView(getApplicationContext());
									TextView name = new TextView(getApplicationContext());
									TextView attendance = new TextView(getApplicationContext());

									//setting the text
									mac.setText(sd1.getMAC() + "  ");
									uid.setText(sd1.getUID() + "  ");
									name.setText(sd1.getName() + "  ");
									attendance.setText(sd1.getAttendance() + "  ");

									mac.setLayoutParams(params1);
									uid.setLayoutParams(params1);
									name.setLayoutParams(params1);
									attendance.setLayoutParams(params1);

									//the textviews have to be added to the row created
									row.addView(mac);
									row.addView(uid);
									row.addView(name);
									row.addView(attendance);
									Log.e("EEEEEE:", String.valueOf(attendance));
									row.setLayoutParams(params2);
									tableLayout.addView(row);


							}
						}
//						else {
//							Toast.makeText(getApplicationContext(), sd.getName() + " Registration ta kralo kanjro ", Toast.LENGTH_SHORT).show();
//						}

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
