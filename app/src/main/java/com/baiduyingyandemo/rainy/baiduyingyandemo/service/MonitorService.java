package com.baiduyingyandemo.rainy.baiduyingyandemo.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.baiduyingyandemo.rainy.baiduyingyandemo.app.MyTraceApplication;

import java.util.List;

/**
 * Author: Rainy <br>
 * Description: BaiduYingyanDemo <br>
 * Since: 2016/11/3 0003 上午 9:51 <br>
 */

public class MonitorService extends Service {
    private MyTraceApplication traceApp = null;
    public static boolean isCheck = false;

    public static boolean isRunning = false;

    private static final String SERVICE_NAME = "com.baidu.trace.LBSTraceService";

    @Override
    public void onCreate() {
        System.out.println("MonitorService onCreate");
        traceApp = (MyTraceApplication) getApplicationContext();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("MonitorService onStartCommand");
        new Thread(){
            @Override
            public void run() {
                while(isCheck){
                    try {
                        Thread.sleep(5*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("thread sleep failed");
                    }

                    if(!isServiceWork(getApplicationContext(),SERVICE_NAME)){
                        System.out.println("轨迹服务已停止，重启轨迹服务");
                        if(null != traceApp.getClient() && null != traceApp.getTrace()){
                            traceApp.getClient().startTrace(traceApp.getTrace());
                        }
                    }else {
                        System.out.println("轨迹服务正在运行");
                    }


                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 判断某个服务是否正在运行的方法
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：com.baidu.trace.LBSTraceService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName){
        boolean isWork = false;

        ActivityManager myAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = myAM.getRunningServices(80);
        if(runningServices.size() <= 0){
            return false;
        }
        for (int i = 0; i < runningServices.size(); i++) {
            String mName = runningServices.get(i).service.getClassName().toString();
            if(mName.equals(serviceName)){
                isWork = true;
                break;
            }
        }
        return isWork;
    }


}
