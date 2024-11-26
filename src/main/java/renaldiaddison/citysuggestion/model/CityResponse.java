package renaldiaddison.citysuggestion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityResponse {

    private Integer id;

    private String name;

    private String asciiName;

    private String alternateNames;

    private Double latitude;

    private Double longitude;

    private String featureClass;

    private String featureCode;

    private String country;

    private String cc2;

    private String admin1;

    private String admin2;

    private String admin3;

    private String admin4;

    private Long population;

    private Integer elevation;

    private Integer dem;

    private String timezone;

    private LocalDate modifiedAt;

}
