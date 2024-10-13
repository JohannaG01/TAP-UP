package com.johannag.tapup.horses.infrastructure.db.repositories;

import com.johannag.tapup.globals.infrastructure.db.entities.SexEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntityState;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
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

    public static Specification<HorseEntity> withBirthDateFrom(LocalDate birthDateFrom) {
        return (Root<HorseEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (birthDateFrom == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get("birthDate"), birthDateFrom);
        };
    }

    public static Specification<HorseEntity> withBirthDateTo(LocalDate birthDateTo) {
        return (Root<HorseEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (birthDateTo == null) {
                return builder.conjunction();
            }
            return builder.lessThanOrEqualTo(root.get("birthDate"), birthDateTo);
        };
    }

    public static Specification<HorseEntity> withBreed(String breed) {
        return (Root<HorseEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (breed == null || breed.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get("breed"), breed);
        };
    }

    public static Specification<HorseEntity> withColor(String color) {
        return (Root<HorseEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (color == null || color.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get("color"), color);
        };
    }

    public static Specification<HorseEntity> withName(String name) {
        return (Root<HorseEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (name == null || name.isEmpty()) {
                return builder.conjunction();
            }
            return builder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<HorseEntity> withCode(String code) {
        return (Root<HorseEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (code == null || code.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get("code"), code);
        };
    }

    public static Specification<HorseEntity> withSex(SexEntity sex) {
        return (Root<HorseEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (sex == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("sex"), sex);
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

        public Builder withBirthDateFrom(LocalDate birthDateFrom) {
            if (birthDateFrom != null) {
                spec = spec.and(JpaHorseSpecifications.withBirthDateFrom(birthDateFrom));
            }
            return this;
        }

        public Builder withBirthDateTo(LocalDate birthDateTo) {
            if (birthDateTo != null) {
                spec = spec.and(JpaHorseSpecifications.withBirthDateTo(birthDateTo));
            }
            return this;
        }

        public Builder withBreed(String breed) {
            if (breed != null && !breed.isEmpty()) {
                spec = spec.and(JpaHorseSpecifications.withBreed(breed));
            }
            return this;
        }

        public Builder withColor(String color) {
            if (color != null && !color.isEmpty()) {
                spec = spec.and(JpaHorseSpecifications.withColor(color));
            }
            return this;
        }

        public Builder withName(String name) {
            if (name != null && !name.isEmpty()) {
                spec = spec.and(JpaHorseSpecifications.withName(name));
            }
            return this;
        }

        public Builder withCode(String code) {
            if (code != null && !code.isEmpty()) {
                spec = spec.and(JpaHorseSpecifications.withCode(code));
            }
            return this;
        }

        public Builder withSex(SexEntity sex) {
            if (sex != null) {
                spec = spec.and(JpaHorseSpecifications.withSex(sex));
            }
            return this;
        }

        public Specification<HorseEntity> build() {
            return spec;
        }
    }
}
