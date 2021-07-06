package epsilon.core;

import java.io.File;
import java.net.URL;
import java.util.Properties;

public class Constant extends orion.core.Constant {

	public static final File assetPath;

	public static final Properties propertyRepository;

	static {
		File file = null;
		Properties properties = new Properties();
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource(applicationConfigurationFile);
			if (url != null) {
				properties.load(url.openStream());

				file = new File(url.getPath());
				file = file.getParentFile().getParentFile().getParentFile();
				file = new File(file, "asset");
				System.out.println("Asset location: " + file.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error reading file. Cause: " + e);
		} finally {

		}
		propertyRepository = properties;

		assetPath = file;

		new File(assetPath, "file").mkdirs();
		new File(assetPath, "small").mkdirs();
		new File(assetPath, "medium").mkdirs();
		new File(assetPath, "large").mkdirs();
	}
}
