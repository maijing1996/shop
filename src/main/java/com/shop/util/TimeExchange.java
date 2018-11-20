package com.shop.util;
 
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.alibaba.druid.util.StringUtils;
 
/**
 * 时间转化工具 date转为时间戳 时间戳转date 互相与String的转换 
 * 所有出现的String time 格式都必须为(yyyy-MM-dd HH:mm:ss)，否则出错
 * @author GuoMing
 * 
 */
public class TimeExchange {
 
    /**
     * String(yyyy-MM-dd HH:mm:ss) 转 Date
     */
    public static Date StringToDate(String time) throws ParseException {
         
    	if(StringUtils.equals(time, "") || time == null)
    		return null;
        Date date = new Date();
        // 注意format的格式要与日期String的格式相匹配
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = dateFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return date;
    }
 
    /**
     * Date转为String(yyyy-MM-dd HH:mm:ss)
     * 
     * @param time
     * @return
     */
    public static String DateToString(Date time) {
        String dateStr = "";
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH/mm/ss");
        try {
            dateStr = dateFormat.format(time);
            System.out.println(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }
    public static String DateToStringSimple(Date time) {
        String dateStr = "";
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            dateStr = dateFormat.format(time);
            System.out.println(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }
    /**
     * String(yyyy-MM-dd HH:mm:ss)转10位时间戳
     * @param time
     * @return
     */
    public static Integer StringToTimestamp(String time){
    
        int times = 0;
        try {  
            times = (int) ((Timestamp.valueOf(time).getTime())/1000);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        if(times==0){
            System.out.println("String转10位时间戳失败");
        }
        return times; 
         
    }
    /**
     * 10位int型的时间戳转换为String(yyyy-MM-dd HH:mm:ss)
     * @param time
     * @return
     */
    public static String timestampToString(Integer time){
        //int转long时，先进行转型再进行计算，否则会是计算结束后在转型
        long temp = (long)time*1000;
        Timestamp ts = new Timestamp(temp);  
        String tsStr = "";  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        try {  
            //方法一  
            tsStr = dateFormat.format(ts);  
            System.out.println(tsStr);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return tsStr;  
    }
    /**
     * 10位时间戳转Date
     * @param time
     * @return
     */
    public static Date TimestampToDate(Long temp){
//        long temp = (long)time*1000;
        Timestamp ts = new Timestamp(temp);  
        Date date = new Date();  
        try {  
            date = ts;  
            System.out.println(date);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return date;
    }
    /**
     * Date类型转换为10位时间戳
     * @param time
     * @return
     */
    public static Integer DateToTimestamp(Date time){
        Timestamp ts = new Timestamp(time.getTime());
         
        return (int) ((ts.getTime())/1000);
    }
    
    
    /**
     * 
     */
    public static int getCurrentTimestamp(){
    	return (int) (System.currentTimeMillis() / 1000);
    }
 
    public static String getCurrentString(){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(new Date());
	}
    
    public static Date getDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (Exception e) {
			try {
				date = sdf2.parse(str);
			} catch (ParseException e1) {
				try {
					date = sdf3.parse(str);
				} catch (ParseException e2) {
				}
			}
		}
		return date;
	}
	public static String getDate(long l) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		String str = sdf.format(new Date(l));
		return str;
	}
}
