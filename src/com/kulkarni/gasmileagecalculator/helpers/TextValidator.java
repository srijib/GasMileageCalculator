package com.kulkarni.gasmileagecalculator.helpers;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public abstract class TextValidator implements TextWatcher {

	private EditText editText;
	
	public TextValidator (EditText edit) {
		editText = edit;
	}
	
	public abstract void validate (EditText edit, String text);
	
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		String text = editText.getText().toString();
		validate(editText, text);
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

}
