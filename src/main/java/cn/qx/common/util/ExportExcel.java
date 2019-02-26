package cn.qx.common.util;

import java.io.BufferedOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportExcel<T> {
    public void exportExcel(String[] headers, Collection<T> dataset, String fileName, HttpServletResponse response) {
        try {
            // 声明一个工作薄
            HSSFWorkbook workbook = new HSSFWorkbook();
            for (int num = 0; num < (dataset.size() + 9) / 10; num++) {
                // 生成一个表格
                HSSFSheet sheet = workbook.createSheet(fileName + num);
                // 设置表格默认列宽度为15个字节
                sheet.setDefaultColumnWidth((short) 20);
                // 产生表格标题行
                HSSFRow row = sheet.createRow(0);
                for (short i = 0; i < headers.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                    cell.setCellValue(text);
                }
                // 遍历集合数据，产生数据行
                List<T>lsit = (List<T>)dataset;
                int index = 0;
                for (int sheetRow = 0; sheetRow < 10; sheetRow++) {
                    index = num * 10 + sheetRow;
                    if(index>=lsit.size()) {
                        break;
                    }
                    row = sheet.createRow(index%10);
                    T t = (T) lsit.get(index);
                    // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                    Field[] fields = t.getClass().getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        if (fields[i].getName().equals("serialVersionUID")) {
                            Field[] newFidlds = new Field[fields.length - 1];
                            System.arraycopy(fields, 0, newFidlds, 0, i);
                            System.arraycopy(fields, i + 1, newFidlds, i, fields.length - i - 1);
                            fields = newFidlds;
                            break;
                        }
                    }
                    for (short i = 0; i < headers.length; i++) {
                        HSSFCell cell = row.createCell(i);
                        Field field = fields[i];
                        String fieldName = field.getName();
                        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Class<?> tCls = t.getClass();
                        Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                        Object value = getMethod.invoke(t, new Object[] {});
                        // 判断值的类型后进行强制类型转换
                        String textValue = null;
                        // 其它数据类型都当作字符串简单处理
                        if (value != null && value != "") {
                            textValue = value.toString();
                        }
                        if (textValue != null) {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }
                }
            }
            getExportedFile(workbook, fileName, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 方法说明: 指定路径下生成EXCEL文件
     * 
     * @return
     */
    public void getExportedFile(HSSFWorkbook workbook, String name, HttpServletResponse response) throws Exception {
        BufferedOutputStream fos = null;
        try {
            String fileName = name + ".xls";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            fos = new BufferedOutputStream(response.getOutputStream());
            workbook.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}