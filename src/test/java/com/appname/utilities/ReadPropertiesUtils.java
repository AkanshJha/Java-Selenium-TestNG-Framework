package com.appname.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadPropertiesUtils {

	public static Logger log = LogManager.getLogger(ReadPropertiesUtils.class.getName());

	/**
	 * 
	 * @param propertiesFilePath : Path of the properties file you want to read and
	 *                           load
	 * @return the Properties object
	 */
	public static Properties loadPropertiesFile(String propertiesFilePath) {

		Properties properties = new Properties();
		try {
			FileInputStream fis = new FileInputStream(propertiesFilePath);
			properties.load(fis);
			log.debug("Properties of '" + propertiesFilePath + "' has been loaded in the object successfully.");
		} catch (FileNotFoundException e) {
			// log.debug(e.getMessage());
			log.error("Properties File is not available in '" + propertiesFilePath
					+ " folder. Please place the file in this location.", e);
			log.error("Terminating the execution abruptly.");
			System.exit(0);

		} catch (IOException e) {
			// log.debug(e.getMessage());
			log.error("Could not read the file. Please make sure properties file is ready to be used.", e);
		} catch (Exception e) {
			log.fatal("Some unexpected error occured. Please make sure properties file is ready to be used.", e);
		}

		return properties;
	}

	/**
	 * 
	 * @param property     : Pass the object the Properties class
	 * @param propertyName : Name of the property, you want to get the Integer value
	 *                     of
	 * @return : the Interger value of that property.
	 */
	public static int getIntegerPropertyValue(Properties property, String propertyName) {
		int result = -1;
		try {
			result = Integer.valueOf(property.getProperty(propertyName));
			log.debug("'" + result + "' value is fetched for property '" + propertyName + "'.");
			return result;
		} catch (NumberFormatException e) {
			log.error("We fetched an invalid value for '" + propertyName
					+ "' property. Please provide the valid integer value for this property. Please check both the Property Name and its value are correct.",
					e);
			return result;
		}

	}

	/**
	 * 
	 * @param property     : Pass the object the Properties class
	 * @param propertyName : Name of the property, you want to get the String value
	 *                     of
	 * @return : the String value of that property.
	 */
	public static String getStringPropertyValue(Properties property, String propertyName) {
		String result = null;
		result = property.getProperty(propertyName);
		if (result.equals("") && result.isEmpty() && result == null) {
			log.warn("'" + result + "' value is fetched for property '" + propertyName
					+ "'. This may cause failure of test cases. Please check both the Property Name and its value, are correct.");
		} else {
			log.debug("'" + result + "' value is fetched for property '" + propertyName + "'.");
		}
		return result;
	}

	/**
	 * 
	 * @param properties : Pass the object of Properties class.
	 * @return : Set of Keys available in this Properties object.
	 */
	public static Set<Object> getAllPropertiesKeys(Properties properties) {
		return properties.keySet();
	}
}
