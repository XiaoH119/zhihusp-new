package com.zhihu.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InitParamMapper {

	@MapKey("paramcode")
	Map<String, String> getSystemParam();
}
