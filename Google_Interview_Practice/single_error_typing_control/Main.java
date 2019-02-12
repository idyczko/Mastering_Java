
public class Main {

	public static void main(String[] args) {
		
		DictionaryDetrie detrie = new DictionaryDetrie(8);

		detrie.insertWord("AAAAAAAA");
		detrie.insertWord("ABBBBBBA");
		detrie.insertWord("AAABBAAA");
		detrie.insertWord("BBBAAAAA");
		detrie.insertWord("BBBBBBBB");
		detrie.insertWord("BAAAAAAB");

		System.out.println(detrie.isZeroOrOneTypoAway(args[0]));
	}
}
