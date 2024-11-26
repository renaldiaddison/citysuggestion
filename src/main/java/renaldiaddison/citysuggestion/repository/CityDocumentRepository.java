package renaldiaddison.citysuggestion.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import renaldiaddison.citysuggestion.document.CityDocument;

public interface CityDocumentRepository extends ElasticsearchRepository<CityDocument, Integer> {
}