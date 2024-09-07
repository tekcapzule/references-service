package com.tekcapzule.references.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tekcapzule.references.domain.model.PrizingModel;
import com.tekcapzule.references.domain.model.Type;
import com.tekcapzule.references.domain.model.TopicLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CreateInput {
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
