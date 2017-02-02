package com.example.Notepad;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.Bean.Notepad;
import com.example.Control.MyDB_operate;
import com.example.Notepad.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/*
 *�����༭����
 *
 */
public class SecondAtivity extends Activity {

	EditText ed1,ed2;
	MyDB_operate myDatabase;
	Notepad cun;
	int ids;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("�ҵļ���");
		setContentView(R.layout.activity_second);
		ed1=(EditText) findViewById(R.id.editText1);
		ed2=(EditText) findViewById(R.id.editText2);
		myDatabase=new MyDB_operate(this);
		
		Intent intent=this.getIntent();
		ids=intent.getIntExtra("ids", 0);
		//Ĭ��Ϊ0����Ϊ0,��Ϊ�޸�����ʱ��ת������
		if(ids!=0){
			cun=myDatabase.getTiandCon(ids);
			ed1.setText(cun.getTitle());
			ed2.setText(cun.getContent());
		}		
	}
	/*
	 * ���ذ�ť���õķ�����
	 */
	@Override
	public void onBackPressed() {
		//TODO Auto-generated method stub		
			Intent intent=new Intent(SecondAtivity.this,MainActivity.class);
			startActivity(intent);
			SecondAtivity.this.finish();
		}
	/*
	 *����һ��isSave()������������������; 
	 */
	private void isSave(){
				SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy��MM��dd��  HH:mm");     
				Date   curDate   =   new   Date(System.currentTimeMillis());//��ȡ��ǰʱ��     
				String times   =   formatter.format(curDate);      
				String title=ed1.getText().toString();
				String content=ed2.getText().toString();
				//��Ҫ�޸�����
				if(ids!=0){
					cun=new Notepad(title,ids, content, times);
					myDatabase.toUpdate(cun);
					Intent intent=new Intent(SecondAtivity.this,MainActivity.class);
					startActivity(intent);
					SecondAtivity.this.finish();
				}
				//�½�����
				else{
					cun=new Notepad(title,content,times);
					myDatabase.toInsert(cun);
					Intent intent=new Intent(SecondAtivity.this,MainActivity.class);
					startActivity(intent);
					SecondAtivity.this.finish();
				}
			}	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.second_ativity, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent=new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_TEXT, "���⣺"+ed1.getText().toString()+"  \n���ݣ�"+ed2.getText().toString());
			startActivity(intent);
			break;
		case R.id.save:
			isSave();
			Intent intent1 = new Intent(SecondAtivity.this,MainActivity.class);
			startActivity(intent1);
			break;
		default:
			break;
		}
		return false;
	}
	

}
