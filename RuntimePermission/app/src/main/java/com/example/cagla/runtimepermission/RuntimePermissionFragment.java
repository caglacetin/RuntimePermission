package com.example.cagla.runtimepermission;

import android.Manifest;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by done on 18/12/2016.
 */

public class RuntimePermissionFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 3;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 11;

    @BindView(R.id.edittext_phone_number)
    EditText phoneNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_runtime_permission, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.button_call)
    public void startCalling(){

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            call();
        }
        else {

            if (FragmentCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)){

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Your permission is required to make call")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentCompat.requestPermissions(RuntimePermissionFragment.this,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
                            }
                        });
                builder.setNegativeButton("DENY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            else {
                Log.e("never", "ask again");
                        FragmentCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }

        }

    }

    /*@OnClick(R.id.read_button)
    public void readContacts() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            text.setText("read contacts");
        } else {

            if (FragmentCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Permission Required for Read Contact!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentCompat.requestPermissions(RuntimePermissionFragment.this,
                                        new String[]{Manifest.permission.READ_CONTACTS},
                                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            }
                        });
                builder.setNegativeButton("DENY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                FragmentCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        //requestcode 0-255 arasında olabilir.
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    call();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }

                return;
            }
           /* case MY_PERMISSIONS_REQUEST_READ_CONTACTS:{
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    text.setText("read contacts");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }

                return;
            }*/



            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void call(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phoneNumber.getText().toString()));
        startActivity(callIntent);
    }
}
