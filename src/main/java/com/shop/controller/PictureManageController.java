package com.shop.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.shop.conf.MySiteSetting;
import com.shop.exception.BusinessException;
import com.shop.model.po.IdeaUser;
import com.shop.service.IdeaUserService;
import com.shop.util.FileUtil;
import com.shop.util.HttpUtil;
import com.shop.util.ImgUtil;
import com.shop.util.RedisUtil;
import com.shop.util.StringUtil;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

@Controller
@RequestMapping("/")
public class PictureManageController {

    private static Logger logger = LoggerFactory.getLogger(PictureManageController.class);

    /**
     * 图片上传目录
     */
    private String HEAD_IMAGE_PATH = "static/upload/head/"; // 头像图片路径
    
    private String PATH = "static/upload/pic/";
    
    private String PATH2 = "/upload/pic/";
    
    public String URL_PATH = "/upload/head/"; // 头像图片路径
    
    @Autowired
	private RedisUtil redisUtil;
    @Autowired
    private IdeaUserService ideaUserService;
    @Autowired
    private MySiteSetting mySiteSetting;

    /**
     *
     *  异步上传图片
     *
     * @param multipartRequest
     * @return
     * @throws URISyntaxException
     */
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String UploadAction(MultipartHttpServletRequest multipartRequest) throws URISyntaxException {

        MultipartFile imgFile = multipartRequest.getFile("file");
        if (StringUtil.isNotNullOrEmpty(imgFile)) {
        	//获取当前时间，按照时间分成不同的文件夹
			Date now=new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//可以方便地修改日期格式
			String day = dateFormat.format(now);
			
            String fileName = imgFile.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            String path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + PATH + day+"/";
            
            File file = new File(path);
			if (!file.exists()){
				file.mkdirs();
			}
            
            try {
                Map<String, String> json = new HashMap<String, String>();
                String imgurl = FileUtil.saveToFile(path, fileType, 
                        ImgUtil.scale(imgFile.getInputStream(), fileType, 731, 731, 2, false));
                
                json.put("file", mySiteSetting.getHost() + PATH2 + day + "/" + imgurl);
                json.put("url", day + "/" + imgurl);
                logger.info("上传图片成功：{imgname: "+PATH + imgurl);
                return JSONObject.toJSONString(json);
            } catch (IOException e) {
                logger.error("上次图片失败"+e);
            }
        }
        return null;
    }
    
    /**
     * 富文本框上传图片
     * @param multipartRequest
     * @return
     * @throws URISyntaxException
     */
   /* @ResponseBody
    @RequestMapping(value = "/editor/upload", method = RequestMethod.POST)
    public String editUpload(MultipartHttpServletRequest multipartRequest) throws URISyntaxException {

        MultipartFile imgFile = multipartRequest.getFile("file");
        if (StringUtil.isNotNullOrEmpty(imgFile)) {
        	//获取当前时间，按照时间分成不同的文件夹
			Date now=new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//可以方便地修改日期格式
			String day = dateFormat.format(now);
			
            String fileName = imgFile.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            String path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + PATH + day+"/";
            
            File file = new File(path);
			if (!file.exists()){
				file.mkdirs();
			}
            
            try {
                Map<String, Object> json = new HashMap<String, Object>();
                String imgurl = FileUtil.saveToFile(path, fileType,
                        ImgUtil.scale(imgFile.getInputStream(), fileType, 731, 731, 2, true));
                
                Map<String, String> res = new HashMap<String, String>();
                res.put("src", mySiteSetting.getHost() + PATH2 + day + "/" + imgurl);
                res.put("title", "pic");
                
                json.put("code", 0);
                json.put("msg", "");
                json.put("data", res);
                
                //json.put("file", );
                //json.put("url", day + "/" + imgurl);
                logger.info("上传图片成功：{imgname: "+PATH + imgurl);
                return JSONObject.toJSONString(json);
            } catch (IOException e) {
                logger.error("上次图片失败："+e);
                
                Map<String, Object> json = new HashMap<String, Object>();
                json.put("code", 204);
                json.put("msg", "上次图片失败");
                json.put("data", "");
                return JSONObject.toJSONString(json);
            }
        } else {
	        Map<String, Object> json2 = new HashMap<String, Object>();
	        json2.put("code", 204);
	        json2.put("msg", "上次图片失败");
	        json2.put("data", "");
	        return JSONObject.toJSONString(json2);
        }
    }*/
    
    /**
     * 富文本框上传图片获得配置文件
     * @param multipartRequest
     * @return
     * @throws URISyntaxException
     */
    @ResponseBody
    @RequestMapping(value = "/editor/upload", method = RequestMethod.GET)
    public String editUpload2(HttpServletRequest request) throws URISyntaxException {
    	
    	if ("config".equals(request.getParameter("action"))) {
    		/* 上传图片配置项 */
    	    String config = getConfig();
            return config;
        } else {
        	return null;
        }
    }
    
    /**
     * 富文本框上传图片获得配置文件
     * @param multipartRequest
     * @return
     * @throws URISyntaxException
     */
    @ResponseBody
    @RequestMapping(value = "/editor/upload", method = RequestMethod.POST)
    public String editUpload3(MultipartHttpServletRequest multipartRequest) throws URISyntaxException {
    	
        MultipartFile imgFile = multipartRequest.getFile("file");
        if (StringUtil.isNotNullOrEmpty(imgFile)) {
        	//获取当前时间，按照时间分成不同的文件夹
			Date now=new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//可以方便地修改日期格式
			String day = dateFormat.format(now);
			
            String fileName = imgFile.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            String path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + PATH + day+"/";
            
            File file = new File(path);
			if (!file.exists()){
				file.mkdirs();
			}
            
            try {
                String imgurl = FileUtil.saveToFile(path, fileType,
                        ImgUtil.scale(imgFile.getInputStream(), fileType, 731, 731, 2, false));
                
                Map<String, Object> json = new HashMap<String, Object>();
                json.put("state", "SUCCESS");
                json.put("url",  "/upload/pic/"+ day + "/" + imgurl);
                json.put("title", imgurl);
                json.put("original", imgurl);
                
                logger.info("上传图片成功：{imgname: "+PATH + imgurl);
                return JSONObject.toJSONString(json);
            } catch (IOException e) {
                logger.error("上次图片失败："+e);
                
                Map<String, Object> json = new HashMap<String, Object>();
                json.put("state", "上次图片失败");
                json.put("url", "");
                json.put("title", "");
                json.put("original", "");
                return JSONObject.toJSONString(json);
            }
        } else {
	        Map<String, Object> json2 = new HashMap<String, Object>();
	        json2.put("state", "上次图片失败");
	        json2.put("url", "");
	        json2.put("title", "");
	        json2.put("original", "");
	        return JSONObject.toJSONString(json2);
        }
    }

    /**
     *
     *  异步上传图片 base64转换为二进制流保存
     *
     * @return
     * @throws URISyntaxException
     */
    @ResponseBody
    @RequestMapping(value = "/toBase64Upload", method = RequestMethod.POST)
    public String uploadBase(@RequestBody Map<String, String> map){
    	if(map != null && map.get("pic") != null && map.get("pic") != ""){
    		String[] sp = map.get("pic").split(":")[1].split(";");
    		String file_type = sp[0];
    		String base64_photo = sp[1].split(",")[1];
            file_type = file_type.substring(file_type.indexOf("/")+1);
            if (StringUtil.isNotNullOrEmpty(base64_photo)) {
                byte[] result  = Base64.decodeBase64(base64_photo);
                String path;
				try {
					path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()
					        + HEAD_IMAGE_PATH;
					try {
	                    Map<String, String> json = new HashMap<String, String>();
	                    InputStream pic_stream = FileUtil.byte2Input(result);
	                    String imgurl = FileUtil.saveToFile(path,file_type,pic_stream);
	                    json.put("file", HEAD_IMAGE_PATH + imgurl);
	                    logger.info("upload picture from ip: upload picture success ,pic path is imgName: "+HEAD_IMAGE_PATH + imgurl);
	                    return JSONObject.toJSONString(json);
	                } catch (IOException e) {
	                    logger.error("upload picture from ip: upload picture fail for : "+e);
	                }
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
            }
    	}
    	return null;
    }
    
  /**
    *
    *  异步上传图片 base64转换为二进制流保存
    *
    * @return
    * @throws URISyntaxException
    */
   @ResponseBody
   @RequestMapping(value = "/toUploadBase64", method = RequestMethod.POST)
   public String uploadBase64(HttpServletRequest request) {

       String base64_photo = request.getParameter("file");
       String file_type = request.getParameter("file_type");
       file_type = file_type.substring(file_type.indexOf("/")+1);
       if (StringUtil.isNotNullOrEmpty(base64_photo)) {
           byte[] result  = Base64.decodeBase64(base64_photo);
           String path;
           try {
				path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath()
				           + HEAD_IMAGE_PATH;
				try {
		               Map<String, String> json = new HashMap<String, String>();
		               InputStream pic_stream = FileUtil.byte2Input(result);
		               String imgurl = FileUtil.saveToFile(path,file_type,pic_stream);
		               json.put("file", HEAD_IMAGE_PATH + imgurl);
		               logger.info("upload picture from ip: "+ HttpUtil.getClientIP(request)+"  upload picture success ,pic path is imgName: "+HEAD_IMAGE_PATH + imgurl);
		               return JSONObject.toJSONString(json);
		           } catch (IOException e) {
		               logger.error("upload picture from ip: "+ HttpUtil.getClientIP(request)+"  upload picture fail for : "+e);
		           }
           } catch (URISyntaxException e1) {
				e1.printStackTrace();
           }
       }
       return null;
   }

    /**
     *  根据图片url来获取图片
     *
     * @param request
     * @param response
     * @param url
     */
    @RequestMapping(value = { "/getPicByUrl" }, method = RequestMethod.GET)
    public void getPicByUrl(HttpServletRequest request, HttpServletResponse response, String url) {

        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
            String filePath = path + url;

            InputStream inputStream = new FileInputStream(filePath);

            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            byte[] data = swapStream.toByteArray();

            Magic parser = new Magic();
            MagicMatch match = parser.getMagicMatch(data);

            response.setContentType(match.getMimeType());
            OutputStream os = response.getOutputStream();
            os.write(data);
            os.flush();
            os.close();

        } catch (Exception e) {
          logger.error("图片找不到"+e);
        }
    }
    
    /**
     * 更新用户头像
     * @param map
     * @return
     * @throws BusinessException
     * @throws IOException 
     * @throws URISyntaxException 
     */
    @ResponseBody
	@RequestMapping(value="/api_upd_pic", method=RequestMethod.POST)
	public String apiUpdPic(@RequestParam(name="image") MultipartFile image, @RequestParam(name="open_id") String openId) throws BusinessException, IOException, URISyntaxException {
		Map<String, String> map3 = new HashMap<String, String>();
		//logger.error("图片上传："+ JSONObject.toJSONString(image));
		//logger.error("图片上传："+ JSONObject.toJSONString(openId));
		@SuppressWarnings("unchecked")
		Map<String, Object> map2 = (Map<String, Object>) redisUtil.get(openId);
		
        if(image != null && !image.isEmpty()) {
            String fileName = image.getOriginalFilename();
            String path = null;
            String type = null;
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if (type != null) {
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
                	//获取当前时间，按照时间分成不同的文件夹
        			Date now=new Date();
        			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//可以方便地修改日期格式
        			String day = dateFormat.format(now);
                    // 项目在容器中实际发布运行的根路径
                	String realPath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath() + "static/upload/pic/" + day;
                	File file = new File(realPath);
        			if (!file.exists()){
        				file.mkdirs();
        			}
        			
        			String trueFileName = UUID.randomUUID()+"";
                    // 设置存放图片文件的路径
                    path = realPath + "/" + trueFileName+"."+type;
                    image.transferTo(new File(path));
                    
                    String res = mySiteSetting.getHost() + "/upload/pic/"+ day + "/" + trueFileName+"."+type;
                    
                    IdeaUser ideaUser = new IdeaUser();
        			ideaUser.setId((Long) map2.get("userId"));
        			ideaUser.setAvatar(res);
        			ideaUserService.update(ideaUser);
        			
                    map3.put("data", res);
        			map3.put("statusCode", "200");
                }else {
                    map3.put("data", "文件类型不支持,请按要求重新上传");
        			map3.put("statusCode", "204");
                }
            }else {
            	map3.put("data", "文件类型为空");
    			map3.put("statusCode", "204");
            }
        }else {
        	map3.put("data", "未找到文件");
			map3.put("statusCode", "204");
        }
        return JSONObject.toJSONString(map3);
    }
    
    /**
     * 百度富文本框获得上传配置文件
     * @return
     */
    private String getConfig() {
    	
    	String val = "{\n"
			    +"imageActionName: \"uploadimage\",\n"
			    +"imageFieldName: \"file\",\n"
			    +"imageMaxSize: 20480000,\n"
			    +"imageAllowFiles: [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"],\n"
			    +"imageCompressEnable: true,\n"
			    +"imageCompressBorder: 1600,\n"
			    +"imageInsertAlign: \"none\",\n"
			    +"imageUrlPrefix: \"\",\n"
			    +"imagePathFormat: \"\",\n"
			
			    +"snapscreenActionName: \"uploadimage\",\n"
			    +"snapscreenPathFormat: \"\",\n"
			    +"snapscreenUrlPrefix: \"\",\n"
			    +"snapscreenInsertAlign: \"none\",\n"
			
			    +"catcherLocalDomain: [\"127.0.0.1\", \"localhost\", \"img.baidu.com\"],\n"
			    +"catcherActionName: \"catchimage\",\n"
			    +"catcherFieldName: \"source\",\n"
			    +"catcherPathFormat: \"/upload/pic/{yyyy}{mm}{dd}/{time}{rand:4}\",\n"
			    +"catcherUrlPrefix: \"\",\n"
			    +"catcherMaxSize: 20480000,\n"
			    +"catcherAllowFiles: [\".png\"],\n"
			
			    +"videoActionName: \"uploadvideo\",\n"
			    +"videoFieldName: \"upfile\",\n"
			    +"videoPathFormat: \"\",\n"
			    +"videoUrlPrefix: \"\",\n"
			    +"videoMaxSize: 102400000,\n"
			    +"videoAllowFiles: [\".flv\"],\n"
			
			    +"fileActionName: \"uploadfile\",\n"
			    +"fileFieldName: \"upfile\",\n"
			    +"filePathFormat: \"\",\n"
			    +"fileUrlPrefix: \"\",\n"
			    +"fileMaxSize: 51200000,\n"
			    +"fileAllowFiles: [\".png\"],\n"
			
			    +"imageManagerActionName: \"listimage\",\n"
			    +"imageManagerListPath: \"/upload/pic/\",\n"
			    +"imageManagerListSize: 20,\n"
			    +"imageManagerUrlPrefix: \"\",\n"
			    +"imageManagerInsertAlign: \"none\",\n"
			    +"imageManagerAllowFiles: [\".png\"],\n"
			
			    +"fileManagerActionName: \"listfile\",\n"
			    +"fileManagerListPath: \"/upload/file/\",\n"
			    +"fileManagerUrlPrefix: \"\",\n"
			    +"fileManagerListSize: 20,\n"
			    +"fileManagerAllowFiles: [\".png\"]\n"
			    +"}";
    	return val;
    }
}
