package Hw_3;

public class ClockWiseTest {

	public static void printCircle(int[][] matrix, int startX, int startY, int endX, int endY) {
		
		// only one row left
		if (startX == endX) {
			for (int i = startY; i <= endY; i++ ) {
				System.out.println(matrix[startX][i]);
			}
			return;
		}
		
		// only one column left
		
		if (startY == endY) {
			for (int i = startX; i <= endX; i++ ) {
				System.out.println(matrix[i][endY]);
			}
			return;
		}
		
		for (int i = startY; i < endY; i++ ) {
			System.out.println(matrix[startX][i]);
		}
		
		for (int i = startX; i < endX; i++ ) {
			System.out.println(matrix[i][endY]);
		}
		
		for (int i = endY; i > startY; i-- ) {
			System.out.println(matrix[endX][i]);
		}
		
		for (int i = endX; i > startX; i-- ) {
			System.out.println(matrix[i][startY]);
		}
		
	}
	
	public static void printMatrix(int[][] matrix) {
		
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


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] matrix = new int [3][3];
		int k = 1;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				matrix[i][j] = k++;
			}
		}
		printMatrix(matrix);
	}

}
