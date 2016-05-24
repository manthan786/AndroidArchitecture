package com.architecture.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidArch.R;

public class OneButtonDialog extends DialogFragment {


    public static final String TAG_TITLE = "title";
    public static final String TAG_MESSAGE = "message";

    public static final String TAG_BUTTON_TEXT = "rightButtonText";

    private View.OnClickListener onButtonClick = null;


    public OneButtonDialog(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        Bundle args = getArguments();
        String title = args.getString(TAG_TITLE, "Alert");
        String message = args.getString(TAG_MESSAGE, "");
        String buttonText = args.getString(TAG_BUTTON_TEXT, "OK");

        View view = getActivity().getLayoutInflater().inflate(R.layout.onebutton_dialog, null);

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView messageView = (TextView) view.findViewById(R.id.message);

        Button button = (Button) view.findViewById(R.id.button_ok);

        titleView.setText(title);
        messageView.setText(message);
        button.setText(buttonText);
        button.setOnClickListener(onButtonClick);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();


    }



    public View.OnClickListener getOnRightButtonClick() {
        return onButtonClick;
    }

    public void setOnRightButtonClick(View.OnClickListener onButtonClick) {
        this.onButtonClick = onButtonClick;
    }



}
