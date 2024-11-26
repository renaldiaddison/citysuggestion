package renaldiaddison.citysuggestion.controller.contract;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import renaldiaddison.citysuggestion.model.*;

import java.util.List;

@RequestMapping("/api/cities")
public interface CityController {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<CityResponse> addCity(@RequestBody CreateCityRequest request);

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<CityResponse> findCityById(@PathVariable("id") Integer id);

    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<CityResponse> updateCityById(@PathVariable("id") Integer id, @RequestBody UpdateCityRequest request);

    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<String> deleteCityById(@PathVariable("id") Integer id);

    @GetMapping(
            path = "/suggest",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<List<SuggestionCityResponse>> suggestCity(
            @RequestParam("q") String query,
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "longitude", required = false) Double longitude);

    @PostMapping(
            path = "/remigrate",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<String> remigrateCity();

}