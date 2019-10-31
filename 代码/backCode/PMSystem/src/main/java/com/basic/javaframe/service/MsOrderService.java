package com.basic.javaframe.service;

import com.basic.javaframe.entity.MsOrder;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-10-31 10:23:16
 */
public interface MsOrderService {
	
	List<MsOrder> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(MsOrder msOrder);
	
	void update(MsOrder msOrder);
	
	void deleteBatch(String[] rowGuids);
}
