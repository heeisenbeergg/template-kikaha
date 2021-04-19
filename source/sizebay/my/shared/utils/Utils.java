package sizebay.my.shared.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sizebay.catalog.client.model.StrongCategoryType;
import sizebay.my.shared.enums.AgeGroup;
import sizebay.my.shared.enums.Gender;
import sizebay.my.shared.enums.SizeType;
import sizebay.my.shared.exceptions.MySizebayExceptions.EnumConstantNotPresentException;

import java.util.Deque;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String getParameter(Deque<String> parameter) {
		return parameter != null ? parameter.getFirst().toLowerCase() : null;
	}

	// FIXME: Refactor me, please
	public static <T> String sanitizeEnumToFilter(Class<T> enumType, String value, String errorMessage) throws EnumConstantNotPresentException {
		if(value == null || value.isEmpty()) {
			return value;
		}

		try {
			final String enumName = enumType.getName();

			switch (enumName) {
				case "sizebay.my.shared.enums.AgeGroup":
					return Enum.valueOf(AgeGroup.class, value).name();
				case "sizebay.my.shared.enums.Gender":
					return Enum.valueOf(Gender.class, value).name();
				case "sizebay.my.shared.enums.SizeType":
					return SizeType.from(value).sizeType;
				case "sizebay.catalog.client.model.StrongCategoryType":
					return Enum.valueOf(StrongCategoryType.class, value).name();
			}

			return null;
		} catch (IllegalArgumentException e) {
			throw new EnumConstantNotPresentException(errorMessage);
		}
	}

	public static String generateJSON(Object value) throws JsonProcessingException {
		return objectMapper.writeValueAsString(value);
	}

}
