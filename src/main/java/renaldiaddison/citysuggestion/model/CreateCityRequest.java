package renaldiaddison.citysuggestion.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCityRequest {

    @NotNull()
    private Integer id;

    @NotBlank()
    private String name;

    private String asciiName;

    private String alternateNames;

    @NotNull()
    private Double latitude;

    @NotNull()
    private Double longitude;

    private String featureClass;

    private String featureCode;

    @NotBlank()
    private String country;

    private String cc2;

    @NotBlank()
    private String admin1;

    private String admin2;

    private String admin3;

    private String admin4;

    private Long population;

    private Integer elevation;

    private Integer dem;

    private String timezone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedAt;

}