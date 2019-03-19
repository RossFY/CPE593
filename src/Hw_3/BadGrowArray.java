package Hw_3;

class BadGrow{
	private int[] p;
	private int len;
	
	public BadGrow() {
		p = new int[len];
		len = 0;
	}
	
	public void insertEnd(int v) {
		int old[] = p;
		p = new int[len+1];
		for(int i = 0; i < len; i++) {
			p[i] = old[i];
		}
		p[len] = v;
		len++;
		// delete old
	}
	
	public String convertString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < len; i++) {
			sb.append(p[i]+" ");
		}
		return sb.toString();
	}
}

public class BadGrowArray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BadGrow bg = new BadGrow();
		long t0 = System.nanoTime();
		for(int i = 0; i < 20; i++) {
			bg.insertEnd(i);
		}
		System.out.println(bg.convertString());//77185627
		//System.out.println(bg.toString()); //77959794
		long t1 = System.nanoTime();
		System.out.println("t = "+(t1-t0)); // 74775425
		
		int[] x = new int[5];
		for(int i = 0; i < 5; i++) {
			x[i] = i;
		}
		int[] p = x;
		for(int i = 0; i < 5; i++) {
			System.out.print(p[i]+" ");
		}
	}

}
