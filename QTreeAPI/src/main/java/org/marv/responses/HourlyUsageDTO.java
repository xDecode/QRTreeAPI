package org.marv.responses;

public class HourlyUsageDTO {
    private int hour;
    private long count;
    private long qrcodeid;
    private long urlid;

    public HourlyUsageDTO(int hour, long count, long qrcode, long url) {
        this.hour = hour;
        this.count = count;
        this.qrcodeid = qrcode;
        this.urlid = url;
    }


    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getQrcodeid() {
        return qrcodeid;
    }

    public void setQrcodeid(long qrcodeid) {
        this.qrcodeid = qrcodeid;
    }

    public long getUrlid() {
        return urlid;
    }

    public void setUrlid(long urlid) {
        this.urlid = urlid;
    }
}