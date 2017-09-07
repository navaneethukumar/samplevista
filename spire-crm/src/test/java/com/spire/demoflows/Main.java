package com.spire.demoflows;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.spire.base.controller.ContextManager;
import com.spire.base.helper.SpireProperties;

public class Main {

	public static void main(String[] args) {

		String ui_Host = ContextManager.getGlobalContext().getUIHostAddress()
				+ SpireProperties.loadEndPointProperties().getProperty(
						"CRM_VISTA_UI");
		System.out.println(ui_Host);

		WebDriver d = new FirefoxDriver();
		d.get(ui_Host);

	}

}
