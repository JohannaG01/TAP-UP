package com.johannag.tapup.horses.infrastructure.db.entities;

import com.johannag.tapup.globals.infrastructure.db.entities.AuditableEntity;
import com.johannag.tapup.globals.infrastructure.db.entities.SexEntity;
import com.johannag.tapup.horseRaces.infrastructure.db.entities.ParticipantEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Entity
@Table(name = "horses")
public class HorseEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "uuid", nullable = false, updatable = false)
    private UUID uuid;

    @lombok.Builder.Default
    @OneToMany(mappedBy = "horse", fetch = FetchType.LAZY)
    private List<ParticipantEntity> participations = new ArrayList<>();

    @Column(name = "code", nullable = false, updatable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private SexEntity sex;

    @Column(name = "color", nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private HorseEntityState state;
}
