package com.he.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {
    private boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<String, Object>();
    private R() {
    }//构造方法私有化
    //方法成功
    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");

        return r;
    }
    //失败
    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setMessage("失败");
        r.setCode(ResultCode.ERROR);
        return r;
    }
    //使用链式编程      R.ok().data("items", list);
    public R message(String message) {
        this.setMessage(message);
        return this;
    }
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }
    public R data(Map<String, Object> map) {

        this.setData(map);
        return this;
    }
    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
