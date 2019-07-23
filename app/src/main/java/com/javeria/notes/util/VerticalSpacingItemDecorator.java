package com.javeria.notes.util;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpacingItemDecorator extends RecyclerView.ItemDecoration {

    private final int mVerticalSpaceheight;

    public VerticalSpacingItemDecorator(int verticalSpaceheight){
        this.mVerticalSpaceheight = verticalSpaceheight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = mVerticalSpaceheight;
    }
}
