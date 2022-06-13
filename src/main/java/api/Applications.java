package api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Applications {

	public static void main(String[] args) {
		log.info("access Applications.main");
		SpringApplication.run(Applications.class, args);
	}

}
