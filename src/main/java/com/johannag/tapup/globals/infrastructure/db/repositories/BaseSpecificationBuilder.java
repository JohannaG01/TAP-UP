package com.johannag.tapup.globals.infrastructure.db.repositories;

import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSpecificationBuilder<Entity> {
    protected Specification<Entity> spec;

    public BaseSpecificationBuilder() {
        this.spec = Specification.where(null);
    }

    protected List<Order> getOrderList(Sort sort, Root<Entity> root, CriteriaBuilder builder) {
        List<Order> orders = new ArrayList<>();
        if (sort != null) {
            for (Sort.Order order : sort) {
                if (order.isAscending()) {
                    orders.add(builder.asc(root.get(order.getProperty())));
                } else {
                    orders.add(builder.desc(root.get(order.getProperty())));
                }
            }
        }
        return orders;
    }

    public Specification<Entity> build(Sort sort) {
        return (root, query, builder) -> {
            if (query != null) {
                List<Order> orders = getOrderList(sort, root, builder);
                if (!orders.isEmpty()) {
                    query.orderBy(orders);
                }
            }
            return spec.toPredicate(root, query, builder);
        };
    }

    public Specification<Entity> build() {
        return spec;
    }

}
