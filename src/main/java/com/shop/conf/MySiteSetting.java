package com.shop.conf;

import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

@Component
@ConfigurationProperties
public class MySiteSetting {

	@Value("${com.yjh.site.host}")
	private String host;
	@Value("${com.yjh.site.staticHost}")
	private String staticHost;
	@Value("${com.yjh.site.whiteDomainList}")
	private String whiteDomainList;
	@Value("${com.yjh.site.siteDirPath}")
	private String siteDirPath;
	@Value("${com.yjh.site.uploadDir}")
	private String uploadDir;
	@Value("${com.yjh.site.session}")
	private String sessionName;
	@Value("${com.yjh.site.prefix}")
	private String prefix;
	@Value("${com.yjh.site.fractionA}")
	private Integer fractionA;
	@Value("${com.yjh.site.coverCharge}")
	private Double coverCharge;
	@Value("${com.yjh.site.fractionB}")
	private int fractionB;
	@Value("${com.yjh.site.fractionC}")
	private int fractionC;
	@Value("${com.yjh.site.AppKey}")
	private String AppKey;
	@Value("${com.yjh.site.EBusinessID}")
	private String EBusinessID;
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	public String getUploadDir() {

		return uploadDir;
	}
	public String getStaticHost() {
		return staticHost;
	}
	public void setStaticHost(String staticHost) {
		this.staticHost = staticHost;
	}
	public String getWhiteDomainList() {
		return whiteDomainList;
	}
	public void setWhiteDomainList(String whiteDomainList) {
		this.whiteDomainList = whiteDomainList;
	}
	public String getSiteDirPath() throws URISyntaxException {

		if (StringUtils.isNullOrEmpty(siteDirPath)) {
			siteDirPath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
		}

		return siteDirPath;
	}

	public void setSiteDirPath(String siteDirPath) {
		this.siteDirPath = siteDirPath;
	}

	/**
	 * 返回上传文件夹路径
	 * 
	 * @param absolute
	 *            是否绝对路径
	 * @return
	 * @throws URISyntaxException
	 */
	public String getUploadDir(boolean absolute) throws URISyntaxException {
		if (StringUtils.isNullOrEmpty(uploadDir)) {
			return uploadDir;
		}

		if (absolute) {
			return this.getSiteDirPath() + uploadDir;
		} else {
			return uploadDir;
		}
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public Integer getFractionA() {
		return fractionA;
	}

	public void setFractionA(Integer fractionA) {
		this.fractionA = fractionA;
	}

	public Double getCoverCharge() {
		return coverCharge;
	}

	public void setCoverCharge(Double coverCharge) {
		this.coverCharge = coverCharge;
	}

	public int getFractionB() {
		return fractionB;
	}

	public void setFractionB(int fractionB) {
		this.fractionB = fractionB;
	}

	public int getFractionC() {
		return fractionC;
	}

	public void setFractionC(int fractionC) {
		this.fractionC = fractionC;
	}

	public String getAppKey() {
		return AppKey;
	}

	public void setAppKey(String appKey) {
		AppKey = appKey;
	}

	public String getEBusinessID() {
		return EBusinessID;
	}

	public void setEBusinessID(String eBusinessID) {
		EBusinessID = eBusinessID;
	}
}
