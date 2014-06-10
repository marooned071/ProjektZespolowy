package com.example.qrpoll;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
/**
 * Klasa odpowiedzialna za wyswietlanie Dialogow
 * @author Wodzu
 *
 */
public class DialogBox {
	/**
	 * Wyswietla komunikat bledu
	 * @param context
	 * @param e
	 */
	public static void showErrorDialog(Context context,Exception e){
		AlertDialog.Builder adb= new AlertDialog.Builder(context);
		adb.setMessage(e.getMessage()).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		AlertDialog ad=adb.create();
		ad.show();
	}
	
	/**
	 * Wyœwietla box z podana wiadomoscia
	 * @param context
	 * @param message
	 */
	public static void showMessageDialog(Context context,String message,String title){
		AlertDialog.Builder adb= new AlertDialog.Builder(context);
		adb.setTitle(title);
		adb.setMessage(message).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		AlertDialog ad=adb.create();
		ad.show();
	}
}
