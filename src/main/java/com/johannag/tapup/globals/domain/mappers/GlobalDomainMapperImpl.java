package com.johannag.tapup.globals.domain.mappers;

import com.johannag.tapup.globals.domain.models.SexModel;
import com.johannag.tapup.globals.infrastructure.db.entities.SexEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class GlobalDomainMapperImpl implements GlobalDomainMapper {

    @Override
    public SexEntity toEntity(@Nullable SexModel sex) {
        if (sex == null) return null;
        return SexEntity.valueOf(sex.name());
    }
}
