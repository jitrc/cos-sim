package ru.cos.sim.ras.duo;

import java.util.ArrayList;
import java.util.List;

import ru.cos.sim.ras.duo.utils.Pair;

public class Segmentator {
	
	public List<Segment> fromHead(float totalSize, float segmentSize) {
		List<Segment> segments = new ArrayList<Segment>();
		for (float x = 0; x < totalSize; x += segmentSize) 
			segments.add(new Segment(x, Math.min(x + segmentSize, totalSize)));
		return segments;
	}
	
	public List<Segment> fromTail(float totalSize, float segmentSize) {
		List<Segment> segments = new ArrayList<Segment>();
		for (float x = totalSize; x > 0; x -= segmentSize) 
			segments.add(new Segment(Math.max(x - segmentSize, 0), x));
		return segments;
	}
	
	public static class Segment extends Pair<Float, Float> {
		public Segment(Float start, Float end) {
			super(start, end);
		}

		public float getStart() {
			return getFirst();
		}
		
		public float getEnd() {
			return getSecond();
		}
		
		public float getLength() {
			return getEnd() - getStart();
		}
		
		@Override
		public String toString() {
			return getStart() + " to " + getEnd();
		}
	}
}
