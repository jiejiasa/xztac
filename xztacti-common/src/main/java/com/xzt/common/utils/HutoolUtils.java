package com.xzt.common.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.poi.ss.usermodel.Sheet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;

public class HutoolUtils {

    public static <T> void downloadData(List<T> data, Class<T> beanType, HttpServletResponse response, String titleName) throws Exception {
        ExcelWriter excelWriter = ExcelUtil.getWriter();

        Field[] fields = beanType.getDeclaredFields();
        Sheet sheet = excelWriter.getSheet();
        for (int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            JsonProperty declaredAnnotation = field.getDeclaredAnnotation(JsonProperty.class);
            if (ObjectUtil.isEmpty(declaredAnnotation)) {
                continue;
            }
            String headName = field.getDeclaredAnnotation(JsonProperty.class).value();
            String fieldName = field.getName();
            excelWriter.addHeaderAlias(fieldName, headName);
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 25 / 10);
        }
        excelWriter.write(data);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //supervise.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(titleName, "UTF-8") + ".xls");

        try (ServletOutputStream out = response.getOutputStream()) {
            excelWriter.flush(out, true);
            excelWriter.close();
            IoUtil.close(out);
        }

    }











}
