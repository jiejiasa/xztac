package com.xzt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class})
@MapperScan(basePackages={"com.xzt.**.mapper"})
public class XztActiApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(XztActiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  苏州行者通车辆库存系统启动成功   ლ(´ڡ`ლ)大大");
    }
}
