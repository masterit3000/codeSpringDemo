package com.elearningbackend.utility;

import com.elearningbackend.dto.Pager;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Paginator<T, R> {

    private Class<R> returnClass;

    public Pager<R> paginate(int currentPage, Page<T> pager, int noOfRowInPage, ModelMapper mapper) {
        noOfRowInPage = validateNoOfRowInPage(noOfRowInPage);
        currentPage = validateCurrentPage(currentPage);
        Pager<R> result = new Pager<>();
        result.setCurrentPage(currentPage);
        result.setTotalRow(pager.getTotalElements());
        result.setNoOfRowInPage(noOfRowInPage);
        result.setTotalPage(calculateTotalPage(pager.getTotalElements(), noOfRowInPage));
        List<R> resultList = pager.getContent().stream().filter(Objects::nonNull).map(
            a -> mapper.map(a, returnClass)).collect(Collectors.toList()
        );
        result.setResults(resultList);
        return result;
    }

    static int calculateTotalPage(long totalElements, long noOfRowInPage){
        return (int) Math.ceil((double)totalElements/noOfRowInPage);
    }

    public static PageRequest getValidPageRequest(int currentPage, int noOfRowInPage, Sort sort){
        return new PageRequest(validateCurrentPage(currentPage), validateNoOfRowInPage(noOfRowInPage), sort);
    }

    private static int validateCurrentPage(int currentPage) {
        currentPage = convertValidCurrentPage(currentPage);
        return currentPage < Constants.ZERO ? Constants.CURRENT_PAGE_DEFAULT_VALUE
            : currentPage;
    }

    private static int validateNoOfRowInPage(int noOfRowInPage) {
        return noOfRowInPage <= Constants.ZERO ? Constants.NO_OF_ROWS_DEFAULT_VALUE
            : noOfRowInPage;
    }

    private static int convertValidCurrentPage(int currentPage){
        return currentPage - 1;
    }
}
