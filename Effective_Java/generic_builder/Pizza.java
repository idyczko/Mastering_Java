import java.util.Set;
import java.util.EnumSet;
import java.util.Objects;
public abstract class Pizza {

	public enum Topping {MUSHROOM, CHEESE, HAM, OLIVES};
	final Set<Topping> toppings;

	//Note, that Builder has a recursive type parameter - a feature that allows us chaining of methods without explicit casting.
	//It has to be mentioned though, that this is a workaround of Java's lack of built-in self-type check.
	abstract static class Builder<T extends Builder<T>> {
		private EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
		public T addTopping(Topping t) {
			toppings.add(t);
			return self();
		}

		abstract Pizza build();

		protected abstract T self();

	}

	Pizza(Builder<?> builder) {
		this.toppings = builder.toppings.clone();
	}
}
