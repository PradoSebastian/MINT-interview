package com.mint.interview.exercise.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PagingUtils {

    public static Pageable createPageable(int page, int size, String[] sort) {
        Sort sortCriteria;
        if (sort.length == 2) {
            Sort.Direction direction = Sort.Direction.fromString(sort[1].toUpperCase());
            sortCriteria = Sort.by(direction, sort[0]);
        } else {
            sortCriteria = Sort.by(Sort.Direction.ASC, sort[0]);
        }
        return PageRequest.of(page, size, sortCriteria);
    }


    private static Sort.Order mapToSortOrder(String sort) {
        String[] split = sort.split(",");
        if (split.length == 2) {
            return new Sort.Order(
                    Sort.Direction.fromString(split[1]),
                    split[0]
            );
        } else if (split.length == 1) {
            return new Sort.Order(Sort.Direction.ASC, split[0]);
        } else {
            throw new IllegalArgumentException("Invalid sort parameter format. Use 'field,direction'.");
        }
    }

}
