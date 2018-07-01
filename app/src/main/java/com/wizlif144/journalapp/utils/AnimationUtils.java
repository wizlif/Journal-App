package com.wizlif144.journalapp.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class AnimationUtils {

    public interface C2447a {
        void mo1940a();
    }

    static class C27482 extends AnimatorListenerAdapter {
        C27482() {
        }

        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
        }
    }

    static class C27526 extends AnimatorListenerAdapter {
        C27526() {
        }

        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
        }
    }

    public static void m12266a(View view) {
        view.startAnimation(AnimationUtils.m12276h(view));
    }

    public static void m12267a(View view, final C2447a c2447a) {
        Animation h = AnimationUtils.m12276h(view);
        h.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                c2447a.mo1940a();
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        view.startAnimation(h);
    }

    public static boolean m12268a(View view, boolean z) {
        view.animate().setDuration(200).setListener(new C27482()).rotation(z ? 135.0f : 0.0f);
        return z;
    }

    public static void m12269b(final View view) {
        final int measuredHeight = view.getMeasuredHeight();
        Animation c27504 = new Animation() {
            protected void applyTransformation(float f, Transformation transformation) {
                if (f == 1.0f) {
                    view.setVisibility(View.GONE);
                    return;
                }
                view.getLayoutParams().height = measuredHeight - ((int) (((float) measuredHeight) * f));
                view.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        c27504.setDuration((long) ((int) (((float) measuredHeight) / view.getContext().getResources().getDisplayMetrics().density)));
        view.startAnimation(c27504);
    }

    public static void m12270b(View view, final C2447a c2447a) {
        view.setAlpha(1.0f);
        view.animate().setDuration(500).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                if (c2447a != null) {
                    c2447a.mo1940a();
                }
                super.onAnimationEnd(animator);
            }
        }).alpha(0.0f);
    }

    public static void m12271c(View view) {
        AnimationUtils.m12270b(view, null);
    }

    public static void m12272d(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0.0f);
        view.setTranslationY((float) view.getHeight());
        view.animate().setDuration(200).translationY(0.0f).setListener(new C27526()).alpha(1.0f).start();
    }

    public static void m12273e(View view) {
        view.setVisibility(View.GONE);
        view.setTranslationY((float) view.getHeight());
        view.setAlpha(0.0f);
    }

    public static void m12274f(final View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(1.0f);
        view.setTranslationY(0.0f);
        view.animate().setDuration(200).translationY((float) view.getHeight()).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
                super.onAnimationEnd(animator);
            }
        }).alpha(0.0f).start();
    }

    public static void m12275g(View view) {
        view.setAlpha(0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        Animator ofFloat = ObjectAnimator.ofFloat(view, "alpha", new float[]{0.0f, 0.5f, 1.0f});
        ObjectAnimator.ofFloat(view, "alpha", new float[]{0.0f}).start();
        ofFloat.setDuration(500);
        animatorSet.play(ofFloat);
        animatorSet.start();
    }

    private static Animation m12276h(final View view) {
        view.measure(-1, -2);
        final int measuredHeight = view.getMeasuredHeight();
        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);///////////////////////////
        Animation c27493 = new Animation() {
            protected void applyTransformation(float f, Transformation transformation) {
                view.getLayoutParams().height = f == 1.0f ? -2 : (int) (((float) measuredHeight) * f);
                view.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        c27493.setDuration((long) ((int) (((float) measuredHeight) / view.getContext().getResources().getDisplayMetrics().density)));
        view.startAnimation(c27493);
        return c27493;
    }
}

