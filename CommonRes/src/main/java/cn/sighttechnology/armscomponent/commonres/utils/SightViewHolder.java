package cn.sighttechnology.armscomponent.commonres.utils;

import android.view.View;
import android.widget.TextView;

/**
 * Description:
 * Data：2018/8/21-10:33
 * Author: yangjichao
 */
public class SightViewHolder {

    private View view;

    public SightViewHolder(View view) {
        this.view   =   view;
    }

    public void setText(int id,String text){

        TextView    textView    =   view.findViewById(id);
        textView.setText(text);
    }

    public void setVisibility(int id,   int isvisible){
        view.findViewById(id).setVisibility(isvisible);
    }

    public void setOnClickListener(int id, View.OnClickListener onClickListener){
        view.findViewById(id).setOnClickListener(onClickListener);
    }

}
