package com.medeil.productservice.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.medeil.productservice.entity.Product;

public class ProductSpecification {
	
	public static Specification<Product> hasCategory(String category) {

        return (root, query, cb) ->

                category == null || category.isBlank()

                        ? null

                        : cb.equal(root.get("category"), category);

    }

    public static Specification<Product> hasBrand(String brand) {

        return (root, query, cb) ->

                brand == null || brand.isBlank()

                        ? null

                        : cb.equal(root.get("brand"), brand);

    }

    public static Specification<Product> hasStatus(Boolean status) {

        return (root, query, cb) ->

                status == null

                        ? null

                        : cb.equal(root.get("status"), status);

    }

    public static Specification<Product> minPrice(BigDecimal minPrice) {

        return (root, query, cb) ->

                minPrice == null

                        ? null

                        : cb.greaterThanOrEqualTo(root.get("sellingPrice"), minPrice);

    }

    public static Specification<Product> maxPrice(BigDecimal maxPrice) {

        return (root, query, cb) ->

                maxPrice == null

                        ? null

                        : cb.lessThanOrEqualTo(root.get("sellingPrice"), maxPrice);

    }


}
