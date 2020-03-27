package e.dell.buletoothapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.OutputStream;

public class My extends View {
    public float x1 = 400, y1 = 400, x2 = 400, y2 = 1300;
    public static int a1, b1, a2, b2,c=0;

    //public static int a, b, c, d;
    //public byte pack[] = {(byte) 0xAA, (byte) 0xA5, (byte) a, (byte) b, (byte) c, (byte) d,0x3C, (byte) 0xC3};
    //public OutputStream os=;

    public boolean in = true;
    Move m = new Move(x1, y1);

    //PointF lastPoint = new PointF(0, 0);




    public My(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {



                float X = event.getX();
                float Y = event.getY();




                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    if ((Math.sqrt((X - 400) * (X - 400) + (Y - 400) * (Y - 400))) <= 550) {
                        m.down(x1, y1);



                        if (event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
                            if ((Math.sqrt((X - 400) * (X - 400) + (Y - 1300) * (Y - 1300))) <= 550) {
                                m.down1(x1, y1);




                            }
                        }

                    } else if ((Math.sqrt((X - 400) * (X - 400) + (Y - 1300) * (Y - 1300))) <= 500) {
                        m.down1(x2, y2);



                        if (event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
                            if ((Math.sqrt((X - 400) * (X - 400) + (Y - 400) * (Y - 400))) <= 550) {
                                m.down(x1, y1);



                            }
                        }
                    }
                }
                if (event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
                    if ((Math.sqrt((X - 400) * (X - 400) + (Y - 400) * (Y - 400))) <= 550) {
                        m.down(x1, y1);

                    } else if ((Math.sqrt((X - 400) * (X - 400) + (Y - 1300) * (Y - 1300))) <= 500) {
                        m.down1(x2, y2);

                    }
                }

                //move();
                if (event.getAction() == MotionEvent.ACTION_POINTER_UP) {
                    if ((Math.sqrt((X - 400) * (X - 400) + (Y - 400) * (Y - 400))) <= 550) {
                        m.up(x1, y1);
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            m.up1(x2, y2);
                        }

                    } else if ((Math.sqrt((X - 400) * (X - 400) + (Y - 1300) * (Y - 1300))) <= 500) {
                        m.up1(x2, y2);
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            m.up(x1, y1);
                        }
                    }

                    /**m.up(x1,y1);
                     //if(event.getAction()==MotionEvent.ACTION_POINTER_UP){
                     //  m.up1(x2,y2);
                     //lastPoint.set(400,400);
                     }**/
                }

                return false;
            }
        });

    }

    private static int coord(float x) {
        int a;
        if (x > 400)
            a = (int) (Math.abs(x - 400) / 1.75);
        else
            a = -(int) (Math.abs(x - 400) / 1.75);
        return a;
    }


    private static int coord1(float x) {
        int a;
        if (x > 1300)
            a = (int) (Math.abs(x - 1300) / 1.75);
        else
            a = -(int) (Math.abs(x - 1300) / 1.75);
        return a;
    }


    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawColor(Color.WHITE);
        canvas.drawCircle(400, 400, 250, paint);


        paint.setColor(Color.RED);
        canvas.drawCircle(x1, y1, 100, paint);


        paint.setColor(Color.BLACK);
        canvas.drawCircle(400, 1300, 250, paint);


        paint.setColor(Color.RED);
        canvas.drawCircle(x2, y2, 100, paint);


    }

    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        float X = event.getX(), Y = event.getY();
        //double d=Math.sqrt((x1-400)*(x1-400)+(y1-400)*(y1-400));
        //
        if ((Math.sqrt((X - 400) * (X - 400) + (Y - 400) * (Y - 400))) <= 350) {
            x1 = event.getX();
            y1 = event.getY();
            a1=coord(x1);
            b1=coord(y1);


            double r = Math.sqrt((x1 - 400) * (x1 - 400) + (y1 - 400) * (y1 - 400));
            double angle = getAngle(x1, y1);
            if (r > 250 * 0.7f) {
                x1 = (float) (400 + Math.sin(angle) * 250 * 0.7f);
                y1 = (float) (400 + Math.cos(angle) * 250 * 0.7f);
                a1=coord(x1);
                b1=coord(y1);
            }
            if (m.isnotPress == false) {
                x1 = 400;
                y1 = 400;
                a1=coord(x1);
                b1=coord(y1);
            }
        } else {
            x1 = 400;
            y1 = 400;
            a1=coord(x1);
            b1=coord(y1);
        }

        if ((Math.sqrt((X - 400) * (X - 400) + (Y - 1300) * (Y - 1300))) <= 350) {
            x2 = event.getX();
            y2 = event.getY();
            a2=coord1(x2);
            b2=coord1(y2);

            double r1 = Math.sqrt((x2 - 400) * (x2 - 400) + (y2 - 1300) * (y2 - 1300));
            double angle = getAngle1(x2, y2);
            if (r1 > 250 * 0.7f) {
                x2 = (float) (400 + Math.sin(angle) * 250 * 0.7f);
                y2 = (float) (1300 + Math.cos(angle) * 250 * 0.7f);
                a2=coord1(x2);
                b2=coord1(y2);
            }
            if (m.isnotPress1 == false) {
                x2 = 400;
                y2 = 1300;
                a2=coord1(x2);
                b2=coord1(y2);
            }
        } else {
            x2 = 400;
            y2 = 1300;
            a2=coord1(x2);
            b2=coord1(y2);
        }

        if (pointerCount == 2) {
            float X1 = event.getX(1), Y1 = event.getY(1);
            if ((Math.sqrt((X1 - 400) * (X1 - 400) + (Y1 - 400) * (Y1 - 400))) <= 350) {
                x1 = event.getX(1);
                y1 = event.getY(1);
                a1=coord(x1);
                b1=coord(y1);

                double r = Math.sqrt((x1 - 400) * (x1 - 400) + (y1 - 400) * (y1 - 400));
                double angle = getAngle(x1, y1);
                if (r > 250 * 0.7f) {
                    x1 = (float) (400 + Math.sin(angle) * 250 * 0.7f);
                    y1 = (float) (400 + Math.cos(angle) * 250 * 0.7f);
                    a1=coord(x1);
                    b1=coord(y1);
                }
                if (m.isnotPress == false) {
                    x1 = 400;
                    y1 = 400;
                    a1=coord(x1);
                    b1=coord(y1);
                }
            }
            /**else{
             x1=400;
             y1=400;
             }**/

            if ((Math.sqrt((X1 - 400) * (X1 - 400) + (Y1 - 1300) * (Y1 - 1300))) <= 350) {
                x2 = event.getX(1);
                y2 = event.getY(1);
                a2=coord1(x2);
                b2=coord1(y2);

                double r1 = Math.sqrt((x2 - 400) * (x2 - 400) + (y2 - 1300) * (y2 - 1300));
                double angle = getAngle1(x2, y2);
                if (r1 > 250 * 0.7f) {
                    x2 = (float) (400 + Math.sin(angle) * 250 * 0.7f);
                    y2 = (float) (1300 + Math.cos(angle) * 250 * 0.7f);
                    a2=coord1(x2);
                    b2=coord1(y2);
                }
                if (m.isnotPress1 == false) {
                    x2 = 400;
                    y2 = 1300;
                    a2=coord1(x2);
                    b2=coord1(y2);
                }
            }
            /**else{
             x1=400;
             y1=1300;
             }**/
            /** else{
             x2=event.getX();
             y2=event.getY();
             double r1 = Math.sqrt((x2 - 400) * (x2 - 400) + (y2 - 1300) * (y2 - 1300));
             double angle = getAngle1(x2, y2);
             if (r1 > 250 * 0.7f) {
             x2 = (float) (400 + Math.sin(angle) * 250 * 0.7f);
             y2 = (float) (1300 + Math.cos(angle) * 250 * 0.7f);
             }
             if (m.isnotPress1 == false) {
             x2 = 400;
             y2 = 1300;

             }
             }**/


        }

        invalidate();
        return true;
    }


    public void move() {
        in = m.in(x1, y1);
        // m.isMove(x1, y1);
        if (in == false) {
            x1 = 400;
            y1 = 400;
        }
        invalidate();
    }

    public float getAngle(float x, float y) {
        double angle, k;
        if (y1 == 400)
            if (x < 400)
                angle = -Math.PI / 2;
            else
                angle = Math.PI / 2;
        else {
            k = (400 - x) / (400 - y);
            if (400 > y) {
                angle = Math.atan(k) + Math.PI;
            } else {
                angle = Math.atan(k);
            }

        }
        return (float) angle;
    }

    public float getAngle1(float x, float y) {
        double angle, k;
        if (y2 == 1300)
            if (x < 400)
                angle = -Math.PI / 2;
            else
                angle = Math.PI / 2;
        else {
            k = (400 - x) / (1300 - y);
            if (1300 > y) {
                angle = Math.atan(k) + Math.PI;
            } else {
                angle = Math.atan(k);
            }

        }
        return (float) angle;
    }
}
