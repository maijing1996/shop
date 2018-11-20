package com.shop.util;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.StringUtils;

public class StringUtil {
	
    public static Integer strToInt(String str) {
        return strToInt(str, null);
    }

    public static Integer strToInt(String str, Integer defValue) {
        if (StringUtils.isNullOrEmpty(str)) {
            return defValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Long strToLong(String str) {
        return strToLong(str, null);
    }

    public static Long strToLong(String str, Long defValue) {
        if (StringUtils.isNullOrEmpty(str)) {
            return defValue;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static Double strToDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static Double strToDouble(String str, Double defValue) {
        if (StringUtils.isNullOrEmpty(str)) {
            return null;
        }
        try {
            return Double.parseDouble(str);
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static String subStr(String str, int subSLength) throws UnsupportedEncodingException {
        if (str == null)
            return "";
        else {
            int tempSubLength = subSLength;// 截取字节数
            String subStr = str.substring(0, str.length() < subSLength ? str.length() : subSLength);// 截取的子串
            int subStrByetsL = subStr.getBytes("GBK").length;// 截取子串的字节长度
            // int subStrByetsL = subStr.getBytes().length;//截取子串的字节长度
            // 说明截取的字符串中包含有汉字
            while (subStrByetsL > tempSubLength) {
                int subSLengthTemp = --subSLength;
                subStr = str.substring(0, subSLengthTemp > str.length() ? str.length() : subSLengthTemp);
                subStrByetsL = subStr.getBytes("GBK").length;
                // subStrByetsL = subStr.getBytes().length;
            }
            return subStr;
        }
    }

    /**
     * 过滤表情
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (!StringUtils.isNullOrEmpty(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "[emoji]");
        } else {
            return source;
        }
    }

    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    public static boolean isNotNullOrEmpty(Object obj) {
        return !isNullOrEmpty(obj);
    }

    /**
     * 适用于sql in方法括号内的语句
     *
     * @param str
     * @return
     */
    public static String transSeparaStringToSql(String str) {
        StringBuilder sb = new StringBuilder();
        if (isNotNullOrEmpty(str)) {
            sb.append("'");
            sb.append(str.replace(",", "','"));
            sb.append("'");
        }
        return sb.toString();
    }

    /****
     * 随机生成一个随机数（纯数字） 随机数的长度 length
     *
     * @return
     */
    public static String RandomNumberNumber(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 判断给定的字符串数组中是否全部都不为null且不为空
     *
     * @param objects 给定的字符串数组
     * @return 是否全部都不为null且不为空
     * @author gyxian9
     */
    public static boolean isNotNullOrEmpty(Object... objects) {
        boolean result = true;
        for (Object object : objects) {
            if (isNullOrEmpty(object)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 连接字符串。例如：String[] ["1","2","3"]，连接为 "1,2,3"。
     *
     * @param array     数组
     * @param separator 分隔符
     * @return
     */
    public static String join(Object[] array, char separator) {
        if (array == null) {
            return null;
        }
        int arraySize = array.length;
        int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0].toString().length()) + 1) * arraySize);
        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 经纬度两点间距离公式
     *
     * @param long1
     * @param lat1
     * @param long2
     * @param lat2
     * @return 米
     */
    public static double distance(double long1, double lat1, double long2, double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    /**
     * 生成8位兑换码
     *
     * @return
     */
    public static String getShortUuid() {
        StringBuffer stringBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int strInteger = Integer.parseInt(str, 16);
            stringBuffer.append(chars[strInteger % 0x3E]);
        }

        return stringBuffer.toString();
    }

    /**
     * 判断是否手机号码
     *
     * @param phone 手机号码
     * @return
     */
    public static boolean isPhone(String phone) {
        Pattern pattern = Pattern.compile("(^(13\\d|15[^4,\\D]|17[13678]|18\\d)\\d{8}|170[^346,\\D]\\d{7})");
        Matcher matcher = pattern.matcher(phone);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 移除指定的字符串
     *
     * @param source   源字符串
     * @param startStr 开始标
     * @param endStr   结束标
     * @return
     */
    public static String removeStr(String source, String startStr, String endStr) {
        StringBuffer des = new StringBuffer();
        int start = source.indexOf(startStr);
        int end = source.indexOf(endStr);
        des.append(source.substring(0, start));
        des.append(source.substring(end + 1, source.length()));
        return des.toString();
    }

    /**
     * 生成唯一的订单号
     *  用户ID(唯一性)+当前时间搓+随机数(数字和字母)
     * @return
     */
    public static String random(String userId) {
		Random random = new Random();
        StringBuffer str = new StringBuffer(21);
        str.append(userId);
        Long current_time = System.currentTimeMillis();
        str.append(current_time);
        int len = str.length();
        if (len < 21) {
            int add = 21 - str.length();
            str.append(random.nextInt(10));
            for (int i = 1; i < add; i++) {
                if (i%2==0){
                    str.append(random.nextInt(10));
                }else {
                    int rad = random.nextInt(26);
                    str.append(chars[rad]);
                }
            }
        }if (len > 21){
            return str.substring(0,20);
        }
        return str.toString();
    }

    /**
     * 流水号-21位流水号
     *
     * @return
     */
    public static String randomSSN() {
        Random random = new Random();
        StringBuffer str = new StringBuffer(21);
        str.append(random.nextInt(10) + "" + chars[random.nextInt(26)]);
        str.append(random.nextInt(10) + "" + chars[random.nextInt(26)]);
        str.append(random.nextInt(10) + "" + chars[random.nextInt(26)]);
        str.append(random.nextInt(10) + "" + chars[random.nextInt(26)]);
        str.append(random.nextInt(10) + "" + chars[random.nextInt(26)]);
        Calendar Cld = Calendar.getInstance();
        int minute = Cld.get(Calendar.MINUTE);
        int second = Cld.get(Calendar.SECOND);
        int millsecond = Cld.get(Calendar.MILLISECOND);
        str.append(minute);
        str.append(second);
        str.append(millsecond);
        int len = str.length();
        if (len < 21) {
            int add = 21 - str.length();
            str.append(random.nextInt(10));
            for (int i = 1; i < add; i++) {
                if (i%2==0){
                    int rad = random.nextInt(26);
                    str.append(chars[rad]);
                }else {
                    str.append(random.nextInt(10));
                }
            }
        }if (len > 21){
            return str.substring(0,20);
        }
        return str.toString();
    }

    /**
     * 截取字符串
     *
     * @param source
     * @param startStr
     * @param endStr
     * @return
     */
    public static String subStringEnd(String source, String startStr, String endStr) {
        int start = source.indexOf(startStr);
        int end = source.indexOf(endStr) + endStr.length();
        return source.substring(start, end);
    }

    /**
     * 截取字符串
     *
     * @param source
     * @param startStr
     * @param endStr
     * @return
     */
    public static String subStringStart(String source, String startStr, String endStr) {
        int start = source.indexOf(startStr) + startStr.length();
        int end = source.indexOf(endStr);
        return source.substring(start, end);
    }
    
    /**
     * 生成系统需要的 UUID
     * @param unionId
     * @return
     */
    public static String getUuid(String unionId) {
    	if(unionId == null) {
    		return null;
    	}
    	String unionIdMD5 = MD5Util.MD5(unionId);
    	StringBuffer buffer = new StringBuffer();
		buffer.append(unionIdMD5.substring(0, 8));
		buffer.append("-");
		buffer.append(unionIdMD5.substring(8, 16));
		buffer.append("-");
		buffer.append(unionIdMD5.substring(16, 24));
		buffer.append("-");
		buffer.append(unionIdMD5.substring(24, 32));
    	return buffer.toString();
    }
    
    /**
     * 获得指定范围的随机数
     * @param num
     */
    public static int getNum(int num) {
    	Random dom = new Random();
    	return dom.nextInt(num);
    }
    
    /**
     * 获得指定位数的订单号
     * 规则：时间戳 + n位随机数: 13 + 19
     * @param amount
     */
    public static String getOrderSn(int amount) {
    	StringBuffer buffer = new StringBuffer();
    	buffer.append(new Date().getTime());
    	for(int i=0; i < amount; i++) {
    		buffer.append(new Random().nextInt(10));
    	}
    	return buffer.toString();
    }
    
    /**
     * 获得新人优惠券编码
     * @return
     */
    public static String getCouponSn(int length) {
    	String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	Random random = new Random();
    	StringBuffer buffer = new StringBuffer();
    	for(int i=0; i < length; i++) {
    		int ic = random.nextInt(base.length());
    		buffer.append(base.charAt(ic));
    	}
    	return buffer.toString();
    }
}
