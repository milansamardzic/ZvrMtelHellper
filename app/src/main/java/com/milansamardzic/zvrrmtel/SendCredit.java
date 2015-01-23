package com.milansamardzic.zvrrmtel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.milansamardzic.pozovime.R;
import com.pkmmte.view.CircularImageView;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by ms on 12/25/14.
 */
public class SendCredit extends Activity {

    private static Context mContext;

    public void onCreate(){
        Context mContext = this.getApplicationContext();
    }

    public static Context getAppContext(){
        return mContext;
    }

    public static void sendCredit(final Dialog dial, String num, String name, String img) {
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


        if (pImg != null) {
            tvNameInitialFav.setVisibility(View.INVISIBLE);
            circularImageView.setVisibility(View.VISIBLE);
            Uri imgUri = Uri.parse(pImg);
            circularImageView.setImageURI(imgUri);
            circularImageView.setSelectorStrokeWidth(10);
            circularImageView.addShadow();
        } else {
            circularImageView.setVisibility(View.INVISIBLE);
            String initial = "";
            String[] split = pName.split(" ");
            for (String value : split) {
                initial += value.substring(0, 1);
            }

            tvNameInitialFav.setText(initial);
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

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+pNumber));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(callIntent);

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
