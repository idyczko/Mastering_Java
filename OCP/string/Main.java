
class Main {

	public static void main(String[] args) {
	
		String h = "Hello";
		String s = "Hello World";
		String d = "Hello World".trim();
		System.out.println((s == d) + " " + (s.trim() == d) + " " + (h == s.substring(0, 5)));
		System.out.println(h + " " + s.substring(0, 5) + " " + h.equals(s.substring(0, 5)));
	}
}
