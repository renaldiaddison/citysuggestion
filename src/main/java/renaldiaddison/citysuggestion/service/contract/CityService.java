package renaldiaddison.citysuggestion.service.contract;

import renaldiaddison.citysuggestion.model.CityResponse;
import renaldiaddison.citysuggestion.model.CreateCityRequest;
import renaldiaddison.citysuggestion.model.SuggestionCityResponse;
import renaldiaddison.citysuggestion.model.UpdateCityRequest;

import java.util.List;

public interface CityService {
    CityResponse addCity(CreateCityRequest request);

    CityResponse findCityById(Integer id);

    CityResponse updateCityById(Integer id, UpdateCityRequest request);

    void deleteCityById(Integer id);

    List<SuggestionCityResponse> suggestCity(String query, Double latitude, Double longitude);

    void deleteAllCity();
}
