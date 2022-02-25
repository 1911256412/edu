package com.he.benteng.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoExcel {

    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;
    @ExcelProperty(value = "学生编号",index = 0)
    private String sno;
}
