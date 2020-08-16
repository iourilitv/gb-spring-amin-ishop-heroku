package ru.geekbrains.spring.ishop.utils.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.repository.specifications.UserSpecification;

import java.util.Map;

@Component
public class UserFilter {
    private UtilFilter utilFilter;

    @Autowired
    public void setUtilFilter(UtilFilter utilFilter) {
        this.utilFilter = utilFilter;
    }

    private Specification<User> spec;
    private StringBuilder filterDefinition;

    public void init(Map<String, String> params) {
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
            if(params.containsKey("role")
                    && !params.get("role").isEmpty()) {
                //инициируем переменную из параметра
                Short role_id = Short.parseShort(params.get("role"));
                //добавляем по и условие фильтра в спецификацию фильтра
                spec = spec.and(UserSpecification.userRoleIsMember(role_id));
                //добавляем параметр фильтра к строке запроса
                filterDefinition.append("&role=").append(role_id);
            }
        }
    }

    public Specification<User> getSpec() {
        return spec;
    }

    public StringBuilder getFilterDefinition() {
        return filterDefinition;
    }

}
