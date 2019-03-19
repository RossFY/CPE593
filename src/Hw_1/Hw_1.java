/*
 * Ye Fang - 10431002
 * reference - https://leetcode.com/problems/pascals-triangle/description/
 */
package Hw_1;

import java.util.*;

public class Hw_1 {

	// generate Pascal's Triangle
	public static ArrayList<ArrayList<Double>> PascalTriangle(int n){
		ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
		if(n <= 0) {
			return result;
		}
		
		ArrayList<Double> previous = new ArrayList<Double>(); //the previous line
		previous.add(1.0); // this is the very first line of pascal's triangle
		result.add(previous);
		
		for(int i = 2; i <= n; i++) {
			ArrayList<Double> current = new ArrayList<Double>();//the current line
			current.add(1.0); // add the first number c(0,n)
			
			for(int j = 0; j < previous.size() - 1; j++) { 
				// except the last number (c(n,n) = 1.0), the numbers of current line is equal to the number of previous line
				current.add(previous.get(j)+previous.get(j+1));
			}
			
			current.add(1.0); // add the last number c(n,n)
			
			result.add(current);
			previous = current;
		}
		return result;
	}
	
	public static double choose(int n,int r)
	{
		//first generate Pascal's Triangle
		ArrayList<ArrayList<Double>> pt = PascalTriangle(500);
		//return the rth number in nth row
		return pt.get(n).get(r);
	}
	
 	public static void main(String[] args) {
 		int testCases[][] = new int[][]{
			{0, 0, 1},
			
			{1, 0, 1},
			{1, 1, 1},
			
			{2, 0, 1},
			{2, 1, 2},
			{2, 2, 1},
			
			{5, 0, 1},
			{5, 1, 5},
			{5, 2, 10},
 			{6, 3, 20}
		};
	
		for (int i = 0; i < testCases.length; i++) {
			int n = testCases[i][0];
			int r = testCases[i][1];
			int result = testCases[i][2];
		
			if (choose(n, r) != result) {
				System.err.println("Failed test case: choose(" + n + ", " + r + ") = " +
													 result + '\n');
			}
		}
 		System.out.println("Large number tests\n");
		System.out.println(choose(52, 6));
		System.out.println(choose(52, 26));
		System.out.println(choose(150, 5));
	}

}
