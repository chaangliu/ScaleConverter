package com.wildbaihe;

//import com.gfan.sdk.statistics.Collector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.text.ClipboardManager;

public class Result1 extends Activity
{
	private TextView resultView2 ;
	private TextView resultView3 ;
	private TextView resultView4 ;
	private Button copy ;
	private Button back ; 
	String result;//成员变量，全局
	
	@Override
	public void onCreate(Bundle savedInstanceState)//原来是protected
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result1);
		resultView2 = (TextView)findViewById(R.id.resultView2);
		resultView3 = (TextView)findViewById(R.id.resultView3);
		resultView4 = (TextView)findViewById(R.id.resultView4);
		copy = (Button)findViewById(R.id.copy);
		back = (Button)findViewById(R.id.back);

		Intent intent = getIntent();
		String str1 = intent.getStringExtra("num");
		long int1 = Long.parseLong(str1);
		result = Long.toBinaryString(int1);//全局变量
		resultView2.setText(str1);
		resultView3.setText(R.string.resultView3_1);
		resultView4.setText(result);
		copy.setOnClickListener(new CopyListener());
		back.setOnClickListener(new BackListener());
		
	}//onCreate
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
    	menu.add(0, 1, 1, R.string.about).setIcon(android.R.drawable.ic_menu_info_details);//(groupId, itemId, order, title)
    	menu.add(0, 2, 2, R.string.exit).setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()== 2)
		{
			new AlertDialog.Builder(this).setTitle(R.string.exit).setMessage(R.string.makesure)
			.setPositiveButton(R.string.yes, new AlertDialog.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					 Intent startMain = new Intent(Intent.ACTION_MAIN);
			         startMain.addCategory(Intent.CATEGORY_HOME);
			         startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			         startActivity(startMain);
			         System.exit(0);
				}}).setNegativeButton(R.string.no, null).show();
		}
		else if(item.getItemId() == 1)
		{
			new AlertDialog.Builder(this).setTitle(R.string.about).setMessage(R.string.about_content).
			setPositiveButton(R.string.yes, null).show();
		}
		return super.onMenuItemSelected(featureId, item);
	}
	public void setClipboard(String text) 
	{
	    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
	    clipboard.setText(text);
	}
	
	class CopyListener implements OnClickListener
	{

		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			setClipboard(result);
			{
				Toast.makeText(Result1.this, R.string.copy_succ, Toast.LENGTH_SHORT).show();
			}
		}		
	}
	
	
	class BackListener implements OnClickListener
	{

		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			Result1.this.finish();
		}		
	}
	

}
