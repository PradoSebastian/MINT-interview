package com.mint.interview.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Getter
@Setter
public class PagedResponseDTO<T> {
    private List<T> content;
    private String next;
    private String previous;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public PagedResponseDTO(Page<T> pageData, String sort, int page, int size) {
        this.content = pageData.getContent();
        this.page = page;
        this.size = size;
        this.totalElements = pageData.getTotalElements();
        this.totalPages = pageData.getTotalPages();
        setUrlResponses(pageData, sort);
    }

    private void setUrlResponses(Page<T> pageData, String sort) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .replacePath(null)
                .build()
                .toUriString();

        this.next = pageData.hasNext()
                ? String.format("%s?page=%d&size=%d&sort=%s", baseUrl, page + 1, size, sort)
                : null;

        this.previous = pageData.hasPrevious()
                ? String.format("%s?page=%d&size=%d&sort=%s", baseUrl, page - 1, size, sort)
                : null;
    }
}
