package com.milansamardzic.zvrrmtel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.milansamardzic.pozovime.R;

import java.util.ArrayList;

/**
 * Created by ms on 12/24/14.
 */
public class Favourite extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */


    People humans;
    public ArrayList<People> peopleList = new ArrayList<People>();
    GridAdapter ga;
    ArrayList<People> init;
    GridView gv;
    String pNumberFixed;
    ImageButton btnSend;
    EditText etNumber;

    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Favourite newInstance(int sectionNumber) {
        Favourite fragment = new Favourite();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Favourite() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.first_fragment, container, false);

        gv= (GridView) rootView.findViewById(R.id.gvFav);

        init = new ArrayList<People>();

        ga = new GridAdapter(this.getActivity().getBaseContext(), init);
        gv.setAdapter(ga);

        selectedListener();

        ContentResolver cr = getActivity().getContentResolver();
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        getAllContacts(cr);

        btnSend = (ImageButton) rootView.findViewById(R.id.btnSend);
        etNumber = (EditText) rootView.findViewById(R.id.etEnterNumber);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   final String pNumber = etNumber.getText().toString();
                   final String pImg = etNumber.getText().toString();
                   Dialog dialog = new Dialog(getActivity());
                   //SendSMS.sendSms(dialog, pNumber, "", "");


                AlertDialog.Builder builder = new AlertDialog.Builder(dialog.getContext());
                builder.setMessage("Izaberite uslugu")
                        .setNegativeButton("Podijeli dopunu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Dialog dialog2 = new Dialog(getActivity());
                                dialog.dismiss();
                                Contacts.sendCredit(dialog2, "", pNumberFixed, pImg);
                            }
                        })


                        .setPositiveButton("Pozovi me", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                final Dialog dialog1 = new Dialog(getActivity());
                                dialog.dismiss();
                                SendSMS.sendSms(dialog1, pNumberFixed, "", pImg);

                            }

                        });
                AlertDialog alert = builder.create();
                alert.show();


                }
        });

        return rootView;
    }

    private void selectedListener() {

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String pName = ga.getItem(position).getName();
                final String pNumber = ga.getItem(position).getPhoneNumber();
                final Dialog dialog = new Dialog(getActivity());
                final String pImg = ga.getItem(position).getImg();
                pNumberFixed = pNumber.replaceAll("[ -]", "");
                // SendSMS.sendSms(dialog, pNumberFixed, pName, pImg);

                AlertDialog.Builder builder = new AlertDialog.Builder(dialog.getContext());
                builder.setMessage("Izaberite uslugu")
                        .setNegativeButton("Podijeli dopunu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Dialog dialog2 = new Dialog(getActivity());
                                dialog.dismiss();
                                Contacts.sendCredit(dialog2, pName, pNumberFixed, pImg);
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

    public  void getAllContacts(ContentResolver cr) {

        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            People p  = new People();
            p.favourite = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
            p.phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            p.frekquently = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED));

            p.phoneNumber = (phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\s+",""));
            if(Integer.parseInt(p.favourite)==1 && (
                            p.phoneNumber.contains("065")
                            || p.phoneNumber.contains("0038765")|| p.phoneNumber.contains("00 387 65") || p.phoneNumber.contains("00387 65")
                            || p.phoneNumber.contains("+38765") || p.phoneNumber.contains("+387 65")

                            || p.phoneNumber.contains("066")
                            || p.phoneNumber.contains("0038766")|| p.phoneNumber.contains("00 387 66") || p.phoneNumber.contains("00387 66")
                            || p.phoneNumber.contains("+38766") || p.phoneNumber.contains("+387 66")

            )) {
                p.name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                p.img = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                ga.add(p);
            }
        }

        phones.close();
    }

}