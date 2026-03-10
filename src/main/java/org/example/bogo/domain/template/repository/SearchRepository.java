package org.example.bogo.domain.template.repository;

import org.example.bogo.domain.template.entity.SearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;
import java.util.Optional;

public interface SearchRepository extends ElasticsearchRepository<SearchTemplate, String> {
    Optional<List<SearchTemplate>> findByTitle(String title);

}
