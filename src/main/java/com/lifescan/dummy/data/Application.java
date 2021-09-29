package com.lifescan.dummy.data;
import com.lifescan.dummy.data.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Application implements CommandLineRunner {

  @Autowired
  private PatientService patientService;

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class)
            .web(WebApplicationType.NONE)
            .run(args);
  }

  @Override
  public void run(String[] args) throws InterruptedException {
    String language = args[0];
    int qtyPatients = Integer.parseInt(args[1]);
    patientService.create(
            language,
            qtyPatients
    );
  }

}
