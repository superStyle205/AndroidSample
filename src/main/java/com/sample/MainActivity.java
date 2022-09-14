package com.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import android.view.View;

import android.content.DialogInterface;

import android.util.Log;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private static final String MESSAGE_VALID_FULLNAME = "Tên người không được để trống và phải có "
                                                        + "ít nhất 3 ký tự";
    private static final String MESSAGE_VALID_IDENT_CARD_NUMBER = "Chứng minh nhân dân chỉ được nhập "
                                                        + "kiểu số và phải có đúng 9 chữ số";
    private static final String MESSAGE_VALID_GIVEN = "Sở thích phải chọn ít nhất 1 chọn lựa";

    private EditText mFullNameEdit;
    private EditText mIdentityCardNumberEdit;
    private RadioGroup mDegreeRadioGroup;
    private LinearLayout mGivenLayout;
    private EditText mProviderInfoEdit;
    private Button mSendInfoBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        init();
    }

    private void init() {
        mFullNameEdit = (EditText) this.findViewById(R.id.full_name);

        mIdentityCardNumberEdit = (EditText) this.findViewById(R.id.identity_card_number);
        
        mDegreeRadioGroup = (RadioGroup) this.findViewById(R.id.degree_group);
        
        mGivenLayout = (LinearLayout) this.findViewById(R.id.given_layout);
        
        mProviderInfoEdit = (EditText) this.findViewById(R.id.provider_info);
        
        mSendInfoBtn = (Button) this.findViewById(R.id.send_info_btn);
        mSendInfoBtn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Question");
        builder.setMessage("Are you sure you want to exit?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("Yes",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }
        );

        builder.setNegativeButton("No",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }
        );

        builder.create()
               .show();
    }

    @Override
    public void onClick (View view) {
        switch(view.getId()) {
            case R.id.send_info_btn:

                if (isValidDataInput()) {
                    String inputProfileStr = this.getInputProfile();

                    Log.d(TAG, inputProfileStr);

                    // Show Dialog info
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Thông tin cá nhân");
                    builder.setPositiveButton("Đóng",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                    });
                    builder.setMessage(inputProfileStr);
                    builder.create()
                           .show();

                }
                break;
        }
    }

    private boolean isValidDataInput() {
        boolean isValidDataInput = true;

        // check full name input
        String fullNameTmp = mFullNameEdit.getText().toString();
        if ("".equals(fullNameTmp) || fullNameTmp.length() < 3) {
            this.showToastMessage(MESSAGE_VALID_FULLNAME);
            return false;
        }

        // check cmnd input
        String iDentCardNumberTmp = mIdentityCardNumberEdit.getText().toString();
        if ("".equals(iDentCardNumberTmp) || iDentCardNumberTmp.length() != 9) {
            this.showToastMessage(MESSAGE_VALID_IDENT_CARD_NUMBER);
            return false;
        }

        // check given input
        int totalGivenItem = mGivenLayout.getChildCount();
        View givenItemView;
        boolean isCheckGiven = false;
        for (int i = 0; i < totalGivenItem; i++) {
            givenItemView = mGivenLayout.getChildAt(i);
            if (givenItemView instanceof CheckBox) {
                if (((CheckBox) givenItemView).isChecked()) {
                    isCheckGiven = true;
                    break;
                }
            }
        }
        if (!isCheckGiven) {
            this.showToastMessage(MESSAGE_VALID_GIVEN);
        }
        isValidDataInput = isCheckGiven;

        return isValidDataInput;
    }

    private void showToastMessage(String message) {
        Toast toast = Toast.makeText(this.getApplicationContext(),
                                     message,
                                     Toast.LENGTH_SHORT);
        toast.show();
    }

    private String getInputProfile() {
        // get full name input
        StringBuffer profileTmp = new StringBuffer();
        profileTmp.append(mFullNameEdit.getText().toString());

        // get ident card number
        profileTmp.append("\n");
        profileTmp.append(mIdentityCardNumberEdit.getText().toString());

        // get degree input
        profileTmp.append("\n");
        int degreeSelectID = mDegreeRadioGroup.getCheckedRadioButtonId();
        profileTmp.append(((RadioButton) findViewById(degreeSelectID)).getText().toString());

        // get given input
        profileTmp.append("\n");
        int totalGivenItem = mGivenLayout.getChildCount();
        View givenItemView;
        String givenTmp = "";
        for (int i = 0; i < totalGivenItem; i++) {
            givenItemView = mGivenLayout.getChildAt(i);
            if (givenItemView instanceof CheckBox) {
                if (((CheckBox) givenItemView).isChecked()) {
                    givenTmp += "-" + ((CheckBox) givenItemView).getText().toString();
                }
            }
        }
        profileTmp.append(givenTmp.replaceFirst("-", ""));

        // get provider info input
        profileTmp.append("\n---------------------");
        profileTmp.append("\nThông tin bổ sung:");
        profileTmp.append("\n");
        profileTmp.append(mProviderInfoEdit.getText().toString());
        profileTmp.append("\n---------------------");

        return profileTmp.toString();
    }

}
