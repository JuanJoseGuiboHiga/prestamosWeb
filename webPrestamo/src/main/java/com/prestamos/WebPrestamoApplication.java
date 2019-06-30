package com.prestamos;
import java.util.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebPrestamoApplication{

    public static void main(String[] args) {
       // SpringApplication.run(WebPrestamoApplication.class, args);
        SpringApplication app = new SpringApplication(WebPrestamoApplication.class);
        Properties props = new Properties();
        props.put( "server.port" , "9090");
        app.setDefaultProperties(props);
        app.run(args);

    }



}
