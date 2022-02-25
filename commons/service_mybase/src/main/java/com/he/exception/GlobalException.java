package com.he.exception;

import com.he.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice//交给spring管理
@Slf4j
public class GlobalException {
    @ExceptionHandler(Exception.class)//需要处理异常的类型
    @ResponseBody//返回给前端的结果
    public R erro(Exception e) {

        e.printStackTrace();
        return R.error().message("执行全局处理异常");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("特定异常处理 ");
    }

    //自定义异常处理
    @ExceptionHandler(HeException.class)
    @ResponseBody
    public R error(HeException e) {
        log.error(e.getMsg());
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }
}
