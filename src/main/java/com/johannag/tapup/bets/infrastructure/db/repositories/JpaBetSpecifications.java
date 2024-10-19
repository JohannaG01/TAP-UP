package com.johannag.tapup.bets.infrastructure.db.repositories;

import com.johannag.tapup.bets.infrastructure.db.entities.BetEntity;
import com.johannag.tapup.bets.infrastructure.db.entities.BetEntityState;
import com.johannag.tapup.globals.infrastructure.db.repositories.BaseSpecificationBuilder;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.HorseRaceEntityState;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class JpaBetSpecifications {

    public static Specification<BetEntity> withBetStates(List<BetEntityState> betStates) {
        return (Root<BetEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (betStates == null || betStates.isEmpty()) {
                return builder.conjunction();
            }
            return root.get("state").in(betStates);
        };
    }

    public static Specification<BetEntity> withHorseRaceStates(List<HorseRaceEntityState> horseRaceStates) {
        return (Root<BetEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (horseRaceStates == null || horseRaceStates.isEmpty()) {
                return builder.conjunction();
            }

            return root.get("participant")
                    .get("horseRace")
                    .get("state").in(horseRaceStates);
        };
    }

    public static Specification<BetEntity> withMinAmount(BigDecimal minAmount) {
        return (Root<BetEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (minAmount == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get("amount"), minAmount);
        };
    }

    public static Specification<BetEntity> withMaxAmount(BigDecimal maxAmount) {
        return (Root<BetEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (maxAmount == null) {
                return builder.conjunction();
            }
            return builder.lessThanOrEqualTo(root.get("amount"), maxAmount);
        };
    }

    public static Specification<BetEntity> withPlacement(Integer placement) {
        return (Root<BetEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (placement == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("participant").get("placement"), placement);
        };
    }

    public static Specification<BetEntity> withHorseUuid(UUID horseUuid) {
        return (Root<BetEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (horseUuid == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("participant").get("horse").get("uuid"), horseUuid);
        };
    }

    public static Specification<BetEntity> withHorseRaceUuid(UUID horseRaceUuid) {
        return (Root<BetEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (horseRaceUuid == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("participant").get("horseRace").get("uuid"), horseRaceUuid);
        };
    }

    public static Specification<BetEntity> withStartTimeFrom(LocalDateTime startTimeFrom) {
        return (Root<BetEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (startTimeFrom == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get("participant").get("horseRace").get("startTime"),
                    startTimeFrom);
        };
    }

    public static Specification<BetEntity> withStartTimeTo(LocalDateTime startTimeTo) {
        return (Root<BetEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (startTimeTo == null) {
                return builder.conjunction();
            }
            return builder.lessThanOrEqualTo(root.get("participant").get("horseRace").get("startTime"), startTimeTo);
        };
    }

    public static class Builder extends BaseSpecificationBuilder<BetEntity> {

        public Builder withBetStates(List<BetEntityState> betStates) {
            if (betStates != null && !betStates.isEmpty()) {
                spec = spec.and(JpaBetSpecifications.withBetStates(betStates));
            }
            return this;
        }

        public Builder withHorseRaceStates(List<HorseRaceEntityState> horseRaceStates) {
            if (horseRaceStates != null && !horseRaceStates.isEmpty()) {
                spec = spec.and(JpaBetSpecifications.withHorseRaceStates(horseRaceStates));
            }
            return this;
        }

        public Builder withMinAmount(BigDecimal minAmount) {
            if (minAmount != null) {
                spec = spec.and(JpaBetSpecifications.withMinAmount(minAmount));
            }
            return this;
        }

        public Builder withMaxAmount(BigDecimal maxAmount) {
            if (maxAmount != null) {
                spec = spec.and(JpaBetSpecifications.withMaxAmount(maxAmount));
            }
            return this;
        }

        public Builder withPlacement(Integer placement) {
            if (placement != null) {
                spec = spec.and(JpaBetSpecifications.withPlacement(placement));
            }
            return this;
        }

        public Builder withHorseUuid(UUID horseUuid) {
            if (horseUuid != null) {
                spec = spec.and(JpaBetSpecifications.withHorseUuid(horseUuid));
            }
            return this;
        }

        public Builder withHorseRaceUuid(UUID horseRaceUuid) {
            if (horseRaceUuid != null) {
                spec = spec.and(JpaBetSpecifications.withHorseRaceUuid(horseRaceUuid));
            }
            return this;
        }

        public Builder withStartTimeFrom(LocalDateTime startTimeFrom) {
            if (startTimeFrom != null) {
                spec = spec.and(JpaBetSpecifications.withStartTimeFrom(startTimeFrom));
            }
            return this;
        }

        public Builder withStartTimeTo(LocalDateTime startTimeTo) {
            if (startTimeTo != null) {
                spec = spec.and(JpaBetSpecifications.withStartTimeTo(startTimeTo));
            }
            return this;
        }

    }
}
