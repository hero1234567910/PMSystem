package com.basic.javaframe.service;

import com.basic.javaframe.entity.MsUser;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author 
 * @date 2019-10-31 10:28:59
 */
public interface MsUserService {
	
	List<MsUser> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(MsUser msUser);
	
	void update(MsUser msUser);
	
	void deleteBatch(String[] rowGuids);
}
