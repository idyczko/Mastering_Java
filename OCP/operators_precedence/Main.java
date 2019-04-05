

public class Main {

	final static int case3 = 20;

	static {
	}
	
	public static void main(String[] args) {
	
		int x = 10;
		int y = x++ * x--;
		int z = ++y * --y;
		int w = --z * z++;
		int c = 10;
		int v = --c * c++;
		System.out.println(y + " " + z + " " + w + " " + v + " " + (false|true));

		boolean b = false == (new Integer(0) == new Object());
		String s = "";
		z = true ? (x += 5) : ((short) 4);
   		
		final int case1 = 10;
		final int case2 = 15;
		switch(x) {
			case case1:
				break;
			case case2:
				break;
			case case3:
				break;
		}
		int j;
		long i;
		for (i =0L, j = 5; i < 10 ; i++){}
	}
}
