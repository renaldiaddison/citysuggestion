package renaldiaddison.citysuggestion.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.SearchHit;
import renaldiaddison.citysuggestion.document.CityDocument;
import renaldiaddison.citysuggestion.entity.City;
import renaldiaddison.citysuggestion.helper.Haversine;
import renaldiaddison.citysuggestion.helper.Helper;
import renaldiaddison.citysuggestion.helper.StringSimilarity;
import renaldiaddison.citysuggestion.model.CityResponse;
import renaldiaddison.citysuggestion.model.SuggestionCityResponse;

public final class Mapper {

    public static CityResponse toCityResponse(City city) {
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .asciiName(city.getAsciiName())
                .alternateNames(city.getAlternateNames())
                .latitude(city.getLatitude())
                .longitude(city.getLongitude())
                .featureClass(city.getFeatureClass())
                .featureCode(city.getFeatureCode())
                .country(city.getCountry())
                .cc2(city.getCc2())
                .admin1(city.getAdmin1())
                .admin2(city.getAdmin2())
                .admin3(city.getAdmin3())
                .admin4(city.getAdmin4())
                .population(city.getPopulation())
                .elevation(city.getElevation())
                .dem(city.getDem())
                .timezone(city.getTimezone())
                .modifiedAt(city.getModifiedAt())
                .build();
    }

    public static SuggestionCityResponse toSuggestionCityResponse(SearchHit<CityDocument> hit, String query, Double latitude, Double longitude) {
        CityDocument cityDoc = hit.getContent();

        double nameScore = StringSimilarity.calculateJaroWinkler(cityDoc.getName().toLowerCase(), query.toLowerCase());

        double proximityScore = 1.0;

        if (latitude != null && longitude != null) {
            double distance = Haversine.calculate(cityDoc.getLocation().getLat(), cityDoc.getLocation().getLon(), latitude, longitude);
            proximityScore = 1 / (1 + (distance / 1000));
        }

        double finalScore = 0.7 * nameScore + 0.3 * proximityScore;

        return SuggestionCityResponse.builder()
                .id(cityDoc.getId())
                .name(cityDoc.getName())
                .latitude(cityDoc.getLocation().getLat())
                .longitude(cityDoc.getLocation().getLon())
                .score(Helper.round(finalScore, 2))
                .build();
    }

}
