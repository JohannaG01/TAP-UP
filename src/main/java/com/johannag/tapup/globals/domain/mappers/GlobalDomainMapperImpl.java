package com.johannag.tapup.globals.domain.mappers;

import com.johannag.tapup.globals.domain.models.SexModel;
import com.johannag.tapup.globals.infrastructure.db.entities.SexEntity;
import org.springframework.stereotype.Component;

@Component
public class GlobalDomainMapperImpl implements GlobalDomainMapper {

    @Override
    public SexEntity toEntity(SexModel sex) {
        return SexEntity.valueOf(sex.name());
    }
}
