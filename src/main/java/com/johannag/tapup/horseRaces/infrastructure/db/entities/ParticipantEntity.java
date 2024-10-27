package com.johannag.tapup.horseRaces.infrastructure.db.entities;

import com.johannag.tapup.bets.infrastructure.db.entities.BetEntity;
import com.johannag.tapup.globals.infrastructure.db.entities.AuditableEntity;
import com.johannag.tapup.horses.infrastructure.db.entities.HorseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Entity
@Table(name = "participants")
public class ParticipantEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "uuid", nullable = false, updatable = false)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "horse_race_id", nullable = false, updatable = false)
    private HorseRaceEntity horseRace;

    @ManyToOne
    @JoinColumn(name = "horse_id", nullable = false, updatable = false)
    private HorseEntity horse;

    @lombok.Builder.Default
    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
    private List<BetEntity> bets = new ArrayList<>();

    @Nullable
    @Column(name = "placement")
    private Integer placement;

    @Nullable
    @Column(name = "time")
    private LocalTime time;
}
