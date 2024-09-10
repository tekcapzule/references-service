package com.tekcapzule.references.domain.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tekcapzule.core.domain.AggregateRoot;
import com.tekcapzule.core.domain.BaseDomainEntity;
import lombok.*;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@DynamoDBTable(tableName = "References")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class References extends BaseDomainEntity implements AggregateRoot {
    @DynamoDBHashKey(attributeName="referenceId")
    @DynamoDBAutoGeneratedKey
    private String referenceId;
    @DynamoDBAttribute(attributeName = "title")
    private String title;
    @DynamoDBAttribute(attributeName="topicCode")
    private String topicCode;
    @DynamoDBAttribute(attributeName = "author")
    private String author;
    @DynamoDBAttribute(attributeName = "description")
    private String description;
    @DynamoDBAttribute(attributeName = "publisher")
    private String publisher;
    @DynamoDBAttribute(attributeName = "publishedOn")
    private String publishedOn;
    @DynamoDBAttribute(attributeName = "resourceUrl")
    private String resourceUrl;
    @DynamoDBAttribute(attributeName = "type")
    @DynamoDBTypeConvertedEnum
    private Type type;
    @DynamoDBAttribute(attributeName = "prizingModel")
    @DynamoDBTypeConvertedEnum
    private PrizingModel prizingModel;
    @DynamoDBAttribute(attributeName = "level")
    @DynamoDBTypeConvertedEnum
    private TopicLevel level;
    @DynamoDBAttribute(attributeName = "status")
    @DynamoDBTypeConvertedEnum
    private Status status;
    @DynamoDBAttribute(attributeName = "duration")
    private Integer duration;
    @DynamoDBAttribute(attributeName="tags")
    private String tags;
    @DynamoDBAttribute(attributeName = "bookmarks")
    private Integer bookmarks;
    @DynamoDBAttribute(attributeName = "recommendations")
    private Integer recommendations;
}