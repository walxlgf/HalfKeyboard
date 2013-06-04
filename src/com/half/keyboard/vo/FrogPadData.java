/**
 * 
 */
package com.half.keyboard.vo;

import java.util.List;

/**
 * @author Thinkpad
 *
 */
public class FrogPadData {

	List<NormalKeyData> normalKeyDatas;

	public List<NormalKeyData> getNormalKeyDatas() {
		return normalKeyDatas;
	}

	public void setNormalKeyDatas(List<NormalKeyData> normalKeyDatas) {
		this.normalKeyDatas = normalKeyDatas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FrogPadData [normalKeyDatas=");
		builder.append(normalKeyDatas);
		builder.append("]");
		return builder.toString();
	}
	
	

}
