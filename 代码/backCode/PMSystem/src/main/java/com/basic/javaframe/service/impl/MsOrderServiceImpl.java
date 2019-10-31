package com.basic.javaframe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import com.basic.javaframe.dao.MsOrderDao;
import com.basic.javaframe.entity.MsOrder;
import com.basic.javaframe.service.MsOrderService;




@Service("msOrderService")
@Transactional
public class MsOrderServiceImpl implements MsOrderService {
	@Autowired
	private MsOrderDao msOrderDao;

	@Override
	public List<MsOrder> getList(Map<String, Object> map){
		return msOrderDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return msOrderDao.getCount(map);
	}

	@Override
	public void save(MsOrder msOrder){
		msOrderDao.save(msOrder);
	}

	@Override
	public void update(MsOrder msOrder){
		msOrderDao.update(msOrder);
	}

	@Override
	public void deleteBatch(String[] rowGuids){
		msOrderDao.deleteBatch(rowGuids);
	}
	
}
