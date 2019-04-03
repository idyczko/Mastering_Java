
public class Main {

	public static void main(String[] args) {
		String result = "";
		String v = null;
		try {
			try {
				result+="1 ";
				v.length();
				result+="2 ";
			} catch (NullPointerException e) {
				result+="3 ";
				throw new IllegalStateException();
			} finally {
				result +="4 ";
				throw new IllegalArgumentException();
			}
		
		} catch (IllegalStateException r) {
			result += "6 ";
		}

		System.out.println(result);
	}
}
