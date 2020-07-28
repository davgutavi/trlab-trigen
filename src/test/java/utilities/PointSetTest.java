package utilities;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algutils.Point;

public class PointSetTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(PointSetTest.class);
	
	public static void main(String args[]) {
		
		Set<Point> s = new HashSet<Point>();
		
		Point p1 = new Point(1,2);
		Point p2 = new Point(1,2);
		Point p3 = new Point(2,2);
		
		s.add(p1);
		s.add(p2);
		
		LOG.debug(s.toString()+"\n");
		
		s.add(p3);
		
		LOG.debug(s.toString()+"\n");
		
		
		
		
		
	}

}
