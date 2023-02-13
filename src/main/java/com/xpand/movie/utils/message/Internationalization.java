package com.xpand.movie.utils.message;

public enum Internationalization {
    ACTOR("actor.object"),
    MOVIE("movie.object"),
    CREATE_SUCCESSFUL("item.created.successfully"),
    UPDATE_SUCCESSFUL("item.updated.successfully"),
    DELETE_SUCCESSFUL("item.deleted.successfully"),
    NOT_FOUND("exception.item.notFound"),
    INVALID_RANK("exception.invalid.rank"),
    INVALID_GENDER("exception.invalid.gender"),
    NOT_EMPTY_NAME("exception.notEmpty.name"),
    NOT_EMPTY_TITLE("exception.notEmpty.title"),
    OPERATION_FAILED("exception.operation.failed"),
    GENERIC_EXCEPTION("exception.generic");

    private final String label;

    Internationalization(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
