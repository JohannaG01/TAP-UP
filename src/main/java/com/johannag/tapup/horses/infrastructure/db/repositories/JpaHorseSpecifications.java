package com.johannag.tapup.horses.infrastructure.db.repositories;

import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntityState;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class JpaHorseSpecifications {

    public static Specification<HorseEntity> withStates(List<HorseEntityState> states) {
        return (Root<HorseEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (states == null || states.isEmpty()) {
                return builder.conjunction();
            }
            return root.get("state").in(states);
        };
    }

    public static class Builder {

        private Specification<HorseEntity> spec;

        public Builder() {
            this.spec = Specification.where(null);
        }

        public Builder withStates(List<HorseEntityState> states) {
            if (states != null && !states.isEmpty()) {
                spec = spec.and(JpaHorseSpecifications.withStates(states));
            }
            return this;
        }

        public Specification<HorseEntity> build() {
            return spec;
        }
    }
}
