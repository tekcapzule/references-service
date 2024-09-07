package com.tekcapzule.references.domain.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.core.domain.Command;
import com.tekcapzule.references.domain.model.PrizingModel;
import com.tekcapzule.references.domain.model.Type;
import com.tekcapzule.references.domain.model.TopicLevel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CreateCommand extends Command {
    private String topicCode;
    private String title;
    private Integer duration;
    private String author;
    private String description;
    private List<String> tags;
    private String publisher;
    private String publishedOn;
    private String resourceUrl;
    private Type type;
    private TopicLevel level;
    private PrizingModel prizingModel;
}
