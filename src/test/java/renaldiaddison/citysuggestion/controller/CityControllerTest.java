package renaldiaddison.citysuggestion.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import renaldiaddison.citysuggestion.document.CityDocument;
import renaldiaddison.citysuggestion.entity.City;
import renaldiaddison.citysuggestion.model.*;
import renaldiaddison.citysuggestion.repository.CityDocumentRepository;
import renaldiaddison.citysuggestion.repository.CityRepository;
import renaldiaddison.citysuggestion.service.CityServiceImpl;
import renaldiaddison.citysuggestion.service.ValidationService;
import renaldiaddison.citysuggestion.service.contract.CityService;
import com.fasterxml.jackson.core.type.TypeReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CityControllerTest {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityDocumentRepository cityDocumentRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        cityService.deleteAllCity();
    }

    @Test
    void createCityBadRequest() throws Exception {
        CreateCityRequest request = new CreateCityRequest();
        request.setId(null);

        mockMvc.perform(
                post("/api/cities")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void createCitySuccess() throws Exception {
        CreateCityRequest request = CreateCityRequest.builder()
                .id(1354)
                .name("Test City 2")
                .asciiName("TestCityAscii 2")
                .alternateNames("CityOne,CityTwo 2")
                .latitude(13.3456)
                .longitude(79.9012)
                .featureClass("Z")
                .featureCode("TEST2")
                .country("TG")
                .cc2("TCC2 2")
                .admin1("TestAdmin12")
                .admin2("TestAdmin22")
                .admin3("TestAdmin32")
                .admin4("TestAdmin42")
                .population(1100000L)
                .elevation(550)
                .dem(270)
                .timezone("Test/Timezone 2")
                .modifiedAt(LocalDate.of(2024, 10, 26))
                .build();

        mockMvc.perform(
                post("/api/cities")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<CityResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(request.getId(), response.getData().getId());
            assertEquals(request.getName(), response.getData().getName());
            assertEquals(request.getAsciiName(), response.getData().getAsciiName());
            assertEquals(request.getAlternateNames(), response.getData().getAlternateNames());
            assertEquals(request.getLatitude(), response.getData().getLatitude());
            assertEquals(request.getLongitude(), response.getData().getLongitude());
            assertEquals(request.getFeatureClass(), response.getData().getFeatureClass());
            assertEquals(request.getFeatureCode(), response.getData().getFeatureCode());
            assertEquals(request.getCountry(), response.getData().getCountry());
            assertEquals(request.getCc2(), response.getData().getCc2());
            assertEquals(request.getAdmin1(), response.getData().getAdmin1());
            assertEquals(request.getAdmin2(), response.getData().getAdmin2());
            assertEquals(request.getAdmin3(), response.getData().getAdmin3());
            assertEquals(request.getAdmin4(), response.getData().getAdmin4());
            assertEquals(request.getPopulation(), response.getData().getPopulation());
            assertEquals(request.getElevation(), response.getData().getElevation());
            assertEquals(request.getDem(), response.getData().getDem());
            assertEquals(request.getTimezone(), response.getData().getTimezone());
            assertEquals(request.getModifiedAt(), response.getData().getModifiedAt());

            assertTrue(cityRepository.existsById(response.getData().getId()));
            assertTrue(cityDocumentRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void updateCityBadRequest() throws Exception {
        UpdateCityRequest request = new UpdateCityRequest();
        request.setName("");

        mockMvc.perform(
                put("/api/cities/1233")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void updateCitySuccess() throws Exception {
        CreateCityRequest createRequest = CreateCityRequest.builder()
                .id(1000)
                .name("Test City 2")
                .asciiName("TestCityAscii 2")
                .alternateNames("CityOne,CityTwo 2")
                .latitude(13.3456)
                .longitude(79.9012)
                .featureClass("Z")
                .featureCode("TEST2")
                .country("TG")
                .cc2("TCC2 2")
                .admin1("TestAdmin12")
                .admin2("TestAdmin22")
                .admin3("TestAdmin32")
                .admin4("TestAdmin42")
                .population(1100000L)
                .elevation(550)
                .dem(270)
                .timezone("Test/Timezone 2")
                .modifiedAt(LocalDate.of(2024, 10, 26))
                .build();

        cityService.addCity(createRequest);

        UpdateCityRequest updateRequest = UpdateCityRequest.builder()
                .name("Test City 3")
                .asciiName("TestCityAscii 3")
                .alternateNames("CityOne,CityTwo 3")
                .latitude(13.3456)
                .longitude(79.9012)
                .featureClass("G")
                .featureCode("TEST2")
                .country("TG")
                .cc2("TCC2 2")
                .admin1("TestAdmin13")
                .admin2("TestAdmin23")
                .admin3("TestAdmin33")
                .admin4("TestAdmin43")
                .population(1200000L)
                .elevation(550)
                .dem(270)
                .timezone("Test/Timezone 2")
                .modifiedAt(LocalDate.of(2024, 10, 26))
                .build();

        mockMvc.perform(
                put("/api/cities/" + createRequest.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<CityResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(createRequest.getId(), response.getData().getId());
            assertEquals(updateRequest.getName(), response.getData().getName());
            assertEquals(updateRequest.getAsciiName(), response.getData().getAsciiName());
            assertEquals(updateRequest.getAlternateNames(), response.getData().getAlternateNames());
            assertEquals(updateRequest.getLatitude(), response.getData().getLatitude());
            assertEquals(updateRequest.getLongitude(), response.getData().getLongitude());
            assertEquals(updateRequest.getFeatureClass(), response.getData().getFeatureClass());
            assertEquals(updateRequest.getFeatureCode(), response.getData().getFeatureCode());
            assertEquals(updateRequest.getCountry(), response.getData().getCountry());
            assertEquals(updateRequest.getCc2(), response.getData().getCc2());
            assertEquals(updateRequest.getAdmin1(), response.getData().getAdmin1());
            assertEquals(updateRequest.getAdmin2(), response.getData().getAdmin2());
            assertEquals(updateRequest.getAdmin3(), response.getData().getAdmin3());
            assertEquals(updateRequest.getAdmin4(), response.getData().getAdmin4());
            assertEquals(updateRequest.getPopulation(), response.getData().getPopulation());
            assertEquals(updateRequest.getElevation(), response.getData().getElevation());
            assertEquals(updateRequest.getDem(), response.getData().getDem());
            assertEquals(updateRequest.getTimezone(), response.getData().getTimezone());
            assertEquals(updateRequest.getModifiedAt(), response.getData().getModifiedAt());

            assertTrue(cityRepository.existsById(response.getData().getId()));
            assertTrue(cityDocumentRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void getCityNotFound() throws Exception {
        mockMvc.perform(
                get("/api/cities/1300")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getCitySuccess() throws Exception {
        CreateCityRequest request = CreateCityRequest.builder()
                .id(1300)
                .name("Test City")
                .asciiName("TestCityAscii")
                .alternateNames("CityOne,CityTwo")
                .latitude(12.3456)
                .longitude(78.9012)
                .featureClass("T")
                .featureCode("TEST")
                .country("TC")
                .cc2("TCC2")
                .admin1("TestAdmin1")
                .admin2("TestAdmin2")
                .admin3("TestAdmin3")
                .admin4("TestAdmin4")
                .population(1000000L)
                .elevation(500)
                .dem(250)
                .timezone("Test/Timezone")
                .modifiedAt(LocalDate.of(2024, 11, 26))
                .build();

        cityService.addCity(request);

        mockMvc.perform(
                get("/api/cities/1300")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<CityResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(request.getId(), response.getData().getId());
            assertEquals(request.getName(), response.getData().getName());
            assertEquals(request.getAsciiName(), response.getData().getAsciiName());
            assertEquals(request.getAlternateNames(), response.getData().getAlternateNames());
            assertEquals(request.getLatitude(), response.getData().getLatitude());
            assertEquals(request.getLongitude(), response.getData().getLongitude());
            assertEquals(request.getFeatureClass(), response.getData().getFeatureClass());
            assertEquals(request.getFeatureCode(), response.getData().getFeatureCode());
            assertEquals(request.getCountry(), response.getData().getCountry());
            assertEquals(request.getCc2(), response.getData().getCc2());
            assertEquals(request.getAdmin1(), response.getData().getAdmin1());
            assertEquals(request.getAdmin2(), response.getData().getAdmin2());
            assertEquals(request.getAdmin3(), response.getData().getAdmin3());
            assertEquals(request.getAdmin4(), response.getData().getAdmin4());
            assertEquals(request.getPopulation(), response.getData().getPopulation());
            assertEquals(request.getElevation(), response.getData().getElevation());
            assertEquals(request.getDem(), response.getData().getDem());
            assertEquals(request.getTimezone(), response.getData().getTimezone());
            assertEquals(request.getModifiedAt(), response.getData().getModifiedAt());

        });
    }

    @Test
    void deleteCityNotFound() throws Exception {
        mockMvc.perform(
                delete("/api/cities/1400")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void deleteCitySuccess() throws Exception {
        CreateCityRequest request = CreateCityRequest.builder()
                .id(1400)
                .name("Test City")
                .asciiName("TestCityAscii")
                .alternateNames("CityOne,CityTwo")
                .latitude(12.3456)
                .longitude(78.9012)
                .featureClass("T")
                .featureCode("TEST")
                .country("TC")
                .cc2("TCC2")
                .admin1("TestAdmin1")
                .admin2("TestAdmin2")
                .admin3("TestAdmin3")
                .admin4("TestAdmin4")
                .population(1000000L)
                .elevation(500)
                .dem(250)
                .timezone("Test/Timezone")
                .modifiedAt(LocalDate.of(2024, 11, 26))
                .build();

        cityService.addCity(request);

        mockMvc.perform(
                delete("/api/cities/1400")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals("OK", response.getData());
        });
    }

    @Test
    void suggestCityFound() throws Exception {
        CreateCityRequest request1 = CreateCityRequest.builder()
                .id(1300)
                .name("London")
                .asciiName("TestCityAscii")
                .alternateNames("CityOne,CityTwo")
                .latitude(12.3456)
                .longitude(78.9012)
                .featureClass("T")
                .featureCode("TEST")
                .country("TC")
                .cc2("TCC2")
                .admin1("TestAdmin1")
                .admin2("TestAdmin2")
                .admin3("TestAdmin3")
                .admin4("TestAdmin4")
                .population(1000000L)
                .elevation(500)
                .dem(250)
                .timezone("Test/Timezone")
                .modifiedAt(LocalDate.of(2024, 11, 26))
                .build();

        CreateCityRequest request2 = CreateCityRequest.builder()
                .id(1900)
                .name("Minnesota")
                .asciiName("TestCityAscii")
                .alternateNames("CityOne,CityTwo")
                .latitude(12.3456)
                .longitude(78.9012)
                .featureClass("T")
                .featureCode("TEST")
                .country("TC")
                .cc2("TCC2")
                .admin1("TestAdmin1")
                .admin2("TestAdmin2")
                .admin3("TestAdmin3")
                .admin4("TestAdmin4")
                .population(1000000L)
                .elevation(500)
                .dem(250)
                .timezone("Test/Timezone")
                .modifiedAt(LocalDate.of(2024, 11, 26))
                .build();

        cityService.addCity(request1);
        cityService.addCity(request2);

        mockMvc.perform(
                get("/api/cities/suggest")
                        .queryParam("q", "London")
                        .queryParam("latitude", "12.3456")
                        .queryParam("longitude", "78.9012")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<SuggestionCityResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(String.format("%s, %s, %s", request1.getName(), request1.getAdmin1(), request1.getCountry()), response.getData().get(0).getName());
        });

    }

    @Test
    void suggestCityEmptyFound() throws Exception {
        mockMvc.perform(
                get("/api/cities/suggest")
                        .queryParam("q", "NotFoundCity")
                        .queryParam("latitude", "12.3456")
                        .queryParam("longitude", "78.9012")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<SuggestionCityResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertTrue(response.getData().isEmpty());
        });

    }

}
