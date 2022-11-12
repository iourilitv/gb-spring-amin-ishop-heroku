package ru.geekbrains.spring.ishop.rest.converters.deserializers.interfaces;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ru.geekbrains.spring.ishop.entity.AbstractEntity;
import ru.geekbrains.spring.ishop.entity.Address;
import ru.geekbrains.spring.ishop.exception.OutEntityDeserializeException;
import ru.geekbrains.spring.ishop.utils.EntityTypes;

public class AddressDeserializerImpl extends AbstractEntityDeserializer<Address> {

    @Override
    public AbstractEntity recognize(JsonElement json) {
        JsonObject jsonObject = json.getAsJsonObject();
        isJsonEntityCorrect(jsonObject);
        Address entity = new Address();
        entity.setId(jsonObject.get(Address.Fields.id.name()).getAsLong());
        entity.setCountry(jsonObject.get(Address.Fields.country.name()).getAsString());
        entity.setCity(jsonObject.get(Address.Fields.city.name()).getAsString());
        entity.setAddress(jsonObject.get(Address.Fields.address.name()).getAsString());
        return entity;
    }

    private void isJsonEntityCorrect(JsonObject jsonObject) {
        Address.Fields[] fields = Address.Fields.values();
        for (Address.Fields field : fields) {
            if (jsonObject.get(field.name()) == null) {
                throw new OutEntityDeserializeException("Wrong json-object with entityType: " + EntityTypes.Address.name() + ". Can't complete deserialize process!");
            }
        }
    }
}

