package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.MsUserDao;
import com.basic.javaframe.entity.MsUser;
import com.basic.javaframe.service.MsUserService;




@Service("msUserService")
@Transactional
public class MsUserServiceImpl implements MsUserService {
	@Autowired
	private MsUserDao msUserDao;

	@Override
	public List<MsUser> getList(Map<String, Object> map){
		return msUserDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return msUserDao.getCount(map);
	}

	@Override
	public void save(MsUser msUser){
		msUserDao.save(msUser);
	}

	@Override
	public void update(MsUser msUser){
		msUserDao.update(msUser);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		msUserDao.deleteBatch(rowGuids);
	}
	
}
