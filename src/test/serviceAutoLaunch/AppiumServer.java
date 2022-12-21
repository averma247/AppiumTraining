import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AppiumServer {

    static String NodePath="C:/Program Files/nodejs/node.exe";
    static String AppiumMainJS_path="C:\\Users\\ajayverma\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    static AppiumDriverLocalService service;
    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    @BeforeTest
    public void startServer() throws InterruptedException {

        service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File(NodePath))
                .withAppiumJS(new File(AppiumMainJS_path))
                .withIPAddress("127.0.0.1")
                .usingPort(4728)
                .withLogFile(new File("D:\\InteliJDirectory\\AppiumTraining\\AppiumLog.txt")));

        System.out.println("Starting the Appium Server........."+"\n"+df.format(new Date())+"\n---------------------------------------------------------------------------------------------");
        service.start();
        Thread.sleep(10000);

    }

    @Test
    public void testServer(){

        System.err.println("This URI is : "+service.getUrl().toString());
        System.err.println("Is server Running: "+service.isRunning());

    }
    @AfterTest
    public  void stopServer(){

        if(service.isRunning()){
            service.stop();
            System.out.println("\n---------------------------------------------------------------------------------------------"+
                    "\nStopping the Appium Server....."+df.format(new Date()));
        }

    }

}
