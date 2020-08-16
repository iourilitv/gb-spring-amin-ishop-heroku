package ru.geekbrains.spring.ishop.utils.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.entity.Address;

import java.util.Map;

@Component
public class AddressFilter {
    private UtilFilter utilFilter;

    @Autowired
    public void setUtilFilter(UtilFilter utilFilter) {
        this.utilFilter = utilFilter;
    }

    private Specification<Address> spec;
    private StringBuilder filterDefinition;

    public void init(Map<String, String> params) {
        //инициируем настройки фильтра №страницы-лимита-направления
        utilFilter.init(params);
        //инициируем нулевую спецификацию фильтра(фильтр не применится)
        this.spec = Specification.where(null);
        //инициируем объект билдера строки для сборки строки с параметрами фильтра,
        // добавляемыми к запросу
        this.filterDefinition = utilFilter.getFilterDefinition();
    }

    public Specification<Address> getSpec() {
        return spec;
    }

    public StringBuilder getFilterDefinition() {
        return filterDefinition;
    }

}
