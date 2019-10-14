package com.basic.javaframe.controller;

import com.alibaba.fastjson.JSONArray;
import com.basic.javaframe.common.customclass.PassToken;
import com.basic.javaframe.common.utils.DateUtil;
import com.basic.javaframe.common.utils.LayuiUtil;
import com.basic.javaframe.common.utils.PageUtils;
import com.basic.javaframe.common.utils.Query;
import com.basic.javaframe.common.utils.R;
import com.basic.javaframe.common.utils.StringUtil;
import com.basic.javaframe.entity.Frame_Dept;
import com.basic.javaframe.entity.Frame_Module;
import com.basic.javaframe.entity.Frame_User;
import com.basic.javaframe.service.Frame_DeptService;
import com.basic.javaframe.service.Frame_ModuleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 模块管理 信息操作处理
 * 部门控制层
 * @author hero
 * @date 2019-03-06
 */
@CrossOrigin
@Api(value = "模块")
@RestController
@RequestMapping("sys/modular") 
public class Frame_ModuleController {
    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(Frame_ModuleController.class);

    @Autowired 
    private Frame_ModuleService moduleService;             
  
    /**   
     * 查询模块列表   
     * <p>Title: getDept</p>    
     * <p>Description: 模块</p>
     * @author hero
     * @param params
     * @return
     */
    @PassToken
    @ApiOperation(value = "查询模块列表")
    @ResponseBody
    @RequestMapping(value = "/listData",produces="application/json;charset=utf-8",method= RequestMethod.GET)
    public LayuiUtil getDept(@RequestParam Map<String, Object> params){
    	Query query = new Query(params);
    	List<Frame_Module> list = moduleService.selectFrameModuleList(query);
		int total = moduleService.getCount(query);
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		return LayuiUtil.data(pageUtil.getTotalCount(), pageUtil.getList());
    }

    /**
     * 新增模块
     * <p>Title: addDept</p>
     * <p>Description: 模块</p>
     * @author hero
     * @param module
     * @return
     */
    @ApiOperation(value = "新增模块")
    @ResponseBody
    @RequestMapping(value = "/add",produces="application/json;charset=utf-8",method= RequestMethod.POST)
    public R addDept(@RequestBody Frame_Module module){
    	//生成uuid作为rowguid
        String uuid = java.util.UUID.randomUUID().toString();
		module.setRowGuid(uuid);
		Date createTime = DateUtil.changeDate(new Date());
		module.setCreateTime(createTime);
		//如果pmoduleCode为空，则将其赋值0作为顶级菜单
		if (StringUtils.isBlank(module.getPmoduleCode())) {
			module.setPmoduleCode("0");
		}
        String uu = UUID.randomUUID().toString();
		module.setModuleCode(uu);
		//如果组件为空 则默认为父菜单
		if ("".equals(module.getComponent()) || module.getComponent() == null) {
			module.setComponent("Layout");
		}
		moduleService.insertFrameModule(module);
        return R.ok().put("data", module);  
    }

    /**
     * 修改模块
     * <p>Title: addDept</p>
     * <p>Description: 模块</p>
     * @author hero
     * @param module
     * @return
     */
    @ApiOperation(value = "修改模块")
    @ResponseBody
    @RequestMapping(value = "/updateModule",produces="application/json;charset=utf-8",method= RequestMethod.PUT)
    public R updateDept(@RequestBody Frame_Module module){
    	Date updateTime = DateUtil.changeDate(new Date());
    	module.setUpdateTime(updateTime);
    	if (StringUtil.isBlank(module.getPmoduleCode())) {
    		module.setModuleCode("0");;
		}
    	moduleService.updateFrameModule(module);
        return R.ok();
    }

    /**
     * 删除模块
     * <p>Title: deleteDept</p>
     * <p>Description: 模块</p>
     * @author hero
     * @param Ids
     * @return
     */
    @ApiOperation(value="删除模块")
	@ResponseBody
	@RequestMapping(value="/deleteModule",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R deleteDept(@RequestBody String[] guids){
    	//如果模块有子集菜单 则不可删除
    	for (String guid : guids) {
			//查询所有的模块 pmoduleCode是否存在
    		List<Frame_Module> module = moduleService.isHasChild(guid);
    		if (module == null || module.size() == 0) {
    			moduleService.deleteFrameModuleById(guids);
			}else{
				return R.error("该模块下仍有子菜单，清先删除子菜单");
			}
		}
    	return R.ok();
    }
    
    /**
     * 获取树
     * <p>Title: getTrees</p>  
     * <p>Description: </p>
     * @author hero  
     * @return
     */
    @ApiOperation(value="获取树")
    @ResponseBody
    @RequestMapping(value="/getTrees",produces="application/json;charset=utf-8",method=RequestMethod.GET)
    public R getTrees(){ 
    	JSONArray trees = moduleService.getTrees();
    	return R.ok().put("data", trees);
    }
       
    /**    
     * 查询复选框模块树   
     * <p>Title: getdeptTrees</p>   
     * <p>Description: </p> 
     * @author hero  
     * @return
     */
    @ApiOperation(value="查询复选框模块树")
	@ResponseBody
	@RequestMapping(value="/getModuleTrees",produces="application/json;charset=utf-8",method=RequestMethod.POST)
    public R getModuleTrees(){
    	JSONArray trees = moduleService.findModules();
    	return R.ok().put("data", trees);
    }

    /**
     * 查询上级模块
     * <p>Title: getByModuleCode</p>
     * <p>Description: </p>
     * @author wzl
     * @return
     */
    @ApiOperation(value = "查询上级模块")
    @ResponseBody
    @RequestMapping(value="/getByModuleCode/{moduleCode}",produces="application/json;charset=utf-8",method=RequestMethod.GET)
    public R getByModuleCode(@PathVariable("moduleCode") String moduleCode){
        String name  = moduleService.getByModuleCode(moduleCode);
        return R.ok().put("data",name);
    }

    /**
     * 通过模块名检验重复
     * <p>Title: checkModuleName</p>
     * <p>Description: 模块验证</p>
     *
     * @param moduleName
     * @return
     * @author wzl
     */
    @ApiOperation(value = "通过模块名检验模块")
    @ResponseBody
    @RequestMapping(value = "/checkModuleName", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R checkModuleName(String moduleName){
        Frame_Module frameModule = moduleService.checkModuleName(moduleName);
        if(frameModule!=null){
            return R.error("模块名重复");
        }
        return R.ok();
    }

    /**
     * 通过模块地址检验重复
     * <p>Title: checkModuleName</p>
     * <p>Description: 模块验证</p>
     *
     * @param moduleAddr
     * @return
     * @author wzl
     */
    @ApiOperation(value = "通过模块地址检验模块")
    @ResponseBody
    @RequestMapping(value = "/checkModuleAddr", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
    public R checkModuleAddr(String moduleAddr){
        Frame_Module frameModule = moduleService.checkModuleAddr(moduleAddr);
        if(frameModule!=null){
            return R.error("模块地址重复");
        }
        return R.ok();
    }
    
    /**
     * 获取所有路由
     * <p>Title: getAllRoute</p>  
     * <p>Description: </p>
     * @author hero  
     * @return
     */
    @ApiOperation(value="获取所有路由")
    @ResponseBody
    @RequestMapping(value="/getAllRoute",produces="application/json;charset=utf-8",method=RequestMethod.GET)
    public R getAllRoute(){
    	JSONArray trees = moduleService.getTrees();
    	
    	return R.ok().put("data", trees);
    }
}
