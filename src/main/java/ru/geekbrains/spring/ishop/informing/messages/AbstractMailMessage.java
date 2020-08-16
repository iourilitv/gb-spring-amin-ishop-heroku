package ru.geekbrains.spring.ishop.informing.messages;

public abstract class AbstractMailMessage extends AbstractMessage {
    private String cc;
    private Object attachment;

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

}
