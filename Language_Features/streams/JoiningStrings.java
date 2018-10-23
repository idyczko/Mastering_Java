import java.util.stream.*;
public class JoiningStrings {

	public static void main(String[] args) {
	
		System.out.println(Stream.of("haha", "hehe", "hihi").collect(Collectors.joining(",")));		
		System.out.println(String.join(",",Stream.of("haha", "hehe", "hihi").collect(Collectors.toList())));
		Stream.of("haha,hehe,hihi".split(",")).forEach(System.out::println);
	}
}
