import java.util.stream.Stream;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		MinimalBST.printMinimalBST(Stream.of(args).mapToInt(s -> Integer.valueOf(s)).toArray());
	}
}
