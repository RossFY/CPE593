/*
 * Ye Fang - 10431002
 * Reference: https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
 */

package Hw_2;

public class Hw_2 {

	public static int Eratosthenes(int a, int b){
		int count = 0;
		//int n = b - a + 1;
		
		// create a boolean array and set all elements to true
		boolean isPrime[] = new boolean[b+1];
		for(int i = 0; i <= b; i++) {
			isPrime[i] = true;
		}
		
		// because 0 and 1 are not prime, so set isPrime[0] and isPrime[1] to false
		isPrime[0] = isPrime[1] = false;
		
		// except 2, all the even numbers are not prime
		for(int i = 4; i <= b; i += 2) {
			isPrime[i] = false;
		}
		
		
		//sqrt(b)
		int n = (int) Math.ceil(Math.sqrt(b));
		//System.out.println("n="+n);
		
		//The main step of Sieve of Eratosthenes
		for(int i = 2; i <= n - 1; i++) {
			if(isPrime[i]) {
				for(int j = i * i; j <= b; j += 2 * i) {
					isPrime[j] = false;
				}	
			}
		}
		
		// count the number of prime between a to b
		for(int i = a; i <= b; i++) {
			//System.out.print(isPrime[i]+" ");
			if(isPrime[i]==true) {
				count++;
			}
		}
		return count;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("There are " + Eratosthenes(11,20) + " prime numbers between 11 to 20.");
		System.out.println("There are " + Eratosthenes(1,20) + " prime numbers between 1 to 20.");
		System.out.println("There are " + Eratosthenes(100,200) + " prime numbers between 100 to 200.");
		System.out.println("There are " + Eratosthenes(1000000000,2000000000) + " prime numbers between 1000000000 to 2000000000.");
	}

}
