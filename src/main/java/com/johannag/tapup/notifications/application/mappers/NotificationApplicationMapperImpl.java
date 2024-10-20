package com.johannag.tapup.notifications.application.mappers;

import com.johannag.tapup.bets.application.dtos.CreateBetDTO;
import com.johannag.tapup.bets.application.dtos.FindBetsDTO;
import com.johannag.tapup.bets.domain.dtos.CreateBetEntityDTO;
import com.johannag.tapup.bets.domain.dtos.FindBetEntitiesDTO;
import com.johannag.tapup.bets.presentation.dtos.queries.FindBetsQuery;
import com.johannag.tapup.bets.presentation.dtos.requests.CreateBetRequestDTO;
import com.johannag.tapup.globals.application.utils.DateTimeUtils;
import com.johannag.tapup.notifications.application.dtos.CreateNotificationDTO;
import com.johannag.tapup.notifications.domain.dtos.CreateNotificationEntityDTO;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class NotificationApplicationMapperImpl implements NotificationApplicationMapper{

    private final TypeMap<CreateNotificationDTO, CreateNotificationEntityDTO.Builder> createDTOMapper;

    public NotificationApplicationMapperImpl() {
        createDTOMapper = builderTypeMapper(CreateNotificationDTO.class, CreateNotificationEntityDTO.Builder.class);
    }

    @Override
    public List<CreateNotificationEntityDTO> toCreateDTO(List<CreateNotificationDTO> dtos) {
        return dtos.stream()
                .map(this::toCreateDTO)
                .toList();
    }

    private CreateNotificationEntityDTO toCreateDTO(CreateNotificationDTO dto) {
        return createDTOMapper
                .map(dto)
                .uuid(UUID.randomUUID())
                .sentAt(DateTimeUtils.nowAsLocalDateTime())
                .read(false)
                .build();
    }
}
