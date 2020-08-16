package ru.geekbrains.spring.ishop.utils.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.entity.Order;
import ru.geekbrains.spring.ishop.repository.specifications.OrderSpecification;
import java.util.Map;

@Component
public class OrderFilter {
    private UtilFilter utilFilter;

    @Autowired
    public void setUtilFilter(UtilFilter utilFilter) {
        this.utilFilter = utilFilter;
    }

    private Specification<Order> spec;
    private StringBuilder filterDefinition;

    public void init(Map<String, String> params, String principalName) {
        //инициируем настройки фильтра №страницы-лимита-направления
        utilFilter.init(params);
        //инициируем нулевую спецификацию фильтра(фильтр не применится)
        this.spec = Specification.where(null);
        //инициируем объект билдера строки для сборки строки с параметрами фильтра,
        // добавляемыми к запросу
        this.filterDefinition = utilFilter.getFilterDefinition();
        //если есть хотя бы один параметр
        if(params != null && !params.isEmpty()) {
            //если в параметрах есть параметр категории товара
            if(params.containsKey("orderStatus")
                    && !params.get("orderStatus").isEmpty()) {
                //инициируем переменную из параметра
                Short orderStatusId = Short.parseShort(params.get("orderStatus"));
                //добавляем по и условие фильтра в спецификацию фильтра
                spec = spec.and(OrderSpecification.orderStatusIdEquals(orderStatusId));
                //добавляем параметр фильтра к строке запроса
                filterDefinition.append("&orderStatus=").append(orderStatusId);
            }
        }
        //для пользовательского режима(не админ)
        if(!principalName.trim().isEmpty()) {
            spec = spec.and(OrderSpecification.userNameEquals(principalName));
        }
    }

    public Specification<Order> getSpec() {
        return spec;
    }

    public StringBuilder getFilterDefinition() {
        return filterDefinition;
    }

}
