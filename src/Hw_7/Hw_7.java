/*
 * Ye Fang - 10431002
 * reference:
 * 1. https://baike.baidu.com/item/三对角矩阵
 * 2. https://www.cnblogs.com/danne823/archive/2011/12/01/2270693.html
 */

package Hw_7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.*;

class spline{
	
	public void CubicSpline(int n, double[] x, double[] y) {
		
		// Xi(t) = ai + bi * t + ci * t^2 + di * t^3
		double[][] calc_x = init_Matrix(n, x);
		double[] D_x = calc(calc_x);
		double[] a_x = get_a(x);
		double[] b_x = get_b(D_x);
		double[] c_x = get_c(x,D_x);
		double[] d_x = get_d(x,D_x);
		
		// Yi(t) = ai + bi * t + ci * t^2 + di * t^3
		double[][] calc_y = init_Matrix(n, y);
		double[] D_y = calc(calc_y);
		double[] a_y = get_a(y);
		double[] b_y = get_b(D_y);
		double[] c_y = get_c(y,D_y);
		double[] d_y = get_d(y,D_y);

		DecimalFormat df = new DecimalFormat("###0.0");
		
		// output result
		System.out.println("Segment\tt\tX\tY\t");
		for(int i = 0; i < a_x.length; i++) {
			for(double t = 0.0; t <= 0.9; t += 0.1) {
				System.out.println(i + "\t" + df.format(t) + "\t" + df.format((a_x[i] + b_x[i] * t + c_x[i] * Math.pow(t, 2) + d_x[i] * Math.pow(t, 3))) + "\t" + df.format((a_y[i] + b_y[i] * t + c_y[i] * Math.pow(t, 2) + d_y[i] * Math.pow(t, 3))));
			}
		}
	}
	
	private double[] get_result(double[] x) {
		// get:
		/*
		 * 3(X1-X0)
		 * 3(X2-X0)
		 * 	   .
		 *     .
		 *     .
		 * 3(Xi -Xi-1)
		 */
		int n = x.length;
		double res[] = new double[n];
		res[0] = 3*(x[1]-x[0]);
		res[n-1] = 3*(x[n-1]-x[n-2]);
		for(int i = 1; i < n - 1; i++) {
			res[i] = 3 * (x[i+1] - x[i-1]);
		}
		return res;
	}
	
	private double[][] init_Matrix(int n, double[] x) {
		/*
		 * 2	  1  0  0		D0		3(X1-X0)
		 * 
		 * 1  4  1  0		D1		3(X2-X0)
		 * 				*		=	
		 * 0  1  4  1		D2		3(X3-X1)
		 * 
		 * 0  0  1  2		D3		3(X3-X2)
		 */
		
		double[][] matrix = new double[n][n];
		// initiate the first row and the last row
		matrix[0][0] = 2.0;
		matrix[0][1] = 1.0;
		matrix[n-1][n-1] = 2.0;
		matrix[n-1][n-2] = 1.0;
		
		// build the rest rows and columns
		for(int i = 1; i < n - 1; i++) {
			for(int j = 0; j < n; j++) {
				matrix[i][j] = buildTriDiagonalMatrix(matrix[i][j], i, j);
			}
		}
		
		// make the matrix become the following format, then return matrix
		/*
		 * 2	  1  0  0  3(X1-X0)
		 * 
		 * 1  4  1  0  3(X2-X0)
		 * 		
		 * 0  1  4  1  3(X3-X1)
		 * 
		 * 0  0  1  2  3(X3-X2)
		 */
		double[][] new_m = new double[n][n+1];
		double[] res_x = get_result(x);
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				new_m[i][j] = matrix[i][j];
			}
		}
		
		for(int i = 0; i < x.length; i++) {
			new_m[i][n] = res_x[i];
		}
		
		return new_m;
	}
	
	private double buildTriDiagonalMatrix(double x, int i, int j){
		/*
		 * build the matrix bellow
		 * 2	 1 0 0
		 * 1 4 1 0
		 * 0 1 4 1
		 * .......
		 * 0 0 1 2
		 */
		int flag = i-j;
		double res = 0.0;
		switch(flag) {
			case 1:
				res = 1.0;
				break;
			case 0:
				res = 4.0;
				break;
			case -1:
				res = 1.0;
				break;
		}
		
		return res;
	}
	
	private double[] calc(double[][] x) {
		// use the method of elimination to calculate D0 D1 D2 D3 .... Di
		// the method is similar to the solution of multiple linear equation.
		
		int row = x.length; // number of rows
		int col = x[0].length; // number of columns
		
		if(row == 1) {
			// if become linear equation with one unknown, return result
			double[] re = {x[0][1] / x[0][0]};
			return re;
		}else {
			double[][] tmp = new double[row - 1][col - 1]; // store the matrix after eliminating
			
			ArrayList<double[]> zerorows = new ArrayList<>(); // the rows that the first columns contain '0'
			ArrayList<double[]> otherrows = new ArrayList<>();
			
			for(int i = 0; i < row; i++) {
				if(x[i][0] == 0.0) {
					zerorows.add(x[i]);
				}else {
					otherrows.add(x[i]);
				}
			}
			
			for(int i = 0; i < otherrows.size() - 1; i++) {
				for(int j = 1; j < col; j++) {
					tmp[i][j-1] = otherrows.get(i+1)[0] * otherrows.get(i)[j] - otherrows.get(i)[0] * otherrows.get(i+1)[j];
				}
			}
			
			for(int i = 0; i < zerorows.size(); i++) {
				for(int j = 1; j < col; j++) {
					tmp[i+otherrows.size() - 1][j - 1] = zerorows.get(i)[j];
				}
			}
			
			double[] res = this.calc(tmp); // recursive
			
			// in order to judge that the coefficient of the first number is '0' or not
			int r = 0;
			while(x[r][0] == 0) {
				r++;
			}
			
			double t = 0.0;
			for(int i = 1; i < col - 1; i++) {
				t+=x[r][i] * res[i-1];
			}
			double d = (x[r][col-1] - t) / x[r][0];
			
			double[] D = new double[res.length + 1];
			D[0] = d;
			for(int i = 0; i < res.length; i++) {
				D[i+1] = res[i];
			}
			
			return D;
		}
	}
	
	private double[] get_a(double[] x) {
		// get ai
		// ai = Xi
		double[] a = new double[x.length - 1];
		
		for(int i = 0; i < x.length - 1; i++) {
			a[i] = x[i];
		}
		
 		return a;
	}
	
	private double[] get_b(double[] D) {
		// get bi
		// bi = Di
		double[] b = new double[D.length - 1];
		
		for(int i = 0; i < D.length - 1; i++) {
			b[i] = D[i];
		}
			
		return b;
	}
	
	private double[] get_c(double[] x, double[] D) {
		// get ci
		// ci = 3 * (X[i+1] - X[i]) - 2D[i] - D[i+1]
		double[] c = new double[D.length - 1];
		
		for(int i = 0; i < D.length - 2; i++) {
			c[i] = 3 * (x[i+1] - x[i]) - 2 * D[i] - D[i+1];
		}
		
		return c;
	}
	
	private double[] get_d(double[] x, double[] D) {
		// get di
		// di = 2 * (X[i] - X[i+1]) + D[i] + D[i+1]
		double[] d = new double[D.length - 1];
		
		for(int i = 0; i < D.length - 2; i++) {
			d[i] = 2 * (x[i] - x[i+1]) + D[i] + D[i+1];
		}
		
		return d;
	}
}

public class Hw_7 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// X(t) = a + b * t + c * t^2 + d * t^3
		// Y(t) = a + b * t + c * t^2 + d * t^3
		/*
		 * In my input file("points.dat"), the format is in the following:
		 * (The first line of the file is the number of points, X and Y are separated by '\t')
		 * for example,
		 * 4
		 * 0.0		0.0
		 * 100.0		50.0
		 * 200.0		25.0
		 * 250.0		0.0
		 * which means there are 4 points in the file, they are (0.0,0.0), (100.0,50.0), (200.0,25.0), (250.0,0.0)
		 */
		
		spline s = new spline();
		
		try {
			File f = new File(System.getProperty("user.dir")+"/points.dat");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			String firstline = br.readLine();
			int n = Integer.parseInt(firstline);
			double[] x = new double[n];
			double[] y = new double[n];
			
 			String line = null;
 			int i = 0;
 			
			while((line = br.readLine()) != null) {
				String[] point = line.split("\t");
			
				x[i] = Double.parseDouble(point[0]);
				y[i] = Double.parseDouble(point[1]);
				
				i++;
			}

			s.CubicSpline(n, x, y);
			
			br.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
