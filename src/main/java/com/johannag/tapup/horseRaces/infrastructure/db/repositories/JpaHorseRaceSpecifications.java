package com.johannag.tapup.horseRaces.infrastructure.db.repositories;

import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntity;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntityState;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class JpaHorseRaceSpecifications {

    public static <HorseRaceStateEntity> Specification<HorseRaceEntity> withStates(List<HorseRaceStateEntity> states) {
        return (Root<HorseRaceEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (states == null || states.isEmpty()) {
                return builder.conjunction();
            }
            return root.get("state").in(states);
        };
    }

    public static Specification<HorseRaceEntity> withStartTimeFrom(LocalDateTime startTimeFrom) {
        return (Root<HorseRaceEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (startTimeFrom == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get("startTime"), startTimeFrom);
        };
    }

    public static Specification<HorseRaceEntity> withStartTimeTo(LocalDateTime startTimeTo) {
        return (Root<HorseRaceEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (startTimeTo == null) {
                return builder.conjunction();
            }
            return builder.lessThanOrEqualTo(root.get("startTime"), startTimeTo);
        };
    }

    public static Specification<HorseRaceEntity> withHorseUuid(UUID horseUuid) {
        return (Root<HorseRaceEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (horseUuid == null) {
                return builder.conjunction();
            }
            return builder.equal(root.join("participants").get("horse").get("uuid"), horseUuid);
        };
    }

    public static Specification<HorseRaceEntity> withHorseCode(String horseCode) {
        return (Root<HorseRaceEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (horseCode == null || horseCode.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.join("participants").get("horse").get("code"), horseCode);
        };
    }

    public static Specification<HorseRaceEntity> withHorseName(String horseName) {
        return (Root<HorseRaceEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (horseName == null || horseName.isEmpty()) {
                return builder.conjunction();
            }
            return builder.like(root.join("participants").get("horse").get("name"), "%" + horseName + "%");
        };
    }

    public static Specification<HorseRaceEntity> withHorseBreed(String horseBreed) {
        return (Root<HorseRaceEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (horseBreed == null || horseBreed.isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.join("participants").get("horse").get("breed"), horseBreed);
        };
    }

    public static class Builder {

        private Specification<HorseRaceEntity> spec;

        public Builder() {
            this.spec = Specification.where(null);
        }

        public Builder withStates(List<HorseRaceEntityState> states) {
            if (states != null && !states.isEmpty()) {
                spec = spec.and(JpaHorseRaceSpecifications.withStates(states));
            }
            return this;
        }

        public Builder withStartTimeFrom(LocalDateTime startTimeFrom) {
            if (startTimeFrom != null) {
                spec = spec.and(JpaHorseRaceSpecifications.withStartTimeFrom(startTimeFrom));
            }
            return this;
        }

        public Builder withStartTimeTo(LocalDateTime startTimeTo) {
            if (startTimeTo != null) {
                spec = spec.and(JpaHorseRaceSpecifications.withStartTimeTo(startTimeTo));
            }
            return this;
        }

        public Builder withHorseUuid(UUID horseUuid) {
            if (horseUuid != null) {
                spec = spec.and(JpaHorseRaceSpecifications.withHorseUuid(horseUuid));
            }
            return this;
        }

        public Builder withHorseCode(String horseCode) {
            if (horseCode != null && !horseCode.isEmpty()) {
                spec = spec.and(JpaHorseRaceSpecifications.withHorseCode(horseCode));
            }
            return this;
        }

        public Builder withHorseName(String horseName) {
            if (horseName != null && !horseName.isEmpty()) {
                spec = spec.and(JpaHorseRaceSpecifications.withHorseName(horseName));
            }
            return this;
        }

        public Builder withHorseBreed(String horseBreed) {
            if (horseBreed != null && !horseBreed.isEmpty()) {
                spec = spec.and(JpaHorseRaceSpecifications.withHorseBreed(horseBreed));
            }
            return this;
        }

        public Specification<HorseRaceEntity> build() {
            return spec;
        }
    }
}
