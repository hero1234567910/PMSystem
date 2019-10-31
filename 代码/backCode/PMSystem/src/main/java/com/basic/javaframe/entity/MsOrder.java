package com.basic.javaframe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



 /**
 * <p>Title: MsOrder</p>
 * <p>Description:</p>
 * @author 
 */
public class MsOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/****/
	private Integer rowId;
	/****/
	private String rowGuid;
	/****/
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date createTime;
	/****/
	private Integer delFlag;
	/****/
	private Integer sortSq;
	/**订单流水号**/
	private String orderNumber;
	/**下订单所属用户标识**/
	private String orderUserGuid;
	/**订单商品标识**/
	private String orderItemGuid;
	/**收货人姓名**/
	private String consigneeName;
	/**收货人手机号**/
	private String consigneeMobile;
	/**订单状态**/
	private Integer orderStatus;
	/**备注**/
	private String remark;
	/**总金额**/
	private BigDecimal orderMoney;
	 /**
	  * 商户订单号
	  */
	 private String merchantNumber;

	 /**支付时间**/
	 private Date payTime;

	/**
	 * 设置：
	 */
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	/**
	 * 获取：
	 */
	public Integer getRowId() {
		return rowId;
	}
	/**
	 * 设置：
	 */
	public void setRowGuid(String rowGuid) {
		this.rowGuid = rowGuid;
	}
	/**
	 * 获取：
	 */
	public String getRowGuid() {
		return rowGuid;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：
	 */
	public Integer getDelFlag() {
		return delFlag;
	}
	/**
	 * 设置：
	 */
	public void setSortSq(Integer sortSq) {
		this.sortSq = sortSq;
	}
	/**
	 * 获取：
	 */
	public Integer getSortSq() {
		return sortSq;
	}
	/**
	 * 设置：订单流水号
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * 获取：订单流水号
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * 设置：下订单所属用户标识
	 */
	public void setOrderUserGuid(String orderUserGuid) {
		this.orderUserGuid = orderUserGuid;
	}
	/**
	 * 获取：下订单所属用户标识
	 */
	public String getOrderUserGuid() {
		return orderUserGuid;
	}
	/**
	 * 设置：订单商品标识
	 */
	public void setOrderItemGuid(String orderItemGuid) {
		this.orderItemGuid = orderItemGuid;
	}
	/**
	 * 获取：订单商品标识
	 */
	public String getOrderItemGuid() {
		return orderItemGuid;
	}
	/**
	 * 设置：收货人姓名
	 */
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	/**
	 * 获取：收货人姓名
	 */
	public String getConsigneeName() {
		return consigneeName;
	}
	/**
	 * 设置：收货人手机号
	 */
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}
	/**
	 * 获取：收货人手机号
	 */
	public String getConsigneeMobile() {
		return consigneeMobile;
	}
	/**
	 * 设置：订单状态
	 */
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * 获取：订单状态
	 */
	public Integer getOrderStatus() {
		return orderStatus;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：总金额
	 */
	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}
	/**
	 * 获取：总金额
	 */
	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	 public String getMerchantNumber() {
		 return merchantNumber;
	 }

	 public void setMerchantNumber(String merchantNumber) {
		 this.merchantNumber = merchantNumber;
	 }

	 public Date getPayTime() {
		 return payTime;
	 }

	 public void setPayTime(Date payTime) {
		 this.payTime = payTime;
	 }
 }
