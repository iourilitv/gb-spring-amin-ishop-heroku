package ru.geekbrains.spring.ishop.utils.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.repository.specifications.ProductSpecification;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ProductFilter {
    private UtilFilter utilFilter;

    @Autowired
    public void setUtilFilter(UtilFilter utilFilter) {
        this.utilFilter = utilFilter;
    }

    private Specification<Product> spec;
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
            //если в параметрах есть параметр минимальной цены
            if(params.containsKey("minPrice")
                    && !params.get("minPrice").isEmpty()) {
                //инициируем переменную минимальной цены из параметра
                BigDecimal minPrice = new BigDecimal(params.get("minPrice"));
                //добавляем по и условие фильтра в спецификацию фильтра
                spec = spec.and(ProductSpecification.priceGEThan(minPrice));
                //добавляем параметр фильтра к строке запроса
                filterDefinition.append("&minPrice=").append(minPrice);
            }
            //если в параметрах есть параметр максимальной цены
            if(params.containsKey("maxPrice")
                    && !params.get("maxPrice").isEmpty()) {
                //инициируем переменную минимальной цены из параметра
                BigDecimal maxPrice = new BigDecimal(params.get("maxPrice"));
                //добавляем по и условие фильтра в спецификацию фильтра
                spec = spec.and(ProductSpecification.priceLEThan(maxPrice));
                //добавляем параметр фильтра к строке запроса
                filterDefinition.append("&maxPrice=").append(maxPrice);
            }
            //если в параметрах есть параметр категории товара
            if(params.containsKey("category")
                    && !params.get("category").isEmpty()) {
                //инициируем переменную из параметра
                Short category_id = Short.parseShort(params.get("category"));
                //добавляем по и условие фильтра в спецификацию фильтра
                spec = spec.and(ProductSpecification.categoryIdEquals(category_id));
                //добавляем параметр фильтра к строке запроса
                filterDefinition.append("&category=").append(category_id);
            }
        }
    }

    public Specification<Product> getSpec() {
        return spec;
    }

    public StringBuilder getFilterDefinition() {
        return filterDefinition;
    }

}
