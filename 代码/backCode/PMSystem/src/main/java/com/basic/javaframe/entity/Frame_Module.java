package com.basic.javaframe.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>Title: Frame_Module</p>
 * <p>Description: 模块管理实体</p>
 * @author wzl
 */
public class Frame_Module {
    //行标识
    private Integer rowId;
    //记录唯一标识
    private String rowGuid;
    //删除标识
    private int delFlag;
    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    //更新时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
    //模块编号
    private String moduleCode;
    //上级模块编号
    private String pmoduleCode;
    //排序号
    private int sortSq;
    
    private int hidden;
    
    private String redirect;
    
    private String name;
    
    private String title;
    
    private String icon;
    
    private int noCache;
    
    private int breadcrumb;
    
    private String component;
    
    private String path;

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getRowId() {
		return rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public String getRowGuid() {
		return rowGuid;
	}

	public void setRowGuid(String rowGuid) {
		this.rowGuid = rowGuid;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getPmoduleCode() {
		return pmoduleCode;
	}

	public void setPmoduleCode(String pmoduleCode) {
		this.pmoduleCode = pmoduleCode;
	}

	public int getSortSq() {
		return sortSq;
	}

	public void setSortSq(int sortSq) {
		this.sortSq = sortSq;
	}

	public int getHidden() {
		return hidden;
	}

	public void setHidden(int hidden) {
		this.hidden = hidden;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getNoCache() {
		return noCache;
	}

	public void setNoCache(int noCache) {
		this.noCache = noCache;
	}

	public int getBreadcrumb() {
		return breadcrumb;
	}

	public void setBreadcrumb(int breadcrumb) {
		this.breadcrumb = breadcrumb;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
    
   

   
}
