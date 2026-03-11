package org.example.bogo.domain.template.repository;

import org.example.bogo.domain.template.entity.SearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;


public interface SearchRepository extends ElasticsearchRepository<SearchTemplate, Long> {
    List<SearchTemplate> findByTitle(String title);

}
