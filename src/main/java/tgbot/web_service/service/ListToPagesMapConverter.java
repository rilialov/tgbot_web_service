package tgbot.web_service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListToPagesMapConverter<T> {
    List<T> initialList;

    int chunkSize;

    int totalPages;

    Map<Integer, List<T>> pagesMap;

    public List<T> getPage(int pageNumber) {
        return pagesMap.get(pageNumber);
    }

    public void createMap(List<T> list, int size) {
        initialList = list;
        chunkSize = size;
        if (size < list.size()) {
            totalPages = list.size() / size;
        } else totalPages = 1;

        pagesMap = new HashMap<>();
        for (int i = 0; i < totalPages; i++) {
            int start = i * chunkSize;
            int end = start + chunkSize;
            if (end > initialList.size()) {
                end = initialList.size();
            }
            pagesMap.put(i, initialList.subList(start, end));
        }
    }

    public int getTotalPages() {
        return totalPages;
    }
}
