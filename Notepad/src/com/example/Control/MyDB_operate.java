package com.example.Control;

import java.util.ArrayList;

import com.example.Bean.Notepad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
	/*
	 * ���������ݿ���в���
	 * ���ݵ�����ɾ���ģ��飬��������ʵ��
	 */
public class MyDB_operate {
	Context context;
	MyDB mydb;
	SQLiteDatabase myDatabase;
	/*
	 * �����ʵ����������ͬʱ���������ݿ�
	 */
	public MyDB_operate(Context context){
		this.context=context;
		mydb=new MyDB(context);
	}
	/*
	 * �õ����ListView�õ�array���ݣ������ݿ�����Һ������MainActivity�������
	 */
	public ArrayList<Notepad> getArray(){
		ArrayList<Notepad> array=new ArrayList<Notepad>();
		ArrayList<Notepad> array1=new ArrayList<Notepad>();
		myDatabase=mydb.getWritableDatabase();
		Cursor cursor=myDatabase.rawQuery("select ids,title,times from mybook" , null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			int id=cursor.getInt(cursor.getColumnIndex("ids"));
			String title=cursor.getString(cursor.getColumnIndex("title"));
			String times=cursor.getString(cursor.getColumnIndex("times"));
			Notepad note=new Notepad(id, title, times);
			array.add(note);
			cursor.moveToNext();
		}
		myDatabase.close();
//		�����ݿ��д洢�ļ��½�������
		for (int i = array.size(); i >0; i--) {
			array1.add(array.get(i-1));
		}
		return array1;		
	}
	
	/*
	 * ���ؿ���Ҫ�޸ĵ����ݣ�SecondActivity������á�
	 */
	public Notepad getTiandCon(int id){
		myDatabase=mydb.getWritableDatabase();
		Cursor cursor=myDatabase.rawQuery("select title,content from mybook where ids='"+id+"'" , null);
		cursor.moveToFirst();
		String title=cursor.getString(cursor.getColumnIndex("title"));
		String content=cursor.getString(cursor.getColumnIndex("content"));
		Notepad cun=new Notepad(title,content);
		myDatabase.close();
		return cun;
	}
	/*
	 * SecondActivity������ã������޸ļ���
	 */
	public void toUpdate(Notepad note){
		myDatabase=mydb.getWritableDatabase();
		myDatabase.execSQL("update mybook set title='"+ note.getTitle()+"',times='"+note.getTimes()+"',content='"+note.getContent() +"' where ids='"+ note.getId()+"'");
		myDatabase.close();
	}
	/*
	 *SecondActivity������ã����������µļ���
	 */
	public void toInsert(Notepad cun){
		myDatabase=mydb.getWritableDatabase();
		myDatabase.execSQL("insert into mybook(title,content,times)values('"+ cun.getTitle()+"','"+cun.getContent()+"','"+cun.getTimes()+"')");
		myDatabase.close();
	}
	/*
	 * SecondActivity������ã����������ѡ��ɾ������
	 */
	public void toDelete(int ids){
		myDatabase=mydb.getWritableDatabase();
		myDatabase.execSQL("delete  from mybook where ids="+ids+"");
		myDatabase.close();
	}
}
