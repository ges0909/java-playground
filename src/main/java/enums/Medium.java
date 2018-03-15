package enums;

import java.util.Optional;

public enum Medium {
	ENERGY(9), GAS(99), WATER(999), OIL(61), STEAM(61);

	private final int value;

	// ordinal() and name() are generic methods of each enum

	private Medium(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}

	public static Optional<Medium> find(String name) {
		for (Medium e : values()) {
			if (e.name().equals(name)) {
				return Optional.of(e);
			}
		}
		return Optional.empty();
	}

	public static Optional<Medium> find(int value) {
		for (Medium e : values()) {
			if (e.value == value) {
				return Optional.of(e);
			}
		}
		return Optional.empty();
	}

	public static boolean isValid(int value) {
		return find(value).isPresent();
	}
}
