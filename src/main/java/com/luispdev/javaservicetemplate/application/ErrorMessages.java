package com.luispdev.javaservicetemplate.application;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessages {
    public final String POST_NOT_FOUND = "error.post.notFound";
    public final String POST_TITLE_REQUIRED = "error.post.title.required";
    public final String INTERNAL_SERVER_ERROR = "error.internal";
}
