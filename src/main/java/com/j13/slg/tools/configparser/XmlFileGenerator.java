package com.j13.slg.tools.configparser;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sunbo
 * Date: 14-2-12
 * Time: 下午2:20
 * To change this template use File | Settings | File Templates.
 */
public class XmlFileGenerator {

    private String[] tabList = null;
    private List<String[]> dataList = new LinkedList<String[]>();
    private File inputFile = null;
    private File outputFolder = null;

    public XmlFileGenerator(File inputFile, File outputFolder) throws IOException {
        this.inputFile = inputFile;
        this.outputFolder = outputFolder;
    }

    public void generate() throws Exception {
        loadDatFile();
        generateXmlFile();
    }

    private void loadDatFile() throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputFile), "GBK"));


        String data = null;
        int index = 0;
        while ((data = br.readLine()) != null) {
            // 第一行不要，是中文
            if (index == 0) {
                index++;
                continue;
            }

            // 第二行，是所有标签的名字
            // 按照顺序用list存储
            if (index == 1) {
                tabList = data.split("\\t");
                if (inputFile.getName().equals("global.dat")) {
                    tabList = ("name" + "\t" + "Key\tValue").split("\\t");
                }
                index++;
                continue;
            }

            index++;

            String[] lines = data.split("\\t");
            dataList.add(lines);

        }

        br.close();
    }


    private void generateXmlFile() throws Exception {
        try {
            String fileName = inputFile.getName().split("\\.")[0];
            File outputFile = new File(outputFolder, fileName + ".xml");

            Element rootElement = new Element(fileName + "List");
            for (String[] lines : dataList) {
                Element e1 = new Element(fileName);

                for (int i = 0; i < lines.length; i++) {
                    if (lines.length != tabList.length)
                        continue;
                    Element e2 = new Element(tabList[i]);
                    e2.setText(lines[i]);
                    e1.addContent(e2);
                }

                rootElement.addContent(e1);
            }
            Document doc = new Document(rootElement);
            XMLOutputter out = new XMLOutputter();
            Format format = Format.getCompactFormat();
            format.setIndent("      ");
            format.setEncoding("GB2312");
            out.setFormat(format);

            FileWriter fw = new FileWriter(outputFile);
            out.output(doc, fw);
            fw.close();
        } catch (Exception e) {
            System.out.println(inputFile.getName());
            throw e;
        }
    }


    public static void main(String[] args) throws Exception {
        File f1 = new File("/Users/sunbo/Downloads/conf/leaguebossreward.dat");
        File f2 = new File("/Users/sunbo/project/h13/slg/slg-config");
        XmlFileGenerator g = new XmlFileGenerator(f1, f2);
        g.generate();
        ;

    }
}
