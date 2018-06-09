package com.example.collapsingconstraint;

import android.animation.ObjectAnimator;
import android.view.View;

public class AnimatorHelper {

    private float initial = 0;
    private View view;

    public AnimatorHelper(View view) {
        this.view = view;
        initial = view.getLeft();

    }

    public void animate() {
        if (initial != view.getLeft()) {
            float val = initial - view.getLeft();
            ObjectAnimator objectAnimator = ObjectAnimator.
                    ofFloat(view, "TranslationX", val, 0f);
            objectAnimator.setDuration(400);
            objectAnimator.start();
            initial = view.getLeft();

        }
    }


}
