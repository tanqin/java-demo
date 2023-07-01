package com.example.hibernatevalidatordemo.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * 全局异常处理器
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler 注解指定要处理的异常类型
    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public String resolveViolationException(Exception ex) {
        // 错误信息拼接对象
        StringJoiner messages = new StringJoiner("; ");

        // 判断异常是否为 ConstraintViolationException（违反约束条件异常） 的实例
        if (ex instanceof ConstraintViolationException) {
            // 获取违约集合
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) ex).getConstraintViolations();
            // 循环拼接错误信息
            for (ConstraintViolation<?> violation : violations) {
                messages.add(violation.getMessage());
            }
        } else {
            // 异常是 MethodArgumentNotValidException（方法参数无效异常） 的实例
            // 获取所有错误集合
            List<ObjectError> allErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            // 循环拼接错误信息
            for (ObjectError error : allErrors) {
                if (error instanceof FieldError) {
                    // 强制转为 FieldError 类型
                    FieldError fieldError = (FieldError) error;
                    messages.add(fieldError.getField() + fieldError.getDefaultMessage());
                }
            }
        }
        //
        return messages.toString();
    }
}
