package snippet;

public enum Enum {
	ENERGY(0), GAS(1), WATER(2);

	int ordinal;

	Enum(int ordinal) {
		this.ordinal = ordinal;
	}

	public static Enum name(int ordinal) {
		for (Enum e : values()) {
			if (e.ordinal() == ordinal) {
				return e;
			}
		}
		return null;
	}

	public static boolean isValid(int ordinal) {
		return name(ordinal) != null;
	}
}
