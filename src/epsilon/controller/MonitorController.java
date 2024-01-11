package epsilon.controller;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;

import epsilon.model.Template;
import epsilon.service.TemplateService;
import orion.annotation.Parameter;
import orion.annotation.Path;
import orion.view.View;
import orion.view.View.Type;

public class MonitorController extends BaseController {

	@Inject
	TemplateService templateService;

	@Path(value = "/system/monitor/ping", allow = {}, deny = {})
	public View ping() {
		List<Template> templateList = templateService.list();
		if (!templateList.isEmpty()) {
			return new View(Type.TEXT_PLAIN, "OK");
		}
		return new View(Type.TEXT_PLAIN, "Error");
	}

	@Path(value = "/system/monitor/storage", allow = {}, deny = {})
	public View storage( //
			@Parameter("token") String token, //
			@Parameter("action") String action, //
			@Parameter("drive") String drive, //
			@Parameter("path") String path, //
			@Parameter("thresholdSize") Long thresholdSize, //
			@Parameter("thresholdPercentage") Integer thresholdPercentage //
	) {
		if ("epsilon".equals(token)) {
			if ("show".equals(action)) {
				File file = null;
				String text = "";
				if (path != null) {
					file = new File(hexToString(path));
				} else if (drive != null) {
					file = new File(hexToString(drive));
				}
				if (file != null && file.canRead()) {
					text = file.toString() + " total:" + file.getTotalSpace() + " usable:" + file.getUsableSpace() + " free:" + file.getFreeSpace() + " " + ((file.getFreeSpace() * 100) / file.getTotalSpace() + "%");
				}
				return new View(Type.JSON, text);
			} else if ("check".equals(action)) {
				if (thresholdPercentage == null || thresholdPercentage < 0) {
					thresholdPercentage = 20;
				}
				File file = null;
				if (path != null) {
					file = new File(hexToString(path));
				} else if (drive != null) {
					file = new File(hexToString(drive));
				}
				if (file != null && file.canRead()) {
					if (thresholdSize != null) {
						long value = file.getFreeSpace();
						if (value < thresholdSize) {
							return new View(Type.JSON, "Error: threshold reached - " + file.toString() + " - total:" + file.getTotalSpace() + " usable:" + file.getUsableSpace() + " free:" + file.getFreeSpace() + " threshold:" + thresholdSize + " value:" + value);
						} else {
							return new View(Type.JSON, "OK - " + file.toString() + " - total:" + file.getTotalSpace() + " usable:" + file.getUsableSpace() + " free:" + file.getFreeSpace() + " threshold:" + thresholdSize + " value:" + value);
						}
					}
					if (thresholdPercentage != null) {
						int value = (int) Math.floor((double) file.getFreeSpace() * 100 / file.getTotalSpace());
						if (value < thresholdPercentage) {
							return new View(Type.JSON, "Error: threshold reached - " + file.toString() + " - total:" + file.getTotalSpace() + " usable:" + file.getUsableSpace() + " free:" + file.getFreeSpace() + " threshold:" + thresholdPercentage + " value:" + value);
						} else {
							return new View(Type.JSON, "OK - " + file.toString() + " - total:" + file.getTotalSpace() + " usable:" + file.getUsableSpace() + " free:" + file.getFreeSpace() + " threshold:" + thresholdPercentage + " value:" + value);
						}
					}
					return new View(Type.JSON, "Error: no threshold to check");
				}
				return new View(Type.JSON, "Error: could not find path");
			} else {
				return new View(Type.JSON, "Error: unknown action");
			}
		} else {
			return new View(Type.JSON, "Error: wrong token");
		}
	}

	public static String stringToHex(String text) {
		return String.format("%x", new BigInteger(1, text.getBytes()));
	}

	public static String hexToString(String hex) {
		int length = hex.length();
		byte[] data = new byte[length / 2];
		for (int i = 0; i < length; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
		}
		return new String(data);
	}

}
