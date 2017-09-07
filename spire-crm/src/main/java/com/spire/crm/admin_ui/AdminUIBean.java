package com.spire.crm.admin_ui;

public class AdminUIBean {

	private String ruleName;
	private String fieldName;
	private String operator;
	private String value;
	private String actionName;
	private String properties;
	private String storeNewRule;

	public String getStoreNewRule() {
		return storeNewRule;
	}

	public void setStoreNewRule(String storeNewRule) {
		this.storeNewRule = storeNewRule;
	}

	public String getRuleName() {
		return ruleName;
		
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

}
