import java.util.stream.Stream;
import java.util.concurrent.atomic.AtomicInteger;
public class Main {

	public static void main(String[] args) {
		MinimalBST.printMinimalBST(Stream.of(args).mapToInt(s -> Integer.valueOf(s)).toArray());

		MinimalBST.Node unbalanced = new MinimalBST.Node(0, null, 
				new MinimalBST.Node(1, null,
					new MinimalBST.Node(2, null,
						new MinimalBST.Node(3, new MinimalBST.Node(3, null, null),
							new MinimalBST.Node(4, null, null)))));
		System.out.println("Is Main tree balanced: " + MinimalBST.checkBalanced(unbalanced, new AtomicInteger(0)));
		System.out.println(MinimalBST.stringify(unbalanced));

		MinimalBST.Node subtree = new MinimalBST.Node(3, new MinimalBST.Node(3,null,null), new MinimalBST.Node(114,null,null));
		System.out.println(MinimalBST.isSubtree(unbalanced, subtree));
	}
}
