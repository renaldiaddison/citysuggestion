package renaldiaddison.citysuggestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import renaldiaddison.citysuggestion.entity.City;

public interface CityRepository extends JpaRepository<City, Integer> {
}
