package org.marv.responses;

public class QRCodeDTO {
    private Long id;
    private String img;
    private String text;
    private String mainlink;
    private String shortedlink;
    private String qrimg;
    private String primarycolor;
    private String textcolor;
    private boolean deactivated;
    private String accountId;
    private boolean permalink;
    private long shortUrlId;

    public QRCodeDTO(Long id, String img, String text, String mainlink, String shortedlink,
                     String qrimg, String primarycolor, String textcolor, boolean deactivated, String accid, boolean permalink, long shortUrlId) {
        this.id = id;
        this.img = img;
        this.text = text;
        this.mainlink = mainlink;
        this.shortedlink = shortedlink;
        this.qrimg = qrimg;
        this.primarycolor = primarycolor;
        this.textcolor = textcolor;
        this.deactivated = !deactivated; // Assuming 'deactivated' is the inverse of 'isActive'
        this.accountId = accid;
        this.permalink = permalink;
        this.shortUrlId = shortUrlId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMainlink() {
        return mainlink;
    }

    public void setMainlink(String mainlink) {
        this.mainlink = mainlink;
    }

    public String getShortedlink() {
        return shortedlink;
    }

    public void setShortedlink(String shortedlink) {
        this.shortedlink = shortedlink;
    }

    public String getQrimg() {
        return qrimg;
    }

    public void setQrimg(String qrimg) {
        this.qrimg = qrimg;
    }

    public String getPrimarycolor() {
        return primarycolor;
    }

    public void setPrimarycolor(String primarycolor) {
        this.primarycolor = primarycolor;
    }

    public String getTextcolor() {
        return textcolor;
    }

    public void setTextcolor(String textcolor) {
        this.textcolor = textcolor;
    }

    public boolean isDeactivated() {
        return deactivated;
    }
    public boolean isDeactivated2() {
        return !deactivated;
    }

    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public boolean isPermalink() {
        return permalink;
    }

    public void setPermalink(boolean permalink) {
        this.permalink = permalink;
    }

    public long getShortUrlId() {
        return shortUrlId;
    }

    public void setShortUrlId(long shortUrlId) {
        this.shortUrlId = shortUrlId;
    }

    // Getters and setters for each field
}
