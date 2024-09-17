package com.tekcapzule.references.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Type {
    ARTICLE("Article"),
    VIDEO("Video"),
    EVENT("Event"),
    BOOK("Book"),
    COURSE("Course"),
    NEWS("News");

    @Getter
    private String value;
}
