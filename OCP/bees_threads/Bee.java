
public class Bee {
	
	public synchronized void go() throws InterruptedException{
		Thread t = new Thread();
		t.start();
		System.out.println("Started a bee...");
		t.wait(5000);
		System.out.println("Bee out.");
	}

	public static void main(String[] args) {
		try {
			new Bee().go();
		} catch (Exception e) {
			System.out.println("Bee has been killed, chopped and burried in main's backyard.");
		}
	}
}
