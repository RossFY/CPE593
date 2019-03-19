/*
 * Ye Fang - 10431002
 * reference:
 * 1. https://www.geeksforgeeks.org/convex-hull-set-1-jarviss-algorithm-or-wrapping/
 * 2. https://blog.csdn.net/Bone_ACE/article/details/46239187
 * 3. https://en.wikipedia.org/wiki/Convex_hull
 * 4. https://blog.csdn.net/beiyeqingteng/article/details/7055499 // print ClockWise Order
 */

package Hw_3;

import java.io.*;

class point{
	public double x;
	public double y;
	
	public point(double x, double y) {
		this.x = x;
		this.y = y;
	}
}

class GrowArray{
	public int capacity;
	public int len;
	public point[] p;
	
	public GrowArray(int initCapacity) {
		capacity = initCapacity == 0 ? 1 : initCapacity;
		p = new point[initCapacity];
		len = 0;
	}
	
	public int getSize() {
		return len;
	}
	
	public point get(int i) { // O(1)
		return p[i];
	}
	
	public void set(int i, point x) { // O(1)
		p[i] = x;
	}
	
	public void insertEnd(point x) { // O(1)
		checkGrow();
		p[len] = x;
		len++;
		// delete old
	}
	
	public void insertStart(point x) { // O(n)
		checkGrow();
		point[] old = new point[len];
		for(int i = 0; i < len; i++) {
			old[i] = p[i];
		}
		for(int i = 1; i <= len; i++) {
			p[i] = old[i-1];
		}
		p[0] = x;
		len++;
		// delete old
	}
	
	public void removeEnd() { // O(1)
		len--;
	}
	
	public void removeStart() { // O(n)
		point[] old = p;
		for(int i = 0; i < len - 1; i++) {
			p[i] = old[i+1];
		}
		len--;
		// delete old
	}
	
	private void checkGrow() {
		if(len==capacity) {
			capacity *= 2;
			point[] new_p = new point[capacity];
			for(int i = 0; i < len; i++) {
				new_p[i] = p[i];
			}
			p=new_p;
		}
	}
}

class ConvexHull{
	public GrowArray[][] grid;
	public int len;
	public double minx, miny, maxx, maxy;
	
	public ConvexHull(int l) {
		grid = new GrowArray[l][l];
		len = l;
		
		for(int i = 0; i < l; i++) {
			for(int j = 0; j < l; j++) {
				grid[i][j] = new GrowArray(l);
			}
		}
		
	}
	
	public void read(String filename) {
		GrowArray Garr = new GrowArray(16);
		minx = Integer.MAX_VALUE;
		miny = Integer.MAX_VALUE;
		maxx = Integer.MIN_VALUE; 
		maxy = Integer.MIN_VALUE;
		
		try {
			File f = new File(filename);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			while((line = br.readLine()) != null) {
				String[] l = line.split("\t");
				if(Double.parseDouble(l[0])<minx) {
					minx = Double.parseDouble(l[0]);
				}
				if(Double.parseDouble(l[0])>maxx) {
					maxx = Double.parseDouble(l[0]);
				}
				if(Double.parseDouble(l[1])<miny) {
					miny = Double.parseDouble(l[1]);
				}
				if(Double.parseDouble(l[1])>maxy) {
					maxy = Double.parseDouble(l[1]);
				}
				point temp = new point(Double.parseDouble(l[0]),Double.parseDouble(l[1]));
				Garr.insertEnd(temp);
			}
			br.close();
			
			double rangeX = (maxx-minx) / len;
			double rangeY = (maxy-miny) / len;
			
			for(int i = 0; i < Garr.getSize(); i++) {
				point t = Garr.get(i);
				int new_x = (int)((t.x - minx) / rangeX); // find the exact x
				int new_y = (int)((t.y - miny) / rangeY); // find the exact y
				new_x = new_x == len ? new_x - 1 : new_x; // if t.x = minx
				new_y = new_y == len ? new_y - 1 : new_y; // if t.y = miny
				//int index = a * b - 1; // because grid is 16x16, the new index of point is a x b - 1.
				grid[new_x][new_y].insertEnd(t);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void printMaxMin() {
		System.out.println("Print All Max & Min:");
		System.out.println("MaxX: "+maxx);
		System.out.println("MaxY: "+maxy);
		System.out.println("MinX: "+minx);
		System.out.println("MinY: "+miny);
		System.out.println();
	}
	
	public void printAllListSizes() {
		System.out.println("The size of all lists:");
		for(int i = 0; i < len; i++) {
			for(int j = 0; j < len; j++) {
				if(grid[i][j].getSize()!=0) {
					System.out.println("List "+(i * len + j)+"'s size: "+grid[i][j].getSize());
				}else {
					continue;
				}
			}	
		}
		System.out.println();
	}
	
	public void printPerimeterClockWiseOrder() {
		//int k = 1;
		System.out.println("Print All Points:");
		for(int i = 0; i < len; i++) {
			for(int j = 0; j < len; j++) {
				if(grid[i][j].getSize()!=0) {
					System.out.println("List "+(i * len + j)+": ");
					for(int m = 0; m < grid[i][j].getSize();m++) {
						System.out.println("Point "+(m+1)+": "+"("+grid[i][j].get(m).x+","+grid[i][j].get(m).y+")");
					}
				}else {
					continue;
				}
			}
			
		}
		/*
		int k = 0;
		point[][] res = new point[len][len];
		for(int i = 0; i < len; i++) {
			for(int j = 0; j < len; j++) {
				res[i][j] = grid[k].get(0);
				k++;
			}
		}
		*/
		
		System.out.println();
		System.out.println("Print All the Points in ClockWise Order:");
		// the order should be List 2 -> 13 -> 112 -> 128 -> 143
		printMatrix(grid);
		/*
		for(int i = 0; i < len; i++) {
			for(int j = 0; j < len; j++) {
				if(res[i][j] != null) {
					System.out.println(res[i][j].x+","+res[i][j].y);
				}
				
			}
		}
		*/
	}
	
	public void printCircle(GrowArray[][] matrix, int startX, int startY, int endX, int endY) {
		// only one row left
		if (startX == endX) {
			for (int i = startY; i <= endY; i++ ) {
				if(matrix[startX][i].getSize() != 0)
					for(int k = 0; k < matrix[startX][i].getSize(); k++)
						System.out.println("Point "+(k+1)+": "+"("+matrix[startX][i].get(k).x+","+matrix[startX][i].get(k).y+")");
			}
			return;
		}
		
		// only one column left
		
		if (startY == endY) {
			for (int i = startX; i <= endX; i++ ) {
				if(matrix[i][endY].getSize() != 0)
					System.out.println("List "+(i * len + endY)+": ");
					for(int k = 0; k < matrix[i][endY].getSize(); k++)
						System.out.println("Point "+(k+1)+": "+"("+matrix[i][endY].get(k).x+","+matrix[i][endY].get(k).y+")");
			}
			return;
		}
		
		for (int i = startY; i < endY; i++ ) {
			if(matrix[startX][i].getSize() != 0)
				System.out.println("List "+(startX * len + i)+": ");
				for(int k = 0; k < matrix[startX][i].getSize(); k++)
					System.out.println("Point "+(k+1)+": "+"("+matrix[startX][i].get(k).x+","+matrix[startX][i].get(k).y+")");
		}
		
		for (int i = startX; i < endX; i++ ) {
			if(matrix[i][endY].getSize() != 0)
				System.out.println("List "+(i * len + endY)+": ");
				for(int k = 0; k < matrix[i][endY].getSize(); k++)
					System.out.println("Point "+(k+1)+": "+"("+matrix[i][endY].get(k).x+","+matrix[i][endY].get(k).y+")");
		}
		
		for (int i = endY; i > startY; i-- ) {
			if(matrix[endX][i].getSize() != 0)
				System.out.println("List "+(endX * len + i)+": ");
				for(int k = 0; k < matrix[endX][i].getSize(); k++)
					System.out.println("Point "+(k+1)+": "+"("+matrix[endX][i].get(k).x + "," + matrix[endX][i].get(k).y+")");
		}
		
		for (int i = endX; i > startX; i-- ) {
			if(matrix[i][startY].getSize() != 0)
				System.out.println("List "+(i * len + startY)+": ");
				for(int k = 0; k < matrix[i][startY].getSize(); k++)
					System.out.println("Point "+(k+1)+": "+"("+matrix[i][startY].get(k).x+","+matrix[i][startY].get(k).y+")");
		}
		
	}
	
	public void printMatrix(GrowArray[][] matrix) {
		
		if (matrix == null) {
			return;
		}
		
		int startX = 0;
		int startY = 0;
		int endY = matrix[0].length - 1;
		int endX = matrix.length - 1;
		
		while ((startX <= endX) && (startY <= endY)) {
			printCircle(matrix, startX, startY, endX, endY);
			
			startX++;
			startY++;
			endX--;
			endY--;
		}
	}
}

public class Hw_3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ConvexHull ch = new ConvexHull(16);
		//ch.read("/Users/ross/eclipse workspace/CPE-593/src/Hw_3/convexhullpoints.dat");
		ch.read(System.getProperty("user.dir")+"/convexhullpoints.dat");
		ch.printAllListSizes();
		ch.printMaxMin();
		ch.printPerimeterClockWiseOrder();
		
	}

}
