package cn.sighttechnology.armscomponent.commonres.view;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Description:
 * Data：2018/6/14-8:59
 * Author: yangjichao
 */
public abstract class SightTextWatcther implements TextWatcher {


    public abstract void afterTextInput(Editable editable);

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        afterTextInput(s);
    }

}
