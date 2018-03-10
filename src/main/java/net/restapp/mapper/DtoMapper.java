package net.restapp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    /**
     * The library for map in models
     */
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    /**
     * Method for map DTO object to entity, use method of {@link ModelMapper}
     *
     * @param source      - dto for map to entity
     * @param destination - class of entity for mapping
     * @return mapped entity of specified destination class
     */
    public <T> T simpleFieldMap(Object source, Class<T> destination) {
        return MODEL_MAPPER.map(source, destination);
    }


}
