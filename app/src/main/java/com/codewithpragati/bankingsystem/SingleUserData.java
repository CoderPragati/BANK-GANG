package com.codewithpragati.bankingsystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class SingleUserData extends AppCompatActivity {

    ProgressDialog codeprogressDialog;
    String strphonenumber;
    Double newbalance;
    TextView codename, codephoneNumber, codeemail, codeaccount_no, codeifsc_code, codebalance;
    Button codetransfer_button;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_user_data);

        codename = findViewById(R.id.username);
        codephoneNumber = findViewById(R.id.userphonenumber);
        codeemail = findViewById(R.id.email);
        codeaccount_no = findViewById(R.id.account_no);
        codeifsc_code = findViewById(R.id.ifsc_code);
        codebalance = findViewById(R.id.balance);
        codetransfer_button = findViewById(R.id.transfer_button);

        codename.startAnimation(AnimationUtils.loadAnimation(SingleUserData.this, R.anim.p_in));
        codephoneNumber.startAnimation(AnimationUtils.loadAnimation(SingleUserData.this, R.anim.p_in));
        codeemail.startAnimation(AnimationUtils.loadAnimation(SingleUserData.this, R.anim.p_in));
        codeaccount_no.startAnimation(AnimationUtils.loadAnimation(SingleUserData.this, R.anim.p_in));
        codeifsc_code.startAnimation(AnimationUtils.loadAnimation(SingleUserData.this, R.anim.p_in));
        codebalance.startAnimation(AnimationUtils.loadAnimation(SingleUserData.this, R.anim.p_in));
        codetransfer_button.startAnimation(AnimationUtils.loadAnimation(SingleUserData.this, R.anim.bottom));

        codeprogressDialog = new ProgressDialog(SingleUserData.this);
        codeprogressDialog.setTitle("Loading data...");
        codeprogressDialog.show();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            strphonenumber = bundle.getString("phonenumber");
            showData(strphonenumber);
        }

        codetransfer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAmount();
            }
        });
    }

    private void showData(String phonenumber) {
        Cursor cursor = new DatabaseHelper(this).readparticulardata(phonenumber);
        while(cursor.moveToNext()) {
            String balancefromdb = cursor.getString(2);
            newbalance = Double.parseDouble(balancefromdb);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(newbalance);

            codeprogressDialog.dismiss();

            codephoneNumber.setText(cursor.getString(0));
            codename.setText(cursor.getString(1));
            codeemail.setText(cursor.getString(3));
            codebalance.setText(price);
            codeaccount_no.setText(cursor.getString(4));
            codeifsc_code.setText(cursor.getString(5));
        }

    }

    private void enterAmount()
    {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(SingleUserData.this);
        View mView = getLayoutInflater().inflate(R.layout.enter_transfer_money, null);
        mBuilder.setTitle("Enter amount").setView(mView).setCancelable(false);

        final EditText mAmount = (EditText) mView.findViewById(R.id.enter_money);

        mBuilder.setPositiveButton("SEND", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            { }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                transactionCancel();
            }
        });

        dialog = mBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAmount.getText().toString().isEmpty()){
                    mAmount.setError("Amount can't be empty");
                }else if(Double.parseDouble(mAmount.getText().toString()) > newbalance){
                    mAmount.setError("Your account don't have enough balance");
                }else{
                    Intent intent = new Intent(SingleUserData.this, SendToUser.class);
                    intent.putExtra("phonenumber", codephoneNumber.getText().toString());
                    intent.putExtra("name", codename.getText().toString());
                    intent.putExtra("currentamount", newbalance.toString());
                    intent.putExtra("transferamount", mAmount.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void transactionCancel() {
        AlertDialog.Builder builder_exitbutton = new AlertDialog.Builder(SingleUserData.this);
        builder_exitbutton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");
                        String date = simpleDateFormat.format(calendar.getTime());

                        new DatabaseHelper(SingleUserData.this).insertTransferData(date, codename.getText().toString(), "Not selected", "0", "Failed");
                        Toast.makeText(SingleUserData.this, "Transaction Cancelled!", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                enterAmount();
            }
        });
        AlertDialog alertexit = builder_exitbutton.create();
        alertexit.show();
    }

}
