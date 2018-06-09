package com.example.collapsingconstraint;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

public class CollapsibleConstraintLayout extends ConstraintLayout implements AppBarLayout.OnOffsetChangedListener {
    private Context context;
    private int lastPosition = 0;
    boolean toolbarOpen = true;
    private ConstraintSet openConstraintSet = new ConstraintSet();
    private ConstraintSet closeConstraintSet = new ConstraintSet();
    private float threshold = 0.35f;
    private TextView textView;
    private ImageView imageView;
    private AnimatorHelper textAnimator;
    private AnimatorHelper imageAnimator;

    public CollapsibleConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CollapsibleConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        if (lastPosition == verticalOffset) {
            return;
        }

        lastPosition = -verticalOffset;
        float progress = Math.abs(verticalOffset / getResources().getDimension(R.dimen.app_bar_height));
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) getLayoutParams();
        layoutParams.topMargin = -verticalOffset;
        setLayoutParams(layoutParams);

        if (toolbarOpen && progress > threshold) {
            closeConstraintSet.applyTo(this);
            toolbarOpen = false;
        } else if (!toolbarOpen && progress < threshold) {
            openConstraintSet.applyTo(this);
            toolbarOpen = true;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.getParent() instanceof AppBarLayout) {
            openConstraintSet.clone(context, R.layout.open);
            closeConstraintSet.clone(context, R.layout.closed);
            AppBarLayout appBarLayout = (AppBarLayout) this.getParent();
            appBarLayout.addOnOffsetChangedListener(this);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (textView != null && textAnimator == null) {
            textAnimator = new AnimatorHelper(textView);
        }
        if (imageView != null && imageAnimator == null) {
            imageAnimator = new AnimatorHelper(imageView);
        }
        textAnimator.animate();
        imageAnimator.animate();

    }

    public void setTitleAndIcon(TextView textView, ImageView imageView) {
        this.textView = textView;
        this.imageView = imageView;
    }
}
