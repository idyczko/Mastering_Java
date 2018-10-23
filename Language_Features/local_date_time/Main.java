import java.time.*;

public class Main {

	public static void main(String[] args) {
	
		System.out.println(LocalDateTime.now());
		System.out.println(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
		System.out.println(LocalDate.now());
	}
}
