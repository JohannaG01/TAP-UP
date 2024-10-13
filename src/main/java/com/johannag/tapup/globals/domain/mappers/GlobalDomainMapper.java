package com.johannag.tapup.globals.domain.mappers;

import com.johannag.tapup.globals.domain.models.SexModel;
import com.johannag.tapup.globals.infrastructure.db.entities.SexEntity;
import org.springframework.lang.Nullable;

public interface GlobalDomainMapper {

    /**
     * Converts a {@link SexModel} to a {@link SexEntity}.
     *
     * @param sex the {@link SexModel} to be converted, may be {@code null}.
     * @return the corresponding {@link SexEntity}, or {@code null} if the input {@link SexModel} is {@code null}.
     */
    SexEntity toEntity(@Nullable SexModel sex);
}
