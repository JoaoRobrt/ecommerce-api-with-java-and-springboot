package com.ecommerce.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "categories")
@Table(
        name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "category_name")
        }
)
public class Category {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "category_name", nullable = false)
    @NotBlank(message = "Category name is required.")
    @Size(min = 4, max = 20, message = "Category name must be between 4 and 20 characters.")
    //Possivelmente atualizarei para um Set, pois podera conter multiplas categorias
    private String categoryName;

    @PrePersist
    @PreUpdate
    private void normalize() {
        this.categoryName = categoryName.trim().toLowerCase();
    }

}
