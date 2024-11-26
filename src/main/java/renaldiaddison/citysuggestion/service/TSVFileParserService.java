package renaldiaddison.citysuggestion.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import renaldiaddison.citysuggestion.helper.Helper;
import renaldiaddison.citysuggestion.model.CreateCityRequest;
import renaldiaddison.citysuggestion.service.contract.CityService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
public class TSVFileParserService {

    private CityService cityService;

    private ResourceLoader resourceLoader;

    @Transactional
    public void migrateTsvFileToCity(String filePath) {
        try {
            cityService.deleteAllCity();

            Resource resource = resourceLoader.getResource("classpath:" + filePath);
            InputStream inputStream = resource.getInputStream();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                boolean isHeader = true;
                String line;
                while ((line = reader.readLine()) != null) {
                    if (isHeader) {
                        isHeader = false;
                        continue;
                    }

                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    String[] parts = line.split("\t");
                    if (parts.length == 19) {
                        try {
                            Integer id = Helper.parseNullableInt(parts[0]);
                            String name = Helper.parseNullableString(parts[1]);
                            String asciiName = Helper.parseNullableString(parts[2]);
                            String alternateNames = Helper.parseNullableString(parts[3]);
                            Double latitude = Helper.parseNullableDouble(parts[4]);
                            Double longitude = Helper.parseNullableDouble(parts[5]);
                            String featureClass = Helper.parseNullableString(parts[6]);
                            String featureCode = Helper.parseNullableString(parts[7]);
                            String country = Helper.parseNullableString(parts[8]);
                            String cc2 = Helper.parseNullableString(parts[9]);
                            String admin1 = Helper.parseNullableString(parts[10]);
                            String admin2 = Helper.parseNullableString(parts[11]);
                            String admin3 = Helper.parseNullableString(parts[12]);
                            String admin4 = Helper.parseNullableString(parts[13]);
                            Long population = Helper.parseNullableLong(parts[14]);
                            Integer elevation = Helper.parseNullableInt(parts[15]);
                            Integer dem = Helper.parseNullableInt(parts[16]);
                            String timezone = Helper.parseNullableString(parts[17]);
                            LocalDate modifiedAt = Helper.parseNullableLocalDate(parts[18]);

                            CreateCityRequest request = CreateCityRequest.builder()
                                    .id(id)
                                    .name(name)
                                    .asciiName(asciiName)
                                    .alternateNames(alternateNames)
                                    .latitude(latitude)
                                    .longitude(longitude)
                                    .featureClass(featureClass)
                                    .featureCode(featureCode)
                                    .country(country)
                                    .cc2(cc2)
                                    .admin1(admin1)
                                    .admin2(admin2)
                                    .admin3(admin3)
                                    .admin4(admin4)
                                    .population(population)
                                    .elevation(elevation)
                                    .dem(dem)
                                    .timezone(timezone)
                                    .modifiedAt(modifiedAt)
                                    .build();

                            cityService.addCity(request);

                        } catch (NumberFormatException e) {
                            log.error("Invalid data format: {}", line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error reading the TSV file: {}", filePath, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TSV file not found: " + filePath, e);
        }
    }

}