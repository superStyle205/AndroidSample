package com.sample;

import android.app.Dialog;
import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.Window;

/**
 *
 * TODO - Template for CustomDialog
 *
 **/
public class PersonalInfoDialog extends Dialog  implements View.OnClickListener  {

    private Activity activity;

    public PersonalInfoDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.personal_dialog);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}
