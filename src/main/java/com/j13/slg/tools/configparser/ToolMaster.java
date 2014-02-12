package com.j13.slg.tools.configparser;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: sunbo
 * Date: 14-2-12
 * Time: 下午7:30
 * To change this template use File | Settings | File Templates.
 */
public class ToolMaster {


    public static void main(String[] args) throws Exception {
        ConfigParser4Text parser = new ConfigParser4Text();
        parser.parse("/Users/sunbo/project/h13/slg/slg-dat",
                "/Users/sunbo/project/h13/slg/slg-config");
    }
}
