package vcoty.vainglory.go.enums;

import vcoty.vainglory.go.R;

public enum Region {
	
	CHINA("cn",R.string.cn_string), //中国
	
	NORTH_AMERICA("na",R.string.na_string), //北美
	
	EUROPE("eu",R.string.eu_string), //欧洲
	
	SOUTH_AMERICA("sa",R.string.sa_string), //南美
	
	EAST_ASIA("ea",R.string.ea_string), //东亚
	
	SOUTHEAST_ASIA("sg",R.string.sg_string), //东南亚
	
	UNKNOW("",R.string.default_string);
	
	private String content;
	private int resId;
	
	public Region(String content,int resId){
		this.content = content;
		this.resId = resId;
	}
	
	// 返回每一个枚举的值 //
	public String value(){
		return content;
	}
	
	/**
	 * 获取地区对应的资源ID
	 */
	public static int getStringResID(Region region){
		int id = R.string.cn_string;
		switch(region){
			case CHINA:
				id = R.string.cn_string;
				break;
			case NORTH_AMERICA:
				id = R.string.na_string;
				break;
			case EUROPE:
				id = R.string.eu_string;
				break;
			case SOUTH_AMERICA:
				id = R.string.sa_string;
				break;
			case EAST_ASIA:
				id = R.string.ea_string;
				break;
			case SOUTHEAST_ASIA:
				id = R.string.sg_string;
				break;
			}
		return id;
	}
	
	public static Region parse(String regionString){
		for(Region region : values()){
			if(region.value().equals(regionString)){
				return region;
			}
		}
		
		return UNKNOW;
	}
	
	public static int getColorResID(Region region){
		int colorRes = R.color.UnknowCountry;
		switch(region){
			case CHINA:
				colorRes = R.color.China;
				break;
			case EAST_ASIA:
				colorRes = R.color.EastAsia;
				break;
			case EUROPE:
				colorRes = R.color.Europe;
				break;
			case NORTH_AMERICA:
				colorRes = R.color.NorthAmerica;
				break;
			case SOUTH_AMERICA:
				colorRes = R.color.SouthAmerica;
				break;
			case SOUTHEAST_ASIA:
				colorRes = R.color.SoutheastAsia;
				break;
		}
		return colorRes;
	}
	
	public int getResId(){
		return resId;
	}

	@Override
	public String toString() {
		return  "name : " + name() + "\nvalue : " + content;
	}
	
	
}
