package com.wildbaihe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.wildbaihe.update.Config;
import com.wildbaihe.update.NetworkTool;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import com.gfan.sdk.statistics.Collector;


public class Act1 extends Activity {
	
    private EditText editText;
    private Button button1 ;
    private Button button2 ;
    private Button button3 ;
    private Button button4 ;
    
	private static final String TAG = "Update";
	public ProgressDialog pBar;
	private Handler handler = new Handler();

	private int newVerCode = 0;
	private String newVerName = "";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        editText = (EditText)findViewById(R.id.editText);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button1.setOnClickListener(new Listener1());
        button2.setOnClickListener(new Listener2());
        button3.setOnClickListener(new Listener3());
        button4.setOnClickListener(new Listener4());

        if (getServerVerCode()) {
			int vercode = Config.getVerCode(this);
			if (newVerCode > vercode) {
				doNewVersionUpdate();
			} else {
				notNewVersionShow();
			}
		}
    }
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
//    	menu.add(0, 3, 3, R.string.currentRadix).setIcon(android.R.drawable.ic_menu_set_as);
    	menu.add(0, 1, 1, R.string.about).setIcon(android.R.drawable.ic_menu_info_details);//(groupId, itemId, order, title)
    	menu.add(0, 2, 2, R.string.exit).setIcon(android.R.drawable.ic_menu_close_clear_cancel);
//    	menu.add(0, 1, 1, R.string.currentRadix).setIcon(android.R.drawable.ic_menu_more);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
			new AlertDialog.Builder(this).setTitle(R.string.about).setMessage(R.string.about_content)
			.setPositiveButton(R.string.yes, null).show();
		}
		return super.onOptionsItemSelected(item);
	}//from-line78
//SingleChoiceItems-----------------------------------------------------------------------------------
/*		
		else if(item.getItemId() == 1)
		{
			showSingleChoiceButton();
		}

		return super.onOptionsItemSelected(item);
	}
	private void showSingleChoiceButton()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.currentRadix);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setSingleChoiceItems(new String[] {"十进制（默认）","二进制","八进制","十六进制" }, 0, buttonOnClick);
		builder.setPositiveButton(R.string.yes, buttonOnClick);
		builder.show();
	}
	private ButtonOnClick buttonOnClick = new ButtonOnClick(0);
	public class ButtonOnClick implements DialogInterface.OnClickListener
	{	
//		String num0;
		int index ;
		public String ws; 
		public ButtonOnClick(int index)
		{
			this.index = index ;
		}
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			if( which >= 0 )
			{
				index = which ;
				ws = index + "";
			}
			else if( which == DialogInterface.BUTTON_POSITIVE)
			{   
				ws = index + "";
				System.out.println(ws);		
			}
		}
		
	}*/
//---------------------------------------------------------------------------------------	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) //back键监听
	{
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			new AlertDialog.Builder(this).setTitle(R.string.exit).setMessage(R.string.makesure)
			.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() 
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					 Intent startMain = new Intent(Intent.ACTION_MAIN);
			         startMain.addCategory(Intent.CATEGORY_HOME);
			         startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			         startActivity(startMain);
			         System.exit(0);
				}
			}).setNegativeButton(R.string.no, null).show();
			return true ;
		}
		else
		return super.onKeyDown(keyCode, event);
	}

	class Listener1 implements OnClickListener
    {	
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			String str1 = editText.getText().toString();
			if(str1 == null||str1.trim().length()==0)
			{Toast.makeText(Act1.this, R.string.input_sth, Toast.LENGTH_SHORT).show();}
			else
			{
			try
			{	
				Long.parseLong(str1);
				Intent intent = new Intent();
				intent.putExtra("num", str1);//也可以用Bundle来实现键值对的存储，这样参数直接填一个bundle对象就行了
//				System.out.println(str1 + "--->"  + aa.ws);
				intent.setClass(Act1.this, Result1.class);
				Act1.this.startActivity(intent);	
			}
			catch(Exception e)
			{
				Toast.makeText(Act1.this, R.string.input_error, Toast.LENGTH_SHORT).show();
			}
			}
								
		}
    }
    class Listener2 implements OnClickListener
    {
		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			String str1 = editText.getText().toString();
			if(str1==null||str1.equals(""))
			{Toast.makeText(Act1.this, R.string.input_sth, Toast.LENGTH_SHORT).show();}
			else{
			try
			{
				Long.parseLong(str1);
				Intent intent = new Intent();
				intent.putExtra("num", str1);
				intent.setClass(Act1.this, Result2.class);
				Act1.this.startActivity(intent);	
			}
			catch(Exception e)
			{
				Toast.makeText(Act1.this, R.string.input_error, Toast.LENGTH_SHORT).show();
			}}
		}
    	
    }
    class Listener3 implements OnClickListener
    {

		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			String str1 = editText.getText().toString();
			if(str1 == null || str1.equals("")||str1.trim().length()==0){Toast.makeText(Act1.this, R.string.input_sth, Toast.LENGTH_SHORT).show();}
			else{
			try
			{
				Long.parseLong(str1);
				Intent intent = new Intent();
				intent.putExtra("num", str1);
				intent.setClass(Act1.this, Result3.class);
				Act1.this.startActivity(intent);	
			}
			catch(Exception e)
			{
				Toast.makeText(Act1.this, R.string.input_error, Toast.LENGTH_SHORT).show();
			}}
		}
    	
    }
    class Listener4 implements OnClickListener
    {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(Act1.this, Custom.class);
			Act1.this.startActivity(intent);
		}
    	
    }
//------------------------UPDATE----------------------------
	private boolean getServerVerCode() {
		try {
			String verjson = NetworkTool.getContent(Config.UPDATE_SERVER
					+ Config.UPDATE_VERJSON);
			JSONArray array = new JSONArray(verjson);
			if (array.length() > 0) {
				JSONObject obj = array.getJSONObject(0);
				try {
					newVerCode = Integer.parseInt(obj.getString("verCode"));
					newVerName = obj.getString("verName");
				} catch (Exception e) {
					newVerCode = -1;
					newVerName = "";
					return false;
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return false;
		}
		return true;
	}

	private void notNewVersionShow() {
		int verCode = Config.getVerCode(this);
		String verName = Config.getVerName(this);
		StringBuffer sb = new StringBuffer();
		sb.append("当前版本:");
		sb.append(verName);
		sb.append(" Code:");
		sb.append(verCode);
		sb.append(",\n已是最新版,无需更新!");
		Dialog dialog = new AlertDialog.Builder(Act1.this)
				.setTitle("软件更新").setMessage(sb.toString())// 设置内容
				.setPositiveButton("确定",// 设置确定按钮
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}

						}).create();// 创建
		// 显示对话框
		dialog.show();
	}

	private void doNewVersionUpdate() {
		int verCode = Config.getVerCode(this);
		String verName = Config.getVerName(this);
		StringBuffer sb = new StringBuffer();
		sb.append("当前版本:");
		sb.append(verName);
		sb.append(" Code:");
		sb.append(verCode);
		sb.append(", 发现新版本:");
		sb.append(newVerName);
		sb.append(" Code:");
		sb.append(newVerCode);
		sb.append(", 是否更新?");
		Dialog dialog = new AlertDialog.Builder(Act1.this)
				.setTitle("软件更新")
				.setMessage(sb.toString())
				// 设置内容
				.setPositiveButton("更新",// 设置确定按钮
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								pBar = new ProgressDialog(Act1.this);
								pBar.setTitle("正在下载");
								pBar.setMessage("请稍候...");
								pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
								downFile(Config.UPDATE_SERVER
										+ Config.UPDATE_APKNAME);
							}

						})
				.setNegativeButton("暂不更新",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// 点击"取消"按钮之后退出程序
								//finish();
							}
						}).create();// 创建
		// 显示对话框
		dialog.show();
	}

	void downFile(final String url) {
		pBar.show();
		new Thread() {
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is != null) {

						File file = new File(
								Environment.getExternalStorageDirectory(),
								Config.UPDATE_SAVENAME);
															
						fileOutputStream = new FileOutputStream(file);

						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {
							fileOutputStream.write(buf, 0, ch);
							count += ch;
							if (length > 0) {
							}
						}

					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					down();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}.start();

	}

	void down() {
		handler.post(new Runnable() {
			public void run() {
				pBar.cancel();
				update();
			}
		});

	}

	void update() {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), Config.UPDATE_SAVENAME)),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

   
}
         