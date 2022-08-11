package com.sunny.common;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @program: sunnyDevOps
 * @description:统一异常处理
 * @author:
 * @create: 2020-03-14 11:21
 **/
@ControllerAdvice
public class CommonExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseWrapper exception(Exception e) {
        log.error("程序执行异常", e);
        return new ResponseWrapper<>(ResponseStatus.FAILED, e.getMessage());
    }

    /**
     * 参数校验
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseWrapper MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuffer stringBuffer = new StringBuffer();
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> errorList = bindingResult.getFieldErrors();
        for (int i = 1; i < errorList.size() + 1; i++) {
            stringBuffer.append(i + "." + errorList.get(i - 1).getDefaultMessage() + ";");
        }
        log.info(String.valueOf(stringBuffer));
        return new ResponseWrapper<>(ResponseStatus.FAILED, String.valueOf(stringBuffer), "");
    }

    /**
     * 实现功能描述：权限不足异常拦截
     *
     * @param
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ResponseWrapper UnauthorizedExceptionHandler(Exception e) {
        log.error("程序执行异常", e);
        return new ResponseWrapper<>(ResponseStatus.FORBIDDEN, e.getMessage());

    }

    /**
     * 实现功能描述：用户名或密码不存在
     *
     * @param
     * @return
     */
    @ExceptionHandler(IncorrectCredentialsException.class)
    @ResponseBody
    public ResponseWrapper IncorrectCredentialsExceptionHandler(Exception e) {
        log.error("程序执行异常", e);
        return new ResponseWrapper<>(ResponseStatus.FAILED, "用户名或密码不存在", "");

    }

    /**
     * 实现功能描述：登录失败，该用户已被冻结
     *
     * @param
     * @return
     */
    @ExceptionHandler(LockedAccountException.class)
    @ResponseBody
    public ResponseWrapper LockedAccountExceptionHandler(Exception e) {
        log.error("程序执行异常", e);
        return new ResponseWrapper<>(ResponseStatus.FAILED, "登录失败，该用户已被冻结", "");

    }

    /**
     * 实现功能描述：用户名或密码不存在
     *
     * @param
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseWrapper AuthenticationExceptionHandler(Exception e) {
        log.error("程序执行异常", e);
        return new ResponseWrapper<>(ResponseStatus.FAILED, "用户名或密码不存在", "");

    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public ResponseWrapper handleAuthorizationException(AuthorizationException e){
        log.error(e.getMessage(), e);
        return new ResponseWrapper<>(ResponseStatus.UNAUTHORIZED,"未登录");
    }
}
