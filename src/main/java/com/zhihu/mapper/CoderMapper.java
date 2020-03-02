package com.zhihu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zhihu.common.bean.Coder;

/**
 * @author HX
 *
 */
@Mapper
public interface CoderMapper {

	/**
	 * 查询代码表
	 * @param coder
	 * @return
	 */
	List<Coder> getcoder(Coder coder);
}
