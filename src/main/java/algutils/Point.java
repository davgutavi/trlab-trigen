package algutils;


public class Point implements Comparable<Point>{
	
	private int x;
	private int y;

	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "{x=" + x + ", y=" + y + "}";
	}

	@Override
	public int compareTo(Point otro) {
		int res = 0;

		if (this.x > otro.x) {
			res = 1;
		} else if (this.y < otro.y) {
			res = -1;
		} else {

			if (this.y > otro.y) {
				res = 1;
			} else if (this.y < otro.y) {
				res = -1;
			} else {
				res = 0;
			}

		}

		return res;
	}
	
	

}
