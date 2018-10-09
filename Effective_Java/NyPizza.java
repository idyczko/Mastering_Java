
public class NyPizza extends Pizza {
	public enum Size {SMALL, MEDIUM, BIG};
	public Size size;
	public boolean withShittyOlives;

	static class Builder extends Pizza.Builder<Builder> {
		Size size = Size.SMALL;
		boolean olives = false;

		public Builder size(Size size) {
			this.size = size;
			return self();
		}

		public Builder olives(boolean olives) {
			this.olives = olives;
			return self();
		}

		public NyPizza build() {
			return new NyPizza(this);
		}

		protected Builder self() {
			return this;
		}
	}

	private NyPizza(Builder b) {
		super(b);
		this.size = b.size;
		this.withShittyOlives = b.olives;
	}
}
