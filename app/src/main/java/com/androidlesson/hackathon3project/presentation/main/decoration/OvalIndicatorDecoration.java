package com.androidlesson.hackathon3project.presentation.main.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class OvalIndicatorDecoration extends RecyclerView.ItemDecoration {
    private final int colorInactive = 0x20FFFFFF;
    private final int colorActive = 0xFFFFFFFF;

    private final Paint paint = new Paint();
    private final int indicatorHeight = 8;
    private final int indicatorWidth = 8;
    private final int activeIndicatorWidth = 32;
    private final int indicatorSpacing = 8;

    private final int indicatorAreaWidth = 64;
    private final int bottomPadding = 84;
    private final int leftPadding = 44;

    public OvalIndicatorDecoration() {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        ViewPager2 viewPager = (ViewPager2) parent.getParent();
        if (viewPager == null || viewPager.getAdapter() == null) return;

        int itemCount = viewPager.getAdapter().getItemCount();
        int activePosition = viewPager.getCurrentItem();

        int indicatorAreaWidthPx = (int) (indicatorAreaWidth * parent.getResources().getDisplayMetrics().density);
        int indicatorHeightPx = (int) (indicatorHeight * parent.getResources().getDisplayMetrics().density);
        int indicatorWidthPx = (int) (indicatorWidth * parent.getResources().getDisplayMetrics().density);
        int activeIndicatorWidthPx = (int) (activeIndicatorWidth * parent.getResources().getDisplayMetrics().density);
        int indicatorSpacingPx = (int) (indicatorSpacing * parent.getResources().getDisplayMetrics().density);
        int leftPaddingPx = (int) (leftPadding * parent.getResources().getDisplayMetrics().density);
        int bottomPaddingPx = (int) (bottomPadding * parent.getResources().getDisplayMetrics().density);

        int totalWidth = (indicatorWidthPx * (itemCount - 1)) + activeIndicatorWidthPx + (indicatorSpacingPx * (itemCount - 1));
        int startX = leftPaddingPx;
        int yPos = parent.getHeight() - bottomPaddingPx - indicatorHeightPx;

        for (int i = 0; i < itemCount; i++) {
            if (i == activePosition) {
                paint.setColor(colorActive);
                float cornerRadius = indicatorHeightPx / 2f;
                canvas.drawRoundRect(new RectF(startX, yPos, startX + activeIndicatorWidthPx, yPos + indicatorHeightPx),
                        cornerRadius, cornerRadius, paint);
                startX += activeIndicatorWidthPx + indicatorSpacingPx;
            } else {
                paint.setColor(colorInactive);
                canvas.drawCircle(startX + (indicatorWidthPx / 2), yPos + (indicatorHeightPx / 2),
                        indicatorWidthPx / 2, paint);
                startX += indicatorWidthPx + indicatorSpacingPx;
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = indicatorHeight + 50;
    }
}
