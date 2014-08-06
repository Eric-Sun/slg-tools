package com.j13.slg.tools.configparser;

import com.google.common.collect.Lists;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sunbo
 * Date: 14-8-6
 * Time: 上午1:21
 * To change this template use File | Settings | File Templates.
 */
public class Excel2XmlFileGemerator {
    private File inputFile;
    private File outputFolder;
    private List<List<String>> data = Lists.newLinkedList();
    private List<String> tabList = Lists.newLinkedList();

    public Excel2XmlFileGemerator(File inputFile, File outputFolder) throws IOException {
        this.inputFile = inputFile;
        this.outputFolder = outputFolder;
    }

    public void generate() throws Exception {
        loadFile();
        generateXml();

    }

    private void loadFile() throws IOException {
        InputStream inp = new FileInputStream(inputFile);
        //InputStream inp = new FileInputStream("workbook.xlsx");

        Workbook wb = new XSSFWorkbook(inp);
        Sheet sheet = wb.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            List<String> list = Lists.newLinkedList();
            int lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                if (i == 0) {
                    tabList.add(getValueFromCell(row.getCell(j)));
                } else {
                    list.add(getValueFromCell(row.getCell(j)));
                }

            }
            if (i != 0)
                this.data.add(list);
        }
        inp.close();
    }

    private void generateXml() throws Exception {
        try {
            String fileName = inputFile.getName().split("\\.")[0];
            File outputFile = new File(outputFolder, fileName + ".xml");

            Element rootElement = new Element(fileName + "List");
            for (List<String> lines : data) {
                Element e1 = new Element(fileName);

                for (int i = 0; i < lines.size(); i++) {
                    if (lines.size() > tabList.size())
                        continue;
                    Element e2 = new Element(tabList.get(i).substring(0, 1).toLowerCase() + tabList.get(i).substring(1));
                    e2.setText(lines.get(i) == null ? "" : lines.get(i));
                    e1.addContent(e2);
                }

                rootElement.addContent(e1);
            }
            Document doc = new Document(rootElement);
            XMLOutputter out = new XMLOutputter();
            Format format = Format.getCompactFormat();
            format.setIndent("      ");
            format.setEncoding("UTF-8");
            out.setFormat(format);

            FileWriter fw = new FileWriter(outputFile);
            out.output(doc, fw);
            fw.close();
        } catch (Exception e) {
            System.out.println(inputFile.getName());
            throw e;
        }
    }


    public static final String getValueFromCell(Cell cell) {
        String value = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                } else value = new Double(cell.getNumericCellValue()).intValue() + "";
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                value = "";
                break;

        }
        return value;

    }


    public static void main(String[] args) throws Exception {
        File f1 = new File("/Users/sunbo/project/h13/slg/slg-dat/global.xlsx");
        File f2 = new File("/Users/sunbo/project/h13/slg/slg-config");
        Excel2XmlFileGemerator g = new Excel2XmlFileGemerator(f1, f2);
        g.generate();
    }

}
