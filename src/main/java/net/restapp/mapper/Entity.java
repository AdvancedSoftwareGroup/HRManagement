package net.restapp.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation of object servs to define a depended entity's ID, used by {@link DtoMapper}
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {

    /**
     * The field
     *
     * @return - Dependent Entity's class
     */
    Class value();
}