package com.example.aswathy.myagriassist;

/**
 * Created by Aswathy on 11/6/2018.
 */

public class Product_View_Bean {

    String pid,pname,pimg,pqty,prate,pdesc,edate,mdate;

    public String getEdate() {
        return edate;
    }

    public String getMdate() {
        return mdate;
    }

    public String getPdesc() {
        return pdesc;
    }

    public String getPrate() {
        return prate;
    }

    public String getPqty() {
        return pqty;
    }

    public String getPimg() {
        return pimg;
    }

    public String getPname() {
        return pname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public void setPimg(String pimg) {
        this.pimg = pimg;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public void setPqty(String pqty) {
        this.pqty = pqty;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public void setPrate(String prate) {
        this.prate = prate;
    }

}
