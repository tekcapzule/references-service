package com.tekcapzule.references.domain.repository;

import com.tekcapzule.references.domain.model.References;
import com.tekcapzule.core.domain.CrudRepository;

import java.util.List;


public interface ReferencesDynamoRepository extends CrudRepository<References, String> {
    
    References findBy(String hashKey, String rangeKey);
    List<References> findByTopicCodes(List<String> subscribedTopics);
    List<References> findByTags(List<String> tags);
    List<References> findAllByDuration(String topicCode, String duration);
    List<References> findAllByLevel(String topicCode, String level);

}
