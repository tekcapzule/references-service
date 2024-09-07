package com.tekcapzule.references.domain.service;

import com.tekcapzule.references.domain.command.*;
import com.tekcapzule.references.domain.model.References;
import com.tekcapzule.references.domain.model.Status;
import com.tekcapzule.references.domain.repository.ReferencesDynamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReferenceServiceImpl implements ReferenceService {
    private ReferencesDynamoRepository referencesDynamoRepository;


    @Autowired
    public ReferenceServiceImpl(ReferencesDynamoRepository referencesDynamoRepository) {
        this.referencesDynamoRepository = referencesDynamoRepository;
    }

    @Override
    public void create(CreateCommand createCommand) {

        log.info(String.format("Entering create reference service - Reference Title:%s", createCommand.getTitle()));

        References references = References.builder()
                .author(createCommand.getAuthor())
                .description(createCommand.getDescription())
                .topicCode(createCommand.getTopicCode())
                .duration(createCommand.getDuration())
                .publishedOn(createCommand.getPublishedOn())
                .publisher(createCommand.getPublisher())
                .resourceUrl(createCommand.getResourceUrl())
                .level(createCommand.getLevel())
                .tags(createCommand.getTags())
                .title(createCommand.getTitle())
                .type(createCommand.getType())
                .prizingModel(createCommand.getPrizingModel())
                .status(Status.SUBMITTED)
                .recommendations(0)
                .bookmarks(0)
                .build();

        references.setAddedOn(createCommand.getExecOn());
        references.setUpdatedOn(createCommand.getExecOn());
        references.setAddedBy(createCommand.getExecBy().getUserId());

        referencesDynamoRepository.save(references);
    }

    @Override
    public void update(UpdateCommand updateCommand) {

        log.info(String.format("Entering update references service - Reference Id:%s", updateCommand.getReferenceId()));

        References references = referencesDynamoRepository.findBy(updateCommand.getReferenceId());
        if (references != null) {
            references.setTitle(updateCommand.getTitle()!=null?updateCommand.getTitle():references.getTitle());
            references.setTopicCode(updateCommand.getTopicCode()!=null?updateCommand.getTopicCode():references.getTopicCode());
            references.setAuthor(updateCommand.getAuthor()!=null?updateCommand.getAuthor():references.getAuthor());
            references.setPublisher(updateCommand.getPublisher()!=null?updateCommand.getPublisher():references.getPublisher());
            references.setDuration(updateCommand.getDuration()!=null?updateCommand.getDuration():references.getDuration());
            references.setResourceUrl(updateCommand.getResourceUrl()!=null?updateCommand.getResourceUrl():references.getResourceUrl());
            references.setDescription(updateCommand.getDescription()!=null?updateCommand.getDescription():references.getDescription());
            references.setPrizingModel(updateCommand.getPrizingModel()!=null?updateCommand.getPrizingModel():references.getPrizingModel());
            references.setType(updateCommand.getType()!=null?updateCommand.getType():references.getType());
            references.setTags(updateCommand.getTags()!=null?updateCommand.getTags():references.getTags());
            references.setLevel(updateCommand.getLevel()!=null?updateCommand.getLevel():references.getLevel());
            references.setUpdatedOn(updateCommand.getExecOn());
            references.setUpdatedBy(updateCommand.getExecBy().getUserId());
            references.setRecommendations(updateCommand.getRecommendations()!= null? updateCommand.getRecommendations():references.getRecommendations());
            references.setRecommendations(updateCommand.getBookmarks() !=null? updateCommand.getBookmarks():references.getBookmarks());
            references.setPublishedOn(updateCommand.getPublishedOn()!=null?updateCommand.getPublishedOn():references.getPublishedOn());
            referencesDynamoRepository.save(references);
        }
    }

    @Override
    public List<References> searchByTags(List<String> subscribedTopics) {
        log.info("Entering getMyFeed service");

        return referencesDynamoRepository.findByTags(subscribedTopics);
    }


    @Override
    public void addBookMark(AddBookmarkCommand addBookmarkCommand) {
        log.info(String.format("Entering addBookmark reference service -  Feed Id:%s", addBookmarkCommand.getReferenceId()));

        References references = referencesDynamoRepository.findBy(addBookmarkCommand.getReferenceId());

        if (references != null) {
            Integer bookmarkCount = references.getBookmarks();
            bookmarkCount += 1;
            references.setBookmarks(bookmarkCount);

            references.setUpdatedOn(addBookmarkCommand.getExecOn());
            references.setUpdatedBy(addBookmarkCommand.getExecBy().getUserId());

            referencesDynamoRepository.save(references);
        }
    }

    @Override
    public void recommend(RecommendCommand recommendCommand) {
        log.info(String.format("Entering recommend reference service -  Reference Id:%s", recommendCommand.getReferenceId()));

        References references = referencesDynamoRepository.findBy(recommendCommand.getReferenceId());
        if (references != null) {
            Integer recommendationsCount = references.getRecommendations();
            recommendationsCount += 1;
            references.setRecommendations(recommendationsCount);

            references.setUpdatedOn(recommendCommand.getExecOn());
            references.setUpdatedBy(recommendCommand.getExecBy().getUserId());

            referencesDynamoRepository.save(references);
        }
    }


    @Override
    public References findBy(String feedId) {

        log.info(String.format("Entering find by feed service - Feed Id:%s", feedId));

        return referencesDynamoRepository.findBy(feedId);
    }

    @Override
    public List<References> findByTopics(List<String> topicCodes) {
        log.info("Entering findBy topic service");

        return referencesDynamoRepository.findByTopicCodes(topicCodes);
    }

    @Override
    public List<References> findAll() {

        log.info("Entering findAll feed service");

        return referencesDynamoRepository.findAll();
    }
    @Override
    public List<References> findAllByDuration(String topicCode, String duration) {

        log.info(String.format("Entering findAllByDuration Reference service - Module code:%s", topicCode));

        return referencesDynamoRepository.findAllByDuration(topicCode, duration);
    }
    @Override
    public List<References> findAllByLevel(String topicCode, String courseLevel) {

        log.info(String.format("Entering findAllByLevel Reference service - Module code:%s", topicCode));

        return referencesDynamoRepository.findAllByLevel(topicCode, courseLevel);
    }

    @Override
    public void approve(ApproveCommand approveCommand) {
        log.info(String.format("Entering approve course service -  reference Id:%s", approveCommand.getReferencesId()));

        References references = referencesDynamoRepository.findBy(approveCommand.getReferencesId());
        if (references != null) {
            references.setStatus(Status.ACTIVE);

            references.setUpdatedOn(approveCommand.getExecOn());
            references.setUpdatedBy(approveCommand.getExecBy().getUserId());

            referencesDynamoRepository.save(references);
        }
    }


}
