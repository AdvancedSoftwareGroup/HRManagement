package net.restapp.mapper;


import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Id;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.ReflectionUtils.*;
/**
 * Class transforms DTO to entity and gets dependent entities from DB by ID that marked {@link Entity}
 * and {@link EntityList}
 */
@Component
@Slf4j
public class DtoMapper {

    /**
     * The library for map in models
     */
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    @Autowired
    private EntityManager entityManager;

    /**
     * Configures the {@link ModelMapper}, to strict mapping of the pointed field and skip null
     */
    public DtoMapper() {
        MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        MODEL_MAPPER.getConfiguration().setSkipNullEnabled(true);
        log.info("matchingStrategy = MatchingStrategies.STRICT");
        log.info("skipNullEnabled = true");
    }

    /**
     * The method for map DTO to pointed entity
     *
     * @param source      - dto for map to entity
     * @param destination - class of entity for mapping
     * @return mapped entity of specified destination class
     */
    public <T> T map(Object source, Class<T> destination) {
        log.debug("Mapping DTO {}:({}) to {} ", source.getClass().getName(), source.toString(), destination.getName());
        Set<Field> fields = getFieldsIncludingSuper(source);
        Object dtoId = getDtoId(source, fields);
        if (dtoId != null) {
            log.debug("DTO {} is already existing entity with id={}", source.getClass().getSimpleName(), dtoId);
            T t = entityManager.find(destination, dtoId);

            if (t == null) {
                String msg = String.format("There is no %S with id: %d", destination.getSimpleName(), dtoId);
                throw new EntityNotFoundException(msg);
            }
            MODEL_MAPPER.map(source, t);
            mapDependencies(source, t, fields);
            log.debug("Mapped DTO Successfully {} {} ", source.getClass().getName(), destination.getName());
            return t;
        } else {
            T t = MODEL_MAPPER.map(source, destination);
            mapDependencies(source, t, fields);
            log.debug("Mapped DTO Successfully {} {} ", source.getClass().getName(), destination.getName());
            return t;
        }
    }

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

    /**
     * Method for map list DTO object to list entity, use method of {@link ModelMapper}
     * @param source -dto for map to entity
     * @param destinationElement -class of entity for mapping
     * @return mapped list entity of specified destination class
     */
    @SuppressWarnings("unchecked")
    public <T, E> List<T> listSimpleFieldMap(List<E> source, Class<T> destinationElement) {
        List list = source.stream()
                .map(e -> MODEL_MAPPER.map(e, destinationElement))
                .collect(Collectors.toCollection(() -> new ArrayList(source.size())));
        return list;
    }


    @Nullable
    private Object getDtoId(Object source, Set<Field> fields) {
        Object dtoId;
        for (Field field : fields) {
            if (field.getAnnotation(Id.class) != null) {
                field.setAccessible(true);
                dtoId = getField(field, source);
                field.setAccessible(false);
                return dtoId;
            }
        }
        return null;
    }

    private <T> void mapDependencies(Object source, T t, Set<Field> fields) {
        for (Field field : fields) {
            field.setAccessible(true);
            mapEntityDependency(source, t, field);
            mapEntityListDependency(source, t, field);
            field.setAccessible(false);
        }
    }

    /**
     * The method reviews if the entity has an annotation {@link Entity} and field ID,  finds dependent entity from DB,
     * sets it to correspondent field of destination class, throws an exception
     * {@link EntityNotFoundException}.
     *
     * @param source - explored object, DTO
     * @param t      - destination class entity
     * @param field  - explored object's field
     */
    @SuppressWarnings("unchecked")
    private <T> void mapEntityDependency(Object source, T t, Field field) {
        Entity entityAnnotation = field.getDeclaredAnnotation(Entity.class);
        if (entityAnnotation != null) {
            Class aClass = entityAnnotation.value();
            Object entityId = getField(field, source);
            log.debug("DTO contains child entity {} id={}", aClass.getName(), entityId);
            if (entityId != null) {
                Object o = entityManager.find(aClass, entityId);
                if (o == null) {
                    String msg = String.format("There is no %S with id: %d", aClass.getSimpleName(), entityId);
                    log.debug("There is no {} with id: {}, throwing handled EntityNotFoundException",
                            aClass.getName(), entityId);
                    throw new EntityNotFoundException();
                }
                for (Field field1 : getFieldsIncludingSuper(t)) {
                    if (field1.getType().isAssignableFrom(aClass)) {
                        field1.setAccessible(true);
                        setField(field1, t, o);
                        field1.setAccessible(false);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void mapEntityListDependency(Object source, T t, Field field) {
        EntityList entityListAnnotation = field.getDeclaredAnnotation(EntityList.class);
        if (entityListAnnotation != null) {
            Class aClass = entityListAnnotation.value();
            String fieldName = entityListAnnotation.fieldName();
            log.debug("DTO contains child entity list {}", aClass.getName());
            Object oArray = getField(field, source);
            int length = Array.getLength(oArray);
            log.debug("List of {} size: {}", aClass.getName(), length);
            List list = new ArrayList();
            for (int i = 0; i < length; i++) {
                Object o = Array.get(oArray, i);
                Object dependency = entityManager.find(aClass, o);
                if (dependency == null) {
                    String msg = String.format("There is no %S with id: %d", aClass.getSimpleName(), i);
                    log.debug("There is no {} with id: {}, throwing handled EntityNotFoundException",
                            aClass.getName(), i);
                    throw new EntityNotFoundException(msg);
                }
                list.add(dependency);
            }
            Field listField = findField(t.getClass(), fieldName);
            listField.setAccessible(true);
            setField(listField, t, list);
            listField.setAccessible(false);
        }
    }

    /**
     * The method finds all declared fields from the class and superclass
     *
     * @param object - explored object
     * @return fields of class and superclasses
     */
    private Set<Field> getFieldsIncludingSuper(Object object) {
        LinkedHashSet<Field> fields = new LinkedHashSet<>();
        Class<?> currentClass = object.getClass();
        while (currentClass != Object.class) {
            fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
            currentClass = currentClass.getSuperclass();
        }
        return fields;
    }

}
