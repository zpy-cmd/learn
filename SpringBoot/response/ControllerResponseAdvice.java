package com.sunny.common.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.common.ResponseWrapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @ClassName ControllerResponseAdvice
 * @Author 张普裕
 * @Version 1.0.0
 * @Description 统一包装响应
 * @CreateTime 2022年08月11日 11:17:00
 */
@RestControllerAdvice
public class ControllerResponseAdvice implements ResponseBodyAdvice<Object> {
    /**
     * 分装包装条件
     *
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // response是ResultVo类型，或者注释了NotControllerResponseAdvice都不进行包装
        boolean isSupports = !(methodParameter.getParameterType().isAssignableFrom(ResponseWrapper.class)
                || methodParameter.hasMethodAnnotation(NotControllerResponseAdvice.class));
        return isSupports;
    }

    /**
     * 统一包装响应数据
     *
     * @param data
     * @param returnType
     * @param mediaType
     * @param aClass
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在ResultVo里后转换为json串进行返回
                return objectMapper.writeValueAsString(new ResponseWrapper(data));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("response返回包装失败");
            }
        }
        return new ResponseWrapper(data);
    }
}
