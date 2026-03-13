package org.example.bogo.domain.template.repository;

import org.example.bogo.domain.template.entity.SearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchRepository extends ElasticsearchRepository<SearchTemplate, Long> {

}
