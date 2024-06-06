package org.marv.responses;

public class Response {
    private String text;

    public Response () {
        setText("Successfull");
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
