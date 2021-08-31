package com.fight.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FileRead {

    public static void main(String[] args) throws Exception {
        String fullFileName = "C:\\new_tdx\\T0002\\export\\自选股.xlsx";
        InputStream is = new FileInputStream(fullFileName);
        readXlsx(is);
        /*ObjectMapper objectMapper = new ObjectMapper();
        String futuresStr = objectMapper.writeValueAsString(futures);
        System.out.println(futuresStr);*/
    }


    //XSSF -- 提供读写Microsoft Excel OOXML格式档案的功能
    //XSSFWorkbook:是操作Excel2007（以上）的版本，扩展名是.xlsx
    public static List<Future> read2007Xlsx(InputStream in) throws Exception {

        List<Future> futures = new ArrayList<>();
        XSSFWorkbook xWorkbook = new XSSFWorkbook(in);
        // 获得一共的sheet页
        int numberOfSheets = xWorkbook.getNumberOfSheets();
        XSSFSheet xssfSheet = null;
        for (int i = 1; i < numberOfSheets; i++) {
            xssfSheet = xWorkbook.getSheetAt(i);

            // Read the Row
            //获取当前日的对应的是第几列
            XSSFRow dataRow = xssfSheet.getRow(0);
            int physicalNumberOfCells = dataRow.getPhysicalNumberOfCells();//总列数
            int colTemp = 1;
            for (int j = 1; j < physicalNumberOfCells; j++) {
                Date dateCellValue = dataRow.getCell(j).getDateCellValue();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String format = simpleDateFormat.format(dateCellValue);
                if (simpleDateFormat.format(new Date()).equals(format)) {
                    colTemp = j;
                    break;
                }
                throw new Exception("没有当日期货指标信息:" + simpleDateFormat.format(new Date()));
            }
            XSSFRow resRow = xssfSheet.getRow(7);//压力位行
            XSSFRow supportRow = xssfSheet.getRow(9);//支撑位行
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            String resNumber = decimalFormat.format(resRow.getCell(colTemp).getNumericCellValue());
            String supportNumber = decimalFormat.format(supportRow.getCell(colTemp).getNumericCellValue());
            Future future = new Future().setName(xssfSheet.getSheetName()).setResNumber(resNumber).setSupportNumber(supportNumber);
            futures.add(future);
        }

        xWorkbook.close();
        return futures;
    }

    //XSSF -- 提供读写Microsoft Excel OOXML格式档案的功能
    //XSSFWorkbook:是操作Excel2007（以上）的版本，扩展名是.xlsx
    public static List<Future> readXlsx(InputStream in) throws Exception {

        List<Future> futures = new ArrayList<>();
        Workbook xWorkbook = new XSSFWorkbook(in);
        Sheet xssfSheet = xWorkbook.getSheetAt(0);
        for (int i = 1; i < xssfSheet.getLastRowNum(); i++) {
            Row xssfRow = xssfSheet.getRow(i);
            if (Objects.nonNull(xssfRow)) {
                Future future = countOnePressLocation(xssfRow.getCell(4).getNumericCellValue(), xssfRow.getCell(5).getNumericCellValue(), xssfRow.getCell(3).getNumericCellValue());
                future.setName(xssfRow.getCell(1).getStringCellValue());//期货品种
                futures.add(future);
                System.out.println(future.getName()+"  "+future.getResNumber()+"  "+future.getSupportNumber());
            }
        }

        xWorkbook.close();
        return futures;
    }

    /**
     * 计算第一压力支撑位
     * param：high，low，end
     * return：Future
     */
    private static Future countOnePressLocation(Double high, Double low, Double end) {
        double pivot = (high + low + end) / 3;
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return new Future().setResNumber(decimalFormat.format(2 * pivot - low)).setSupportNumber(decimalFormat.format(2 * pivot - high));
    }
}
