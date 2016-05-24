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

/**
 * Created by pratik on 07-04-2015.
 */
public class TwoButtonDialog extends DialogFragment {

    public static final String TAG_TITLE = "title";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_LEFT_BUTTON_TEXT = "leftButtonText";
    public static final String TAG_RIGHT_BUTTON_TEXT = "rightButtonText";

    private String leftButtonText = "No";
    private String rightButtonText = "Yes";
    private String title = "Alert";
    private String message = "";
    private View.OnClickListener onLeftButtonClick= null;
    private View.OnClickListener onRightButtonClick = null;

    public TwoButtonDialog(){

    }
    /*public TwoButtonDialog(String title, String message, DialogInterface.OnClickListener onLeftButtonClick, DialogInterface.OnClickListener onRightButtonClick){
        this.title = title;
        this.message = message;
        this.onLeftButtonClick = onLeftButtonClick;
        this.onRightButtonClick =onRightButtonClick;
    }
*/


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
        String leftButtonText = args.getString(TAG_LEFT_BUTTON_TEXT, "No");
        String rightButtonText = args.getString(TAG_RIGHT_BUTTON_TEXT, "Yes");

        View view = getActivity().getLayoutInflater().inflate(R.layout.twobutton_dialog, null);

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView messageView = (TextView) view.findViewById(R.id.message);
        Button leftButton = (Button) view.findViewById(R.id.leftButton);
        Button rightButton = (Button) view.findViewById(R.id.rightButton);

        titleView.setText(title);
        messageView.setText(message);
        leftButton.setText(leftButtonText);
        rightButton.setText(rightButtonText);

        leftButton.setOnClickListener(onLeftButtonClick);
        rightButton.setOnClickListener(onRightButtonClick);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();

        /*return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(rightButtonText, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                    }
                })
                .setNegativeButton(leftButtonText, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                    }
                })
                .create();*/
    }

    public View.OnClickListener getOnLeftButtonClick() {
        return onLeftButtonClick;
    }

    public void setOnLeftButtonClick(View.OnClickListener onLeftButtonClick) {
        this.onLeftButtonClick = onLeftButtonClick;
    }

    public View.OnClickListener getOnRightButtonClick() {
        return onRightButtonClick;
    }

    public void setOnRightButtonClick(View.OnClickListener onRightButtonClick) {
        this.onRightButtonClick = onRightButtonClick;
    }
}
