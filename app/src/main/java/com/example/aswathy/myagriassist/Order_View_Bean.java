package com.example.aswathy.myagriassist;

/**
 * Created by Aswathy on 11/23/2018.
 */

public class Order_View_Bean {
    String opid,opname,opimg,opqty,oprate,odate;


    public String getOdate() {
        return odate;
    }

    public String getOpid() {
        return opid;
    }

    public String getOpimg() {
        return opimg;
    }

    public String getOpname() {
        return opname;
    }

    public String getOpqty() {
        return opqty;
    }

    public String getOprate() {
        return oprate;
    }

    public void setOdate(String odate) {
        this.odate = odate;
    }

    public void setOpid(String opid) {
        this.opid = opid;
    }

    public void setOpimg(String opimg) {
        this.opimg = opimg;
    }

    public void setOpname(String opname) {
        this.opname = opname;
    }

    public void setOpqty(String opqty) {
        this.opqty = opqty;
    }

    public void setOprate(String oprate) {
        this.oprate = oprate;
    }
}
