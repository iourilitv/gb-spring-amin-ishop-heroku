package ru.geekbrains.spring.ishop.informing;

public enum TextTemplates {
    SUBJECT_NEW_ORDER_CREATED("Your order id: %d%n has been created successfully"),
    SUBJECT_ORDER_STATUS_CHANGED("New Status of order id: %d%n. %s"),

    NOTICE_NEW_ORDER_CREATED("Your order id: %d%n has been created successfully"),
    NOTICE_ORDER_STATUS_CHANGED("Status of the order id: %d%n has been changed to %s."),

    ERROR_CREATE_ORDER_MAIL("Unable to create order mail message for order: %d%n."),
    ERROR_SEND_ORDER_MAIL("Unable to send order mail message for order: %d%n.")

    ;
    private final String text;

    TextTemplates(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
