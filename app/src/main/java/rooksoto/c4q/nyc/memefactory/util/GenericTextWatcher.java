package rooksoto.c4q.nyc.memefactory.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by SACC on 1/21/17.
 */

public class GenericTextWatcher implements TextWatcher {

    private TextView view;
    private EditText editText;

    public GenericTextWatcher(TextView view, EditText editText) {
        this.view = view;
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() == 0) {
            view.setVisibility(View.GONE);
        } else{
            view.setText(editText.getText());
        }
    }
}
