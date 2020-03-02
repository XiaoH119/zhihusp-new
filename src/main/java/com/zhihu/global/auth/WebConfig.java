package com.zhihu.global.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//	/**
//	 * 添加拦截器
//	 */
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//        //拦截路径可自行配置多个 可用 ，分隔开
//		registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/admin/adminUser/adminLoginO**");
// 
//	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(authenticationInterceptor()).excludePathPatterns("/api/user/**").addPathPatterns("/**"); // 拦截所有请求，通过判断是否有
		registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/api/**"); // 拦截所有请求，通过判断是否有
																												// @LoginRequired
																															// 注解
																															// 决定是否需要登录
	}

	@Bean
	public JwtInterceptor authenticationInterceptor() {
		return new JwtInterceptor();
	}

//	/**
//	 * 跨域支持
//	 * 
//	 * @param registry
//	 */
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**")
//        .allowedOrigins("*")
//        .allowCredentials(true)
//        .allowedMethods("GET", "POST", "DELETE", "PUT")
//        .maxAge(3600 * 24);
//	}
// 
//	/**
//	 * 配置消息转换器--这里用的是alibaba 开源的 fastjson
//	 * 
//	 * @param converters
//	 */
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		
//		 //1.需要定义一个convert转换消息的对象;
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        //2.添加fastJson的配置信息，比如：是否要格式化返回的json数据;
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullStringAsEmpty,
//                SerializerFeature.DisableCircularReferenceDetect,
//                SerializerFeature.WriteNullListAsEmpty,
//                SerializerFeature.WriteDateUseDateFormat);
//        //3处理中文乱码问题
//        List<MediaType> fastMediaTypes = new ArrayList<>();
//        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        //4.在convert中添加配置信息.
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
//        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
//        //5.将convert添加到converters当中.
//        converters.add(fastJsonHttpMessageConverter);
//		
//	}
}
