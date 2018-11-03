package com.emu.adam.will.physicsar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ResolveDialogFragment extends DialogFragment {

    interface OkListener {
        void onOkPressed(String dialogValue);
    }

    private OkListener okListener;
    private EditText shortCodeField;

    /** Sets a listener that is invoked when the OK button on this dialog is pressed. */
    void setOkListener(OkListener okListener) {
        this.okListener = okListener;
    }

    /**
     * Creates a simple layout for the dialog. This contains a single user-editable text field whose
     * input type is retricted to numbers only, for simplicity.
     */
    private LinearLayout getDialogLayout() {
        Context context = getContext();
        LinearLayout layout = new LinearLayout(context);
        shortCodeField = new EditText(context);
        shortCodeField.setInputType(InputType.TYPE_CLASS_NUMBER);
        shortCodeField.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        shortCodeField.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        layout.addView(shortCodeField);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return layout;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(getDialogLayout())
                .setTitle(R.string.resolve_dialog_title)
                .setPositiveButton(
                        R.string.ok,
                        (dialog, which) -> {
                            Editable shortCodeText = shortCodeField.getText();
                            if (okListener != null && shortCodeText != null && shortCodeText.length() > 0) {
                                // Invoke the callback with the current checked item.
                                okListener.onOkPressed(shortCodeText.toString());
                            }
                        })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {});
        return builder.create();
    }
}