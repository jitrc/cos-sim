package ru.cos.sim.visualizer.traffic.graphs.integrator;

import ru.cos.sim.visualizer.traffic.graphs.classes.Point;
import ru.cos.sim.visualizer.traffic.graphs.classes.PointCollection;

import java.util.List;
import java.util.Collections;

public class Integrator {

    private enum Limit{
        UPPER, LOWER
    }

    public static float getAverageValue(PointCollection c, Point start, Point end){
        List<Point> points =  c.getPoints();
        Collections.sort(points, new PointComparator());

/*        System.out.println("after sorting: ");
        for(Point p : points){
            System.out.println("x = " + p.getX() + " y = " + p.getY());
        }*/

        float area = 0.0f;

        Point lowerLimit;
        Point upperLimit;
        try{
            lowerLimit = getLimit(c, start, Limit.LOWER);
            upperLimit = getLimit(c, end, Limit.UPPER);
        } catch (ArithmeticException e){
            return 0;
        }

//        System.out.println("lower = " + lowerLimit.getX());
//        System.out.println("upper = " + upperLimit.getX());

        points = points.subList(points.indexOf(lowerLimit), points.indexOf(upperLimit) + 1);

/*        System.out.println("after sublisting: ");
        for(Point p : points){
            System.out.println("x = " + p.getX() + " y = " + p.getY());
        }*/

        for(int i = 0; i < points.size() - 1; i++ ){
            Point prev = points.get(i);
            Point next = points.get(i + 1);

            area += (next.getX() - prev.getX()) * (prev.getY() + next.getY()) / 2; 
        }
        return area / (upperLimit.getX() - lowerLimit.getX());
    }

    private static Point getLimit(PointCollection c, Point p, Limit l){
        for(int i = 0; i < c.getPoints().size() - 1; i++){
            Point prev = c.getPoints().get(i);
            Point next = c.getPoints().get(i + 1);

            if(prev.getX() == p.getX())
                return prev;

            else if(next.getX() == p.getX())
                return next;

            else if(prev.getX() < p.getX() && p.getX() < next.getX()){
                if(l == Limit.UPPER) return prev;
                else return next;
            }
        }
        throw new ArithmeticException("Cannot find limit of integration");
    }
}
