package com.johannag.tapup.bets.application.mappers;

import com.johannag.tapup.bets.application.dtos.CreateBetDTO;
import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.models.BetModelState;
import com.johannag.tapup.bets.presentation.dtos.requests.CreateBetRequestDTO;
import com.johannag.tapup.horseRaces.domain.models.HorseRaceModel;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class BetApplicationMapperImpl implements BetApplicationMapper {

    private final TypeMap<CreateBetRequestDTO, CreateBetDTO.Builder> createDTOMapper;
    private final TypeMap<CreateBetDTO, CreateBetEntityDTO.Builder> createEntityDTOMapper;


    public BetApplicationMapperImpl() {
        createDTOMapper = builderTypeMapper(CreateBetRequestDTO.class, CreateBetDTO.Builder.class);
        createEntityDTOMapper = builderTypeMapper(CreateBetDTO.class, CreateBetEntityDTO.Builder.class);
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


}
