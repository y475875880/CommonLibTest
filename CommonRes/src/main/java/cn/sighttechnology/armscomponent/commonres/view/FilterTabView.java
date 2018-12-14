package cn.sighttechnology.armscomponent.commonres.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.ButterKnife;
import cn.sighttechnology.armscomponent.commonres.R;
import cn.sighttechnology.armscomponent.commonres.bean.FilterTabBean;

/**
 * Description:
 * Data：2018/8/3-15:39
 * Author: yangjichao
 */
public class FilterTabView extends LinearLayout {

    public static final int TYPE1 = 1;
    public static final int TYPE2 = 2;


    public Context context;
    //顶部的筛选条件栏
//    @BindView(R.id.rv_top)
    public RecyclerView recyclerView;
    //    @BindView(R.id.ll_pop)
    public LinearLayout linearLayoutPop;
    //    @BindView(R.id.ll_content)
    public LinearLayout linearLayoutContent;

    private View lastView;
    private int lastPositon;

    private List<FilterTabBean> dataList;

    private int textSize = 20;

    public FilterTabView(Context context) {
        this(context, null);
    }

    public FilterTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count >= 0) {
            View view = getChildAt(count - 1);
            removeView(view);
            linearLayoutContent.addView(view);
//            for (int i = 0; i < count ; i ++){
//                View view = getChildAt(i);
//
//                Log.d("className",view.getAccessibilityClassName()+"");
//            }

        }

    }

    void init(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.public_filter_tab, this);
        ButterKnife.bind(this, view);

        recyclerView = view.findViewById(R.id.rv_top);
        linearLayoutPop = view.findViewById(R.id.ll_pop);
        linearLayoutContent = view.findViewById(R.id.ll_content);

        linearLayoutPop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
            }
        });
    }

    public void dismissPop() {
        linearLayoutPop.setVisibility(GONE);
        if (dataList != null) {
            FilterTabBean filterTabBean = dataList.get(lastPositon);
            if (filterTabBean.isSelect()) {
                filterTabBean.setStates(2);
            } else {
                filterTabBean.setStates(0);
            }
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    public void setDatas(List<FilterTabBean> list) {
        dataList = list;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,
                list.size());
        gridLayoutManager.setOrientation(VERTICAL);
//        LinearLayoutManager linearLayoutManager =   new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        FilterAdapter filterAdapter = new FilterAdapter(list);
        recyclerView.setAdapter(filterAdapter);

    }

    public void viewNotifyDataSetChanged() {
        dismissPop();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void viewNotifyDataSetChangedNotDismiss() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public class FilterAdapter extends BaseMultiItemQuickAdapter<FilterTabBean, BaseViewHolder>
            implements BaseQuickAdapter.OnItemClickListener {


        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public FilterAdapter(List<FilterTabBean> data) {
            super(data);
            addItemType(TYPE1, R.layout.public_filter_tab_type1);
            addItemType(TYPE2, R.layout.public_filter_tab_type2);
            setOnItemClickListener(this);
        }

        @Override
        protected void convert(BaseViewHolder helper, FilterTabBean item) {
            switch (helper.getItemViewType()) {
                case TYPE1:
                    handleTypeView1(helper, item);
                    break;
                case TYPE2:
                    handleTypeView2(helper, item);
                    break;
            }
        }

        void handleTypeView1(BaseViewHolder helper, FilterTabBean item) {
            String selectStr = item.getSelectValue();
            if (selectStr != null && selectStr.length() > 4) {
                selectStr = selectStr.substring(0, 4) + "...";
            }

            switch (item.getStates()) {
                //默认展示状态
                case 0: {
                    helper.setImageResource(R.id.iv_filter, R.drawable.public_split_gray);
                    helper.setText(R.id.tv_filter, item.getDefaultValue());
                    helper.setTextColor(R.id.tv_filter,
                            ContextCompat.getColor(context, R.color.public_gray_7f)
                    );
                    break;
                }
                //打开弹窗时
                case 1: {
                    helper.setImageResource(R.id.iv_filter, R.drawable.public_split_yellow);
                    if (item.getSelectValue() != null && item.getSelectValue().length() > 0) {
                        helper.setText(R.id.tv_filter, selectStr);
                    } else {
                        helper.setText(R.id.tv_filter, item.getDefaultValue());
                    }

                    break;
                }
                //选中数据时
                case 2: {
                    helper.setImageResource(R.id.iv_filter, R.drawable.public_split_yellow);
                    helper.setText(R.id.tv_filter, selectStr);
                    helper.setTextColor(R.id.tv_filter,
                            ContextCompat.getColor(context, R.color.public_yellow_0a)
                    );
                    break;
                }

            }
//            helper.addOnClickListener(R.id.ll_filter);

        }

        void handleTypeView2(BaseViewHolder helper, FilterTabBean item) {
            switch (item.getStates()) {
                //默认展示状态
                case 0: {
                    helper.setImageResource(R.id.iv_filter, R.drawable.public_filter_sort_gray);
                    break;
                }
                //打开弹窗时
                case 1: {
                    helper.setImageResource(R.id.iv_filter, R.drawable.public_filter_sort_yellow);
                    break;
                }
                //选中数据时
                case 2: {
                    helper.setImageResource(R.id.iv_filter, R.drawable.public_filter_sort_yellow);
                    break;
                }

            }
        }


        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//            if (view.getId() == R.id.ll_filter){

            FilterTabBean.ChangeContentView changeContentView =
                    this.getData().get(position).getChangeContentView();
            if (changeContentView != null) {
                changeContentView.beforeContentViewShow();
            }
            if (this.getData().get(position).getStates() == 0 ||
                    this.getData().get(position).getStates() == 2) {
                this.getData().get(position).setStates(1);
                final View popview = this.getData().get(position).getPopView();
                if (popview != null) {
                    if (lastView != null && lastView == popview) {

                    } else {
                        lastView = popview;
                        linearLayoutPop.removeAllViews();
                        linearLayoutPop.addView(popview);
                        //重置上一个状态
                        //这里有 0 和 2 两个状态备选 通过展示内容进行判断
                        if (this.getData().get(lastPositon).getSelectValue() == null
                                || this.getData().get(lastPositon).getSelectValue().length() == 0) {
                            this.getData().get(lastPositon).setStates(0);
                        } else {
                            this.getData().get(lastPositon).setStates(2);
                        }
                        lastPositon = position;
                    }

                    popview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            popview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            int panelHeight = popview.getHeight();
                            ObjectAnimator.ofFloat(popview, "translationY", -panelHeight, 0).setDuration(200).start();
                        }
                    });
                }
                linearLayoutPop.setVisibility(VISIBLE);

            } else if (this.getData().get(position).getStates() == 1) {
                linearLayoutPop.setVisibility(GONE);
                this.getData().get(position).setStates(0);
            }

            notifyDataSetChanged();
        }
    }

}
