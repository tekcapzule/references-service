package com.tekcapzule.references.domain.service;

import com.tekcapzule.references.domain.command.*;
import com.tekcapzule.references.domain.model.References;

import java.util.List;
import java.util.Map;


public interface ReferenceService {

    void create(CreateCommand createCommand);

    void update(UpdateCommand updateCommand);

    List<References> searchByTags(List<String> subscribedTopics);

    void addBookMark(AddBookmarkCommand addBookmarkCommand);

    void recommend(RecommendCommand recommendCommand);

    References findBy(String referenceId);

    List<References> findByTopics(List<String> topicNames);

    List<References> findAll();
    void approve(ApproveCommand approveCommand);
    List<References> findAllByDuration(String code, String duration);
    List<References> findAllByLevel(String code, String level);

}
