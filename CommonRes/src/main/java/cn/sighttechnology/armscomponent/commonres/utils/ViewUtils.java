package cn.sighttechnology.armscomponent.commonres.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridLayout;

public class ViewUtils {
    public static void setRecycleViewGridAdapter( int num, int rientation,
                                                 Context    context,
                                                  RecyclerView...   recyclerView){
        if (recyclerView!=null && recyclerView.length   >   0){
            for (int i =0;i<recyclerView.length;i++){
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, num);
                gridLayoutManager.setReverseLayout(false);
                gridLayoutManager.setOrientation(rientation);
                recyclerView[i].setLayoutManager(gridLayoutManager);
            }
        }


    }
}
