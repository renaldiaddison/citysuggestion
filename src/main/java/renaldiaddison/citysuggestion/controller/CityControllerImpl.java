package renaldiaddison.citysuggestion.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import renaldiaddison.citysuggestion.constant.Resource;
import renaldiaddison.citysuggestion.controller.contract.CityController;
import renaldiaddison.citysuggestion.model.*;
import renaldiaddison.citysuggestion.service.TSVFileParserService;
import renaldiaddison.citysuggestion.service.contract.CityService;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class CityControllerImpl implements CityController {

    private CityService cityService;

    private TSVFileParserService tsvFileParserService;

    @Override
    public WebResponse<CityResponse> addCity(CreateCityRequest request) {
        CityResponse response = cityService.addCity(request);
        return WebResponse.<CityResponse>builder().data(response).build();
    }

    @Override
    public WebResponse<CityResponse> findCityById(Integer id) {
        CityResponse response = cityService.findCityById(id);
        return WebResponse.<CityResponse>builder().data(response).build();
    }

    @Override
    public WebResponse<CityResponse> updateCityById(Integer id, UpdateCityRequest request) {
        CityResponse response = cityService.updateCityById(id, request);
        return WebResponse.<CityResponse>builder().data(response).build();
    }

    @Override
    public WebResponse<String> deleteCityById(Integer id) {
        cityService.deleteCityById(id);
        return WebResponse.<String>builder().data("OK").build();
    }

    @Override
    public WebResponse<List<SuggestionCityResponse>> suggestCity(String query, Double latitude, Double longitude) {
        List<SuggestionCityResponse> response = cityService.suggestCity(query, latitude, longitude);
        return WebResponse.<List<SuggestionCityResponse>>builder().data(response).build();
    }

    @Override
    public WebResponse<String> remigrateCity() {
        tsvFileParserService.migrateTsvFileToCity(Resource.CITY_TSV_FILE_PATH);
        return WebResponse.<String>builder().data("OK").build();
    }
}