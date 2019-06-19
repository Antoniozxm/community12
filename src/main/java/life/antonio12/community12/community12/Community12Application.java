package life.antonio12.community12.community12;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("life.antonio12.community12.mapper")
public class Community12Application {

    public static void main(String[] args) {
        SpringApplication.run(Community12Application.class, args);
    }

}

