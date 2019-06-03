package com.app.goalwise.models;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FundsList {

    @SerializedName("fundname")
    @Expose
    private String fundname;
    @SerializedName("minsipamount")
    @Expose
    private Integer minsipamount;
    @SerializedName("minsipmultiple")
    @Expose
    private Integer minsipmultiple;
    @SerializedName("sipdates")
    @Expose
    private List<Integer> sipdates = null;
    private boolean isClicked;

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public String getFundname() {
        return fundname;
    }

    public void setFundname(String fundname) {
        this.fundname = fundname;
    }

    public Integer getMinsipamount() {
        return minsipamount;
    }

    public void setMinsipamount(Integer minsipamount) {
        this.minsipamount = minsipamount;
    }

    public Integer getMinsipmultiple() {
        return minsipmultiple;
    }

    public void setMinsipmultiple(Integer minsipmultiple) {
        this.minsipmultiple = minsipmultiple;
    }

    public List<Integer> getSipdates() {
        return sipdates;
    }

    public void setSipdates(List<Integer> sipdates) {
        this.sipdates = sipdates;
    }

}
