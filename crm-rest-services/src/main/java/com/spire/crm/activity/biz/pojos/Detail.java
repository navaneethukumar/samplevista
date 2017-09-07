package com.spire.crm.activity.biz.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Detail {

	private String channel;
	private String status;
	private String from;
	private String to;
	private String subject;
	@JsonProperty("contentHeader")
	private String contentheader;
	@JsonProperty("contentSignature")
	private String contentsignature;
	@JsonProperty("templateId")
	private String templateid;
	private boolean read;
	@JsonProperty("message_id")
	private String messageId;
	@JsonProperty("content_body")
	private String contentBody;
	@JsonProperty("is_read")
	private boolean isRead;
	@JsonProperty("sent_at")
	private String sentAt;
	@JsonProperty("read_at")
	private String readAt;
	@JsonProperty("flow_id")
	private String flowId;
	@JsonProperty("created_on")
	private String createdOn;
	@JsonProperty("created_by")
	private String createdBy;
	@JsonProperty("email_priority")
	private String emailPriority;

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChannel() {
		return channel;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFrom() {
		return from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getTo() {
		return to;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setContentheader(String contentheader) {
		this.contentheader = contentheader;
	}

	public String getContentheader() {
		return contentheader;
	}

	public void setContentsignature(String contentsignature) {
		this.contentsignature = contentsignature;
	}

	public String getContentsignature() {
		return contentsignature;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public boolean getRead() {
		return read;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setContentBody(String contentBody) {
		this.contentBody = contentBody;
	}

	public String getContentBody() {
		return contentBody;
	}

	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}

	public boolean getIsRead() {
		return isRead;
	}

	public void setSentAt(String sentAt) {
		this.sentAt = sentAt;
	}

	public String getSentAt() {
		return sentAt;
	}

	public void setReadAt(String readAt) {
		this.readAt = readAt;
	}

	public String getReadAt() {
		return readAt;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setEmailPriority(String emailPriority) {
		this.emailPriority = emailPriority;
	}

	public String getEmailPriority() {
		return emailPriority;
	}
}

