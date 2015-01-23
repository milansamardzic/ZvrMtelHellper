package com.milansamardzic.zvrrmtel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.milansamardzic.pozovime.R;
import com.pkmmte.view.CircularImageView;

/**
 * Created by ms on 12/25/14.
 */
public class SendSMS{

    public static void sendSms(final Dialog dial, String num, String name, String img){
        final String pNumber = num;
        final Dialog dialog = dial;
        final String pName = name;
        final String pImg = img;

        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Poslati?");

        TextView textViewUser = (TextView) dialog.findViewById(R.id.text);
        textViewUser.setText("Izabrani kontakt:\n" + pName + "\n(" + pNumber + ")");

        TextView tvNameInitialFav = (TextView) dialog.findViewById(R.id.tvNameInitial);
        CircularImageView circularImageView = (CircularImageView) dialog.findViewById(R.id.civImage1);


        if(pImg!=null){
            tvNameInitialFav.setVisibility(View.INVISIBLE);
            circularImageView.setVisibility(View.VISIBLE);
            Uri imgUri=Uri.parse(pImg);
            circularImageView.setImageURI(imgUri);
            circularImageView.setSelectorStrokeWidth(10);
            circularImageView.addShadow();
        }
        else {
            circularImageView.setVisibility(View.INVISIBLE);
            String initial = "";
            String[] split = pName.split(" ");
            for(String value : split){
                initial += value.substring(0,1); }

            tvNameInitialFav.setText( initial );
            tvNameInitialFav.setVisibility(View.VISIBLE);
        }



        Button btnOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button btnCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager.getDefault().sendTextMessage("+387651122", null, "POZOVI " + pNumber, null, null);
                dialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(dial.getContext());
                builder.setMessage("Uspješno poslana poruka!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

        });
        dialog.show();

    }

}
