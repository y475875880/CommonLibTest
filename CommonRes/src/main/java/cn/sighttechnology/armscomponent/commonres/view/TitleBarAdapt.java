package cn.sighttechnology.armscomponent.commonres.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.sighttechnology.armscomponent.commonres.R;
import cn.sighttechnology.armscomponent.commonres.utils.SightUtils;

public class TitleBarAdapt extends RelativeLayout {

    private TextView titleTv;
    private LinearLayout    linearLayoutRight;
    private OnBackPressedListener backListener;

    public TitleBarAdapt(Context context) {
        this(context, null);
    }

    public TitleBarAdapt(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.public_title_bar_layout14dp, this);
        ImageView backBtn = findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用时若未设置返回按钮点击事件，默认执行 Activity.finish()
                if (backListener == null) {
                    Context c = getContext();
                    if (c instanceof Activity) {
                        ((Activity) c).finish();
                    }
                } else {
                    backListener.onBackPressed();
                }
            }
        });
        titleTv = findViewById(R.id.tv_include_title);
        initAttrs(context, attrs);
        SightUtils.setTopPadding(getContext(), this);
//        initChildview();
        linearLayoutRight            =   findViewById(R.id.ll_right);
    }

    public void setTitle(String title) {
        this.titleTv.setText(title);
    }

    public void setOnBackPressedListener(OnBackPressedListener listener) {
        this.backListener = listener;
    }

    public interface OnBackPressedListener {
        void onBackPressed();
    }

    private void initAttrs(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar10dp);
        String titleText = ta.getString(R.styleable.TitleBar10dp_titleTextDp);
        ta.recycle();
        if (titleText == null || "".equals(titleText)) {
            titleText = "未设置标题";
        }
        titleTv.setText(titleText);

    }

//    void initChildview(){
//        int count = getChildCount();
//        if (count >   0){
//            View view   =   getChildAt(count    -   1);
////            removeView(view);

//        }
//    }

    public void addRightView(View view){
        //先移除
        removeView(view);
        //然后添加
        if (linearLayoutRight   ==  null){
            linearLayoutRight   =   findViewById(R.id.ll_right);
        }
        if (view    !=  null){
            linearLayoutRight.addView(view);
        }
    }
}
