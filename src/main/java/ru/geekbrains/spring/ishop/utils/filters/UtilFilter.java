package ru.geekbrains.spring.ishop.utils.filters;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UtilFilter {
    //номер страницы
    private int pageIndex;
    //количество объектов, выводимых на страницу
    private int limit;
    //направление сортировки (по умолчанию "по возрастанию")
    private Sort.Direction direction;
    private StringBuilder filterDefinition;

    public void init(Map<String, String> params) {
        //инициируем объект билдера строки для сборки строки с параметрами фильтра,
        // добавляемыми к запросу
        this.filterDefinition = new StringBuilder();
        //устанавливаем значения по умолчанию
        pageIndex = 0;//TODO pageIndex default -> константы
        //количество объектов, выводимых на страницу
        limit = 3;//TODO limit default -> константы
        //направление сортировки (по умолчанию "по возрастанию")
        direction = Sort.Direction.ASC;//TODO direction default -> константы
        //если в запросе указан хотя бы один параметр, вынимаем параметры
        if(params != null && !params.isEmpty()) {
            //если указан номер страницы
            if(params.containsKey("page") && !params.get("page").isEmpty()) {
                pageIndex = Integer.parseInt(params.get("page")) - 1;
            }
            //если указан количество объектов, выводимых на страницу
            if(params.containsKey("limit") && !params.get("limit").isEmpty()) {
                limit = Integer.parseInt(params.get("limit"));
                //добавляем параметр к строке запроса
                filterDefinition.append("&limit=").append(limit);
            }
            //если указан направление сортировки
            if(params.containsKey("direction") && !params.get("direction").isEmpty()) {
                direction = params.get("direction").equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
                //добавляем параметр к строке запроса
                filterDefinition.append("&direction=").append(direction);
            }
        }
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getLimit() {
        return limit;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public StringBuilder getFilterDefinition() {
        return filterDefinition;
    }
}
