package org.example.bogo.domain.template.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "search_templates")
@Getter
public class SearchTemplate {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Long)
    private Long creatorId;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Date,
            format = {}, // 또는 format = DateFormat.none
            pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS||yyyy-MM-dd HH:mm:ss||yyyy-MM-dd'T'HH:mm:ss||strict_date_hour_minute_second")
    private LocalDateTime createdAt;


    @Builder
    public SearchTemplate(Long id, String title, Long creatorId, String description, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.description = description;
        this.createdAt = createdAt;
    }
}