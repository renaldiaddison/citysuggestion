package renaldiaddison.citysuggestion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuggestionCityResponse {

    private Integer id;

    private String name;

    private Double latitude;

    private Double longitude;

    private Double score;

}
