
public class Main {

	public static void main(String[] args) {

		//Well, to be honest this is pretty amazing stuff...
		NyPizza p = new NyPizza.Builder().olives(true).size(NyPizza.Size.BIG).addTopping(Pizza.Topping.MUSHROOM).build();

		System.out.println("This pizza is an NyPizza: " + (p instanceof NyPizza) +  "it has " + (p.withShittyOlives? "" : "no") + " olives.");
	}
}
