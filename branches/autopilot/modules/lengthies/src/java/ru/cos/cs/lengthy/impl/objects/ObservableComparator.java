package ru.cos.cs.lengthy.impl.objects;

import java.util.Comparator;

import ru.cos.cs.lengthy.objects.Point;

/**
 * Point comparator.
 * Compare points on the base of their positions.
 * @author zroslaw
 */
public class ObservableComparator implements Comparator<Point> {
	@Override
	public int compare(Point o1, Point o2) {
		return Float.compare(o1.getPosition(), o2.getPosition());
	}
}