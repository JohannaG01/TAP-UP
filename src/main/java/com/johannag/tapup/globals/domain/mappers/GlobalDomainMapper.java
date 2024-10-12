package com.johannag.tapup.globals.domain.mappers;

import com.johannag.tapup.globals.domain.models.SexModel;
import com.johannag.tapup.globals.infrastructure.db.entities.SexEntity;

public interface GlobalDomainMapper {

    /**
     * Converts a {@link SexModel} to a {@link SexEntity}.
     *
     * @param sex The sex model to be converted.
     * @return The corresponding sex entity for the given model,
     */
    SexEntity toEntity(SexModel sex);
}
