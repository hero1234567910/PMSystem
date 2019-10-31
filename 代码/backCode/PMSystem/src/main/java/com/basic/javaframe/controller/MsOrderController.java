package com.basic.javaframe.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.entity.MsOrder;
import com.basic.javaframe.service.MsOrderService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author 
 * @date 2019-10-31 10:23:16
 */
@Api(value = "订单管理")
@RestController
@CrossOrigin
@RequestMapping("sys/msorder")
public class MsOrderController {
	@Autowired
	private MsOrderService msOrderService;
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="订单列表")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<MsOrder> msOrderList = msOrderService.getList(query);
		int total = msOrderService.getCount(query);
		PageUtils pageUtil = new PageUtils(msOrderList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增订单")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody MsOrder msOrder){
    	//如果排序号为空，则自动转为0
    	if (msOrder.getSortSq() == null) {
			msOrder.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		msOrder.setRowGuid(uuid);
		msOrder.setDelFlag(0);
		Date createTime = DateUtil.changeDate(new Date());
		msOrder.setCreateTime(createTime);
		msOrderService.save(msOrder);
        return R.ok().put("data",msOrder);
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="修改订单")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody MsOrder msOrder){
		msOrderService.update(msOrder);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除订单")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		msOrderService.deleteBatch(rowGuids);
		return R.ok();
	}
	
}
