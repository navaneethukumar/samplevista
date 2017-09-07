package com.spire.async;

public class Async {
	String topicName;
	String testSteps;
	String partitions;
	String replicas;
	String method;
	String exception;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getPaartitions() {
		return partitions;
	}

	public void setPaartitions(String paartitions) {
		this.partitions = paartitions;
	}

	public String getReplicas() {
		return replicas;
	}

	public void setReplicas(String replicas) {
		this.replicas = replicas;
	}

	public String getTestSteps() {
		return testSteps;
	}

	public void setTestSteps(String testSteps) {
		this.testSteps = testSteps;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

}
