package renaldiaddison.citysuggestion.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cities")
public class City {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "ascii_name", length = 200)
    private String asciiName;

    @Column(name = "alternate_names", length = 5000)
    private String alternateNames;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "feature_class", length = 1)
    private String featureClass;

    @Column(name = "feature_code", length = 10)
    private String featureCode;

    @Column(name = "country", length = 2, nullable = false)
    private String country;

    @Column(name = "cc2", length = 60)
    private String cc2;

    @Column(name = "admin1", length = 20, nullable = false)
    private String admin1;

    @Column(name = "admin2", length = 80)
    private String admin2;

    @Column(name = "admin3", length = 20)
    private String admin3;

    @Column(name = "admin4", length = 20)
    private String admin4;

    @Column(name = "population")
    private Long population;

    @Column(name = "elevation")
    private Integer elevation;

    @Column(name = "dem")
    private Integer dem;

    @Column(name = "tz", length = 40)
    private String timezone;

    @Column(name = "modified_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate modifiedAt;

}
