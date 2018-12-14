package cn.common.test.mvp.model;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.File;
import java.util.List;

import cn.common.test.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class DelEditFilterAdapter extends BaseQuickAdapter<File, BaseViewHolder> {

        public DelEditFilterAdapter(int layoutResId, @Nullable List<File> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, File item) {

        }
    }
}
