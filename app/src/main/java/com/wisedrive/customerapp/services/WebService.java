package com.wisedrive.customerapp.services;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.wisedrive.customerapp.commonclasses.Common;
import com.wisedrive.customerapp.commonclasses.Connectivity;
import com.wisedrive.customerapp.commonclasses.ResponseExtension;
import com.wisedrive.customerapp.commonclasses.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class WebService
{

//    public static WebService service = new WebService();

    private Context mContext;
    private String mRequestUrl;
    private HashMap<String, String> mHeaders;
    private HttpMethod mRequestMethod;
    private RequestType mRequestType;
    private Boolean mProgressNeeded;
    private int mRepeatCount;
    private int currentRepeatCount = 0;
    private ResponseListener mOnResponseListener;
    private ProgressDialog mDialog;
    private Operation mOperation = new Operation();
    public WebService() {}
    public void LoadwithUrl(Context context, String url, HttpMethod requestMethod, HashMap<String, String> headers,
                            RequestType requestType, Boolean progressNeeded, int repeatCount, ResponseListener listener) {

        mContext = context;
        mRequestUrl = url;
        mRequestMethod = requestMethod;
        mHeaders = headers;
        mRequestType = requestType;
        mProgressNeeded = progressNeeded;
        mRepeatCount = repeatCount;
        mOnResponseListener = listener;
        if (mProgressNeeded) {
            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("Please wait...");
            mDialog.setCancelable(false);
        }

        if (mOperation != null) {
            mOperation = null;
            mOperation = new Operation();
        }

        if (!Connectivity.isNetworkConnected(mContext)) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Common.CallToast(mContext,"Internet connection not available. Check your wifi or mobile data",3);
                    mOnResponseListener.ResponseFailure(0,"Internet connection not available. Check your wifi or mobile data");
                }
            });
            return;
        }

        mOperation.execute();

    }
     private class Operation extends AsyncTask<String, Void, Void>
     {
        private String Content;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (mProgressNeeded) {
                mDialog.show();
            }
        }

        @Override
        protected Void doInBackground(String... strings) {
            BufferedReader reader = null;
            OutputStream os = null;
            try
            {
                URL mUrl = null;
                byte[] body = null;
                HashMap<String, String> headerobj= new HashMap<>();
                if (mHeaders != null) {
                    headerobj = mHeaders;
                }
                switch (mRequestType.Request) {
                    case REST:
                        String urlStr = mRequestUrl + mRequestType.mMethod;
                        String AppendStr = "";
                        switch (mRequestType.mRestType) {
                            case normal:
                                if (mRequestType.mParams != null) {
                                    Iterator hIT = GetHashmap(mRequestType.mParams).entrySet().iterator();
                                    while (hIT.hasNext()) {
                                        Map.Entry mH = (Map.Entry) hIT.next();
                                        AppendStr += AppendStr.isEmpty() ? mH.getKey() + "=" + mH.getValue() : "&" + mH.getKey() + "=" + mH.getValue();
                                        hIT.remove();
                                    }
                                }
                                if (mRequestMethod == HttpMethod.post) {
                                    body = AppendStr.getBytes("UTF-8");
                                }
                                else {
                                    urlStr += "?" + AppendStr;
                                }

                                break;
                            case UrlEncode:

                                if (mRequestType.mParams != null) {
                                    Iterator hIT = GetHashmap(mRequestType.mParams).entrySet().iterator();
                                    while (hIT.hasNext()) {
                                        Map.Entry mH = (Map.Entry) hIT.next();
                                        AppendStr += AppendStr.isEmpty() ? mH.getKey() + "=" + mH.getValue() : "&" + mH.getKey() + "=" + mH.getValue();
                                        hIT.remove();
                                    }
                                    urlStr += "?" + AppendStr;
                                }

                                break;
                            case JSONBody:
                                body = mRequestType.mParams.toString().getBytes("UTF-8");
                                headerobj.put("Content-Type","application/json");
                                break;
                            default:
                                throw new Exception("Invalid Post data Request Type");
                        }
                        mUrl = new URL(urlStr);
                        break;
                    case SOAP:
                        String url = mRequestUrl.substring(0,mRequestUrl.length() - 1);
                        body = mRequestType.mSoapString.getBytes("UTF-8");
                        mUrl = new URL(url);
                        break;
                    default:
                        throw new Exception("Invalid Request Type");

                }


                HttpURLConnection httpConnection = (HttpURLConnection) mUrl.openConnection();
                httpConnection.setRequestMethod(mRequestMethod.getString());
                if (headerobj != null) {
                    Iterator hIT = headerobj.entrySet().iterator();
                    while (hIT.hasNext()) {
                        Map.Entry mH = (Map.Entry) hIT.next();
                        httpConnection.setRequestProperty(mH.getKey().toString(),mH.getValue().toString());
                        hIT.remove();
                    }
                }
                httpConnection.setUseCaches(false);
                httpConnection.setAllowUserInteraction(true);
                httpConnection.setConnectTimeout(100000);
                httpConnection.setReadTimeout(100000);
                if (body != null) {
                    os = httpConnection.getOutputStream();
                    os.write( body );
                }
                httpConnection.connect();
                int responseCode = httpConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    reader.close();
                    Content = sb.toString();
                }
                else {
                    requestFailure(responseCode,"Something went wrong!!!");
                }

                if (os != null) {
                    os.close();
                }
            }
            catch(Exception ex)
            {
                Log.e("Exception",ex.getMessage());
                requestFailure(0,ex.getMessage());
            }
            finally
            {
                try
                {
                    if (os != null) {
                        os.close();
                    }
                    if (reader!= null) {
                        reader.close();
                    }
                }
                catch(Exception ex) {
                    Log.e("Exception",ex.getMessage());
                    requestFailure(0,ex.getMessage());
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Content != null) {
                try {
                    JSONObject Json = new JSONObject(Content);
                    final ResponseExtension ressponse = new ResponseExtension(Json);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mProgressNeeded) {
                                mDialog.dismiss();
                                mDialog.cancel();
                            }
                            mOnResponseListener.ResponseSuccess(ressponse);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    requestFailure(0,e.getLocalizedMessage());
                }
            }
            else {
                requestFailure(0,"No data available");
            }
        }
        private void requestFailure(int responseCode, final String errorMsg) {

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (mProgressNeeded) {
                        mDialog.dismiss();
                        mDialog.cancel();
                    }

                    if (mRepeatCount > currentRepeatCount) {
                        currentRepeatCount++;
                        mOperation.execute();
                    }
                    else {
                        mOnResponseListener.ResponseFailure(0,errorMsg == null ? "Something went wrong!!!" : errorMsg);
                    }                }
            });
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
            requestFailure(0,"icon_cancelled");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            requestFailure(0,"icon_cancelled");
        }
    }

    public enum HttpMethod {
        post, get;
        private String getString() {
            String Method = "";
            switch (this) {
                case get:
                    Method = "GET";
                    break;
                case post:
                    Method = "POST";
                    break;
            }
            return Method;
        }
    }

    public static class RequestType {

        public static RequestType shared = new RequestType();
        private String mMethod;
        private RESTType mRestType;
        private JSONObject mParams;
        private String mSoapString;
        private Type Request;
        public RequestType REST(String method, RESTType requestType, JSONObject params) {
            mMethod = method;
            mParams = params;
            mRestType = requestType;
            Request = Type.REST;
            return this;
        }

        public RequestType SOAP(String soapString) {
            mSoapString = soapString;
            Request = Type.SOAP;
            return this;
        }
    }

    public enum Type {
        REST, SOAP;
    }

    public enum RESTType {

        JSONBody, UrlEncode, normal;
    }

    private static HashMap<String, Object> GetHashmap(JSONObject jsonObject) {

        HashMap<String, Object> params = new HashMap<String, Object>();

        try
        {
            Iterator<?> keys = jsonObject.keys();

            while (keys.hasNext())
            {
                String key = (String) keys.next();
                Object value = jsonObject.get(key);
                params.put(key, value);
            }

        }
        catch (Exception xx)
        {
            xx.toString();
        }

        return params;
    }
}