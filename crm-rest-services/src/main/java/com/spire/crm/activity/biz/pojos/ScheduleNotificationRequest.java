package com.spire.crm.activity.biz.pojos;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Manikanta on 26/12/16.
 */

public class ScheduleNotificationRequest {

    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("trigger_time")
    private ZonedDateTime triggerTime;
    private String content;
    @JsonProperty("flow_id")
    private String flowId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ZonedDateTime getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(ZonedDateTime triggerTime) {
        this.triggerTime = triggerTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override public String toString() {
        return "ScheduleNotificationRequest{" +
                        "userId='" + userId + '\'' +
                        ", triggerTime=" + triggerTime +
                        ", content='" + content + '\'' +
                        ", flowId='" + flowId + '\'' +
                        '}';
    }


}
