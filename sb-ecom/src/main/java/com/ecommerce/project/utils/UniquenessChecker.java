package com.ecommerce.project.utils;

import com.ecommerce.project.exceptions.ResourceAlreadyExistsException;

import java.util.Optional;
import java.util.function.Function;

public class UniquenessChecker {
    /*
     *
     * @param name                nome que quer verificar
     * @param findByNameFunc      função para encontrar pelo nome do repository
     *                            ex: productRepository :: findByProductNameIgnoreCase
     * @param <E>                 tipo da entidade
     * @param <ID>                ID da entidade
     * @param <entityName>        nome da classe da entidade ex: "Product" ou "Category"
     *
     * @return VOID
     */

    public static  <E, ID> void checkNameUniqueness(
            String name,
            Function<String , Optional<E>> findByNameFunc,
            Function<E, ID>  getIdFunc,
            ID currentId,
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
