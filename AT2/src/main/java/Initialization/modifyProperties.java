package Initialization;

import com.dpo.centralized_restaurant.model.configJson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class modifyProperties {

    private configJson configuration;

    public modifyProperties(configJson config){

        this.configuration = config;

        File fnew=new File("src/main/resources/application.properties");

        try {
            FileWriter f2 = new FileWriter(fnew, false);
            f2.write("spring.datasource.url=jdbc:mysql://" + config.getIP_BBDD() + ":" + config.getPort_BBDD() + "/" + config.getNomBBDD() + "\n");
            f2.write("spring.datasource.username=" + config.getUser() + "\n");
            f2.write("spring.datasource.password=" + config.getPassword() + "\n");
            f2.write("spring.datasource.driver-class-name=com.mysql.jdbc.Driver\n");
            f2.write("\n");
            f2.write("spring.jpa.hibernate.ddl-auto=create\n" +
                    "#spring.jpa.hibernate.ddl-auto=validate\n" +
                    "#spring.jpa.hibernate.ddl-auto=update\n" +
                    "spring.jpa.show-sql = false\n");

            f2.close();
        } catch (IOException e) {

        }

    }
}
