package net.restapp.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation of object servs to define a dependent entity's ID, used by {@link DtoMapper}
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityList {
    /**
     * Field
     * @return types of element in dependent entity's list
     */
    Class value();

    /**
     * Field
     * @return field's name in destination object to map into it
     */
    String fieldName();
}