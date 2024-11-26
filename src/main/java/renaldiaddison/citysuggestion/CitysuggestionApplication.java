package renaldiaddison.citysuggestion;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import renaldiaddison.citysuggestion.service.TSVFileParserService;

@SpringBootApplication
@AllArgsConstructor
public class CitysuggestionApplication implements CommandLineRunner {

	private TSVFileParserService tsvFileParserService;

	public static void main(String[] args) {
		SpringApplication.run(CitysuggestionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
