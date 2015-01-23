package com.milansamardzic.zvrrmtel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

import com.milansamardzic.pozovime.R;

/**
 * Created by ms on 12/25/14.
 */
public class About extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.about);
        super.onCreate(savedInstanceState);
        Button b2;
        Button b5;
        Button b10;

        b2 = (Button) findViewById(R.id.button2);
        b5 = (Button) findViewById(R.id.button5);
        b10 = (Button) findViewById(R.id.button10);

        b2.setOnClickListener(this);
        b5.setOnClickListener(this);
        b10.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                buyMeBeer("2");
                break;
            case R.id.button5:
                buyMeBeer("5");
                break;
            case R.id.button10:
                buyMeBeer("10");
                break;
        }

    }

    private void buyMeBeer(final String s) {

        final Dialog dialog = new Dialog(this);
        final Dialog dialog1 = new Dialog(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(dialog.getContext());
        builder.setMessage("Aplikacija je potpuno besplatna, autor i tim nemaju nikakve materijalne koristi od aplikacije.\n" +
                "Ovim putem direktno podržavate rad i trud autora i samog tima.\n" +
                "Hvala vam!")
                .setCancelable(true)
                .setNegativeButton("Odustajem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                        .setPositiveButton("Nastavi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String phone = "*100*6*6*" + "065308858" + "*" + s + "#";
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phone, null));
                        startActivity(intent);
                        dialog.dismiss();

                        AlertDialog.Builder builder = new AlertDialog.Builder(dialog1.getContext());
                        builder.setMessage("Hvala na podršci!\nZa sve sugestije i komentare slobodno nam pišite!")
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
        AlertDialog alert = builder.create();
        alert.show();
    }
}
