package com.example.musicandmovietest;

/**
 * Created by dang on 2020-11-04.
 * Time will tell.
 *
 * @description
 */
public class TimeDealer {
    public static  int MORETHANHOUR=60*60*1000;
    public static  int MORETHANMinute=60*1000;
    public static  String getTime(int timeLength){

        String time=null;
        if(timeLength>=MORETHANHOUR){
            //自己处理一下

        }
        else if(timeLength>=MORETHANMinute){
            int tempMinute=timeLength/1000/60;
            int tempSecond=(timeLength-tempMinute*1000*60)/1000;
            if(tempMinute<10){
                if(tempSecond<10) {
                    time = "00:" + "0" + tempMinute + ":"+"0"+tempSecond;
                }
                else{
                    time = "00:" + "0" + tempMinute + ":"+tempSecond;
                }
            }
            else{
                if(tempSecond<10) {
                    time = "00:" + tempMinute + ":" + "0" + tempSecond;
                }
                else{
                    time = "00:" + tempMinute + ":" + tempSecond;
                }
            }
        }
        return time;
    }

}
