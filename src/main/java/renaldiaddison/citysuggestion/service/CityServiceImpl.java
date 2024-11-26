package renaldiaddison.citysuggestion.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import renaldiaddison.citysuggestion.document.CityDocument;
import renaldiaddison.citysuggestion.entity.City;
import renaldiaddison.citysuggestion.mapper.Mapper;
import renaldiaddison.citysuggestion.model.CityResponse;
import renaldiaddison.citysuggestion.model.CreateCityRequest;
import renaldiaddison.citysuggestion.model.SuggestionCityResponse;
import renaldiaddison.citysuggestion.model.UpdateCityRequest;
import renaldiaddison.citysuggestion.repository.CityDocumentRepository;
import renaldiaddison.citysuggestion.repository.CityRepository;
import renaldiaddison.citysuggestion.service.contract.CityService;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    private CityRepository cityRepository;

    private CityDocumentRepository cityDocumentRepository;

    private ValidationService validationService;

    private ElasticsearchOperations elasticsearchOperations;

    @Transactional
    @Override
    public CityResponse addCity(CreateCityRequest request) {
        log.info("Adding new city with id: {}", request.getId());
        validationService.validate(request);

        if (cityRepository.existsById(request.getId()) || cityDocumentRepository.existsById(request.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City with this ID already exists");
        }

        City city = new City(request.getId(), request.getName(), request.getAsciiName(), request.getAlternateNames(), request.getLatitude(), request.getLongitude(), request.getFeatureClass(), request.getFeatureCode(), request.getCountry(), request.getCc2(), request.getAdmin1(), request.getAdmin2(), request.getAdmin3(), request.getAdmin4(), request.getPopulation(), request.getElevation(), request.getDem(), request.getTimezone(), request.getModifiedAt());
        cityRepository.save(city);

        CityDocument cityDocument = new CityDocument(city.getId(), String.format("%s, %s, %s", city.getName(), city.getAdmin1(), city.getCountry()), new GeoPoint(city.getLatitude(), city.getLongitude()));
        cityDocumentRepository.save(cityDocument);

        return Mapper.toCityResponse(city);
    }

    @Override
    public CityResponse findCityById(Integer id) {
        City city = cityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
        return Mapper.toCityResponse(city);
    }

    @Transactional
    @Override
    public CityResponse updateCityById(Integer id, UpdateCityRequest request) {
        validationService.validate(request);

        City city = cityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));

        CityDocument cityDocument = cityDocumentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));

        city.setName(request.getName());
        city.setAsciiName(request.getAsciiName());
        city.setAlternateNames(request.getAlternateNames());
        city.setLatitude(request.getLatitude());
        city.setLongitude(request.getLongitude());
        city.setFeatureClass(request.getFeatureClass());
        city.setFeatureCode(request.getFeatureCode());
        city.setCountry(request.getCountry());
        city.setCc2(request.getCc2());
        city.setAdmin1(request.getAdmin1());
        city.setAdmin2(request.getAdmin2());
        city.setAdmin3(request.getAdmin3());
        city.setAdmin4(request.getAdmin4());
        city.setPopulation(request.getPopulation());
        city.setElevation(request.getElevation());
        city.setDem(request.getDem());
        city.setTimezone(request.getTimezone());
        city.setModifiedAt(request.getModifiedAt());

        cityRepository.save(city);

        cityDocument.setName(String.format("%s, %s, %s", city.getName(), city.getAdmin1(), city.getCountry()));
        cityDocument.setLocation(new GeoPoint(city.getLatitude(), city.getLongitude()));

        cityDocumentRepository.save(cityDocument);

        return Mapper.toCityResponse(city);
    }

    @Transactional
    @Override
    public void deleteCityById(Integer id) {
        City city = cityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));
        CityDocument cityDocument = cityDocumentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "City not found"));

        cityRepository.delete(city);
        cityDocumentRepository.delete(cityDocument);
    }

    @Override
    public List<SuggestionCityResponse> suggestCity(String query, Double latitude, Double longitude) {
        Criteria criteria = new Criteria("name").is(query);
        Criteria fuzzyCriteria = new Criteria("name").fuzzy(query);

        Criteria combinedCriteria = criteria.or(fuzzyCriteria);

        for (String q : query.split(" ")) {
            combinedCriteria = combinedCriteria.or(new Criteria("name").contains(q));
        }

        if (latitude != null && longitude != null) {
            GeoPoint location = new GeoPoint(latitude, longitude);
            combinedCriteria.and("location").within(location, "200km");
        }

        SearchHits<CityDocument> searchHits = elasticsearchOperations.search(new CriteriaQuery(combinedCriteria), CityDocument.class);

        List<SuggestionCityResponse> suggestionCityResponses = new ArrayList<>(searchHits.stream()
                .map(cityDocumentSearchHit -> Mapper.toSuggestionCityResponse(cityDocumentSearchHit, query, latitude, longitude))
                .toList());

        suggestionCityResponses.sort(Comparator.comparingDouble(SuggestionCityResponse::getScore).reversed());

        return suggestionCityResponses;
    }

    @Transactional
    @Override
    public void deleteAllCity() {
        cityRepository.deleteAll();
        cityDocumentRepository.deleteAll();
    }


}
