package com.example.miki.canvasproject;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Miki on 1/20/2018.
 */

public class Circle {
    private float x;
    private float y;
    private float radius;
    private boolean computed;
    private static final String TAG = Circle.class.getName();
    public static final float MIN_RADIUS = 30;
    public static final float MAX_RADIUS = 100;


    public Circle(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public Circle(float x, float y, float radius, boolean computed) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.computed = computed;
    }




    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public boolean isComputed() {
        return computed;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    void computePosition2(Circle[] c, float cx, float cy) {

    }

    void computePosition1 (Circle[] c, float cx, float cy) {
        int i, j;
        boolean collision;
        ArrayList<PointF> candidates = new ArrayList<>();
        PointF pnt;

        if (computed) { return; }

        for (i = 0; i < c.length; i++)
        {
            if (c[i].computed)
            {
                for (int ang = 0; ang < 360; ang++)
                {
                    collision = false;
                    pnt = new PointF();
                    pnt.x = c[i].getX() + (float)(Math.cos(ang*Math.PI/180) * (radius + c[i].radius + 1));
                    pnt.y = c[i].getY() + (float)(Math.sin(ang*Math.PI/180) * (radius + c[i].radius + 1));

                    for (j = 0; j < c.length; j++) { // For each circle, check if it collides with c[i].
                        if (c[j].computed && !collision) {
                            double dist = getDistance(pnt.x, pnt.y, c[j].x,  c[j].y);
                            if (dist <= radius + c[j].radius) {
                                collision = true;
                            }
//                            collision = doesIntersect(new Circle(pnt.x, pnt.y, radius));
                        }
                    }

                    if (!collision) {
                        candidates.add(pnt);
                    }
                }
            }
        }

        float min_dist = -1;
        int best_point = 0;
        for (i = 0; i < candidates.size(); i++)
        {
            double dist = getDistance(cx, cy, candidates.get(i).x, candidates.get(i).y);
            if (min_dist == -1 || dist < min_dist)
            {
                best_point = i;
                min_dist = (float)dist;
            }
        }
        if (candidates.size() == 0)
        {
            Log.d(TAG, "No points?!?!?");
        } else
        {
            Log.d(TAG,candidates.size() + " points");
            x = candidates.get(best_point).x;
            y = candidates.get(best_point).y;
        }

        computed = true;
    }

    private double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private boolean doesIntersect(Circle other) {
        double dist = getDistance(x, y, other.x, other.y);
        return dist >= Math.abs(radius - other.radius) && dist <= radius + other.radius;
    }
}
