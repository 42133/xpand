package com.xpand.movie.utils.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class PageWrapper<U> {

    private Page<U> page;
    private Metadata<U> metadata;

    public PageWrapper(Page<U> page) {
        this.page = page;
        this.metadata = new Metadata<U>(page);
    }

    public PageWrapper(int size) {
        this.page = new PageImpl<U>(new ArrayList<>(), PageRequest.of(0, size), 0);
        this.metadata = new Metadata<U>(page);
    }

    public List<U> getContent() {
        return page.getContent();
    }

    public Metadata<U> getMetadata() {
        return metadata;
    }
}
