package ru.geekbrains.spring.ishop.informing.messages;

import ru.geekbrains.spring.ishop.informing.TextTemplates;

public abstract class AbstractMessage {
    private String sendTo, subject, body;
    protected Object origin;
    protected TextTemplates textTemplate;

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Object getOrigin() {
        return origin;
    }

    public void setOrigin(Object origin) {
        this.origin = origin;
    }

    public TextTemplates getTextTemplate() {
        return textTemplate;
    }

    public void setTextTemplate(TextTemplates textTemplate) {
        this.textTemplate = textTemplate;
    }
}
