package com.milansamardzic.zvrrmtel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.milansamardzic.pozovime.R;
import com.pkmmte.view.CircularImageView;
import com.shamanland.fab.FloatingActionButton;
import com.shamanland.fab.ShowHideOnScroll;

import java.util.ArrayList;

/**
 * Created by ms on 12/24/14.
 */
public class Contacts extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */


    People humans;
    public ArrayList<People> peopleList = new ArrayList<People>();
    PhonebookAdapter pa;
    ArrayList<People> init;
    ListView lv;
    ContentResolver cr;
    Cursor phones;
    String pNumberFixed;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static Contacts newInstance(int sectionNumber) {
        Contacts fragment = new Contacts();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;

    }

    public Contacts() {
    }

    FloatingActionButton fab;

    private static Context mContext;

    public static Context getAppContext(){
        return mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.second_fragment, container, false);

        mContext = this.getActivity();

        lv = (ListView) rootView.findViewById(R.id.lv);
        init = new ArrayList<People>();
        pa = new PhonebookAdapter(this.getActivity().getBaseContext(), init);
        fab = (FloatingActionButton) rootView.findViewById(R.id.btnFind);

        lv.setAdapter(pa);

        lv.setItemsCanFocus(false);
        lv.setTextFilterEnabled(true);
        lv.setOnTouchListener(new ShowHideOnScroll(fab));

        cr = getActivity().getContentResolver();
        phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        getAllContacts(cr, phones);
        clicked();
        selectedListener();
        return rootView;
    }

    private void selectedListener() {

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String pName = pa.getItem(position).getName();
                final String pNumber = pa.getItem(position).getPhoneNumber();
                final String pImg = pa.getItem(position).getImg();
                final Dialog dialog = new Dialog(getActivity());
                pNumberFixed = pNumber.replaceAll("[ -]", "");

                AlertDialog.Builder builder = new AlertDialog.Builder(dialog.getContext());
                builder.setMessage("Izaberite uslugu")
                        .setNegativeButton("Podijeli dopunu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Dialog dialog2 = new Dialog(getActivity());
                                dialog.dismiss();
                                sendCredit(dialog2, pName, pNumberFixed, pImg);
                            }
                        })


                        .setPositiveButton("Pozovi me", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                final Dialog dialog1 = new Dialog(getActivity());
                                dialog.dismiss();
                                SendSMS.sendSms(dialog1, pNumberFixed, pName, pImg);

                            }

                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    public static void sendCredit(final Dialog dialog, final String pName, final String pNumber, String pImg) {

        dialog.setContentView(R.layout.custom_dialog_credit);
        dialog.setTitle("Poslati?");

        TextView textViewUser = (TextView) dialog.findViewById(R.id.text);
        TextView tvNameInitialFav = (TextView) dialog.findViewById(R.id.tvNameInitial);
        final TextView tvValuePF = (TextView) dialog.findViewById(R.id.tvValuePF);
        final TextView tvSumaVal = (TextView) dialog.findViewById(R.id.tvSumaVal);
        final TextView tvSB = (TextView) dialog.findViewById(R.id.tvValue);
        final SeekBar sb = (SeekBar) dialog.findViewById(R.id.seekBarKM);
        final SeekBar sbp = (SeekBar) dialog.findViewById(R.id.seekBarPF);
        RelativeLayout rlPF = (RelativeLayout) dialog.findViewById(R.id.rlPF);
        CircularImageView circularImageView = (CircularImageView) dialog.findViewById(R.id.civImage);


        textViewUser.setText("Izabrani kontakt:\n" + pName + "\n(" + pNumber + ")");
        sbp.setEnabled(false);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSB.setText(String.valueOf(sb.getProgress()) + " KM");
                tvSumaVal.setText(String.valueOf(sb.getProgress()) + "." + String.valueOf(sbp.getProgress()) + " km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvValuePF.setText(String.valueOf(sbp.getProgress()) + " PF");
                tvSumaVal.setText(String.valueOf(sb.getProgress()) + "." + String.valueOf(sbp.getProgress()) + " KM");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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
                //+String.valueOf(sbp.getProgress())
                String hashEncode = Uri.encode("#");
                //"." + String.valueOf(sbp.getProgress())
                String phone = "*100*6*6*" + pNumber + "*" + String.valueOf(sb.getProgress()) + "#";
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phone, null));
                mContext.startActivity(intent);

                dialog.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(dialog.getContext());
                builder.setMessage("Iznos uspješno poslan!")
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



    private void clicked() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               scroll();
            }
        });
    }

    public  void getAllContacts(ContentResolver cr, Cursor phones ) {

        while (phones.moveToNext())
        {
            People p  = new People();

            p.phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            p.phoneNumber = (phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\s+",""));
//|| p.phoneNumber.contains("00387 65")|| p.phoneNumber.contains("00387 65")
            if(
                    p.phoneNumber.contains("065")
                            || p.phoneNumber.contains("0038765")|| p.phoneNumber.contains("00 387 65") || p.phoneNumber.contains("00387 65")
                            || p.phoneNumber.contains("+38765") || p.phoneNumber.contains("+387 65")

                    || p.phoneNumber.contains("066")
                            || p.phoneNumber.contains("0038766")|| p.phoneNumber.contains("00 387 66") || p.phoneNumber.contains("00387 66")
                            || p.phoneNumber.contains("+38766") || p.phoneNumber.contains("+387 66")
                    ) {

                p.name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                p.favourite = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
                p.img = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                pa.addAll(p);

            }

        }

        phones.close();
    }

    public void scroll(){

        lv.smoothScrollToPosition(0);
    }



}

