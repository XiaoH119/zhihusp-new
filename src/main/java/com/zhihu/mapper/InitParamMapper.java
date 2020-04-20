package com.zhihu.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import com.zhihu.common.bean.SystemParam;

@Mapper
public interface InitParamMapper {

	@MapKey("paramcode")
	Map<String, Map<String,String>> getSystemParam();
}
