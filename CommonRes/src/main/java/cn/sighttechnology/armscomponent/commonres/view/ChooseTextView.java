package cn.sighttechnology.armscomponent.commonres.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.sighttechnology.armscomponent.commonres.R;

public class ChooseTextView extends LinearLayout {

    private TextView textViewShow;

    public ChooseTextView(Context context) {
        this(context,null);
    }

    public ChooseTextView(Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.public_textview_choose, this);
    }

    public ChooseTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setShowText(String text){

        if (text!=null &&!text.equals("")){
            TextView    textViewShow = findViewById(R.id.tv_show);
            textViewShow.setText(text);
        }

    }

    public CharSequence getText(){
        TextView    textViewShow = findViewById(R.id.tv_show);
        return textViewShow.getText();
    }



}
