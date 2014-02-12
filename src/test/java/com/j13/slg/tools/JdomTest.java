package com.j13.slg.tools;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: sunbo
 * Date: 14-2-12
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
public class JdomTest {

    @Test
    public void genXml() throws IOException {

        Element root = new Element("fdsa");

        Document doc = new Document(root);
        XMLOutputter out = new XMLOutputter();
        out.setFormat(Format.getCompactFormat().setEncoding("GB2312"));
        FileWriter fw = new FileWriter(new File("demo.xml"));
        out.output(doc,fw);

    }


}
