package epsilon.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;

public class Utility extends orion.core.Utility {

	public static final Gson gson = new GsonBuilder() //
			.setObjectToNumberStrategy(ToNumberPolicy.BIG_DECIMAL) //
			// .disableHtmlEscaping() //
			.create();

	public static final JsonParser jsonParser = new JsonParser();

	public static final DateTimeFormatter fileDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmm");

	public static final Type typeMapOfStringObject = new TypeToken<Map<String, Object>>() {
	}.getType();

	public static String format(DateTimeFormatter formatter, TemporalAccessor value) {
		return value == null ? null : formatter.format(value);
	}

	public static boolean copyFile(File source, File destination) {
		return copyFile(source, destination, true);
	}

	public static boolean copyFile(File source, File destination, boolean overwrite) {
		try {
			if (!overwrite && destination != null && destination.exists()) {
				return true;
			}
			Files.copy(source, destination);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static List<Map> listify(Enum[] inputList) {
		List<Map> outputList = new ArrayList<>();

		for (Enum element : inputList) {
			outputList.add(Utility.makeMap("value", element, "text", element.name().replace("_", " ")));
		}

		return outputList;
	}

}
