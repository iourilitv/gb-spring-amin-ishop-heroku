package ru.geekbrains.spring.ishop.utils.websocket;

public class Request {
    private String prodId;
    private String quantity;
    private String title;

    public String getProdId() {
        return prodId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTitle() {
        return title;
    }
}
