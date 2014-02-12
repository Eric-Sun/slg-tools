package com.j13.slg.tools.configparser;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: sunbo
 * Date: 14-2-12
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 */
public interface ConfigParser {

    public void parse(String inputFolder,String outputFolder) throws Exception;

}
