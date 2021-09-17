package com.fight.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SharesExport {

    private final static String EXCEL2003 = ".xls";
    private final static String EXCEL2007 = ".xlsx";
    private final static String YYYYMMDD = "yyyyMMdd";

    /**
     * 获取每日自选股的支撑压力位
     * param：high，low，end
     * return：Future
     */
    public static void main(String[] args) throws Exception {
        //用当日15:00收盘的价格预测明天的位置
        String fullFileNameTom = "C:\\new_tdx\\T0002\\export\\自选股" + format(new Date(), YYYYMMDD) + ".xlsx";
        List<Shares> SharesTom = readXlsx(fullFileNameTom);
//        System.out.println(SharesTom);
        //用当日15:00收盘的价格总结昨天的预测精准度
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        String fullFileNameYes = "C:\\new_tdx\\T0002\\export\\自选股" + format(cal.getTime(), YYYYMMDD) + ".xlsx";
        List<Shares> SharesYes = readXlsx(fullFileNameYes);
        for (int i = 0; i < SharesYes.size(); i++) {
            SharesYes.get(i).setEndPrice(SharesTom.get(i).getEndPrice());
        }
        System.out.println(SharesYes);
    }

    /**
     * 读取文件并计算
     * param：fullFileName
     * return：List<Future>
     */
    public static List<Shares> readXlsx(String fullFileName) throws Exception {

        List<Shares> SharesList = new ArrayList<>();
        InputStream in = new FileInputStream(fullFileName);
        Workbook xWorkbook = null;
        String ext = fullFileName.substring(fullFileName.lastIndexOf("."));
        if (EXCEL2003.equals(ext)) {
            xWorkbook = new HSSFWorkbook(in);
        } else if (EXCEL2007.equals(ext)) {
            xWorkbook = new XSSFWorkbook(in);
        } else {
            xWorkbook = null;
        }
        Sheet xssfSheet = xWorkbook.getSheetAt(0);
        for (int i = 1; i < xssfSheet.getLastRowNum(); i++) {
            Row xssfRow = xssfSheet.getRow(i);
            if (Objects.nonNull(xssfRow)) {
                Shares Shares = countOnePressLocation(xssfRow.getCell(4).getNumericCellValue(), xssfRow.getCell(5).getNumericCellValue(), xssfRow.getCell(3).getNumericCellValue());
                Shares.setName(xssfRow.getCell(1).getStringCellValue());//期货品种
                SharesList.add(Shares);
            }
        }

        xWorkbook.close();
        return SharesList;
    }

    /**
     * 计算第一压力支撑位
     * param：high，low，end
     * return：Future
     */
    private static Shares countOnePressLocation(Double high, Double low, Double end) {
        double pivot = (high + low + end) / 3;
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return new Shares().
                setResPrice(decimalFormat.format(2 * pivot - low)).
                setSupportPrice(decimalFormat.format(2 * pivot - high)).
                setEndPrice(decimalFormat.format(end));
    }

    /**
     * 文件通过输入流写到字节数组，文件到字节数组的过程
     * param：fileName
     * return：byte[]
     */
    private static byte[] FileToByteArray(String fileName) {
        //选择源
        File src = new File(fileName);
        //选择流
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            //搬运
            bos = new ByteArrayOutputStream();
            fis = new FileInputStream(src);
            int temp;
            //用来暂时存放数据的，FileInputStream 的read方法会重复向里面读数据，
            //接着通过ByteArrayOutputStream写，这是一个重复的过程。直到temp= -1 代表读完。然后return。
            byte[] bt = new byte[1024 * 10];
            while ((temp = fis.read(bt)) != -1) {
                bos.write(bt, 0, temp);
            }
            bos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关流
            try {
                if (null != fis)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bos.toByteArray();
    }

    private static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                result = simpleDateFormat.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }
}
