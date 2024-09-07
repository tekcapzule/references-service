package com.tekcapzule.references.application.function.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class GetByTopicInput {
    private List<String> topics;
}
