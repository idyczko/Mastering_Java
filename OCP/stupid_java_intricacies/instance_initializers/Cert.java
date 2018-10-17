public class Cert {


	{
		System.out.println("I am the first instance initializer!");
	}
	
	{
		System.out.println("I am the second instance initializer!");
		field = 20;
	}

	int field  = 10;
	Cert() {
		System.out.println("And I am a package-accessible constructor!");
	}	
}
