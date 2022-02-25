package com.he.benteng.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

//创建监听器
public class  ExcelListener  extends AnalysisEventListener<DemoExcel> {

    //设置总体封装数据
    //List<DemoExcel> list =new ArrayList<DemoExcel>();
    //读取表头中的数据
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头中的数据"+headMap);
    }
    //一行一行读取数据
    @Override
    public void invoke(DemoExcel demoExcel, AnalysisContext analysisContext) {
        System.out.println("数据"+demoExcel);
      //  list.add(demoExcel);
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
