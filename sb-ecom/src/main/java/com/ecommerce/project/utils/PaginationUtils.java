package com.ecommerce.project.utils;

import com.ecommerce.project.dtos.commoms.PageMetaDTO;
import com.ecommerce.project.dtos.commoms.PageResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class PaginationUtils {

    /*
     * Converte Page<E> em PageResponseDTO<DTO> genérico
     *
     * @param page         a página da entidade
     * @param mapper       função para converter entidade em DTO
     * @param <E>          tipo da entidade
     * @param <DTO>        tipo do DTO
     * @return PageResponseDTO<DTO>
     */

    public static  <E, DTO> PageResponseDTO<DTO> toPageResponseDTO(Page<E> page, Function<E, DTO> mapper){
        List<DTO> content = page.getContent()
                .stream()
                .map(mapper)
                .toList();

        PageMetaDTO meta = PageMetaDTO.fromPage(page);

        return new PageResponseDTO<>(content, meta);
    }

}
