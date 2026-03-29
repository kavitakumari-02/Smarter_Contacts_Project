package com.springboot.project1.message;

public class PrintingMessage {
private String msgContent;
private String type;
public String getMsgContent() {
	return msgContent;
}
public void setMsgContent(String msgContent) {
	this.msgContent = msgContent;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public PrintingMessage(String msgContent, String type) {
	super();
	this.msgContent = msgContent;
	this.type = type;
}

}
