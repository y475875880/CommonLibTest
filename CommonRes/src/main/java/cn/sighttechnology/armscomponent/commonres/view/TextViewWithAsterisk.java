package cn.sighttechnology.armscomponent.commonres.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.sighttechnology.armscomponent.commonres.R;


public class TextViewWithAsterisk extends LinearLayout {

    /**
     * TextView
     */
    private TextView mTextView;

    /**
     * TextView
     */
    private ImageView mImageView;

    /**
     * Text
     */
    private String mText;

    /**
     * 文本颜色
     */
    private int mTextColor = -1;

    /**
     * 文本尺寸
     */
    private int mTextSize;

    public TextViewWithAsterisk(Context context) {
        this(context, null);
    }

    public TextViewWithAsterisk(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewWithAsterisk(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.public_custom_required_text_view_layout, this);
        mTextView = v.findViewById(R.id.textView);
        mImageView = v.findViewById(R.id.imageView);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextViewWithAsterisk);
        mText = ta.getString(R.styleable.TextViewWithAsterisk_public_text);

        // f 为 xml文件中配置的用于控制字体大小的数字
        float f = ta.getFloat(R.styleable.TextViewWithAsterisk_public_size, 15);
        mTextColor = ta.getColor(R.styleable.TextViewWithAsterisk_public_color, Color.parseColor("#000000"));
        ta.recycle();

        mTextView.setText(mText);

        //使用dp作为单位设置字体大小
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, f);

        mTextView.setTextColor(mTextColor == -1 ? Color.parseColor("#000000") : mTextColor);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public void setStartVisible(int visible){
        mImageView.setVisibility(visible);
    }
}
