package epsilon.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import orion.controller.Notification;
import orion.validation.field.DoubleRangeField;
import orion.validation.field.EmailField;
import orion.validation.field.GenericRangeField;
import orion.validation.field.RegexField;
import orion.validation.field.RequiredField;
import orion.validation.field.RequiredStringField;
import orion.validation.field.StringLengthField;
import orion.validation.field.URLField;
import orion.validation.validator.DoubleRangeValidator;
import orion.validation.validator.EmailValidator;
import orion.validation.validator.IntegerRangeValidator;
import orion.validation.validator.LocalDateRangeValidator;
import orion.validation.validator.LocalDateTimeRangeValidator;
import orion.validation.validator.LongRangeValidator;
import orion.validation.validator.RegexValidator;
import orion.validation.validator.RequiredStringValidator;
import orion.validation.validator.RequiredValidator;
import orion.validation.validator.ShortRangeValidator;
import orion.validation.validator.StringLengthValidator;
import orion.validation.validator.URLValidator;
import orion.view.View;

public class BaseController {

	protected Notification notification = new Notification();

	protected View badRequestNotification = new View(View.Type.JSON, HttpServletResponse.SC_BAD_REQUEST, getNotificationMap());
	protected View unauthorizedNotification = new View(View.Type.JSON, HttpServletResponse.SC_UNAUTHORIZED, getNotificationMap());
	protected View forbiddenNotification = new View(View.Type.JSON, HttpServletResponse.SC_FORBIDDEN, getNotificationMap());
	protected View notFoundNotification = new View(View.Type.JSON, HttpServletResponse.SC_NOT_FOUND, getNotificationMap());
	protected View errorNotification = new View(View.Type.JSON, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, getNotificationMap());

	protected View ok = new View(View.Type.JSON_TEXT, HttpServletResponse.SC_OK, "");
	protected View badRequest = new View(View.Type.JSON_TEXT, HttpServletResponse.SC_BAD_REQUEST, "");
	protected View unauthorized = new View(View.Type.JSON_TEXT, HttpServletResponse.SC_UNAUTHORIZED, "");
	protected View forbidden = new View(View.Type.JSON_TEXT, HttpServletResponse.SC_FORBIDDEN, "");
	protected View notFound = new View(View.Type.JSON_TEXT, HttpServletResponse.SC_NOT_FOUND, "");
	protected View error = new View(View.Type.JSON_TEXT, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "");

	public View ok(Object value) {
		return new View(View.Type.JSON, value);
	}

	public View badRequest(Object value) {
		return new View(View.Type.JSON, HttpServletResponse.SC_BAD_REQUEST, value);
	}

	public View unauthorized(Object value) {
		return new View(View.Type.JSON, HttpServletResponse.SC_UNAUTHORIZED, value);
	}

	public View forbidden(Object value) {
		return new View(View.Type.JSON, HttpServletResponse.SC_FORBIDDEN, value);
	}

	public View notFound(Object value) {
		return new View(View.Type.JSON, HttpServletResponse.SC_NOT_FOUND, value);
	}

	public View error(Object value) {
		return new View(View.Type.JSON, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, value);
	}

	protected RequiredValidator requiredValidator = new RequiredValidator();
	protected RequiredStringValidator requiredStringValidator = new RequiredStringValidator();
	protected StringLengthValidator stringLengthValidator = new StringLengthValidator();
	protected IntegerRangeValidator integerRangeValidator = new IntegerRangeValidator();
	protected LongRangeValidator longRangeValidator = new LongRangeValidator();
	protected ShortRangeValidator shortRangeValidator = new ShortRangeValidator();
	protected LocalDateRangeValidator localDateRangeValidator = new LocalDateRangeValidator();
	protected LocalDateTimeRangeValidator localDateTimeRangeValidator = new LocalDateTimeRangeValidator();
	protected DoubleRangeValidator doubleRangeValidator = new DoubleRangeValidator();
	protected RegexValidator regexValidator = new RegexValidator();
	protected EmailValidator emailValidator = new EmailValidator();
	protected URLValidator urlValidator = new URLValidator();

	protected boolean validateRequired(RequiredField... fieldArray) {
		return requiredValidator.validate(notification, fieldArray);
	}

	protected boolean validateRequiredString(RequiredStringField... fieldArray) {
		return requiredStringValidator.validate(notification, fieldArray);
	}

	protected boolean validateStringLength(StringLengthField... fieldArray) {
		return stringLengthValidator.validate(notification, fieldArray);
	}

	protected boolean validateIntegerRange(GenericRangeField<Integer>... fieldArray) {
		return integerRangeValidator.validate(notification, fieldArray);
	}

	protected boolean validateLongRange(GenericRangeField<Long>... fieldArray) {
		return longRangeValidator.validate(notification, fieldArray);
	}

	protected boolean validateShortRange(GenericRangeField<Short>... fieldArray) {
		return shortRangeValidator.validate(notification, fieldArray);
	}

	protected boolean validateLocalDateRange(GenericRangeField<LocalDate>... fieldArray) {
		return localDateRangeValidator.validate(notification, fieldArray);
	}

	protected boolean validateLocalDateTimeRange(GenericRangeField<LocalDateTime>... fieldArray) {
		return localDateTimeRangeValidator.validate(notification, fieldArray);
	}

	protected boolean validateDoubleRange(DoubleRangeField... fieldArray) {
		return doubleRangeValidator.validate(notification, fieldArray);
	}

	protected boolean validateRegex(RegexField... fieldArray) {
		return regexValidator.validate(notification, fieldArray);
	}

	protected boolean validateEmail(EmailField... fieldArray) {
		return emailValidator.validate(notification, fieldArray);
	}

	protected boolean validateURL(URLField... fieldArray) {
		return urlValidator.validate(notification, fieldArray);
	}

	protected boolean validateRequired(Object... array) {
		if (array.length % 2 != 0) {
			throw new RuntimeException("Validation array length should be even");
		}
		RequiredField[] fieldArray = new RequiredField[array.length / 2];
		for (int i = 0; i < array.length; i = i + 2) {
			fieldArray[i / 2] = new RequiredField((String) array[i], array[i + 1]);
		}
		return validateRequired(fieldArray);
	}

	protected boolean validateRequiredString(Object... array) {
		if (array.length % 2 != 0) {
			throw new RuntimeException("Validation array length should be even");
		}
		RequiredStringField[] fieldArray = new RequiredStringField[array.length / 2];
		for (int i = 0; i < array.length; i = i + 2) {
			fieldArray[i / 2] = new RequiredStringField((String) array[i], array[i + 1]);
		}
		return validateRequiredString(fieldArray);
	}

	public Notification getNotification() {
		return notification;
	}

	public Map<String, Object> getNotificationMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("type", "NOTIFICATION");
		map.put("noticeList", notification.getNoticeList());
		map.put("errorList", notification.getErrorList());
		map.put("fieldErrorList", notification.getFieldErrorList());
		return map;
	}

}