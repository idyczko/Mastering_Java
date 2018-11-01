import java.time.*;

public class Main {
	
	public static void main(String[] args) {
		LocalDateTime localDateTime = LocalDateTime.of(LocalDate.of(2018, 11, 2), LocalTime.MIN);
		System.out.println(localDateTime.isAfter(localDateTime));
	}
}
