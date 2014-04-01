package com.wildbaihe;

//import com.gfan.sdk.statistics.Collector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Custom extends Activity 
{
	private EditText customEdit0 ;
	private EditText customEdit1 ;
	private EditText customEdit2 ;
	private TextView customResult ;
	private TextView customView3;
	private Button copy ;
	private Button customButton1 ;
	private Button customButton2 ;
	static String result ;// 巧妙的static，为了能让clipboard监听器类能够用到转换按钮所得的result的值。final不行。
	String str0 , str1 , str2 ;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom);
//		Intent intent = getIntent();

		customEdit0 = (EditText)findViewById(R.id.customEdit0);
		customEdit1 = (EditText)findViewById(R.id.customEdit1);
		customEdit2 = (EditText)findViewById(R.id.customEdit2);
		customResult = (TextView)findViewById(R.id.customResult);
		customView3 = (TextView)findViewById(R.id.customView3);
		copy = (Button)findViewById(R.id.copy);
		customButton1 = (Button)findViewById(R.id.customButton1);
		customButton2 = (Button)findViewById(R.id.customButton2);
		copy.setOnClickListener(new CopyListener());
		customButton1.setOnClickListener(new CustomListener1());
		customButton2.setOnClickListener(new OnClickListener() 
		{
			//customButton2用了匿名内部类
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}//OnCreate()
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
	class CustomListener1 extends Custom implements OnClickListener
	{

		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			str0 = customEdit0.getText().toString();
			str1 = customEdit1.getText().toString();
			str2 = customEdit2.getText().toString();
			try
			{
			int r0 = Integer.parseInt(str0);
			int r1 = Integer.parseInt(str1);//toString()只允许第二个参数为int型
//			long r2 = Long.parseLong(str2);
			int numDec = Integer.parseInt(str2, r0);
			result = Long.toString(numDec, r1);
			customView3.setVisibility(View.VISIBLE);
			customResult.setText(result);
			copy.setVisibility(View.VISIBLE);
			}
			catch(Exception e)
			{
				Toast.makeText(Custom.this, R.string.input_error, Toast.LENGTH_SHORT).show();
			}
		}
		
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
				Toast.makeText(Custom.this, R.string.copy_succ, Toast.LENGTH_SHORT).show();
			}
			
		}
	
}
