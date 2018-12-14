package cn.sighttechnology.armscomponent.commonres.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;

import java.util.ArrayList;

import cn.sighttechnology.armscomponent.commonres.R;

/**
 * 尝试实现列表页顶部筛选控件的封装
 * <p>
 * 封装了如下功能：
 * 动态设置选项数量 （1-6 个）
 * 弹窗之间的直接切换
 * 点击外部隐藏弹窗
 * 设置以及恢复选项选中的样式、文本
 *
 * @author ZhouZhengfei
 */
public class ScreenBar extends LinearLayout {


    /**
     * 宿主Context
     */
    private Context mContext;


    /**
     * 外层布局
     */
    private View contentView;

    /**
     * 筛选栏视图
     */
    private LinearLayout mBarView;

    /**
     * 选项文本
     */
    private String[] optionTextArr = new String[6];

    /**
     * 筛选项集
     */
    private ArrayList<ScreenOption> screenOptions = new ArrayList<>(6);

    /**
     * 选项布局集
     */
    private ArrayList<LinearLayout> optionLayouts = new ArrayList<>(6);

    /**
     * 选项弹窗集
     */
    private ArrayList<CustomPopupWindow> popupWindows = new ArrayList<>(6);

    /**
     * 用于点击关闭主弹窗的顶部空白弹窗
     */
    private CustomPopupWindow topEmptyPop;

    /**
     * 弹窗底部黑色半透明遮罩
     */
    private View mask;

    /**
     * 一些ID常量
     */
    private final int ID_OUTER_LAYOUT = 1;
    private final int ID_CONTENT_LAYOUT = 2;
    private final int ID_PLACE_HOLDER = 3;

    /**
     * 未知高度标记
     */
    private final int PRI_FLAG_UNKNOWN_HEIGHT = -1;

    /**
     * 弹窗正确显示应具有的高度
     */
    private int mPopHeight = PRI_FLAG_UNKNOWN_HEIGHT;

    /**
     * 顶部空弹窗高度
     */
    private int topEmptyPopHeight = PRI_FLAG_UNKNOWN_HEIGHT;

    /**
     * 点击选项时是否展示弹窗
     */
    private boolean showPopWhenOptionIsClicked = true;


    public ScreenBar(Context context) {
        this(context, null);
    }

    public ScreenBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScreenBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }


    /**
     * 初始化视图
     */
    private void initView() {

        contentView = LayoutInflater.from(mContext)
                .inflate(R.layout.public_custom_view_screen_bar_layout, this);
        mBarView = contentView.findViewById(R.id.screenBar);

        optionLayouts.add((LinearLayout) contentView.findViewById(R.id.optionsLayout1));
        optionLayouts.add((LinearLayout) contentView.findViewById(R.id.optionsLayout2));
        optionLayouts.add((LinearLayout) contentView.findViewById(R.id.optionsLayout3));
        optionLayouts.add((LinearLayout) contentView.findViewById(R.id.optionsLayout4));
        optionLayouts.add((LinearLayout) contentView.findViewById(R.id.optionsLayout5));
        optionLayouts.add((LinearLayout) contentView.findViewById(R.id.optionsLayout6));

        screenOptions.add(new ScreenOption((TextView) contentView.findViewById(R.id.option1),
                (ImageView) contentView.findViewById(R.id.iv_option1)));

        screenOptions.add(new ScreenOption((TextView) contentView.findViewById(R.id.option2),
                (ImageView) contentView.findViewById(R.id.iv_option2)));

        screenOptions.add(new ScreenOption((TextView) contentView.findViewById(R.id.option3),
                (ImageView) contentView.findViewById(R.id.iv_option3)));

        screenOptions.add(new ScreenOption((TextView) contentView.findViewById(R.id.option4),
                (ImageView) contentView.findViewById(R.id.iv_option4)));

        screenOptions.add(new ScreenOption((TextView) contentView.findViewById(R.id.option5),
                (ImageView) contentView.findViewById(R.id.iv_option5)));

        screenOptions.add(new ScreenOption((TextView) contentView.findViewById(R.id.option6),
                (ImageView) contentView.findViewById(R.id.iv_option6)));

    }

    /**
     * 显示/隐藏 分割线
     */
    public ScreenBar setShowOptionDivider(boolean isShow) {
        if (isShow) {
            mBarView.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        } else {
            mBarView.setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        }
        return this;
    }

    /**
     * 设置选项列表，从第七个选项起将被忽略
     *
     * @param options 选项名称列表
     */
    public ScreenBar setOptions(String[] options) {
        int optionCount = options.length > 6 ? 6 : options.length;

        for (int i = 0; i < optionCount; i++) {
            screenOptions.get(i).setText(options[i]);
            LinearLayout optionLayout = optionLayouts.get(i);
            optionLayout.setVisibility(VISIBLE);
            optionTextArr[i] = options[i];
            final int optionIndex = i;
            optionLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //调用用户逻辑
                    if (onOptionClickedListener != null) {
                        onOptionClickedListener.onClick(optionIndex, optionTextArr[optionIndex]);
                    }
                    //内部处理
                    onOptionClicked(optionIndex, optionTextArr[optionIndex]);
                }
            });
        }

        initPopupWindows(optionCount);

        return this;
    }

    /**
     * 设置选项图标
     */
    public ScreenBar customOptionIcon(int optionIndex, int checkedIconResId, int uncheckedIconResId) {
        screenOptions.get(optionIndex).setOptionIcon(checkedIconResId, uncheckedIconResId);
        return this;
    }

    /**
     * 设置选项图标大小
     *
     * @param optionIndex 选项索引
     * @param width       宽
     * @param height      高
     * @return this
     */
    public ScreenBar setOptionIconSize(int optionIndex, int width, int height) {
        ViewGroup.LayoutParams iconLayoutParams =
                screenOptions.get(optionIndex).getOptionIv().getLayoutParams();
        iconLayoutParams.width = width;
        iconLayoutParams.height = height;
        screenOptions.get(optionIndex).getOptionIv().setLayoutParams(iconLayoutParams);
        return this;
    }

    /**
     * 隐藏选项文本
     */
    public ScreenBar hideOptionText(int optionIndex) {
        screenOptions.get(optionIndex).getOptionTv().setVisibility(View.GONE);
        return this;
    }

    /**
     * 设置选项为选中状态
     *
     * @param optionIndex 选项索引
     * @param optionText  选项文本  传入 "" 将不改变文本内容，只改变文本颜色
     */
    public void setOptionChecked(int optionIndex, String optionText) {

        if (optionText != null && !"".equals(optionText)) {
            screenOptions.get(optionIndex).setText(optionText);
        }
        screenOptions.get(optionIndex).setChecked(true);
    }

    /**
     * 恢复选项的未选中状态
     *
     * @param optionIndex 指定的选项索引
     */
    public void restoreOption(int optionIndex) {
        screenOptions.get(optionIndex).setText(optionTextArr[optionIndex]);
        screenOptions.get(optionIndex).setChecked(false);
    }

    /**
     * 初始化弹窗
     *
     * @param popCount 弹窗个数
     */
    private void initPopupWindows(int popCount) {

        for (int i = 0; i < popCount; i++) {
            CustomPopupWindow pop = new CustomPopupWindow(
                    createBasalPopView(),
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    false);

            final int popIndex = i;

            pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (popDismissListener != null) {
                        popDismissListener.onDismiss(popIndex);
                    }
                }
            });

            popupWindows.add(pop);
        }


        //筛选栏上方空白处点击消失
        View topEmptyView = new View(mContext);
        topEmptyView.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );
        topEmptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePop();
            }
        });

        //初始化选项栏上方的空弹窗
        topEmptyPop = new CustomPopupWindow(
                topEmptyView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                false);
    }

    /**
     * 创建一个基本的弹窗内容视图
     *
     * @return empty contentView
     */
    private LinearLayout createBasalPopView() {

        //outer layout
        LinearLayout popView = new LinearLayout(mContext);
        popView.setOrientation(VERTICAL);
        popView.setId(ID_OUTER_LAYOUT);
        LayoutParams lp =
                new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        popView.setLayoutParams(lp);

        //FrameLayout
        FrameLayout contentLayout = new FrameLayout(mContext);
        contentLayout.setId(ID_CONTENT_LAYOUT);
        FrameLayout.LayoutParams flp =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        contentLayout.setLayoutParams(flp);

        //TextView
        TextView placeHolder = new TextView(mContext);
        placeHolder.setId(ID_PLACE_HOLDER);
        placeHolder.setGravity(Gravity.CENTER);
        placeHolder.setText("请设置contentView");
        placeHolder.setBackgroundColor(Color.parseColor("#FDD100"));
        placeHolder.setTextColor(Color.parseColor("#000000"));
        placeHolder.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 220)
        );


        //mask view  在弹窗底部 用于点击后隐藏弹窗的 View
        View bottomEmptyView = new View(mContext);
        bottomEmptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmptyViewClicked();
            }
        });
        bottomEmptyView.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );

        contentLayout.addView(placeHolder);
        popView.addView(contentLayout);
        popView.addView(bottomEmptyView);


        return popView;

    }

    /**
     * 设置弹窗底部半透明遮罩
     *
     * @param mask 遮罩
     */
    public ScreenBar setMask(View mask) {
        this.mask = mask;
        return this;
    }

    /**
     * 设置弹窗内容视图
     */
    public void setPopContentViews(ArrayList<View> popContentViews) {
        int popViewsCount = popContentViews.size() > 6 ? 6 : popContentViews.size();

        for (int i = 0; i < popViewsCount; i++) {
            setRealContentView(i, popContentViews.get(i));
        }
    }

    /**
     * 设置弹窗内容视图
     *
     * @param popIndex    要设置的pop索引
     * @param contentView 内容视图
     */
    public void setPopContentView(int popIndex, View contentView) {
        setRealContentView(popIndex, contentView);
    }


    private void setRealContentView(int popIndex, View contentView) {

        //若popIndex不合理，忽略本次调用
        if (popIndex >= popupWindows.size() || popupWindows.get(popIndex) == null) {
            return;
        }

        View realContentView = popupWindows.get(popIndex).getContentView();

        //隐藏占位符
        realContentView.findViewById(ID_PLACE_HOLDER).setVisibility(View.GONE);

        //设置用户指定的内容视图
        FrameLayout contentLayout = realContentView.findViewById(ID_CONTENT_LAYOUT);
        contentLayout.addView(contentView);

    }



    /**
     * 各筛选项的点击事件处理方法
     *
     * @param position   The position of option
     * @param optionDesc The description of option
     */
    private void onOptionClicked(int position, String optionDesc) {

        if (showPopWhenOptionIsClicked) {
            showPop(popupWindows.get(position));
        }

    }

    /**
     * 弹窗空白区域点击事件处理
     */
    private void onEmptyViewClicked() {
        hidePop();
    }

    /**
     * 储存上一个打开的弹窗
     */
    private CustomPopupWindow prevPop;

    /**
     * 重置PopupWindow高度并使其显示在正确位置
     */
    private void showPop(CustomPopupWindow popupWindow) {

        //首次执行时存储高度值
        if (mPopHeight == PRI_FLAG_UNKNOWN_HEIGHT) {
            mPopHeight = getPopupWindowHeight();
        }

        //显示蒙层
        if (mask != null && mask.getVisibility() == View.GONE) {
            mask.setVisibility(View.VISIBLE);
        }

        //关闭上一个打开的 pop
        if (prevPop != null && prevPop != popupWindow) {
            prevPop.dismiss();
        }

        //设置高度，显示
        if (!popupWindow.isShowing()) {
            popupWindow.setHeight(mPopHeight);
            popupWindow.showAsDropDown(contentView);

            //显示空白弹窗
            if (!topEmptyPop.isShowing()) {

                if (topEmptyPopHeight == PRI_FLAG_UNKNOWN_HEIGHT)
                    topEmptyPopHeight = getEmptyPopHeight();

                topEmptyPop.setHeight(topEmptyPopHeight);
                topEmptyPop.showAtLocation(this, Gravity.TOP, 0, 0);
            }
        }

        //将当前pop存储
        prevPop = popupWindow;

    }

    /**
     * 手动展示弹窗
     *
     * @param popIndex 弹窗索引
     */
    public void showPop(int popIndex) {
        if (popIndex < popupWindows.size()) {
            showPop(popupWindows.get(popIndex));
        }
    }

    /**
     * 隐藏弹窗
     */
    public void hidePop() {
        topEmptyPop.dismiss();
        dismissShowingPop();
        //隐藏遮罩
        if (mask != null) {
            mask.setVisibility(GONE);
        }
    }

    /**
     * 关闭正正在显示的弹窗
     */
    private void dismissShowingPop() {
        if (hasShowingPop()) {
            prevPop.dismiss();
        }
    }

    /**
     * 返回是否存在正在显示的弹窗
     *
     * @return 存在正在显示的弹窗
     */
    public boolean hasShowingPop() {
        return prevPop != null && prevPop.isShowing();
    }

    /**
     * 计算弹窗正确展示应具有的高度
     *
     * @return popHeight
     */
    private int getPopupWindowHeight() {
        final int[] location = new int[2];
        getLocationOnScreen(location);

//        if(navigationBarHeight == PRI_FLAG_UNKNOWN_HEIGHT){
//            if(BarUtils.isNavBarVisible((Activity) mContext))
//                navigationBarHeight = BarUtils.getNavBarHeight();
//            else
//                navigationBarHeight = 0;
//        }

//        return ScreenUtils.getScreenHeight() - location[1] - getHeight() - navigationBarHeight;
        return getScreenContentHeight() - location[1] - getHeight();
    }

    /**
     * 获取空弹窗应具有的的高度
     *
     * @return view到状态栏的距离
     */
    private int getEmptyPopHeight() {
        final int[] location = new int[2];
        getLocationOnScreen(location);

        return location[1] - BarUtils.getStatusBarHeight();
    }

    /**
     * 设置选项点击监听器
     *
     * @param listener 监听器
     * @return this
     */
    public ScreenBar setOnOptionClickedListener(OnOptionClickedListener listener) {
        this.onOptionClickedListener = listener;
        return this;
    }

    /**
     * option点击事件监听器
     */
    private OnOptionClickedListener onOptionClickedListener;

    /**
     * option点击监听接口
     */
    public interface OnOptionClickedListener {

        /**
         * option被点击时被调用的方法
         *
         * @param position   option 位置
         * @param optionDesc option文字描述 即 {@link #setOptions(String[])} 中设置的参数
         */
        void onClick(int position, String optionDesc);
    }

    /**
     * 设置弹窗隐藏监听器
     *
     * @param dismissListener 监听器
     * @return this
     */
    public ScreenBar setOnPopupWindowDismissListener(OnPopupWindowDismissListener dismissListener) {
        this.popDismissListener = dismissListener;
        return this;
    }

    /**
     * 弹窗隐藏监听器
     */
    private OnPopupWindowDismissListener popDismissListener;

    /**
     * 弹窗隐藏监听接口
     */
    public interface OnPopupWindowDismissListener {
        void onDismiss(int popIndex);
    }

    /**
     * 设置选项被点击时是否弹出弹窗
     *
     * @param showPopWhenOptionIsClicked 选项被点击时是否弹出弹窗
     */
    public ScreenBar setShowPopWhenOptionIsClicked(boolean showPopWhenOptionIsClicked) {
        this.showPopWhenOptionIsClicked = showPopWhenOptionIsClicked;
        return this;
    }

    /**
     * For develop
     *
     * @param log The text of log
     */
    private void printLog(String log) {
        Log.d("ZZF", log);
    }


    /**
     * 获取屏幕显示内容像素高度（不包括虚拟按键的高度）
     *
     * @return 屏幕显示内容像素高度
     * <p>
     * 通过此方法获取实际内容高度的正确性已通过
     * 【不可隐藏虚拟导航栏的设备】以及【没有虚拟导航栏的设备】的测试
     */
    private int getScreenContentHeight() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }


    /**
     * 筛选项
     */
    private class ScreenOption {

        private boolean isChecked;
        private TextView optionTv;
        private ImageView optionIv;
        private int checkedIconResId = R.drawable.split_yellow;
        private int uncheckedIconResId = R.drawable.split_gray;

        /**
         * 一些颜色值
         */
        private final int COLOR_OPTION_CHECKED = Color.parseColor("#FDD100");
        private final int COLOR_OPTION_NORMAL = Color.parseColor("#82817F");

        public ScreenOption(TextView optionTv, ImageView optionIv) {
            this.optionTv = optionTv;
            this.optionIv = optionIv;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;

            if (isChecked) {
                setCheckedStyle();
            } else {
                setUncheckedStyle();
            }
        }

        private void setCheckedStyle() {
            this.optionTv.setTextColor(COLOR_OPTION_CHECKED);
            this.optionIv.setImageResource(checkedIconResId);
        }

        private void setUncheckedStyle() {
            this.optionTv.setTextColor(COLOR_OPTION_NORMAL);
            this.optionIv.setImageResource(uncheckedIconResId);
        }

        public TextView getOptionTv() {
            return optionTv;
        }

        public ImageView getOptionIv() {
            return optionIv;
        }

        public void setText(String text) {
            this.optionTv.setText(text);
        }

        //设置选中与未选中状态的图标
        public void setOptionIcon(int checkedIconResId, int uncheckedIconResId) {
            this.checkedIconResId = checkedIconResId;
            this.uncheckedIconResId = uncheckedIconResId;

            //重设图标
            setChecked(isChecked);
        }
    }

}
