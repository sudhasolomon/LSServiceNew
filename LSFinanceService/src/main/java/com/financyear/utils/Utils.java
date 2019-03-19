/*
 * Utils.java Jan 12, 2008
 * 
 * Copyright 2009 Uralian, LLC. All rights reserved.
 */
package com.financyear.utils;

import java.beans.Introspector;
import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import com.financyear.dto.CustomerDto;
import com.financyear.dto.UserDto;

/**
 * A collection of various utility methods.
 * 
 * @author Vlad Orzhekhovskiy
 */
public class Utils {
	private static final Logger log = Logger.getLogger(Utils.class);

	private Utils() {
	}

	private final static ThreadLocal<SimpleDateFormat> sdf=new ThreadLocal<SimpleDateFormat>(){protected SimpleDateFormat initialValue(){return new SimpleDateFormat("yyyy-MM-dd");}};
	
	private final static ThreadLocal<SimpleDateFormat> sdf_WithTime =new ThreadLocal<SimpleDateFormat>(){protected SimpleDateFormat initialValue(){return new SimpleDateFormat("yyyy-MM-dd HH:mm");}};

	private final static ThreadLocal<SimpleDateFormat> sd_SlashFormat=new ThreadLocal<SimpleDateFormat>(){protected SimpleDateFormat initialValue(){return new SimpleDateFormat("M/d/yyyy");}};
	private final static ThreadLocal<SimpleDateFormat> sdfWithTime=new ThreadLocal<SimpleDateFormat>(){protected SimpleDateFormat initialValue(){return new SimpleDateFormat("MMM d,yyyy hh:mm aaa");}};

	private final static ThreadLocal<SimpleDateFormat> sd_SlashWithTimeFormat=new ThreadLocal<SimpleDateFormat>(){protected SimpleDateFormat initialValue(){return new SimpleDateFormat("dd/MM/yyyy hh:mm a");}};
	private final static ThreadLocal<SimpleDateFormat> sdf_SlashWithOutMaridianTimeFormat=new ThreadLocal<SimpleDateFormat>(){protected SimpleDateFormat initialValue(){return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");}};
	private final static ThreadLocal<SimpleDateFormat> INDIA_SDF = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("dd-MM-yyyy");
		}
	};

	/*
	 * W3C helper objects used to build xml documents.
	 */
	private static DocumentBuilder docBuilder;
	private static Map<Class<?>, JAXBContext> ctxMap;

	static {
		ctxMap = new HashMap<Class<?>, JAXBContext>();
		try {
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			docBuilder = dbfac.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public static DocumentBuilder getDocumentBuilder() {
		return docBuilder;
	}

	public static Map<String, String> buildSimpleNodeMap(List<Element> elements) {
		Map<String, String> map = new HashMap<String, String>();
		for (Element node : elements) {
			String name = node.getNodeName();
			String value = node.getFirstChild().getNodeValue();

			map.put(name, value);
		}
		return map;
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isBlank(String str) {
		return isEmpty(str) || str.trim().length() == 0;
	}

	public static int size(String str) {
		return str != null ? str.length() : 0;
	}

	public static boolean isEmpty(Collection<?> coll) {
		return coll == null || coll.isEmpty();
	}

	public static int size(Collection<?> coll) {
		return coll != null ? coll.size() : 0;
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	public static int size(Map<?, ?> map) {
		return map != null ? map.size() : 0;
	}

	public static boolean isEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

	public static boolean isEmpty(byte[] array) {
		return array == null || array.length == 0;
	}

	public static int size(Object[] array) {
		return array != null ? array.length : 0;
	}

	public static int safeHashCode(Object obj) {
		return obj != null ? obj.hashCode() : 0;
	}

	public static boolean isOneOf(Object choice, Object... values) {
		if (choice == null)
			return false;

		for (Object value : values) {
			if (value.equals(choice))
				return true;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public static <T> T choice(int seed, T... values) {
		return values[seed];
	}

	/**
	 * There is a problem with {@link Math#round(double)} method, as it produces
	 * asymmetric results for positive and negative numbers:
	 * 
	 * <blockquote>
	 * 
	 * <pre>
	 * Math.round(0.5) == 1
	 * but
	 * Math.round(-0.5) == 0
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param a
	 * @return
	 */
	public static int round(double a) {
		return a >= 0 ? (int) Math.floor(a + 0.5) : (int) Math.ceil(a - 0.5);
	}

	public static boolean safeEqualsCheck(Object obj1, Object obj2) {
		return obj1 != null ? obj1.equals(obj2) : obj2 == null;
	}

	public static <T> int safeCompare(Comparable<T> c1, T c2) {
		if (c1 == null)
			return c2 == null ? 0 : -1;
		else if (c2 == null)
			return 1;
		else
			return c1.compareTo(c2);
	}

	public static <T> int safeCompareStrings(String s1, String s2, boolean ignoreCase) {
		if (s1 == null || s2 == null)
			return safeCompare(s1, s2);
		else if (ignoreCase)
			return s1.compareToIgnoreCase(s2);
		else
			return s1.compareTo(s2);
	}

	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static String extractFieldName(Method method) {
		String methodName = method.getName();

		String fName = null;
		if (methodName.startsWith("get") || methodName.startsWith("set"))
			fName = methodName.substring(3);
		else if (methodName.startsWith("is"))
			fName = methodName.substring(2);

		return fName != null ? fName.substring(0, 1).toLowerCase() + fName.substring(1) : null;
	}

	public static Map<String, Accessors> buildAccessMap(Class<?> targetClass) {
		Map<String, Accessors> map = new HashMap<String, Accessors>();

		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			Class<?>[] params = method.getParameterTypes();

			String fieldName = extractFieldName(method);
			if (fieldName != null) {
				Accessors accessors = map.get(fieldName);
				if (accessors == null) {
					accessors = new Accessors();
					map.put(fieldName, accessors);
				}

				if (methodName.startsWith("get") && isEmpty(params))
					accessors.getter = method;
				else if (methodName.startsWith("is") && isEmpty(params))
					accessors.getter = method;
				else if (methodName.startsWith("set") && isEmpty(params))
					accessors.setter = method;
			}
		}

		return map;
	}

	public static class Accessors {
		public Method getter;
		public Method setter;
	}

	public static Map<String, Object> buildPropMap(Object... props) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < props.length; i += 2) {
			String key = (String) props[i];
			Object value = props[i + 1];

			map.put(key, value);
		}
		return map;
	}

	public static void copyProperty(Object target, String propName, Object value, boolean force) {
		try {
			if (value != null || force) {
				Field field = target.getClass().getDeclaredField(propName);
				field.setAccessible(true);
				field.set(target, value);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Converts a Java object into XML.
	 * 
	 * @param obj
	 *            object to convert.
	 * @param os
	 *            generic output stream.
	 * @throws JAXBException
	 *             if an error occurs during conversion.
	 */
	public static <T> void toXml(T obj, OutputStream os) throws JAXBException {
		toXml(obj, new StreamResult(os));
	}

	/**
	 * Converts a Java object into XML.
	 * 
	 * @param obj
	 *            object to convert.
	 * @param writer
	 *            generic writer.
	 * @throws JAXBException
	 *             if an error occurs during conversion.
	 */
	public static <T> void toXml(T obj, Writer writer) throws JAXBException {
		toXml(obj, new StreamResult(writer));
	}

	/**
	 * Converts a Java object to XML.
	 * 
	 * @param obj
	 *            object to convert.
	 * @param result
	 *            generic XML result.
	 * @throws JAXBException
	 *             if an error occurs during conversion.
	 */
	@SuppressWarnings("unchecked")
	public static <T> void toXml(T obj, Result result) throws JAXBException {
		Class<T> clazz = (Class<T>) obj.getClass();
		String typeName = Introspector.decapitalize(clazz.getSimpleName());

		JAXBContext context = getContext(clazz);
		QName qName = new QName(typeName);
		JAXBElement<T> jaxbObject = new JAXBElement<T>(qName, clazz, obj);

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.setProperty(Marshaller.JAXB_FRAGMENT, true);
		m.marshal(jaxbObject, result);
	}

	/**
	 * Returns the JAXB context for the specified class.
	 * 
	 * @param type
	 *            Java type.
	 * @return statically cached JAXB context.
	 * @throws JAXBException
	 *             if an error occurs while creating a new context.
	 */
	private static <T> JAXBContext getContext(Class<T> type) throws JAXBException {
		JAXBContext ctx = ctxMap.get(type);
		if (ctx == null) {
			ctx = JAXBContext.newInstance(type);
			ctxMap.put(type, ctx);
		}

		return ctx;
	}

	/**
	 * Parses a string parameter in form "name separator value" and returns an
	 * array of 2 elements: first contains the name, and the second - the value.
	 * 
	 * @param token
	 *            string token to parse.
	 * @param separator
	 *            separator regular expression.
	 * @param errorIfEmpty
	 *            if the token is actually in form "name" or "name separator"
	 *            (i.e. no value is specified), and this flag is set - the
	 *            method will throw an exception. Otherwise, the second array
	 *            element will be <code>null</code>.
	 * @return array of two elements containing the name and the value of the
	 *         parameter.
	 * @throws IllegalArgumentException
	 *             if the <code>errorIfEmpty</code> flag set, and there is no
	 *             value.
	 */
	public static String[] parseParameter(String token, String separator, boolean errorIfEmpty) throws IllegalArgumentException {
		String[] parts = token.split(separator, 2);
		if (isEmpty(parts))
			throw new IllegalArgumentException("Empty parameter: " + token);

		if (parts.length != 2 && errorIfEmpty)
			throw new IllegalArgumentException("Value not set for: " + parts[0]);

		return parts.length == 2 ? parts : new String[] { parts[0], null };
	}

	/**
	 * Sanitizes the string argument to make it safe to output into the log, by
	 * removing CR and LF characters and replacing non-printable data with a
	 * single dot.
	 * 
	 * @param str
	 *            string to sanitize.
	 */
	public static String sanitize(String str) {
		return str.replaceAll("\\r*\\n", " ").replaceAll("[\r\u0085\u2028\u2029]+", " ").replaceAll("[^\\p{Print}]+", ".");
	}

	/**
	 * Checks whether the supplied file path is valid and returns the canonical
	 * file this path points to.
	 * 
	 * @param filePath
	 *            path to check.
	 * @return canonical file.
	 * @throws IllegalArgumentException
	 *             if the path has an invalid format.
	 */
	public static File canonize(String filePath) throws IllegalArgumentException {
		try {
			File file = new File(filePath);
			return file.getCanonicalFile();
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid file format: " + filePath);
		}
	}

	/**
	 * Replaces special character with their escape equivalents.
	 * 
	 * @param text
	 * @return
	 */
	public static String escapeHtml(String text) {
		if (Utils.isEmpty(text))
			return text;

		StringBuffer result = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);

			if (ch == '<')
				result.append("&lt;");
			else if (ch == '>')
				result.append("&gt;");
			else if (ch == '&')
				result.append("&amp;");
			else if (ch == '\"')
				result.append("&quot;");
			else
				result.append(ch);
		}

		return result.toString();
	}

	public static String formatRGB(int rgb) {
		StringBuffer sb = new StringBuffer();
		sb.append(Integer.toHexString(rgb));
		while (sb.length() < 6)
			sb.insert(0, "0");

		return sb.toString();
	}

	public static String getStringFromByteArray(byte[] bytes) {
		String str;
		if (bytes != null) {
			str = new String(bytes);
			return str;
		} else {
			return null;
		}
	}

	/* dao utils */

	public static Map<String, Object> toMap(String[] names, Object[] values) {
		if (names.length != values.length)
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");

		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < names.length; i++)
			map.put(names[i], values[i]);
		return map;
	}

	public static String nullIfBlank(String s) {
		return s != null && s.trim().length() == 0 ? null : s;
	}

	public static String blankIfNull(Object o) {
		return o != null ? o.toString() : "";
	}

	/**
	 * @param str
	 * @param prefix
	 * @param suffix
	 * @param from
	 * @return
	 */
	public static String findSubstring(String str, String prefix, String suffix, int from) {
		if (str == null) {
			return null;
		}
		int index1 = !Utils.isEmpty(prefix) ? str.indexOf(prefix, from) : from;
		if (index1 < 0)
			return null;

		int index2 = !Utils.isEmpty(suffix) ? str.indexOf(suffix, index1 + prefix.length()) : str.length();
		if (index2 < 0)
			return null;

		String substr = str.substring(index1 + prefix.length(), index2).trim();
		return substr;
	}

	public static boolean isNull(Object object) {

		return (object == null ? true : false);

	}

	public static String parseDateRange(Map<String, String> map) {

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = null;
		Date enddate = null;
		String date1 = null;
		String date2 = null;
		String retVal = "";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		try {
			startdate = sdf1.parse(map.get("startDate").substring(0, 10));
			enddate = sdf1.parse(map.get("endDate").substring(0, 10));
			date1 = sdf.format(startdate);
			date2 = sdf.format(enddate);
			retVal = date1 + " TO " + date2;
		} catch (Exception ex) {
			log.error("Exception in parsing date ranges", ex);
		}
		return retVal;
	}

	public static Date convertAngularStrToDate(String strDate) {
		Date date = null;
		try {
			if (strDate != null && strDate.length() >= 10) {
				// One day is less from angularjs
				date = sdf.get().parse(strDate.substring(0, 10));
				// Calendar cal = Calendar.getInstance();
				// cal.setTime(date);
				// cal.add(Calendar.DATE, 1);
				// date = cal.getTime();
			} else {
				date = convertStringToDateWithSlash(strDate);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return date;
	}

	public static Date getDateWithTimeFromDate(Date date) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(date);
			Calendar presentDate = Calendar.getInstance();
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), presentDate.get(Calendar.HOUR), presentDate.get(Calendar.MINUTE),
					presentDate.get(Calendar.SECOND));

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return cal.getTime();
	}

	public static Date convertDateWithTimeToDate(String strDate) {
		Date date = null;
		try {
			if (strDate != null && strDate.trim().length() > 0) {
				date = sdf_SlashWithOutMaridianTimeFormat.get().parse(strDate);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return date;
	}

	public static String convertDateToStringWithTime_WithOutMeridian(Date date) {
		String strValue = null;
		try {
			if (date != null) {
				strValue = sdf_SlashWithOutMaridianTimeFormat.get().format(date);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return strValue;
	}

	public static Date convertStringToDate(String strDate) {
		Date date = null;
		try {
			if (strDate != null && strDate.trim().length() > 0) {
				date = sdf.get().parse(strDate);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error at convertStringToDate() in Utils : " + e.getMessage(), e);
		}
		return date;
	}

	public static String convertDateToString(Date date) {
		String strDate = null;
		try {
			if (date != null) {
				strDate = sdf.get().format(date);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error at convertDateToString() in Utils : " + e.getMessage(), e);
		}
		return strDate;
	}

	public static String convertDateToStringWithSlash(Date date) {
		String strDate = null;
		try {
			if (date != null) {
				strDate = sd_SlashFormat.get().format(date);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error at convertDateToString() in Utils : " + e.getMessage(), e);
		}
		return strDate;
	}

	public static String convertDateToStringWithTimelash(Date date) {
		String strDate = null;
		try {
			if (date != null) {
				strDate = sd_SlashWithTimeFormat.get().format(date);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error at convertDateToString() in Utils : " + e.getMessage(), e);
		}
		return strDate;
	}

	public static Date convertStringToDateWithSlash(String strDate) {
		Date date = null;
		try {
			if (strDate != null) {
				date = sd_SlashFormat.get().parse(strDate);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error at convertDateToString() in Utils : " + e.getMessage(), e);
		}
		return date;
	}

	public static String convertDateToStringWithTime(Date date) {
		String strDate = null;
		try {
			if (date != null) {
				strDate = sdfWithTime.get().format(date);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error at convertDateToString() in Utils : " + e.getMessage(), e);
		}
		return strDate;
	}
	
	public static String convertDateToStringWithTimeForSorting(Date date) {
		String strDate = null;
		try {
			if (date != null) {
				strDate = sdf_WithTime.get().format(date);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error at convertDateToString() in Utils : " + e.getMessage(), e);
		}
		return strDate;
	}


	public static String findActiveDays(Date date) {
		long days = 0;
		if (date != null) {
			Date today = Utils.convertStringToDate(Utils.convertDateToString(new Date()));
			long daysDiff = today.getTime() - date.getTime();
			days = daysDiff / (24 * 60 * 60 * 1000);
		}
		return String.valueOf(days);
	}

	
	public static Date convertStringToDate_India(String strDate) {
		Date date = null;
		try {
			if (strDate != null && strDate.trim().length() > 0) {
				date = INDIA_SDF.get().parse(strDate);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error at convertStringToDate() in Utils : " + e.getMessage(), e);
		}
		return date;
	}

	public static String convertDateToString_India(Date date) {
		String strDate = null;
		try {
			if (date != null) {
				strDate = INDIA_SDF.get().format(date);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error at convertDateToString() in Utils : " + e.getMessage(), e);
		}
		return strDate;
	}
	
	public static String convertDateToString_IndiaWithSlashes(Date date) {
		String strDate = null;
		try {
			if (date != null) {
				strDate = INDIA_SDF.get().format(date);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error at convertDateToString() in Utils : " + e.getMessage(), e);
		}
		return strDate;
	}

	
	public static String[] getStrArray_FromStr(String str) {
		String strArray[] = null;
		if (str != null && str.length() > 0) {
			strArray = str.split(",");
			for (int i = 0; i < strArray.length; i++) {
				strArray[i] = strArray[i].trim();
			}
		}
		return strArray;
	}

	public static String getStringFromArray(String[] strArray) {
		String strValue = "";
		if (strArray != null && strArray.length > 0) {
			strValue = Arrays.asList(strArray).toString().replace("[", "").replace("]", "");
		}
		return strValue;
	}

	public static String getStringValueOfObj(Object obj) {
		String strValue = "";
		if (obj != null) {
			strValue = obj.toString();
		}
		return strValue;
	}

	public static String replaceNullWithEmpty(String value) {
		String strValue = "";
		if (value != null && value.length() > 0) {
			strValue = value.toString();
		}
		return strValue;
	}

	public static String getFileMimeTypeByEnum(String strContentType) {
		String mimeType = "DOCX";
		if (strContentType != null) {
			if (strContentType.equalsIgnoreCase("PLAIN")) {
				mimeType = "txt";
			}
			if (strContentType.equalsIgnoreCase("MS_WORD")) {
				mimeType = "doc";
			}
			if (strContentType.equalsIgnoreCase("DOCX")) {
				mimeType = "docx";
			}
			if (strContentType.equalsIgnoreCase("HTML")) {
				mimeType = "html";
			}
			if (strContentType.equalsIgnoreCase("PDF")) {
				mimeType = "pdf";
			}
			if (strContentType.equalsIgnoreCase("RTF")) {
				mimeType = "rtf";
			}
		}
		return mimeType;
	}

	public static Integer getDefaultIntegerValue(Integer value) {
		return value != null ? value : 0;
	}

	public static Boolean getDefaultBooleanValue(Boolean value) {
		return value != null ? value : false;
	}

	public static Boolean convertStringToBooleanValue(String value) {
		Boolean boolValue = false;
		if (value != null && value.equals(Constants.YES)) {
			boolValue = true;
		} else {
			boolValue = false;
		}
		return boolValue;
	}

	public static String convertBooleanToStringValue(Boolean value) {
		String strValue = null;
		if (value != null && value) {
			strValue = Constants.YES;
		} else {
			strValue = Constants.NO;
		}
		return strValue;
	}

	public static Long getDefaultLongValue(Long value) {
		return value != null ? value : 0l;
	}

	public static String getLoginUserName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserDto dto = (UserDto) session.getAttribute(Constants.LOGIN_USER);
		if (dto != null) {
			return dto.getUserName();
		}
		return null;
	}

	public static UserDto getLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserDto dto = (UserDto) session.getAttribute(Constants.LOGIN_USER);
		return dto;
	}

	

	public static String getFileExtensionByName(String fileName) {
		String extension = null;
		if (fileName != null) {
			String strArray[] = fileName.split("\\.");
			extension = strArray[strArray.length - 1];
		}
		return extension;
	}

 

	public static String concatenateTwoStrings(String str1, String str2) {
		String strValue = null;
		if (str1 != null && str2 != null) {
			strValue = str1 + "_" + str2;
		}
		if (str1 != null && str2 == null) {
			strValue = str1 + "_" + "";
		}
		if (str1 == null && str2 != null) {
			strValue = "" + "_" + str2;
		}
		return strValue;
	}

	public static String concatenateTwoStringsWithPipe(String str1, String str2) {
		String strValue = null;
		if (str1 != null && str2 != null) {
			strValue = str1 + "|" + str2;
		}
		if (str1 != null && str2 == null) {
			strValue = str1 + "|" + "";
		}
		if (str1 == null && str2 != null) {
			strValue = "" + "|" + str2;
		}
		return strValue;
	}

	public static String concatenateTwoStringsWithSemicolon(String str1, String str2) {
		String strValue = "";
		if (str1 != null && str2 != null) {
			strValue = str1 + ";" + str2;
		}
		if (str1 != null && str2 == null) {
			strValue = str1;
		}
		if (str1 == null && str2 != null) {
			strValue = str2;
		}
		return strValue;
	}

	public static String concatenateTwoStringsWithSpace(String str1, String str2) {
		String strValue = null;
		if (str1 != null && str2 != null) {
			strValue = str1 + " " + str2;
		}
		if (str1 != null && str2 == null) {
			strValue = str1;
		}
		if (str1 == null && str2 != null) {
			strValue = str2;
		}
		return strValue;
	}

	public static String concatenateTwoStringsWithComma(String str1, String str2) {
		String strValue = null;
		if (str1 != null && str2 != null) {
			strValue = str1 + "," + str2;
		}
		if (str1 != null && str2 == null) {
			strValue = str1;
		}
		if (str1 == null && str2 != null) {
			strValue = str2;
		}
		return strValue;
	}

	public static Object getOneValueFromTwo(Object val1, Object val2) {
		Object value = null;
		if (val1 != null) {
			value = val1;
			return value;
		}
		if (val2 != null) {
			value = val2;
			return value;
		}
		return value;
	}

	public static String[] splitAWordIntoTwoStrings(String strValue) {
		String[] strArray = null;
		if (strValue != null) {
			strArray = strValue.split("_");
			if (strArray.length == 1) {
				strArray = new String[2];
				strArray[0] = strValue.split("_")[0];
				strArray[1] = null;
			}
		}
		if (strValue == null || strArray.length == 0) {
			strArray = new String[2];
			strArray[0] = null;
			strArray[1] = null;
		}
		return strArray;
	}

	public static String[] splitAPipeWordIntoTwoStrings(String strValue) {
		String[] strArray = null;
		if (strValue != null) {
			strArray = strValue.split("\\|");
			if (strArray.length == 1) {
				strArray = new String[2];
				strArray[0] = strValue.split("\\|")[0];
				strArray[1] = null;
			}
		}
		if (strValue == null || strArray.length == 0) {
			strArray = new String[2];
			strArray[0] = null;
			strArray[1] = null;
		}
		return strArray;
	}

	public static List<String> convertStringToArrayList(String stringWithCommas) {
		List<String> stringList = null;
		if (stringWithCommas != null) {
			stringList = Arrays.asList(stringWithCommas.split("\\s*,\\s*"));
		}
		return stringList;
	}

	public static String getStringFromList(List<String> relocationBenefits) {
		String strValue = null;
		if (relocationBenefits != null && relocationBenefits.size() > 0) {
			for (String value : relocationBenefits) {
				if (strValue == null) {
					strValue = value;
				} else {
					strValue += "," + value;
				}
			}
		}
		return strValue;
	}

	public static String getFileExtensionFromBase64String(String base64EncodedStr) {
		String extensionType = null;
		try {
			if (base64EncodedStr != null && base64EncodedStr.length() > 0) {
				extensionType = base64EncodedStr.split(",")[0].split(":")[1].split(";")[0].split("/")[1];
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return extensionType;
	}

	public static Date getDateFormMonthAndYear(String month, String year) {
		Date date = null;
		if(!isBlank(year)&& !isBlank(month)){
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.set(Integer.parseInt(year), Integer.parseInt(month), 1, 0, 0, 0 );
			 date = calendar.getTime();
		}
		return date;
	}

	public static Date getlastDateOfMonth(String month, String year) {
		Date date = null;
		if(!isBlank(year)&& !isBlank(month)){
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.set(Integer.parseInt(year), Integer.parseInt(month), 1, 0, 0, 0 );
			calendar.add(Calendar.MONTH, +1);
			date = calendar.getTime();
		}
		return date;
	}

	

 

}