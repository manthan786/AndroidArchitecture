package com.architecture.main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.architecture.login.LoginActivity;
import com.architecture.utils.OneButtonDialog;

/**
 * Created by manthan on 24-05-2016.
 */
public abstract class BaseActivity extends ActionBarActivity {


    final DialogFragment buttonDialog = new OneButtonDialog();

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getLayoutResource());
    }

    protected abstract int getLayoutResource();

    public void oneButton(String title,String message){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonDialog.dismiss();
            }
        };
        ((OneButtonDialog) buttonDialog).setOnRightButtonClick(onClickListener);
        Bundle args = new Bundle();
        args.putString(OneButtonDialog.TAG_TITLE, title);
        args.putString(OneButtonDialog.TAG_MESSAGE, message);
        buttonDialog.setArguments(args);
        buttonDialog.show(getSupportFragmentManager(), "AlertDialog");
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void internetCheck(){
        if(!isNetworkAvailable()){
            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

}
