package cn.sighttechnology.armscomponent.commonres.bean;

import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Description:
 * Data：2018/8/4-9:50
 * Author: yangjichao
 */
public class FilterTabBean implements MultiItemEntity {

    private ChangeContentView changeContentView;

    public ChangeContentView getChangeContentView() {
        return changeContentView;
    }

    public void setChangeContentView(ChangeContentView changeContentView) {
        this.changeContentView = changeContentView;
    }

    int itemtype = 0;
    int states = 0;//状态标志位 0 普通 未选中 1 popwindow弹出状态 2 popwindow的选中状态

    boolean isSelect = false; //是否选中 为没有图标的做判断

    private String defaultValue;//默认展示文字
    private String selectValue;//选中时展示的文字
    private View popView;//弹窗的视图

    public FilterTabBean(String defaultValue, String selectValue, int itemtype) {
        this.defaultValue = defaultValue;
        this.selectValue = selectValue;
        this.itemtype = itemtype;
    }

    @Override
    public int getItemType() {
        return itemtype;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getSelectValue() {
        return selectValue;
    }

    public void setSelectValue(String selectValue) {
        isSelect = true;
        this.selectValue = selectValue;
    }

    public View getPopView() {
        return popView;
    }

    public void setPopView(View popView) {
        this.popView = popView;
    }

    public void clearSelect() {
        this.isSelect = false;
        this.states = 0;
        this.selectValue = "";
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }


    public interface ChangeContentView {
        //在弹窗展示之前做的操作
        void beforeContentViewShow();
    }
}
