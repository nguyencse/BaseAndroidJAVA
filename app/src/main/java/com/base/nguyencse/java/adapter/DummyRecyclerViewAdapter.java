package com.base.nguyencse.java.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.base.nguyencse.java.R;
import com.base.nguyencse.java.database.entity.DummyEntity;
import com.base.nguyencse.java.ui.base.BaseAdapter;
import com.base.nguyencse.java.ui.base.BaseViewHolder;

public class DummyRecyclerViewAdapter extends BaseAdapter<DummyEntity> {

    public interface DummyListListener {
        void onClickItem(DummyEntity entity);

        void onLoadingMore();
    }

    private static final int VISIBLE_THRESHOLD = 2;

    private final DummyListListener mListener;

    public DummyRecyclerViewAdapter(RecyclerView rcv, DummyListListener listener) {
        mListener = listener;
        if (rcv != null) {
            final LinearLayoutManager lnlManager = (LinearLayoutManager) rcv.getLayoutManager();
            rcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0 && lnlManager != null) {
                        int totalItemCount = lnlManager.getItemCount();
                        int lastVisibleItem = lnlManager.findLastVisibleItemPosition();
                        if (totalItemCount > 0 && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                            listener.onLoadingMore();
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public BaseViewHolder<DummyEntity> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DummyViewHolder(parent);
    }

    public void hideLoadingMore() {
        DummyEntity lastItem = getItemAtPosition(getItemCount() - 1);
        if (lastItem != null) {
            removeItem(getItemCount() - 1);
        }
    }

    class DummyViewHolder extends BaseViewHolder<DummyEntity> {

        @BindView(R.id.txt_title)
        TextView txtTitle;

        DummyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_dummy);
        }

        @OnClick({R.id.csl_item_dummy, R.id.txt_title})
        public void onViewClicked(View view) {
            DummyEntity item = getItemAtPosition(getAdapterPosition());
            switch (view.getId()) {
                case R.id.csl_item_dummy:
                    if (item != null) {
                        mListener.onClickItem(item);
                    }
                    break;
            }
        }

        @Override
        public void onBind(DummyEntity entity) {
            txtTitle.setText(entity.getTitle());
        }
    }
}
