package com.ecommerce.project.utils;

import com.ecommerce.project.exceptions.ResourceAlreadyExistsException;

import java.util.Optional;
import java.util.function.Function;

public class UniquenessChecker {

    public static  <E, ID> void checkNameUniqueness(
            String name,
            Function<String , Optional<E>> findByNameFunc,
            Function<E, ID>  getIdFunc,
            ID currentId, // Para usar nas funçoes criar colocar null,
                          // campo é apenas utilizado para verificar em updates

            String entityName
    ){
        String normalized = SortUtils.normalize(name);

        findByNameFunc.apply(normalized).ifPresent(existing -> {
            if(currentId == null || !getIdFunc.apply(existing).equals(currentId)){
                throw new ResourceAlreadyExistsException(
                        entityName + "with name '" + normalized + "' already exists."
                );
            }
        });

    }
}
