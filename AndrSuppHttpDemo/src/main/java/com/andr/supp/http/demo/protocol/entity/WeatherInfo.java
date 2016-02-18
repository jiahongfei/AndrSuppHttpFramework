package com.andr.supp.http.demo.protocol.entity;

import java.io.Serializable;

public class WeatherInfo implements Serializable {
//	"city": "北京", 
//    "cityid": "101010100", 
//    "temp": "9", 
//    "WD": "西南风", 
//    "WS": "2级", 
//    "SD": "22%", 
//    "WSE": "2", 
//    "time": "10:35", 
//    "isRadar": "1", 
//    "Radar": "JC_RADAR_AZ9010_JB", 
//    "njd": "暂无实况", 
//    "qy": "1015"
	private String city;
	private String cityid;
	private String temp;
	private String njd;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getNjd() {
		return njd;
	}
	public void setNjd(String njd) {
		this.njd = njd;
	}
	
}
