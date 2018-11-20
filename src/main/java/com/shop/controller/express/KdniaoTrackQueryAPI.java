package com.shop.controller.express;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shop.conf.MySiteSetting;
import com.shop.model.dto.express.KdniaoTrackQueryResp;
import com.shop.util.JsonUtil; 

/**
 *
 * 快递鸟物流轨迹即时查询接口
 *
 * @技术QQ群: 456320272
 * @see: http://www.kdniao.com/YundanChaxunAPI.aspx
 * @copyright: 深圳市快金数据技术服务有限公司
 *
 * DEMO中的电商ID与私钥仅限测试使用，正式环境请单独注册账号
 * 单日超过500单查询量，建议接入我方物流轨迹订阅推送接口
 * 
 * ID和Key请到官网申请：http://www.kdniao.com/ServiceApply.aspx
 */
@Component
public class KdniaoTrackQueryAPI {
	
	@Autowired
	private MySiteSetting mySiteSetting;
	
	//DEMO
	public static void main(String[] args) {
		KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
		try {
			String result = api.getOrderTracesByJson("STO", "3377124526022");
			System.out.println(result);
			KdniaoTrackQueryResp kdniaoTrackQueryResp = JsonUtil.getObject(result, KdniaoTrackQueryResp.class);
			System.out.println(kdniaoTrackQueryResp.getSuccess());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//电商ID
	//private String EBusinessID="test1375771";
	//private String EBusinessID=mySiteSetting.getEBusinessID();
	//电商加密私钥，快递鸟提供，注意保管，不要泄漏
	//正式：ba49f178-60ba-4c93-b6d9-c13dd521a090
	//private String AppKey="b700e676-7238-4229-b3a1-4b94c8ca12d3";
	//private String AppKey=mySiteSetting.getAppKey();
	//请求url
	//测试地址：http://sandboxapi.kdniao.cc:8080/kdniaosandbox/gateway/exterfaceInvoke.json
	//正式地址：http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx
	//private String ReqURL="http://sandboxapi.kdniao.cc:8080/kdniaosandbox/gateway/exterfaceInvoke.json";
	
	private String ReqURL="http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";
 
	/**
     * Json方式 查询订单物流轨迹
	 * @throws Exception 
     */
	public String getOrderTracesByJson(String expCode, String expNo) throws Exception{
		String requestData= "{'OrderCode':'','ShipperCode':'" + expCode + "','LogisticCode':'" + expNo + "'}";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", mySiteSetting.getEBusinessID());
		params.put("RequestType", "1002");
		String dataSign=encrypt(requestData, mySiteSetting.getAppKey(), "UTF-8");
		params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");
		
		String result=sendPost(ReqURL, params);	
		return result;
	}
 
	/**
     * MD5加密
     * @param str 内容       
     * @param charset 编码方式
	 * @throws Exception 
     */
	private String MD5(String str, String charset) throws Exception {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(str.getBytes(charset));
	    byte[] result = md.digest();
	    StringBuffer sb = new StringBuffer(32);
	    for (int i = 0; i < result.length; i++) {
	        int val = result[i] & 0xff;
	        if (val <= 0xf) {
	            sb.append("0");
	        }
	        sb.append(Integer.toHexString(val));
	    }
	    return sb.toString().toLowerCase();
	}
	
	/**
     * base64编码
     * @param str 内容       
     * @param charset 编码方式
	 * @throws UnsupportedEncodingException 
     */
	private String base64(String str, String charset) throws UnsupportedEncodingException{
		String encoded = base64Encode(str.getBytes(charset));
		return encoded;    
	}	
	
	private String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
		String result = URLEncoder.encode(str, charset);
		return result;
	}
	
	/**
     * 电商Sign签名生成
     * @param content 内容   
     * @param keyValue Appkey  
     * @param charset 编码方式
	 * @throws UnsupportedEncodingException ,Exception
	 * @return DataSign签名
     */
	private String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception {
		if (keyValue != null)
		{
			return base64(MD5(content + keyValue, charset), charset);
		}
		return base64(MD5(content, charset), charset);
	}
	
	 /**
     * 向指定 URL 发送POST方法的请求     
     * @param url 发送请求的 URL    
     * @param params 请求的参数集合     
     * @return 远程资源的响应结果
     */
	private String sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;        
        StringBuilder result = new StringBuilder(); 
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数            
            if (params != null) {
		          StringBuilder param = new StringBuilder(); 
		          for (Map.Entry<String, String> entry : params.entrySet()) {
		        	  if(param.length()>0){
		        		  param.append("&");
		        	  }	        	  
		        	  param.append(entry.getKey());
		        	  param.append("=");
		        	  param.append(entry.getValue());		        	  
		        	  //System.out.println(entry.getKey()+":"+entry.getValue());
		          }
		          //System.out.println("param:"+param.toString());
		          out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {            
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
	
	
    private static char[] base64EncodeChars = new char[] { 
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
        'w', 'x', 'y', 'z', '0', '1', '2', '3', 
        '4', '5', '6', '7', '8', '9', '+', '/' }; 
	
    public static String base64Encode(byte[] data) { 
        StringBuffer sb = new StringBuffer(); 
        int len = data.length; 
        int i = 0; 
        int b1, b2, b3; 
        while (i < len) { 
            b1 = data[i++] & 0xff; 
            if (i == len) 
            { 
                sb.append(base64EncodeChars[b1 >>> 2]); 
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]); 
                sb.append("=="); 
                break; 
            } 
            b2 = data[i++] & 0xff; 
            if (i == len) 
            { 
                sb.append(base64EncodeChars[b1 >>> 2]); 
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]); 
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]); 
                sb.append("="); 
                break; 
            } 
            b3 = data[i++] & 0xff; 
            sb.append(base64EncodeChars[b1 >>> 2]); 
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]); 
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]); 
            sb.append(base64EncodeChars[b3 & 0x3f]); 
        } 
        return sb.toString(); 
    }
}

/*
{
    "error_code": 0,
    "reason": "成功",
    "result": [{
        "com": "申通快递",
        "no": "shentong"
    },
    {
        "com": "EMS",
        "no": "ems"
    },
    {
        "com": "顺丰速运",
        "no": "shunfeng"
    },
    {
        "com": "韵达快递",
        "no": "yunda"
    },
    {
        "com": "圆通速递",
        "no": "yuantong"
    },
    {
        "com": "中通快递",
        "no": "zhongtong"
    },
    {
        "com": "百世汇通",
        "no": "huitongkuaidi"
    },
    {
        "com": "天天快递",
        "no": "tiantian"
    },
    {
        "com": "宅急送",
        "no": "zhaijisong"
    },
    {
        "com": "鑫飞鸿",
        "no": "xinhongyukuaidi"
    },
    {
        "com": "CCES/国通快递",
        "no": "cces"
    },
    {
        "com": "全一快递",
        "no": "quanyikuaidi"
    },
    {
        "com": "彪记快递",
        "no": "biaojikuaidi"
    },
    {
        "com": "星晨急便",
        "no": "xingchengjibian"
    },
    {
        "com": "亚风速递",
        "no": "yafengsudi"
    },
    {
        "com": "源伟丰",
        "no": "yuanweifeng"
    },
    {
        "com": "全日通",
        "no": "quanritongkuaidi"
    },
    {
        "com": "安信达",
        "no": "anxindakuaixi"
    },
    {
        "com": "民航快递",
        "no": "minghangkuaidi"
    },
    {
        "com": "凤凰快递",
        "no": "fenghuangkuaidi"
    },
    {
        "com": "京广速递",
        "no": "jinguangsudikuaijian"
    },
    {
        "com": "配思货运",
        "no": "peisihuoyunkuaidi"
    },
    {
        "com": "中铁物流",
        "no": "ztky"
    },
    {
        "com": "UPS",
        "no": "ups"
    },
    {
        "com": "FedEx-国际件",
        "no": "fedex"
    },
    {
        "com": "DHL-中国件",
        "no": "dhl"
    },
    {
        "com": "AAE-中国件",
        "no": "aae"
    },
    {
        "com": "大田物流",
        "no": "datianwuliu"
    },
    {
        "com": "德邦物流",
        "no": "debangwuliu"
    },
    {
        "com": "新邦物流",
        "no": "xinbangwuliu"
    },
    {
        "com": "龙邦速递",
        "no": "longbanwuliu"
    },
    {
        "com": "一邦速递",
        "no": "yibangwuliu"
    },
    {
        "com": "速尔快递",
        "no": "suer"
    },
    {
        "com": "联昊通",
        "no": "lianhaowuliu"
    },
    {
        "com": "广东邮政",
        "no": "guangdongyouzhengwuliu"
    },
    {
        "com": "中邮物流",
        "no": "zhongyouwuliu"
    },
    {
        "com": "天地华宇",
        "no": "tiandihuayu"
    },
    {
        "com": "盛辉物流",
        "no": "shenghuiwuliu"
    },
    {
        "com": "长宇物流",
        "no": "changyuwuliu"
    },
    {
        "com": "飞康达",
        "no": "feikangda"
    },
    {
        "com": "元智捷诚",
        "no": "yuanzhijiecheng"
    },
    {
        "com": "邮政包裹/平邮",
        "no": "youzhengguonei"
    },
    {
        "com": "国际包裹",
        "no": "youzhengguoji"
    },
    {
        "com": "万家物流",
        "no": "wanjiawuliu"
    },
    {
        "com": "远成物流",
        "no": "yuanchengwuliu"
    },
    {
        "com": "信丰物流",
        "no": "xinfengwuliu"
    },
    {
        "com": "文捷航空",
        "no": "wenjiesudi"
    },
    {
        "com": "全晨快递",
        "no": "quanchenkuaidi"
    },
    {
        "com": "佳怡物流",
        "no": "jiayiwuliu"
    },
    {
        "com": "优速物流",
        "no": "youshuwuliu"
    },
    {
        "com": "快捷速递",
        "no": "kuaijiesudi"
    },
    {
        "com": "D速快递",
        "no": "dsukuaidi"
    },
    {
        "com": "全际通",
        "no": "quanjitong"
    },
    {
        "com": "能达速递",
        "no": "ganzhongnengda"
    },
    {
        "com": "青岛安捷快递",
        "no": "anjiekuaidi"
    },
    {
        "com": "越丰物流",
        "no": "yuefengwuliu"
    },
    {
        "com": "DPEX",
        "no": "dpex"
    },
    {
        "com": "急先达",
        "no": "jixianda"
    },
    {
        "com": "百福东方",
        "no": "baifudongfang"
    },
    {
        "com": "BHT",
        "no": "bht"
    },
    {
        "com": "伍圆速递",
        "no": "wuyuansudi"
    },
    {
        "com": "蓝镖快递",
        "no": "lanbiaokuaidi"
    },
    {
        "com": "COE",
        "no": "coe"
    },
    {
        "com": "南京100",
        "no": "nanjing"
    },
    {
        "com": "恒路物流",
        "no": "hengluwuliu"
    },
    {
        "com": "金大物流",
        "no": "jindawuliu"
    },
    {
        "com": "华夏龙",
        "no": "huaxialongwuliu"
    },
    {
        "com": "运通中港",
        "no": "yuntongkuaidi"
    },
    {
        "com": "佳吉快运",
        "no": "jiajiwuliu"
    },
    {
        "com": "盛丰物流",
        "no": "shengfengwuliu"
    },
    {
        "com": "源安达",
        "no": "yuananda"
    },
    {
        "com": "加运美",
        "no": "jiayunmeiwuliu"
    },
    {
        "com": "万象物流",
        "no": "wanxiangwuliu"
    },
    {
        "com": "宏品物流",
        "no": "hongpinwuliu"
    },
    {
        "com": "GLS",
        "no": "gls"
    },
    {
        "com": "上大物流",
        "no": "shangda"
    },
    {
        "com": "中铁快运",
        "no": "zhongtiewuliu"
    },
    {
        "com": "原飞航",
        "no": "yuanfeihangwuliu"
    },
    {
        "com": "海外环球",
        "no": "haiwaihuanqiu"
    },
    {
        "com": "三态速递",
        "no": "santaisudi"
    },
    {
        "com": "晋越快递",
        "no": "jinyuekuaidi"
    },
    {
        "com": "联邦快递",
        "no": "lianbangkuaidi"
    },
    {
        "com": "飞快达",
        "no": "feikuaida"
    },
    {
        "com": "全峰快递",
        "no": "quanfengkuaidi"
    },
    {
        "com": "如风达",
        "no": "rufengda"
    },
    {
        "com": "乐捷递",
        "no": "lejiedi"
    },
    {
        "com": "忠信达",
        "no": "zhongxinda"
    },
    {
        "com": "芝麻开门",
        "no": "zhimakaimen"
    },
    {
        "com": "赛澳递",
        "no": "saiaodi"
    },
    {
        "com": "海红网送",
        "no": "haihongwangsong"
    },
    {
        "com": "共速达",
        "no": "gongsuda"
    },
    {
        "com": "嘉里大通",
        "no": "jialidatong"
    },
    {
        "com": "OCS",
        "no": "ocs"
    },
    {
        "com": "USPS",
        "no": "usps"
    },
    {
        "com": "美国快递",
        "no": "meiguokuaidi"
    },
    {
        "com": "成都立即送",
        "no": "lijisong"
    },
    {
        "com": "银捷速递",
        "no": "yinjiesudi"
    },
    {
        "com": "门对门",
        "no": "menduimen"
    },
    {
        "com": "递四方",
        "no": "disifang"
    },
    {
        "com": "郑州建华",
        "no": "zhengzhoujianhua"
    },
    {
        "com": "河北建华",
        "no": "hebeijianhua"
    },
    {
        "com": "微特派",
        "no": "weitepai"
    },
    {
        "com": "DHL-德国件（DHL Deutschland）",
        "no": "dhlde"
    },
    {
        "com": "通和天下",
        "no": "tonghetianxia"
    },
    {
        "com": "EMS-国际件",
        "no": "emsguoji"
    },
    {
        "com": "FedEx-美国件",
        "no": "fedexus"
    },
    {
        "com": "风行天下",
        "no": "fengxingtianxia"
    },
    {
        "com": "康力物流",
        "no": "kangliwuliu"
    },
    {
        "com": "跨越速递",
        "no": "kuayue"
    },
    {
        "com": "海盟速递",
        "no": "haimengsudi"
    },
    {
        "com": "圣安物流",
        "no": "shenganwuliu"
    },
    {
        "com": "一统飞鸿",
        "no": "yitongfeihong"
    },
    {
        "com": "中速快递",
        "no": "zhongsukuaidi"
    },
    {
        "com": "新蛋奥硕",
        "no": "neweggozzo"
    },
    {
        "com": "OnTrac",
        "no": "ontrac"
    },
    {
        "com": "七天连锁",
        "no": "sevendays"
    },
    {
        "com": "明亮物流",
        "no": "mingliangwuliu"
    },
    {
        "com": "凡客配送（作废）",
        "no": "vancl"
    },
    {
        "com": "华企快运",
        "no": "huaqikuaiyun"
    },
    {
        "com": "城市100",
        "no": "city100"
    },
    {
        "com": "红马甲物流",
        "no": "sxhongmajia"
    },
    {
        "com": "穗佳物流",
        "no": "suijiawuliu"
    },
    {
        "com": "飞豹快递",
        "no": "feibaokuaidi"
    },
    {
        "com": "传喜物流",
        "no": "chuanxiwuliu"
    },
    {
        "com": "捷特快递",
        "no": "jietekuaidi"
    },
    {
        "com": "隆浪快递",
        "no": "longlangkuaidi"
    },
    {
        "com": "EMS-英文",
        "no": "emsen"
    },
    {
        "com": "中天万运",
        "no": "zhongtianwanyun"
    },
    {
        "com": "香港(HongKong Post)",
        "no": "hkpost"
    },
    {
        "com": "邦送物流",
        "no": "bangsongwuliu"
    },
    {
        "com": "国通快递",
        "no": "guotongkuaidi"
    },
    {
        "com": "澳大利亚(Australia Post)",
        "no": "auspost"
    },
    {
        "com": "加拿大(Canada Post)",
        "no": "canpost"
    },
    {
        "com": "加拿大邮政",
        "no": "canpostfr"
    },
    {
        "com": "UPS-全球件",
        "no": "upsen"
    },
    {
        "com": "TNT-全球件",
        "no": "tnten"
    },
    {
        "com": "DHL-全球件",
        "no": "dhlen"
    },
    {
        "com": "顺丰-美国件",
        "no": "shunfengen"
    },
    {
        "com": "汇强快递",
        "no": "huiqiangkuaidi"
    },
    {
        "com": "希优特",
        "no": "xiyoutekuaidi"
    },
    {
        "com": "昊盛物流",
        "no": "haoshengwuliu"
    },
    {
        "com": "尚橙物流",
        "no": "shangcheng"
    },
    {
        "com": "亿领速运",
        "no": "yilingsuyun"
    },
    {
        "com": "大洋物流",
        "no": "dayangwuliu"
    },
    {
        "com": "递达速运",
        "no": "didasuyun"
    },
    {
        "com": "易通达",
        "no": "yitongda"
    },
    {
        "com": "邮必佳",
        "no": "youbijia"
    },
    {
        "com": "亿顺航",
        "no": "yishunhang"
    },
    {
        "com": "飞狐快递",
        "no": "feihukuaidi"
    },
    {
        "com": "潇湘晨报",
        "no": "xiaoxiangchenbao"
    },
    {
        "com": "巴伦支",
        "no": "balunzhi"
    },
    {
        "com": "Aramex",
        "no": "aramex"
    },
    {
        "com": "闽盛快递",
        "no": "minshengkuaidi"
    },
    {
        "com": "佳惠尔",
        "no": "syjiahuier"
    },
    {
        "com": "民邦速递",
        "no": "minbangsudi"
    },
    {
        "com": "上海快通",
        "no": "shanghaikuaitong"
    },
    {
        "com": "北青小红帽",
        "no": "xiaohongmao"
    },
    {
        "com": "GSM",
        "no": "gsm"
    },
    {
        "com": "安能物流",
        "no": "annengwuliu"
    },
    {
        "com": "KCS",
        "no": "kcs"
    },
    {
        "com": "City-Link",
        "no": "citylink"
    },
    {
        "com": "店通快递",
        "no": "diantongkuaidi"
    },
    {
        "com": "凡宇快递",
        "no": "fanyukuaidi"
    },
    {
        "com": "平安达腾飞",
        "no": "pingandatengfei"
    },
    {
        "com": "广东通路",
        "no": "guangdongtonglu"
    },
    {
        "com": "中睿速递",
        "no": "zhongruisudi"
    },
    {
        "com": "快达物流",
        "no": "kuaidawuliu"
    },
    {
        "com": "佳吉快递",
        "no": "jiajikuaidi"
    },
    {
        "com": "ADP国际快递",
        "no": "adp"
    },
    {
        "com": "颿达国际快递",
        "no": "fardarww"
    },
    {
        "com": "颿达国际快递-英文",
        "no": "fandaguoji"
    },
    {
        "com": "林道国际快递",
        "no": "shlindao"
    },
    {
        "com": "中外运速递-中文",
        "no": "sinoex"
    },
    {
        "com": "中外运速递",
        "no": "zhongwaiyun"
    },
    {
        "com": "深圳德创物流",
        "no": "dechuangwuliu"
    },
    {
        "com": "林道国际快递-英文",
        "no": "ldxpres"
    },
    {
        "com": "瑞典（Sweden Post）",
        "no": "ruidianyouzheng"
    },
    {
        "com": "PostNord(Posten AB)",
        "no": "postenab"
    },
    {
        "com": "偌亚奥国际快递",
        "no": "nuoyaao"
    },
    {
        "com": "城际速递",
        "no": "chengjisudi"
    },
    {
        "com": "祥龙运通物流",
        "no": "xianglongyuntong"
    },
    {
        "com": "品速心达快递",
        "no": "pinsuxinda"
    },
    {
        "com": "宇鑫物流",
        "no": "yuxinwuliu"
    },
    {
        "com": "陪行物流",
        "no": "peixingwuliu"
    },
    {
        "com": "户通物流",
        "no": "hutongwuliu"
    },
    {
        "com": "西安城联速递",
        "no": "xianchengliansudi"
    },
    {
        "com": "煜嘉物流",
        "no": "yujiawuliu"
    },
    {
        "com": "一柒国际物流",
        "no": "yiqiguojiwuliu"
    },
    {
        "com": "Fedex-国际件-中文",
        "no": "fedexcn"
    },
    {
        "com": "联邦快递-英文",
        "no": "lianbangkuaidien"
    },
    {
        "com": "中通（带电话）",
        "no": "zhongtongphone"
    },
    {
        "com": "赛澳递for买卖宝",
        "no": "saiaodimmb"
    },
    {
        "com": "上海无疆for买卖宝",
        "no": "shanghaiwujiangmmb"
    },
    {
        "com": "新加坡小包(Singapore Post)",
        "no": "singpost"
    },
    {
        "com": "音素快运",
        "no": "yinsu"
    },
    {
        "com": "南方传媒物流",
        "no": "ndwl"
    },
    {
        "com": "速呈宅配",
        "no": "sucheng"
    },
    {
        "com": "创一快递",
        "no": "chuangyi"
    },
    {
        "com": "云南滇驿物流",
        "no": "dianyi"
    },
    {
        "com": "重庆星程快递",
        "no": "cqxingcheng"
    },
    {
        "com": "四川星程快递",
        "no": "scxingcheng"
    },
    {
        "com": "贵州星程快递",
        "no": "gzxingcheng"
    },
    {
        "com": "运通中港快递(作废)",
        "no": "ytkd"
    },
    {
        "com": "Gati-英文",
        "no": "gatien"
    },
    {
        "com": "Gati-中文",
        "no": "gaticn"
    },
    {
        "com": "jcex",
        "no": "jcex"
    },
    {
        "com": "派尔快递",
        "no": "peex"
    },
    {
        "com": "凯信达",
        "no": "kxda"
    },
    {
        "com": "安达信",
        "no": "advancing"
    },
    {
        "com": "汇文",
        "no": "huiwen"
    },
    {
        "com": "亿翔",
        "no": "yxexpress"
    },
    {
        "com": "东红物流",
        "no": "donghong"
    },
    {
        "com": "飞远配送",
        "no": "feiyuanvipshop"
    },
    {
        "com": "好运来",
        "no": "hlyex"
    },
    {
        "com": "Toll",
        "no": "dpexen"
    },
    {
        "com": "增益速递",
        "no": "zengyisudi"
    },
    {
        "com": "四川快优达速递",
        "no": "kuaiyouda"
    },
    {
        "com": "日昱物流",
        "no": "riyuwuliu"
    },
    {
        "com": "速通物流",
        "no": "sutongwuliu"
    },
    {
        "com": "晟邦物流",
        "no": "nanjingshengbang"
    },
    {
        "com": "爱尔兰(An Post)",
        "no": "anposten"
    },
    {
        "com": "日本（Japan Post）",
        "no": "japanposten"
    },
    {
        "com": "丹麦(Post Denmark)",
        "no": "postdanmarken"
    },
    {
        "com": "巴西(Brazil Post/Correios)",
        "no": "brazilposten"
    },
    {
        "com": "荷兰挂号信(PostNL international registered mail)",
        "no": "postnlcn"
    },
    {
        "com": "荷兰挂号信(PostNL international registered mail)",
        "no": "postnl"
    },
    {
        "com": "乌克兰EMS-中文(EMS Ukraine)",
        "no": "emsukrainecn"
    },
    {
        "com": "乌克兰EMS(EMS Ukraine)",
        "no": "emsukraine"
    },
    {
        "com": "乌克兰邮政包裹",
        "no": "ukrpostcn"
    },
    {
        "com": "乌克兰小包、大包(UkrPost)",
        "no": "ukrpost"
    },
    {
        "com": "海红for买卖宝",
        "no": "haihongmmb"
    },
    {
        "com": "FedEx-英国件（FedEx UK)",
        "no": "fedexuk"
    },
    {
        "com": "FedEx-英国件",
        "no": "fedexukcn"
    },
    {
        "com": "叮咚快递",
        "no": "dingdong"
    },
    {
        "com": "DPD",
        "no": "dpd"
    },
    {
        "com": "UPS Freight",
        "no": "upsfreight"
    },
    {
        "com": "ABF",
        "no": "abf"
    },
    {
        "com": "Purolator",
        "no": "purolator"
    },
    {
        "com": "比利时（Bpost）",
        "no": "bpost"
    },
    {
        "com": "比利时国际(Bpost international)",
        "no": "bpostinter"
    },
    {
        "com": "LaserShip",
        "no": "lasership"
    },
    {
        "com": "英国大包、EMS（Parcel Force）",
        "no": "parcelforce"
    },
    {
        "com": "英国邮政大包EMS",
        "no": "parcelforcecn"
    },
    {
        "com": "YODEL",
        "no": "yodel"
    },
    {
        "com": "DHL-荷兰（DHL Netherlands）",
        "no": "dhlnetherlands"
    },
    {
        "com": "MyHermes",
        "no": "myhermes"
    },
    {
        "com": "DPD Germany",
        "no": "dpdgermany"
    },
    {
        "com": "Fastway Ireland",
        "no": "fastway"
    },
    {
        "com": "法国大包、EMS-法文（Chronopost France）",
        "no": "chronopostfra"
    },
    {
        "com": "Selektvracht",
        "no": "selektvracht"
    },
    {
        "com": "蓝弧快递",
        "no": "lanhukuaidi"
    },
    {
        "com": "比利时(Belgium Post)",
        "no": "belgiumpost"
    },
    {
        "com": "UPS Mail Innovations",
        "no": "upsmailinno"
    },
    {
        "com": "挪威（Posten Norge）",
        "no": "postennorge"
    },
    {
        "com": "瑞士邮政",
        "no": "swisspostcn"
    },
    {
        "com": "瑞士(Swiss Post)",
        "no": "swisspost"
    },
    {
        "com": "英国邮政小包",
        "no": "royalmailcn"
    },
    {
        "com": "英国小包（Royal Mail）",
        "no": "royalmail"
    },
    {
        "com": "DHL Benelux",
        "no": "dhlbenelux"
    },
    {
        "com": "Nova Poshta",
        "no": "novaposhta"
    },
    {
        "com": "DHL-波兰（DHL Poland）",
        "no": "dhlpoland"
    },
    {
        "com": "Estes",
        "no": "estes"
    },
    {
        "com": "TNT UK",
        "no": "tntuk"
    },
    {
        "com": "Deltec Courier",
        "no": "deltec"
    },
    {
        "com": "OPEK",
        "no": "opek"
    },
    {
        "com": "DPD Poland",
        "no": "dpdpoland"
    },
    {
        "com": "Italy SDA",
        "no": "italysad"
    },
    {
        "com": "MRW",
        "no": "mrw"
    },
    {
        "com": "Chronopost Portugal",
        "no": "chronopostport"
    },
    {
        "com": "西班牙(Correos de Espa?a)",
        "no": "correosdees"
    },
    {
        "com": "Direct Link",
        "no": "directlink"
    },
    {
        "com": "ELTA Hellenic Post",
        "no": "eltahell"
    },
    {
        "com": "捷克（?eská po?ta）",
        "no": "ceskaposta"
    },
    {
        "com": "Siodemka",
        "no": "siodemka"
    },
    {
        "com": "International Seur",
        "no": "seur"
    },
    {
        "com": "久易快递",
        "no": "jiuyicn"
    },
    {
        "com": "克罗地亚（Hrvatska Posta）",
        "no": "hrvatska"
    },
    {
        "com": "保加利亚（Bulgarian Posts）",
        "no": "bulgarian"
    },
    {
        "com": "Portugal Seur",
        "no": "portugalseur"
    },
    {
        "com": "EC-Firstclass",
        "no": "ecfirstclass"
    },
    {
        "com": "DTDC India",
        "no": "dtdcindia"
    },
    {
        "com": "Safexpress",
        "no": "safexpress"
    },
    {
        "com": "韩国（Korea Post）",
        "no": "koreapost"
    },
    {
        "com": "TNT Australia",
        "no": "tntau"
    },
    {
        "com": "泰国（Thailand Thai Post）",
        "no": "thailand"
    },
    {
        "com": "SkyNet Malaysia",
        "no": "skynetmalaysia"
    },
    {
        "com": "马来西亚小包（Malaysia Post(Registered)）",
        "no": "malaysiapost"
    },
    {
        "com": "马来西亚大包、EMS（Malaysia Post(parcel,EMS)）",
        "no": "malaysiaems"
    },
    {
        "com": "京东",
        "no": "jd"
    },
    {
        "com": "沙特阿拉伯(Saudi Post)",
        "no": "saudipost"
    },
    {
        "com": "南非（South African Post Office）",
        "no": "southafrican"
    },
    {
        "com": "OCA Argentina",
        "no": "ocaargen"
    },
    {
        "com": "尼日利亚(Nigerian Postal)",
        "no": "nigerianpost"
    },
    {
        "com": "智利(Correos Chile)",
        "no": "chile"
    },
    {
        "com": "以色列(Israel Post)",
        "no": "israelpost"
    },
    {
        "com": "Toll Priority(Toll Online)",
        "no": "tollpriority"
    },
    {
        "com": "Estafeta",
        "no": "estafeta"
    },
    {
        "com": "港快速递",
        "no": "gdkd"
    },
    {
        "com": "墨西哥（Correos de Mexico）",
        "no": "mexico"
    },
    {
        "com": "罗马尼亚（Posta Romanian）",
        "no": "romanian"
    },
    {
        "com": "TNT Italy",
        "no": "tntitaly"
    },
    {
        "com": "Mexico Multipack",
        "no": "multipack"
    },
    {
        "com": "葡萄牙（Portugal CTT）",
        "no": "portugalctt"
    },
    {
        "com": "Interlink Express",
        "no": "interlink"
    },
    {
        "com": "DPD UK",
        "no": "dpduk"
    },
    {
        "com": "华航快递",
        "no": "hzpl"
    },
    {
        "com": "Gati-KWE",
        "no": "gatikwe"
    },
    {
        "com": "Red Express",
        "no": "redexpress"
    },
    {
        "com": "Mexico Senda Express",
        "no": "mexicodenda"
    },
    {
        "com": "TCI XPS",
        "no": "tcixps"
    },
    {
        "com": "高铁速递",
        "no": "hre"
    },
    {
        "com": "新加坡EMS、大包(Singapore Speedpost)",
        "no": "speedpost"
    },
    {
        "com": "EMS-国际件-英文",
        "no": "emsinten"
    },
    {
        "com": "Asendia USA",
        "no": "asendiausa"
    },
    {
        "com": "法国大包、EMS-英文(Chronopost France)",
        "no": "chronopostfren"
    },
    {
        "com": "意大利(Poste Italiane)",
        "no": "italiane"
    },
    {
        "com": "冠达快递",
        "no": "gda"
    },
    {
        "com": "出口易",
        "no": "chukou1"
    },
    {
        "com": "黄马甲",
        "no": "huangmajia"
    },
    {
        "com": "新干线快递",
        "no": "anlexpress"
    },
    {
        "com": "飞洋快递",
        "no": "shipgce"
    },
    {
        "com": "贝海国际速递",
        "no": "xlobo"
    },
    {
        "com": "阿联酋(Emirates Post)",
        "no": "emirates"
    },
    {
        "com": "新顺丰（NSF）",
        "no": "nsf"
    },
    {
        "com": "巴基斯坦(Pakistan Post)",
        "no": "pakistan"
    },
    {
        "com": "世运快递",
        "no": "shiyunkuaidi"
    },
    {
        "com": "合众速递(UCS）",
        "no": "ucs"
    },
    {
        "com": "阿富汗(Afghan Post)",
        "no": "afghan"
    },
    {
        "com": "白俄罗斯(Belpochta)",
        "no": "belpost"
    },
    {
        "com": "全通快运",
        "no": "quantwl"
    },
    {
        "com": "宅急便",
        "no": "zhaijibian"
    },
    {
        "com": "EFS Post",
        "no": "efs"
    },
    {
        "com": "TNT Post",
        "no": "tntpostcn"
    },
    {
        "com": "英脉物流",
        "no": "gml"
    },
    {
        "com": "广通速递",
        "no": "gtongsudi"
    },
    {
        "com": "东瀚物流",
        "no": "donghanwl"
    },
    {
        "com": "rpx",
        "no": "rpx"
    },
    {
        "com": "日日顺物流",
        "no": "rrs"
    },
    {
        "com": "黑猫雅玛多",
        "no": "yamato"
    },
    {
        "com": "华通快运",
        "no": "htongexpress"
    },
    {
        "com": "吉尔吉斯斯坦(Kyrgyz Post)",
        "no": "kyrgyzpost"
    },
    {
        "com": "拉脱维亚(Latvijas Pasts)",
        "no": "latvia"
    },
    {
        "com": "黎巴嫩(Liban Post)",
        "no": "libanpost"
    },
    {
        "com": "立陶宛（Lietuvos pa?tas）",
        "no": "lithuania"
    },
    {
        "com": "马尔代夫(Maldives Post)",
        "no": "maldives"
    },
    {
        "com": "马耳他（Malta Post）",
        "no": "malta"
    },
    {
        "com": "马其顿(Macedonian Post)",
        "no": "macedonia"
    },
    {
        "com": "新西兰（New Zealand Post）",
        "no": "newzealand"
    },
    {
        "com": "摩尔多瓦(Posta Moldovei)",
        "no": "moldova"
    },
    {
        "com": "孟加拉国(EMS)",
        "no": "bangladesh"
    },
    {
        "com": "塞尔维亚(PE Post of Serbia)",
        "no": "serbia"
    },
    {
        "com": "塞浦路斯(Cyprus Post)",
        "no": "cypruspost"
    },
    {
        "com": "突尼斯EMS(Rapid-Poste)",
        "no": "tunisia"
    },
    {
        "com": "乌兹别克斯坦(Post of Uzbekistan)",
        "no": "uzbekistan"
    },
    {
        "com": "新喀里多尼亚[法国](New Caledonia)",
        "no": "caledonia"
    },
    {
        "com": "叙利亚(Syrian Post)",
        "no": "republic"
    },
    {
        "com": "亚美尼亚(Haypost-Armenian Postal)",
        "no": "haypost"
    },
    {
        "com": "也门(Yemen Post)",
        "no": "yemen"
    },
    {
        "com": "印度(India Post)",
        "no": "india"
    },
    {
        "com": "英国(大包,EMS)",
        "no": "england"
    },
    {
        "com": "约旦(Jordan Post)",
        "no": "jordan"
    },
    {
        "com": "越南小包(Vietnam Posts)",
        "no": "vietnam"
    },
    {
        "com": "黑山(Po?ta Crne Gore)",
        "no": "montenegro"
    },
    {
        "com": "哥斯达黎加(Correos de Costa Rica)",
        "no": "correos"
    },
    {
        "com": "西安喜来快递",
        "no": "xilaikd"
    },
    {
        "com": "格陵兰[丹麦]（TELE Greenland A/S）",
        "no": "greenland"
    },
    {
        "com": "菲律宾（Philippine Postal）",
        "no": "phlpost"
    },
    {
        "com": "厄瓜多尔(Correos del Ecuador)",
        "no": "ecuador"
    },
    {
        "com": "冰岛(Iceland Post)",
        "no": "iceland"
    },
    {
        "com": "波兰小包(Poczta Polska)",
        "no": "emonitoring"
    },
    {
        "com": "阿尔巴尼亚(Posta shqipatre)",
        "no": "albania"
    },
    {
        "com": "阿鲁巴[荷兰]（Post Aruba）",
        "no": "aruba"
    },
    {
        "com": "埃及（Egypt Post）",
        "no": "egypt"
    },
    {
        "com": "爱尔兰(An Post)",
        "no": "ireland"
    },
    {
        "com": "爱沙尼亚(Eesti Post)",
        "no": "omniva"
    },
    {
        "com": "云豹国际货运",
        "no": "leopard"
    },
    {
        "com": "中外运空运",
        "no": "sinoairinex"
    },
    {
        "com": "上海昊宏国际货物",
        "no": "hyk"
    },
    {
        "com": "城晓国际快递",
        "no": "ckeex"
    },
    {
        "com": "匈牙利（Magyar Posta）",
        "no": "hungary"
    },
    {
        "com": "澳门(Macau Post)",
        "no": "macao"
    },
    {
        "com": "台湾（中华邮政）",
        "no": "postserv"
    },
    {
        "com": "北京EMS",
        "no": "bjemstckj"
    },
    {
        "com": "快淘快递",
        "no": "kuaitao"
    },
    {
        "com": "秘鲁(SERPOST)",
        "no": "peru"
    },
    {
        "com": "印度尼西亚EMS(Pos Indonesia-EMS)",
        "no": "indonesia"
    },
    {
        "com": "哈萨克斯坦(Kazpost)",
        "no": "kazpost"
    },
    {
        "com": "立白宝凯物流",
        "no": "lbbk"
    },
    {
        "com": "百千诚物流",
        "no": "bqcwl"
    },
    {
        "com": "皇家物流",
        "no": "pfcexpress"
    },
    {
        "com": "法国(La Poste)",
        "no": "csuivi"
    },
    {
        "com": "奥地利(Austrian Post)",
        "no": "austria"
    },
    {
        "com": "乌克兰小包、大包(UkrPoshta)",
        "no": "ukraine"
    },
    {
        "com": "乌干达(Posta Uganda)",
        "no": "uganda"
    },
    {
        "com": "阿塞拜疆EMS(EMS AzerExpressPost)",
        "no": "azerbaijan"
    },
    {
        "com": "芬兰(Itella Posti Oy)",
        "no": "finland"
    },
    {
        "com": "斯洛伐克(Slovenská Posta)",
        "no": "slovak"
    },
    {
        "com": "埃塞俄比亚(Ethiopian postal)",
        "no": "ethiopia"
    },
    {
        "com": "卢森堡(Luxembourg Post)",
        "no": "luxembourg"
    },
    {
        "com": "毛里求斯(Mauritius Post)",
        "no": "mauritius"
    },
    {
        "com": "文莱(Brunei Postal)",
        "no": "brunei"
    },
    {
        "com": "Quantium",
        "no": "quantium"
    },
    {
        "com": "坦桑尼亚(Tanzania Posts)",
        "no": "tanzania"
    },
    {
        "com": "阿曼(Oman Post)",
        "no": "oman"
    },
    {
        "com": "直布罗陀[英国]( Royal Gibraltar Post)",
        "no": "gibraltar"
    },
    {
        "com": "博源恒通",
        "no": "byht"
    },
    {
        "com": "越南EMS(VNPost Express)",
        "no": "vnpost"
    },
    {
        "com": "安迅物流",
        "no": "anxl"
    },
    {
        "com": "达方物流",
        "no": "dfpost"
    },
    {
        "com": "兰州伙伴物流",
        "no": "huoban"
    },
    {
        "com": "天纵物流",
        "no": "tianzong"
    },
    {
        "com": "波黑(JP BH Posta)",
        "no": "bohei"
    },
    {
        "com": "玻利维亚",
        "no": "bolivia"
    },
    {
        "com": "柬埔寨(Cambodia Post)",
        "no": "cambodia"
    },
    {
        "com": "巴林(Bahrain Post)",
        "no": "bahrain"
    },
    {
        "com": "纳米比亚(NamPost)",
        "no": "namibia"
    },
    {
        "com": "卢旺达(Rwanda i-posita)",
        "no": "rwanda"
    },
    {
        "com": "莱索托(Lesotho Post)",
        "no": "lesotho"
    },
    {
        "com": "肯尼亚(POSTA KENYA)",
        "no": "kenya"
    },
    {
        "com": "喀麦隆(CAMPOST)",
        "no": "cameroon"
    },
    {
        "com": "伯利兹(Belize Postal)",
        "no": "belize"
    },
    {
        "com": "巴拉圭(Correo Paraguayo)",
        "no": "paraguay"
    },
    {
        "com": "十方通物流",
        "no": "sfift"
    },
    {
        "com": "飞鹰物流",
        "no": "hnfy"
    },
    {
        "com": "UPS i-parcle",
        "no": "iparcel"
    },
    {
        "com": "鑫锐达",
        "no": "bjxsrd"
    },
    {
        "com": "麦力快递",
        "no": "mailikuaidi"
    },
    {
        "com": "瑞丰速递",
        "no": "rfsd"
    },
    {
        "com": "美联快递",
        "no": "letseml"
    },
    {
        "com": "CNPEX中邮快递",
        "no": "cnpex"
    },
    {
        "com": "鑫世锐达",
        "no": "xsrd"
    },
    {
        "com": "同舟行物流",
        "no": "chinatzx"
    },
    {
        "com": "秦邦快运",
        "no": "qbexpress"
    },
    {
        "com": "大达物流",
        "no": "idada"
    },
    {
        "com": "skynet",
        "no": "skynet"
    },
    {
        "com": "红马速递",
        "no": "nedahm"
    },
    {
        "com": "云南中诚",
        "no": "czwlyn"
    },
    {
        "com": "万博快递",
        "no": "wanboex"
    },
    {
        "com": "腾达速递",
        "no": "nntengda"
    },
    {
        "com": "郑州速捷",
        "no": "sujievip"
    },
    {
        "com": "UBI Australia",
        "no": "gotoubi"
    },
    {
        "com": "ECMS Express",
        "no": "ecmsglobal"
    },
    {
        "com": "速派快递(FastGo)",
        "no": "fastgo"
    },
    {
        "com": "易客满",
        "no": "ecmscn"
    },
    {
        "com": "俄顺达",
        "no": "eshunda"
    },
    {
        "com": "广东速腾物流",
        "no": "suteng"
    },
    {
        "com": "新鹏快递",
        "no": "gdxp"
    },
    {
        "com": "美国韵达",
        "no": "yundaexus"
    },
    {
        "com": "Toll",
        "no": "toll"
    },
    {
        "com": "深圳DPEX",
        "no": "szdpex"
    },
    {
        "com": "百世物流",
        "no": "baishiwuliu"
    },
    {
        "com": "荷兰包裹(PostNL International Parcels)",
        "no": "postnlpacle"
    },
    {
        "com": "乐天速递",
        "no": "ltexp"
    },
    {
        "com": "智通物流",
        "no": "ztong"
    },
    {
        "com": "鑫通宝物流",
        "no": "xtb"
    },
    {
        "com": "airpak expresss",
        "no": "airpak"
    },
    {
        "com": "荷兰邮政-中国件",
        "no": "postnlchina"
    },
    {
        "com": "法国小包（colissimo）",
        "no": "colissimo"
    },
    {
        "com": "PCA Express",
        "no": "pcaexpress"
    },
    {
        "com": "韩润",
        "no": "hanrun"
    },
    {
        "com": "TNT",
        "no": "tnt"
    },
    {
        "com": "中远e环球",
        "no": "cosco"
    },
    {
        "com": "顺达快递",
        "no": "sundarexpress"
    },
    {
        "com": "捷记方舟",
        "no": "ajexpress"
    },
    {
        "com": "方舟速递",
        "no": "arkexpress"
    },
    {
        "com": "明大快递",
        "no": "adaexpress"
    },
    {
        "com": "长江国际速递",
        "no": "changjiang"
    },
    {
        "com": "八达通",
        "no": "bdatong"
    },
    {
        "com": "美国申通",
        "no": "stoexpress"
    },
    {
        "com": "泛捷国际速递",
        "no": "epanex"
    }]
}*/
