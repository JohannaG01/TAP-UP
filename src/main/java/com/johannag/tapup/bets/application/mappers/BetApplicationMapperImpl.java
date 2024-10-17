package com.johannag.tapup.bets.application.mappers;

import com.johannag.tapup.bets.application.dtos.CreateBetDTO;
import com.johannag.tapup.bets.application.dtos.FindBetsDTO;
import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.dtos.FindBetEntitiesDTO;
import com.johannag.tapup.bets.domain.models.BetModelState;
import com.johannag.tapup.bets.presentation.dtos.queries.FindBetsQuery;
import com.johannag.tapup.bets.presentation.dtos.requests.CreateBetRequestDTO;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class BetApplicationMapperImpl implements BetApplicationMapper {

    private final TypeMap<CreateBetRequestDTO, CreateBetDTO.Builder> createDTOMapper;
    private final TypeMap<CreateBetDTO, CreateBetEntityDTO.Builder> createEntityDTOMapper;
    private final TypeMap<FindBetsQuery, FindBetsDTO.Builder> findBetsDTOMapper;
    private final TypeMap<FindBetsDTO, FindBetEntitiesDTO.Builder> findBetEntitiesDTOMapper;

    public BetApplicationMapperImpl() {
        createDTOMapper = builderTypeMapper(CreateBetRequestDTO.class, CreateBetDTO.Builder.class);
        createEntityDTOMapper = builderTypeMapper(CreateBetDTO.class, CreateBetEntityDTO.Builder.class);
        findBetsDTOMapper = builderTypeMapper(FindBetsQuery.class, FindBetsDTO.Builder.class);
        findBetEntitiesDTOMapper = builderTypeMapper(FindBetsDTO.class, FindBetEntitiesDTO.Builder.class);
    }

    @Override
    public CreateBetDTO toCreateDTO(UUID userUuid, CreateBetRequestDTO dto) {
        return createDTOMapper
                .map(dto)
                .userUuid(userUuid)
                .build();
    }

    @Override
    public CreateBetEntityDTO toCreateEntityDTO(CreateBetDTO dto) {
        return createEntityDTOMapper
                .map(dto)
                .uuid(UUID.randomUUID())
                .state(BetModelState.PENDING)
                .build();
    }

    @Override
    public FindBetsDTO toFindDTO(UUID userUuid, FindBetsQuery query) {
        return findBetsDTOMapper
                .map(query)
                .userUuid(userUuid)
                .build();
    }

    @Override
    public FindBetEntitiesDTO toFindEntitiesDTO(FindBetsDTO dto) {
        return findBetEntitiesDTOMapper
                .map(dto)
                .build();
    }


}
