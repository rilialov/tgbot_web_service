package tgbot.web_service.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ControllersUtils {

    public static List<Integer> getPageNumbersList(int currentPage, int totalPage) {
        List<Integer> pageNumbers;
        if (currentPage < totalPage) {
            if (currentPage > 1) {
                pageNumbers = IntStream.rangeClosed(currentPage - 1, currentPage + 1).boxed().collect(Collectors.toList());
            } else pageNumbers = IntStream.rangeClosed(currentPage, currentPage + 1).boxed().collect(Collectors.toList());
        } else {
            if (totalPage == 1) {
                pageNumbers = IntStream.rangeClosed(currentPage, currentPage).boxed().collect(Collectors.toList());
            } else pageNumbers = IntStream.rangeClosed(currentPage - 1, currentPage).boxed().collect(Collectors.toList());
        }
        return pageNumbers;
    }
}
