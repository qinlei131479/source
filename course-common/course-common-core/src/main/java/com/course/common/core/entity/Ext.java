package com.course.common.core.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.course.common.core.utils.HuToolUtil;

import cn.hutool.core.util.ArrayUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * 返回参数对象的扩展
 *
 * @author qinlei
 * @date 2020/3/26 下午2:57
 */
public class Ext {
	/**
	 * 扩展对象
	 */
	@Getter
	@Setter
	private Map<String, Object> extMap;
	private boolean check = true;
	private String[] keyList;

	public static Ext keys(String... keyList) {
		Ext ret = create();
		ret.keyList = keyList;
		return ret;
	}

	public Ext key(String... keyList) {
		this.keyList = keyList;
		return this;
	}

	public Ext listEnum(Class... enumClazzs) {
		if (this.check && ArrayUtil.isNotEmpty(this.keyList)) {
			for (int i = 0; i < keyList.length; i++) {
				this.append(keyList[i] + "List", HuToolUtil.buildEnumListOfCodeName(enumClazzs[i]));
			}
		}
		return this;
	}

	public Ext mapListEnum(Class... enumClazzs) {
		if (this.check && ArrayUtil.isNotEmpty(this.keyList)) {
			for (int i = 0; i < keyList.length; i++) {
				this.append(keyList[i] + "Map", HuToolUtil.buildEnumMapOfCodeName(enumClazzs[i]));
				this.append(keyList[i] + "List", HuToolUtil.buildEnumListOfCodeName(enumClazzs[i]));
			}
		}
		return this;
	}

	public Ext listObj(Object... obj) {
		if (this.check && ArrayUtil.isNotEmpty(this.keyList)) {
			for (int i = 0; i < keyList.length; i++) {
				this.append(keyList[i] + "List", obj[i]);
			}
		}
		return this;
	}

	public Ext obj(Object... obj) {
		if (this.check && ArrayUtil.isNotEmpty(this.keyList)) {
			for (int i = 0; i < keyList.length; i++) {
				this.append(keyList[i], obj[i]);
			}
		}
		return this;
	}

	public Ext mapEnum(Class... enumClazzs) {
		if (this.check && ArrayUtil.isNotEmpty(this.keyList)) {
			for (int i = 0; i < keyList.length; i++) {
				this.append(keyList[i] + "Map", HuToolUtil.buildEnumMapOfCodeName(enumClazzs[i]));
			}
		}
		return this;
	}

	public Ext append(String key, Object val) {
		this.getExtMap().put(key, val);
		return this;
	}

	private static Ext create() {
		Ext ext = new Ext();
		ext.setExtMap(new LinkedHashMap<String, Object>());
		return ext;
	}

	public static Ext build(String key, Object val) {
		return Ext.create().append(key, val);
	}
}
