package com.he.benteng.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class ExcelTest {
    public static void main(String[] args) {
        //写入文件夹的地址和名称
        String filename ="D:\\write.xlsx";
//        EasyExcel.write(filename,DemoExcel.class).sheet("学生列表").doWrite(getData());
    //进行读操作
        EasyExcel.read(filename,DemoExcel.class,new ExcelListener()).sheet().doRead();
    }
    public static List<DemoExcel> getData(){
        List<DemoExcel> list=new ArrayList<DemoExcel>();
        for(int i=0;i<=10;i++){
            DemoExcel demoExcel=new DemoExcel();
            demoExcel.setSname("zan"+i);
            demoExcel.setSno(i+"");
            list.add(demoExcel);
        }
        return list;
    }
}
