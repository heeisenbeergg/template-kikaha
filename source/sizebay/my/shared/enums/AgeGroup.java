package sizebay.my.shared.enums;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum AgeGroup {

	adult, infant, kids, newborn, toddler;

	public static AgeGroup from(String value) {
		try {
			if(value == null || value.isEmpty()) {
				return null;
			}

			return AgeGroup.valueOf(value.toLowerCase());
		} catch (Throwable e) {
			throw new IllegalArgumentException();
		}
	}
}
