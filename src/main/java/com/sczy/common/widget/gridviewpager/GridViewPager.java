package com.sczy.common.widget.gridviewpager;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sczy.common.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SC16004984 on 2017/1/21.
 */
public class GridViewPager extends RelativeLayout implements PagingScrollHelper.onPageChangeListener {
    private boolean hasCustomOval = false;
    private LayoutInflater inflater;
    private Context mContext;
    private RecyclerPageView mPager;
    private LinearLayout mLlDot;
    private GridItemClickListener gridItemClickListener;
    private GridItemLongClickListener gridItemLongClickListener;
    PagingScrollHelper scrollHelper = new PagingScrollHelper();
    private BaseQuickAdapter adapter;

    private List items = new ArrayList();

    private float mHeight;
    private int row = 2;  // 列
    private int col = 4;  // 行
    /**
     * 总的页数 计算得出
     */
    private int pageCount =0;

    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;
    private ViewConvert convert;

    @Override
    public void onPageChange(int index) {
        if (pageCount== 0){
            return;
        }
        // 取消圆点选中
        mLlDot.getChildAt(curIndex)
                .findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_normal);
        // 圆点选中
        mLlDot.getChildAt(index)
                .findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        curIndex = index;
    }


    public interface GridItemClickListener<T> {
        void click(T t);
    }


    public interface GridItemLongClickListener<T> {
        void click(T t);
    }

    public interface ViewConvert<T> {
        void convert(BaseViewHolder holder, T t);
    }


    public GridViewPager(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public GridViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public GridViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mHeight = SizeUtils.dp2px(180);
        initView();
    }

    private void initView() {
        inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.page_recycle_view, this);
        mPager = (RecyclerPageView) view.findViewById(R.id.viewpager);
        mLlDot = (LinearLayout) view.findViewById(R.id.ll_dot);

        HorizontalPageLayoutManager pageLayoutManager = new HorizontalPageLayoutManager(row, col);
        mPager.setLayoutManager(pageLayoutManager);
        PagingScrollHelper scrollHelper = new PagingScrollHelper();
        scrollHelper.setUpRecycleView(mPager);
        scrollHelper.setOnPageChangeListener(this);
    }

    public void notifyViewChange() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * necessary 必须作为最后一步
     *
     * @param list
     * @return
     */
    public <T> GridViewPager init(@LayoutRes int resId, final List<T> list) {
        this.items.addAll(list);
        adapter = new BaseQuickAdapter<T, BaseViewHolder>(resId, this.items) {

            @Override
            protected void convert(BaseViewHolder helper, T item) {
                if (convert != null) {
                    convert.convert(helper, item);
                }
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (gridItemClickListener != null) {
                    gridItemClickListener.click(items.get(position));
                }
            }
        });
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                if (gridItemLongClickListener == null) {
                    return false;
                }
                gridItemLongClickListener.click(list.get(position));
                return true;
            }
        });
        mPager.setAdapter(adapter);
        setOvalLayout();
        return this;
    }

    public RecyclerPageView getPageRecyclerView(){
        return mPager;
    }

    public <T> void refresh(List<T> list) {
        this.items.clear();
        this.items.addAll(list);
        adapter.notifyDataSetChanged();
        setOvalLayout();
    }

    /**
     * optional 设置自定义圆点
     */
    public void setOvalLayout(View view, PagingScrollHelper.onPageChangeListener listener) {
        hasCustomOval = true;
        mLlDot.removeAllViews();
        mLlDot.addView(view);
        scrollHelper.setOnPageChangeListener(this);
    }

    /**
     * 设置圆点
     */
    private void setOvalLayout() {
        final int pageSize = col * row;
        pageCount = (int) Math.ceil(items.size() * 1.0 / pageSize);

        if (pageCount <= 1) {
            mLlDot.setVisibility(GONE);
            return;
        }

        mLlDot.setVisibility(VISIBLE);
        mLlDot.removeAllViews();
        for (int i = 0; i < pageCount; i++) {
            mLlDot.addView(inflater.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        mLlDot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        scrollHelper.setOnPageChangeListener(this);
    }

    /**
     * optional 设置单元点击事件
     *
     * @param listener
     * @return
     */
    public GridViewPager setGridItemClickListener(GridItemClickListener listener) {
        gridItemClickListener = listener;
        return this;
    }

    /**
     * optional 设置单元长按事件
     *
     * @param listener
     * @return
     */
    public GridViewPager setGridItemLongClickListener(GridItemLongClickListener listener) {
        gridItemLongClickListener = listener;
        return this;
    }

    public GridViewPager setViewConvert(ViewConvert convert) {
        this.convert = convert;
        return this;
    }

    public int getPageCount() {
        return pageCount;
    }

    public GridViewPager setRowNum(int row) {
        this.row = row;
        return this;
    }

    public GridViewPager setColNum(int col) {
        this.col = col;
        return this;
    }

    public int getCurIndex() {
        return curIndex;
    }
}
