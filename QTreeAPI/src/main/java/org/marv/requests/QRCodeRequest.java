package org.marv.requests;

public class QRCodeRequest {
    private String url;
    private String img;  // Logo image URL
    private String text;
    private boolean permalink;
    private String primaryColor;
    private String textColor;
    private String accountId;

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public boolean isPermalink() { return permalink; }
    public void setPermalink(boolean permalink) { this.permalink = permalink; }

    public String getPrimaryColor() { return primaryColor; }
    public void setPrimaryColor(String primaryColor) { this.primaryColor = primaryColor; }

    public String getTextColor() { return textColor; }
    public void setTextColor(String textColor) { this.textColor = textColor; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
}
