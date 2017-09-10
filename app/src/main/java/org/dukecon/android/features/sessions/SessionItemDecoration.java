package org.dukecon.android.features.sessions;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SessionItemDecoration extends RecyclerView.ItemDecoration {
    private final Drawable divider;
    private final Context context;


    public SessionItemDecoration(@NonNull Context context) {
        super();
        this.context = context;
        TypedArray a = this.context.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        this.divider = a.getDrawable(0);
        a.recycle();
    }

    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView
            .State state) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (view != null) {
                RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);
                if ((holder instanceof SessionAdapter.ViewHolder) && holder.getAdapterPosition() != 0) {
                    if (((SessionAdapter.ViewHolder) holder).getTimeslot().getVisibility() == View.VISIBLE) {
                        int left = 0;
                        int top = holder.itemView.getTop();
                        int right = parent.getWidth();
                        int bottom = top + this.divider.getIntrinsicHeight();
                        this.divider.setBounds(left, top, right, bottom);
                        this.divider.draw(c);
                    }

                }
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);
        if (holder instanceof SessionAdapter.ViewHolder && ((SessionAdapter.ViewHolder) holder)
                .getTimeslot().getVisibility() == View.VISIBLE && ((SessionAdapter.ViewHolder) holder)
                .getAdapterPosition() != 0) {
            outRect.set(0, this.divider.getIntrinsicHeight(), 0, 0);
        }

    }
}

