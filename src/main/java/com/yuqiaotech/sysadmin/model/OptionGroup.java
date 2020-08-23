package com.yuqiaotech.sysadmin.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
/**
 * 选项组。
 * TODO 注释尚未写完，没想好合适的例子。
 * 有时一个选项可能有多个属性。这些属性都有自己的名称，
 * 比如，你有个选项组叫油种，每个选项都有这么几个属性：
 * 标号，种类，
 * 所以这里扩展了5个字段，
 * 在每个选项中有的字段是一个选项的唯一标志，
 * 有个字段叫keyName
 * 
 */
@Entity
public class OptionGroup implements Serializable {

	private static final long serialVersionUID = -1940660074315549095L;
	/* 选项组的唯一标志 */
	private String groupId;
	/* 选项组名称 */
	private java.lang.String groupName;
	private String keyName;
	private String editor;
	private String columnName1;
	private String columnName2;
	private String columnName3;
	private String columnName4;
	private String columnName5;
	private String description;
	private String type;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private Set<SelectOption> selectOptions = new HashSet<SelectOption>(0);

	public OptionGroup() {
	}

	@Id
	@Column(name="f_group_id",length=60)
	public String getId() {
		return groupId;
	}

	public void setId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * 选项组的名称，该名称仅仅为显示名称。
	 * 
	 * @return
	 */
	public java.lang.String getGroupName() {
		return groupName;
	}

	public void setGroupName(java.lang.String groupName) {
		this.groupName = groupName;
	}

	/**
	 * 选项。
	 * 
	 * @return
	 */
	@OneToMany
	@JoinColumn(name="f_group_id")
	@OrderBy("displayOrder")
	public Set<SelectOption> getSelectOptions() {
		return selectOptions;
	}

	public void setSelectOptions(Set<SelectOption> selectOptions) {
		this.selectOptions = selectOptions;
	}
	/**
	 * 第一个属性的显示名称。
	 * 
	 * @return
	 */
	public String getColumnName1() {
		return columnName1;
	}

	public void setColumnName1(String columnName1) {
		this.columnName1 = columnName1;
	}
	public String getColumnName2() {
		return columnName2;
	}

	public void setColumnName2(String columnName2) {
		this.columnName2 = columnName2;
	}
	public String getColumnName3() {
		return columnName3;
	}

	public void setColumnName3(String columnName3) {
		this.columnName3 = columnName3;
	}
	public String getColumnName4() {
		return columnName4;
	}

	public void setColumnName4(String columnName4) {
		this.columnName4 = columnName4;
	}
	public String getColumnName5() {
		return columnName5;
	}

	public void setColumnName5(String columnName5) {
		this.columnName5 = columnName5;
	}
	/**
	 * 作为选项的唯一标志的选项属性的名称。
	 * 
	 * @return
	 */
	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	/**
	 * 说明。
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 选项组编辑方式。
	 * @return
	 */
	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}
}
