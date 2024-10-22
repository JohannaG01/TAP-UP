package com.johannag.tapup.globals.infrastructure.db.repositories;

import org.springframework.data.jpa.domain.Specification;

public abstract class BaseSpecificationBuilder<Entity> {
    protected Specification<Entity> spec;

    public BaseSpecificationBuilder() {
        this.spec = Specification.where(null);
    }

    public Specification<Entity> build() {
        return spec;
    }

}
