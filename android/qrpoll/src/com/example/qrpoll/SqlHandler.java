package com.example.qrpoll;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * szkic klasy do obslugi lokalnej bazy danych, zawiera na ten moment jedna tabele
 * @author wodzu
 *
 */
public class SqlHandler {
		private SQLiteDatabase db;
		private Context context;
		private DatabaseHelper dbHelper;
		/**
		 * 
		 * @param context
		 */
		public SqlHandler(Context context){
			this.context=context;
		}
		/**
		 * 
		 * @return
		 */
		public SqlHandler open(){
			dbHelper=new DatabaseHelper(context,DB_NAME,null,DB_VERSION);
			try{
				db=dbHelper.getWritableDatabase();
			}catch(SQLException e){
				db=dbHelper.getReadableDatabase();
			}
			return this;
		}
		/**
		 * 
		 */
		public void close(){
			dbHelper.close();
		}
		private static final int DB_VERSION=1;
		private static final String DB_NAME="database.db";
		private static final String DB_TABLE_1="spotkania";
		
		public static final String KEY_ID="id";
		public static final String ID_OPTION="INTEGER PRIMARY KEY AUTOINCREMENT";
		public static final int ID_COLUMN=0;
		
		public static final String KEY_HASH="hash";
		public static final String HASH_OPTION="TEXT NOT NULL";
		public static final int HASH_COLUMN=1;
		
		public static final String KEY_SUBJECT="subject";
		public static final String SUBJECT_OPTION="TEXT NOT NULL";
		public static final int SUBJECT_COLUMN=2;
		
		public static final String KEY_ROOM="room";
		public static final String ROOM_OPTION="TEXT NOT NULL";
		public static final int ROOM_COLUMN=3;
		
		public static final String KEY_DATE="date";
		public static final String DATE_OPTION="TEXT NOT NULL"; //potem moze zmieni sie na jakis typ date
		public static final int DATE_COLUMN=4;
		
		private static final String Create_table_spotkania="CREATE TABLE IF NOT EXISTS "+DB_TABLE_1+"("
				+KEY_ID+ " "+ID_OPTION+","+KEY_HASH+ " "+HASH_OPTION+","+KEY_SUBJECT+ " "+SUBJECT_OPTION+","
				+KEY_ROOM+ " "+ROOM_OPTION+","+KEY_DATE+ " "+DATE_OPTION+")";
		/**
		 * 
		 */
		private static final String Drop_table_spotkania="DROP TABLE IF EXISTS "+DB_TABLE_1;
		
		/**
		 * 
		 * @param hash
		 * @param subject
		 * @param room
		 * @param date
		 * @return id wstawianej krotki
		 */
		public long insertSpotkania(String hash,String subject,String room, String date){
			ContentValues newSpotkanieValues = new ContentValues();
			newSpotkanieValues.put(KEY_HASH, hash);
			newSpotkanieValues.put(KEY_SUBJECT, subject);
			newSpotkanieValues.put(KEY_ROOM, room);
			newSpotkanieValues.put(KEY_DATE, date);
			Cursor cursor=getDataWithSpecificHash(hash);
			if(cursor.getCount()==0){
				Log.d("SqLiteTodoManager", "not exists");
				return db.insert(DB_TABLE_1, null, newSpotkanieValues);
			}
			
			return -1;
		}
		
		public Cursor getDataFromSpotkanie(){
			String[]columns={KEY_ID,KEY_HASH,KEY_SUBJECT,KEY_ROOM,KEY_DATE};
			return db.query(DB_TABLE_1, columns, null, null, null, null, null);
		}
		
		public Cursor getDataWithSpecificId(int id){
			String[]columns={KEY_ID,KEY_HASH,KEY_SUBJECT,KEY_ROOM,KEY_DATE};
			String where=KEY_ID + "=" + id;
			return db.query(DB_TABLE_1, columns, where, null, null, null, null);
		}
		public Cursor getDataWithSpecificHash(String hash){
			String[]columns={KEY_ID};
			String where=KEY_HASH+"='"+hash+"'";
			return db.query(DB_TABLE_1, columns, where, null, null, null, null);
		}
		public ArrayList<Item> getAll(){
			ArrayList<Item>list=new ArrayList<Item>();
			String[]columns={KEY_ID,KEY_HASH,KEY_SUBJECT,KEY_ROOM,KEY_DATE};
			Cursor csr=db.query(DB_TABLE_1, columns, null, null, null, null, null);
			while(csr.moveToNext()){
				Item item=new Item(csr.getString(SUBJECT_COLUMN),csr.getString(HASH_COLUMN),csr.getString(DATE_COLUMN),csr.getString(ROOM_COLUMN));
				list.add(item);
			}
			return list;
		}
		/**
		 * pomocnicza klasa do tworzenia bazy danych
		 * @author wodzu
		 *
		 */
		private static class DatabaseHelper extends SQLiteOpenHelper{
			/**
			 * 
			 * @param context
			 * @param name
			 * @param factory
			 * @param version
			 */
			public DatabaseHelper(Context context,String name,CursorFactory factory,int version){
				super(context,name,factory,version);
			}
			/**
			 * 
			 */
			@Override
			public void onCreate(SQLiteDatabase db) {
				db.execSQL(Create_table_spotkania);
				Log.d("SqLiteTodoManager", "Database creating...");
			}
			/**
			 * 
			 */
			@Override
			public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
		}
}
