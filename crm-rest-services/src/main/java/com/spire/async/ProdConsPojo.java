package com.spire.async;

public class ProdConsPojo {

	String messages;
	String testSteps;
	String topic;
	String partitions;
	String replica;
	String readDataCount;
	String expectedOffset;
	String expectedLag;
	String exception;
	String resend;
	String sendMessaageAs;

	public String getResend() {
		return resend;
	}

	public void setResend(String resend) {
		this.resend = resend;
	}

	public String getSendMessaageAs() {
		return sendMessaageAs;
	}

	public void setSendMessaageAs(String sendMessaageAs) {
		this.sendMessaageAs = sendMessaageAs;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getExpectedOffset() {
		return expectedOffset;
	}

	public void setExpectedOffset(String expectedOffset) {
		this.expectedOffset = expectedOffset;
	}

	public String getExpectedLag() {
		return expectedLag;
	}

	public void setExpectedLag(String expectedLag) {
		this.expectedLag = expectedLag;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public String getTestSteps() {
		return testSteps;
	}

	public void setTestSteps(String testSteps) {
		this.testSteps = testSteps;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getPartitions() {
		return partitions;
	}

	public void setPartitions(String partitions) {
		this.partitions = partitions;
	}

	public String getReplica() {
		return replica;
	}

	public void setReplica(String replica) {
		this.replica = replica;
	}

	public String getReadDataCount() {
		return readDataCount;
	}

	public void setReadDataCount(String readDataCount) {
		this.readDataCount = readDataCount;
	}

}
