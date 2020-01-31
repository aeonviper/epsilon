package epsilon.controller;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import epsilon.security.Principal;
import orion.annotation.Request;
import orion.annotation.Response;
import orion.controller.Notification;
import orion.validation.field.DoubleRangeField;
import orion.validation.field.EmailField;
import orion.validation.field.GenericRangeField;
import orion.validation.field.RegexField;
import orion.validation.field.RequiredField;
import orion.validation.field.RequiredStringField;
import orion.validation.field.StringLengthField;
import orion.validation.field.URLField;
import orion.validation.validator.DateRangeValidator;
import orion.validation.validator.DoubleRangeValidator;
import orion.validation.validator.EmailValidator;
import orion.validation.validator.IntegerRangeValidator;
import orion.validation.validator.LongRangeValidator;
import orion.validation.validator.RegexValidator;
import orion.validation.validator.RequiredStringValidator;
import orion.validation.validator.RequiredValidator;
import orion.validation.validator.ShortRangeValidator;
import orion.validation.validator.StringLengthValidator;
import orion.validation.validator.URLValidator;
import orion.view.View;

public class BaseController {

	protected static final Random random = new SecureRandom();

	protected Principal principal;

	protected String parameter;

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	@Request
	public void setRequest(HttpServletRequest request) {
		this.request = request;
		this.principal = (Principal) request.getAttribute(Principal.class.getCanonicalName());
	}

	@Response
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	protected Notification notification = new Notification();
	protected View viewNotification = new View(View.Type.JSON, HttpServletResponse.SC_BAD_REQUEST, getNotificationMap());

	protected RequiredValidator requiredValidator = new RequiredValidator();
	protected RequiredStringValidator requiredStringValidator = new RequiredStringValidator();
	protected StringLengthValidator stringLengthValidator = new StringLengthValidator();
	protected IntegerRangeValidator integerRangeValidator = new IntegerRangeValidator();
	protected LongRangeValidator longRangeValidator = new LongRangeValidator();
	protected ShortRangeValidator shortRangeValidator = new ShortRangeValidator();
	protected DateRangeValidator dateRangeValidator = new DateRangeValidator();
	protected DoubleRangeValidator doubleRangeValidator = new DoubleRangeValidator();
	protected RegexValidator regexValidator = new RegexValidator();
	protected EmailValidator emailValidator = new EmailValidator();
	protected URLValidator urlValidator = new URLValidator();

	protected boolean validateRequired(RequiredField... fields) {
		return requiredValidator.validate(notification, fields);
	}

	protected boolean validateRequiredString(RequiredStringField... fields) {
		return requiredStringValidator.validate(notification, fields);
	}

	protected boolean validateStringLength(StringLengthField... fields) {
		return stringLengthValidator.validate(notification, fields);
	}

	protected boolean validateIntegerRange(GenericRangeField<Integer>... fields) {
		return integerRangeValidator.validate(notification, fields);
	}

	protected boolean validateLongRange(GenericRangeField<Long>... fields) {
		return longRangeValidator.validate(notification, fields);
	}

	protected boolean validateShortRange(GenericRangeField<Short>... fields) {
		return shortRangeValidator.validate(notification, fields);
	}

	protected boolean validateDateRange(GenericRangeField<Date>... fields) {
		return dateRangeValidator.validate(notification, fields);
	}

	protected boolean validateDoubleRange(DoubleRangeField... fields) {
		return doubleRangeValidator.validate(notification, fields);
	}

	protected boolean validateRegex(RegexField... fields) {
		return regexValidator.validate(notification, fields);
	}

	protected boolean validateEmail(EmailField... fields) {
		return emailValidator.validate(notification, fields);
	}

	protected boolean validateURL(URLField... fields) {
		return urlValidator.validate(notification, fields);
	}

	public Notification getNotification() {
		return notification;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
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