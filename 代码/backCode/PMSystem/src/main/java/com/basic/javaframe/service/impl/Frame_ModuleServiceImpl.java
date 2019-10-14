package com.basic.javaframe.service.impl;

import java.awt.Frame;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basic.javaframe.common.enumresource.TrueOrFalseEnum;
import com.basic.javaframe.dao.Frame_ModuleDao;
import com.basic.javaframe.dao.Frame_ModuleRightDao;
import com.basic.javaframe.entity.Frame_Dept;
import com.basic.javaframe.entity.Frame_Module;
import com.basic.javaframe.entity.Frame_ModuleRight;
import com.basic.javaframe.service.Frame_ModuleService;

@Service("moduleService")
public class Frame_ModuleServiceImpl implements Frame_ModuleService{
	
	@Autowired
	private Frame_ModuleDao frame_moduleDao;
	
	@Autowired
	private Frame_ModuleRightDao modeuleRightDao;
	 /**
     * 查询模块管理列表
     *
     * @param params 模块管理信息
     * @return 模块管理集合
     */
    @Override
    public List<Frame_Module> selectFrameModuleList(Map<String, Object> params) {
        return frame_moduleDao.selectFrameModuleList(params);
    }


    /**
     * 增加模块
     *
     * @param module 模块管理信息
     * @return 模块管理集合
     */
    @Override
    public void insertFrameModule(Frame_Module module) {
    	frame_moduleDao.insertFrameModule(module);
    }

    /**
     * 更新模块
     *
     * @param module 模块管理信息
     * @return 模块管理集合
     */
    @Override
    public void updateFrameModule(Frame_Module module) {
    	frame_moduleDao.updateFrameModule(module);
    }


    /**
     * 删除模块
     *
     * @param ids 模块id
     * @return 模块管理集合
     */
    @Override
    public void deleteFrameModuleById(String[] ids) {
    	frame_moduleDao.deleteFrameModuleById(ids);
    }
    
    @Override
	public int getCount(Map<String, Object> params) {
		return frame_moduleDao.getCount(params);
	}
	
	@Override
	public JSONArray getTrees() {
		//查询所有顶级模块
		List<Frame_Module> deptTopTrees = frame_moduleDao.findModules();
		return getModuleTrees(deptTopTrees);
	}

	@Override
	public String getByModuleCode(String moduleCode) {
		return frame_moduleDao.getByModuleCode(moduleCode);
	}

	@Override
	public Frame_Module checkModuleName(String moduleName) {
		return frame_moduleDao.checkModuleName(moduleName);
	}

	@Override
	public Frame_Module checkModuleAddr(String moduleAddr) {
		return frame_moduleDao.checkModuleAddr(moduleAddr);
	}


	private JSONArray getModuleTrees(List<Frame_Module> deptTopTrees) {
		JSONArray array = new JSONArray();
		for (Frame_Module module : deptTopTrees) {
			JSONObject json = new JSONObject();
			json.put("name", module.getName());
			json.put("moduleCode", module.getModuleCode());
			json.put("component", module.getComponent());
			json.put("path", module.getPath());
			json.put("hidden",TrueOrFalseEnum.getValueByCode(module.getHidden()));
			json.put("redirect", module.getRedirect());
			json.put("label", module.getTitle());
			JSONObject js = new JSONObject();
			js.put("title", module.getTitle());
			js.put("icon", module.getIcon());
			js.put("noCache",TrueOrFalseEnum.getValueByCode(module.getNoCache()));
			js.put("breadcrumb", TrueOrFalseEnum.getValueByCode(module.getBreadcrumb()));
			//根据moduleGuid获取对应角色
			List<Frame_ModuleRight> moduleRight = modeuleRightDao.getByModuleGuid(module.getRowGuid());
			JSONArray arr = new JSONArray();
			if (moduleRight != null && moduleRight.size() != 0) {
				for (int i = 0; i < moduleRight.size(); i++) {
					arr.add(moduleRight.get(i).getRoleName());
				}
			}
			if ("0".equals(module.getPmoduleCode())) {
				js.put("roles", arr);
			}else{
				js.put("role", arr);
			}
			json.put("meta", js);
			//获取子模块
			List<Frame_Module> childDept = frame_moduleDao.getByPmoduleCode(module.getModuleCode());
			json.put("children", getModuleTrees(childDept));
			array.add(json);
		}
		return array;
	}

	@Override
	public JSONArray findModules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Frame_Module> isHasChild(String guid) {
		// TODO Auto-generated method stub
		return frame_moduleDao.isHasChild(guid);
	}
}
