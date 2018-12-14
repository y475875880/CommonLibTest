package cn.sighttechnology.armscomponent.commonres.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.sighttechnology.armscomponent.commonres.R;
import cn.sighttechnology.armscomponent.commonres.utils.SightUtils;

/**
 * 自定义标题栏，可静态、动态设置标题，拥有自定义功能的返回按钮（若不手动设置，默认功能为终止当前 Activity）
 */
public class TitleBar extends RelativeLayout {

    private TextView titleTv;

    private OnBackPressedListener backListener;

    private ImageView backBtn;

    private View bottomHorizontalLine;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        View v = LayoutInflater.from(context).inflate(R.layout.public_title_bar_layout, this);
        backBtn = v.findViewById(R.id.back_icon);
        bottomHorizontalLine = v.findViewById(R.id.view_line);

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

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        String titleText = ta.getString(R.styleable.TitleBar_titleText);
        boolean showBackBtn = ta.getBoolean(R.styleable.TitleBar_showBackBtn, true);
        boolean showBottomLine = ta.getBoolean(R.styleable.TitleBar_showBottomLine, true);
        ta.recycle();
        if (titleText == null || "".equals(titleText)) {
            titleText = "未设置标题";
        }
        titleTv.setText(titleText);

        if (showBackBtn) {
            backBtn.setVisibility(VISIBLE);
        } else {
            backBtn.setVisibility(GONE);
        }

        if (showBottomLine) {
            bottomHorizontalLine.setVisibility(VISIBLE);
        } else {
            bottomHorizontalLine.setVisibility(GONE);
        }

    }

}
