

public class Main {

	public static void main(String[] args) {
	
		int x = 10;
		int y = x++ * x--;
		int z = ++y * --y;
		int w = --z * z++;
		int c = 10;
		int v = --c * c++;
		System.out.println(y + " " + z + " " + w + " " + v + " " + (false|true));

	}
}
