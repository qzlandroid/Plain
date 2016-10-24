package com.example.jit.plain.UI;


import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * 存储各种Animation的类
 */
public class AnimManager {
    /**
     * 得到水平移动动画的Cursor类
     *
     * 详情等页面的选择中
     *
     * @param start
     *            起始位置
     * @param end
     *            终止位置
     * @param screenW
     *            屏幕宽度
     * @param sumPiece
     *            分的区域总数
     *
     * @return TranslateAnimation
     */
    public static Animation getCursor(Context context, int start, int end,
                                      int screenW, int sumPiece) {
        int bmpW = screenW / sumPiece;
        Animation animation = new TranslateAnimation(start * bmpW, end * bmpW,
                0, 0);
        animation.setFillAfter(true);
        animation.setDuration(300);
        return animation;
    }
}
