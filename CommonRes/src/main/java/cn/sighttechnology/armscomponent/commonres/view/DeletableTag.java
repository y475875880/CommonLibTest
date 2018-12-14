package cn.sighttechnology.armscomponent.commonres.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.sighttechnology.armscomponent.commonres.R;


/**
 * 自定义View --- 可删除标签
 */
public class DeletableTag extends RelativeLayout {

    private OnDelBtnPressedListener listener;

    private TextView tv;

    private ImageView delBtn;

    public DeletableTag(Context context) {
        this(context, null);
    }

    public DeletableTag(Context context, AttributeSet attrs) {
        super(context, attrs);
        View tagView = LayoutInflater.from(context).inflate(R.layout.public_deletable_tag_layout, this);
        tv = tagView.findViewById(R.id.text);
        delBtn = tagView.findViewById(R.id.delete_button);

        delBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPress();
                }
            }
        });
    }

    public void setTagText(String text) {
        if (text != null) {
            this.tv.setText(text);
        }
    }

    public void setOnDelBtnPressedListener(OnDelBtnPressedListener listener) {
        this.listener = listener;
    }

    public interface OnDelBtnPressedListener {
        void onPress();
    }

}
