package com.yapu.system.util;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

/**
 * 基于jxl.jar 提供了常用的Excel读取和写入的方法
 * User: Administrator
 * Date: 12-3-20
 * Time: 下午5:47
 */
public class Excel {
    int sheetCount = 1; // excel工作簿，默认为1
    WritableWorkbook wwb = null; // 构建Workbook对象,只读Workbook对象
    Vector vSheet = null;

    /**
     * 无参构造函数，生成默认名字的excel文件
     */
    public Excel() {
        this("noName.excel");
    }

    /**
     * 带有一个String类型参数的构造函数
     *
     * @param fileName String 将要生成的excel文件名
     */
    public Excel(String fileName) {
        try {
            wwb = Workbook.createWorkbook(new File(fileName));
            vSheet = new Vector(5);
        } catch (Exception e) {
        }
    }

    /**
     * 带有一个File类型参数的构造函数
     *
     * @param file String 将要生成的excel文件名
     */
    public Excel(File file) {
        try {
            wwb = Workbook.createWorkbook(file);
            vSheet = new Vector(5);
        } catch (Exception e) {
        }
    }

    /**
     * 读取一个EXCEL文件的所有行和列,在同一行上的数据以一个String的形式保存在Vector中,各列数据以","号分割
     *
     * @param fileName String 文件名
     * @return Vector
     * @throws Exception
     */
    public static Vector readFromExcel(String fileName) throws Exception {
        Vector v = new Vector();
        File file = new File(fileName);
        if (!file.isFile()) {
            return null;
        }
        v = readExl(file, -1, -1);
        return v;
    }
    
    public static Vector readFromExcel(InputStream is) throws Exception {
    	Vector v = new Vector();
        v = readExl(is, -1, -1);
        return v;
    }

    public static Vector readFromExcel(File file) throws Exception {
        Vector v = new Vector();
        if (!file.isFile()) {
            return null;
        }
        v = readExl(file, -1, -1);
        return v;
    }

    /**
     * 读取一行多列或者一列多行
     *
     * @param fileName String 文件名
     * @param rowORcol int 第几行或者第几列
     * @param flag     String ROW表示前面一个参数的值指的是行数，行COL表示前面一个参数的值指的是列数
     * @return Vector
     * @throws Exception
     */
    public static Vector readFromExcel(String fileName, int rowORcol,
                                       String flag) throws Exception {
        Vector v = new Vector();
        File file = new File(fileName);
        if (!file.isFile()) {
            return null;
        }
        if (flag != null && flag.equals("ROW")) {
            v = readExl(file, rowORcol, -1);
        } else if (flag != null && flag.equals("COL")) {
            v = readExl(file, -1, rowORcol);
        } else {
            return null;
        }
        return v;
    }

    public static Vector readFromExcel(File file, int rowORcol, String flag)
            throws Exception {
        Vector v = new Vector();
        if (!file.isFile()) {
            return null;
        }
        if (flag != null && flag.equals("ROW")) {
            v = readExl(file, rowORcol, -1);
        } else if (flag != null && flag.equals("COL")) {
            v = readExl(file, -1, rowORcol);
        } else {
            return null;
        }

        return v;
    }

    /**
     * 读取多行或者多列,可以任意挑选几行或几列
     *
     * @param fileName String 文件名
     * @param rowORcol int 任意的行或列
     * @param flag     String ROW表示行COL表示列
     * @return Vector
     * @throws Exception
     */
    public static Vector readFromExcel(String fileName, int[] rowORcol,
                                       String flag) throws Exception {
        Vector v = new Vector();
        return v;
    }

    public static Vector readFromExcel(File file, int[] rowORcol, String flag)
            throws Exception {
        Vector v = new Vector();
        return v;
    }

    /**
     * 读取第几行第几列的一个数据
     *
     * @param fileName String
     * @param row      int
     * @param col      int
     * @return String
     * @throws Exception
     */
    public static String readFromExcel(String fileName, int row, int col)
            throws Exception {
        String res = null;
        File file = new File(fileName);
        if (!file.isFile()) {
            return null;
        }
        return res;
    }

    public static String readFromExcel(File file, int row, int col)
            throws Exception {
        String res = null;
        if (!file.isFile()) {
            return null;
        }

        return res;
    }

    /**
     * 读取xls文件
     *
     * @param f   File 文件
     * @param row int 读取从0到row行
     * @param col int 读取从0到col列
     * @return Vector
     * @throws Exception
     */

    private static Vector readExl(File f, int row, int col) throws Exception {
        Vector v = new Vector();
        Workbook wb = null;
        Sheet st = null;
        wb = Workbook.getWorkbook(f);
        st = wb.getSheet(0);
        int allRow = st.getRows();
        if (row == -1) {
            row = allRow;
        }
        int allCol = st.getColumns();
        if (col == -1) {
            col = allCol;
        }
        for (int i = 0; i < row; i++) {
            String sRow = null;
            for (int j = 0; j < col; j++) {
                Cell c1 = st.getCell(j, i);
                String sCol = c1.getContents();
                if (j == 0) {
                    sRow = sCol;
                } else {
                    if (sCol != null) {
                        sRow = sRow + "," + sCol;
                    } else {
                        sRow = sRow + "," + "";
                    }
                }
                c1 = null;
                sCol = null;
            }
            v.addElement(sRow);
            sRow = null;
        }

        st = null;
        wb.close();
        wb = null;
        return v;
    }
    
    private static Vector readExl(InputStream is, int row, int col) throws Exception {
        Vector v = new Vector();
        Workbook wb = null;
        Sheet st = null;
        wb = Workbook.getWorkbook(is);
        st = wb.getSheet(0);
        int allRow = st.getRows();
        if (row == -1) {
            row = allRow;
        }
        int allCol = st.getColumns();
        if (col == -1) {
            col = allCol;
        }
        for (int i = 0; i < row; i++) {
            String sRow = null;
            for (int j = 0; j < col; j++) {
                Cell c1 = st.getCell(j, i);
                String sCol = c1.getContents();
                if (j == 0) {
                    sRow = sCol;
                } else {
                    if (sCol != null) {
                        sRow = sRow + "," + sCol;
                    } else {
                        sRow = sRow + "," + "";
                    }
                }
                c1 = null;
                sCol = null;
            }
            v.addElement(sRow);
            sRow = null;
        }

        st = null;
        wb.close();
        wb = null;
        return v;
    }

    public void addSheet() throws Exception {
        addSheet(String.valueOf(sheetCount));
    }

    public void addSheet(String sheetName) throws Exception {

        // 创建Excel工作表
        WritableSheet ws = wwb.createSheet(sheetName, sheetCount);
        vSheet.addElement(ws);
        sheetCount++;
    }

    /**
     * 为工作表添加内容,指定添加到第几列
     *
     * @param v   Vector 要添加到工作表的内容 格式
     *            String,String,String,String,...,...,...,..., 每列内容以逗号隔开
     * @param col int 指定列数
     * @throws Exception
     */
    public void addContent(Vector v, int col) throws Exception {
        WritableSheet ws = (WritableSheet) vSheet.get(sheetCount - 2);

        int size = v.size();
        try {
            for (int i = 0; i < size; i++) {
                String s = (String) v.get(i);
                String[] s1 = s.split(",");
                for (int j = 0; j < col; j++) {
                    Label label = new Label(j, i, s1[j]);

                    ws.addCell(label);
                    label = null;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("check column!");
        }
        ws = null;
    }

    /**
     * 为工作表添加内容,不指定添加几列
     *
     * @param v Vector 要添加到工作表的内容 格式
     *          String,String,String,String,...,...,...,..., 每列内容以逗号隔开
     * @throws Exception
     */
    public void addContent(Vector v) throws Exception {
        WritableSheet ws = (WritableSheet) vSheet.get(sheetCount - 2);

        int size = v.size();
        try {
            for (int i = 0; i < size; i++) {
                String s = (String) v.get(i);
                String[] s1 = s.split(",");
                int col_size = s1.length;
                for (int j = 0; j < col_size; j++) {
                    Label label = new Label(j, i, s1[j]);

                    ws.addCell(label);
                    label = null;
                }
            }
        } catch (Exception e) {
            throw new Exception();
        }
        ws = null;
    }

    /**
     * 为工作表添加内容,不指定添加几列
     *
     * @param rs ResultSet 从数据库中得到的结果集
     * @throws Exception
     */
    public void addContent(ResultSet rs) throws Exception {
        if (rs == null) {
            return;
        }
        WritableSheet ws = (WritableSheet) vSheet.get(sheetCount - 2);
        ResultSetMetaData rsMetaD = rs.getMetaData();
        int col = rsMetaD.getColumnCount();
        int i = 0;
        while (rs.next()) {
            for (int j = 0; j < col; j++) {
                Label label = new Label(j, i, rs.getString(j));// Chinese.fromDatabase(rs.getString(j))
                ws.addCell(label);
                label = null;
            }
            i++;
        }
    }

    /**
     * 最终生成excel文件
     *
     * @throws Exception
     */
    public void createExcel() throws Exception {
        wwb.write();
        wwb.close();
    }

    public static void main(String[] args) {
        Excel t = new Excel("d:\\test.xls");
        Vector v = new Vector();
        try {
            v.addElement("ding,wen,yuan");
            v.addElement("ding,wen,yuan");
            t.addSheet("first");
            t.addContent(v, 3);

            v.clear();
            v.addElement("xuhy,hai,yong");
            v.addElement("xuhy,hai,yong");
            t.addSheet("second");
            t.addContent(v, 3);

            v.clear();
            v.addElement("wu,jia,qian");
            v.addElement("wu,jia,qian");
            t.addSheet("third");
            t.addContent(v, 3);

            t.createExcel();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
