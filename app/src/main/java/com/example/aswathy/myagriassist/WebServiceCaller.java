package com.example.aswathy.myagriassist;



import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Android on 21-08-2015.
 */

final public class WebServiceCaller {

    private String response;
    private int responseCode;
    private SoapObject request;
    private String url;
    private String exception;
    private String soapAction;


    public void setSoapObject(String methodName) {
        request = new SoapObject("http://db/", methodName);
        url = "http://192.168.43.244:8084/AgriWebappln/agriwebservice";
    }

    public void addProperty(String key, Object value){
        request.addProperty(key, value);
    }

    public void setSoapActon() {
        this.soapAction = "";
    }

    public void setUrl(String url) {

        this.url = url;

    }

    public String getResponse() {

        return response;

    }

    public void callWebService() {

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransportSE = new HttpTransportSE(url, 60*1000);

        try {

            httpTransportSE.call(soapAction, envelope);

            response = ((SoapPrimitive)envelope.getResponse()).toString();

        } catch (Exception ex) {

            response =ex.toString();

        }

    }


}
