package com.shop;

import java.util.Date;

import com.shop.util.DateUtil;

public class DateTest {

	public static void main(String[] args) {
		Date now = new Date();
		//System.out.println("日期："+DateUtil.format(now, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss));
		//System.out.println("时间戳："+now.getTime());
		//System.out.println("日期："+DateUtil.format(new Date(1534930485000L).getTime(), DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss));
		//System.out.println(StringUtil.getUuid("ddd"));
		
		
		//HttpUtil.getminiqrQr(Long.toString(scene), access_token, "pages/user/index");//个人中心二维码
        /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//可以方便地修改日期格式
        String day=dateFormat.format(now);
        System.out.println(day);
        
        Double dou = 5.556;
		BigDecimal bg = new BigDecimal(dou).setScale(2, RoundingMode.HALF_UP);
		Double discount = bg.doubleValue();
		System.out.println(discount);*/
		
		Date date = new Date(1537882040000L);
		System.out.println(DateUtil.format(date, DateUtil.FORMAT_YYYY_MM_dd_hh_mm_ss));
	}
}
