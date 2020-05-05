import java.util.concurrent.atomic.*;
import java.time.*;

public class Atomics {

	public static void main(String[] args) {
		AtomicReference<LocalDateTime> lastUpdate = new AtomicReference();
		System.out.println(lastUpdate.get());
		lastUpdate.set(LocalDateTime.now());
		System.out.println(lastUpdate.get());
	}
}
