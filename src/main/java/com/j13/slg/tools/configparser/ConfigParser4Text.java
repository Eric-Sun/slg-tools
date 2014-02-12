package com.j13.slg.tools.configparser;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: sunbo
 * Date: 14-2-12
 * Time: 上午11:44
 * To change this template use File | Settings | File Templates.
 */
public class ConfigParser4Text implements ConfigParser {
    File inputFolder = null;
    File outputFolder = null;

    @Override
    public void parse(String inputFolderStr, String outputFolderStr) throws Exception {
        inputFolder = new File(inputFolderStr);
        outputFolder = new File(outputFolderStr);


        File[] inputFiles = inputFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                if (s.indexOf(".dat") > 0)
                    return true;
                return false;
            }
        });
        for (File inputFile : inputFiles) {
            XmlFileGenerator g = new XmlFileGenerator(inputFile,outputFolder);
            g.generate();
        }


    }


}
