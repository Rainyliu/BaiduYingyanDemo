package com.baiduyingyandemo.rainy.baiduyingyandemo.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.Trace;

import java.lang.ref.WeakReference;

/**
 * Author: Rainy <br>
 * Description: BaiduYingyanDemo <br>
 * Since: 2016/11/1 0001 下午 3:24 <br>
 */

public class MyTraceApplication extends Application {
    private Context mContext = null;

    /**
     * 轨迹服务
     */
    private Trace trace = null;

    /**
     * 轨迹服务客户端
     */
    private LBSTraceClient client = null;

    /**
     * 鹰眼服务ID，开发者创建的鹰眼服务对应的服务ID
     */
    private int serviceId = 128190;

    /**
     * entity标识
     */
    private String entityName = "traceDemo";

    /**
     * 轨迹服务类型（0：不建立socket长连接，1：建立socket长连接，但不上传位置数据，2：UPLOAD_LOCATION建立socket长连接并上传位置数据）
     * 轨迹服务类型，traceType必须设置为UPLOAD_LOCATION才能追踪轨迹
     */
    private int traceType = 2;

    private MapView bMapView = null;

    private BaiduMap mBaiduMap = null;

    private TrackHandler mHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        SDKInitializer.initialize(this);

        //初始化轨迹服务客户端
        client = new LBSTraceClient(mContext);

        //初始化轨迹服务
        trace = new Trace(mContext,serviceId,entityName,traceType);

        //设置定位模式-高精度
        client.setLocationMode(LocationMode.High_Accuracy);

        mHandler = new TrackHandler(this);

    }

    public void initBmap(MapView bMapView){
        this.bMapView = bMapView;
        this.mBaiduMap = bMapView.getMap();
        this.bMapView.showZoomControls(false);

    }

    static class TrackHandler extends Handler {
        WeakReference<MyTraceApplication> trackApp;

        TrackHandler(MyTraceApplication myTraceApplication) {
            trackApp = new WeakReference<MyTraceApplication>(myTraceApplication);
        }

        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(trackApp.get().mContext, (String) msg.obj, Toast.LENGTH_SHORT).show();
        }
    }

    public Context getmContext() {
        return mContext;
    }

    public Trace getTrace() {
        return trace;
    }

    public LBSTraceClient getClient() {
        return client;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getEntityName() {
        return entityName;
    }

    public Handler getmHandler() {
        return mHandler;
    }

    public MapView getBmapView() {
        return bMapView;
    }

    public BaiduMap getmBaiduMap() {
        return mBaiduMap;
    }
}
