package com.tekcapzule.references.domain.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tekcapzule.references.domain.model.References;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class ReferencesRepositoryImpl implements ReferencesDynamoRepository {

    private DynamoDBMapper dynamo;

    public static final String ACTIVE_STATUS = "ACTIVE";
    public static final String SUBMITTED_STATUS = "SUBMITTED";
    public static final String STATUS_KEY = "status";


    @Autowired
    public ReferencesRepositoryImpl(DynamoDBMapper dynamo) {
        this.dynamo = dynamo;
    }

    @Override
    public List<References> findAll() {
        return dynamo.scan(References.class,new DynamoDBScanExpression());
    }

    @Override
    public References findBy(String referenceId) {
        return dynamo.load(References.class, referenceId);
    }

    @Override
    public References save(References references) {
        dynamo.save(references);
        return references;
    }

    @Override
    public References findBy(String hashKey, String rangeKey) {
        return dynamo.load(References.class, hashKey, rangeKey);
    }

    @Override
    public List<References> findByTopicCodes(List<String> subscribedTopics) {

        List<References> references = new ArrayList<>();

        subscribedTopics.forEach(topic -> {

            HashMap<String, AttributeValue> expAttributes = new HashMap<>();
            expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));
            expAttributes.put(":topicCode", new AttributeValue().withS(topic));

            HashMap<String, String> expNames = new HashMap<>();
            expNames.put("#status", "status");
            expNames.put("#topicCode", "topicCode");

            DynamoDBQueryExpression<References> queryExpression = new DynamoDBQueryExpression<References>()
                    .withIndexName("topicGSI").withConsistentRead(false)
                    .withKeyConditionExpression("#status = :status and #topicCode = :topicCode")
                    .withExpressionAttributeValues(expAttributes)
                    .withExpressionAttributeNames(expNames);
            PaginatedQueryList<References> myReferences = dynamo.query(References.class, queryExpression);

            if (myReferences != null && myReferences.size() > 0) {
                references.addAll(myReferences);
            }
        });
        return references;
    }

    //Find all references by topic code.
    @Override
    public List<References> findByTags(List<String> tags) {
        List<References> references = new ArrayList<>();

        tags.forEach(tag -> {

            HashMap<String, AttributeValue> expAttributes = new HashMap<>();
            expAttributes.put(":status", new AttributeValue().withS(ACTIVE_STATUS));
            expAttributes.put(":tags", new AttributeValue().withS(tag));

            HashMap<String, String> expNames = new HashMap<>();
            expNames.put("#status", "status");
            expNames.put("#tags", "tags");

            DynamoDBQueryExpression<References> queryExpression = new DynamoDBQueryExpression<References>()
                    .withIndexName("topicGSI").withConsistentRead(false)
                    .withKeyConditionExpression("#status = :status and #tags = :tags")
                    .withExpressionAttributeValues(expAttributes)
                    .withExpressionAttributeNames(expNames);
            PaginatedQueryList<References> myReferences = dynamo.query(References.class, queryExpression);

            if (myReferences != null && myReferences.size() > 0) {
                references.addAll(myReferences);
            }
        });
        return references;
    }

    @Override
    public List<References> findAllByDuration(String topicCode, String duration) {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":topicCode", new AttributeValue().withS(topicCode));
        expAttributes.put(":duration", new AttributeValue().withS(duration));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#duration", "duration");
        expNames.put("#topicCode", "topicCode");


        DynamoDBQueryExpression<References> queryExpression = new DynamoDBQueryExpression<References>()
                .withIndexName("durationGSI").withConsistentRead(false)
                .withKeyConditionExpression("#duration = :duration and #topicCode = :topicCode")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(References.class, queryExpression);
    }
    @Override
    public List<References> findAllByLevel(String topicCode, String level) {

        HashMap<String, AttributeValue> expAttributes = new HashMap<>();
        expAttributes.put(":level", new AttributeValue().withS(level));
        expAttributes.put(":topicCode", new AttributeValue().withS(topicCode));

        HashMap<String, String> expNames = new HashMap<>();
        expNames.put("#level", "level");
        expNames.put("#topicCode", "topicCode");


        DynamoDBQueryExpression<References> queryExpression = new DynamoDBQueryExpression<References>()
                .withIndexName("levelGSI").withConsistentRead(false)
                .withKeyConditionExpression("#level = :level and #topicCode = :topicCode")
                .withExpressionAttributeValues(expAttributes)
                .withExpressionAttributeNames(expNames);

        return dynamo.query(References.class, queryExpression);

    }


}
