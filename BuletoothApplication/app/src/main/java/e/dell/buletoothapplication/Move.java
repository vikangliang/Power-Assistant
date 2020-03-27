package e.dell.buletoothapplication;

import android.graphics.BitmapFactory;

public class Move {
    private float x1, y1;//大圆坐标
    private float x2, y2;//小圆坐标
    //private final float r1,r2;//大圆半径和小圆半径
    public float angle;//x1x2指向x2y2角度
    public boolean isnotPress = false, isnotPress1 = false;//是否按下按钮
    public boolean in = false;//小圆是否在大圆里
    public boolean move = false;//小圆是否有移动

    public Move(float x1, float y1) {
        this.x1 = x1;
        this.y1 = y1;

    }


    public boolean in(float x, float y) {//防止小圆被拖太远
        double r = Math.sqrt((x - 400) * (x - 400) + (y - 400) * (y - 400));//两点距离公式
        if (r < 250 * 0.7f)
            return true;
        else
            return false;
    }


    public void down(float x, float y) {//遥杆按下后的操作
        isnotPress = true;
    }

    public void down1(float x, float y) {//遥杆按下后的操作
        isnotPress1 = true;
    }

    public void up(float x, float y) {
        isnotPress = false;
    }

    public void up1(float x, float y) {
        isnotPress1 = false;
    }

}
