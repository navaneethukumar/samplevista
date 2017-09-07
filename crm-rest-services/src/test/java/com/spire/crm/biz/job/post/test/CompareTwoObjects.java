package com.spire.crm.biz.job.post.test;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtilsBean;

public class CompareTwoObjects {

	public static void main(String[] args)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		TestBean test1 = new TestBean("pradeep", 1234);
		TestBean test2 = new TestBean("Pradeep", 1234);
		System.out.println(compareObjects(test1, test2));
	}

	public static boolean compareObjects(Object actual, Object expected)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		BeanMap map = new BeanMap(actual);
		PropertyUtilsBean propUtils = new PropertyUtilsBean();
		for (Object propNameObject : map.keySet()) {
			String propertyName = (String) propNameObject;
			Object property1 = propUtils.getProperty(actual, propertyName);
			Object property2 = propUtils.getProperty(expected, propertyName);
			if (property1 == null)
				continue;
			if (property1.equals(property2)) {
				System.out.println("Property Name >>>  " + propertyName + " is equal");
				
			} else {
				System.out.println("Property Name >>>  " + propertyName + " is different (oldValue=\"" + property1
						+ "\", newValue=\"" + property2 + "\")");
				return false;
			}
		}
		return true;
	}

}
