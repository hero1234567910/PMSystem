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
import com.basic.javaframe.entity.MsUser;
import com.basic.javaframe.service.MsUserService;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;


/**
 * 
 * 
 * @author wzl
 * @date 2019-10-31 10:28:59
 */
@Api(value = "用户管理")
@RestController
@CrossOrigin
@RequestMapping("sys/msuser")
public class MsUserController {
	@Autowired
	private MsUserService msUserService;
	
	/**
	 * 列表数据
	 */
	@PassToken
	@ApiOperation(value="用户数据列表")
    @ResponseBody
	@RequestMapping(value="/listData",produces="application/json;charset=utf-8",method=RequestMethod.GET)
	public LayuiUtil listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<MsUser> msUserList = msUserService.getList(query);
		int total = msUserService.getCount(query);
		PageUtils pageUtil = new PageUtils(msUserList, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
	}

    /**
     * 新增
     **/
    @ApiOperation(value="新增用户")
    @ResponseBody
    @RequestMapping(value="/add",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R add(@RequestBody MsUser msUser){
    	//如果排序号为空，则自动转为0
    	if (msUser.getSortSq() == null) {
			msUser.setSortSq(0);
		}
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		msUser.setRowGuid(uuid);
		msUser.setDelFlag(0);
		Date createTime = DateUtil.changeDate(new Date());
		msUser.setCreateTime(createTime);
		msUserService.save(msUser);
        return R.ok().put("data",msUser);
    }

	/**
	 * 修改
	 */
	@ApiOperation(value="修改用户")
    @ResponseBody
	@RequestMapping(value="/update", produces = "application/json; charset=utf-8", method=RequestMethod.POST)
	public R update(@RequestBody MsUser msUser){
		msUserService.update(msUser);
		return R.ok();
	}

	/**
	 * 删除
	 */
    @ApiOperation(value="删除用户")
	@ResponseBody
	@RequestMapping(value="/delete",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	public R delete(@RequestBody String[] rowGuids){
		msUserService.deleteBatch(rowGuids);
		return R.ok();
	}
	
}
