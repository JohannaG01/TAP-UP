package com.johannag.tapup.notifications.application.mappers;

import com.johannag.tapup.globals.application.utils.DateTimeUtils;
import com.johannag.tapup.notifications.application.dtos.CreateNotificationDTO;
import com.johannag.tapup.notifications.application.dtos.FindNotificationsDTO;
import com.johannag.tapup.notifications.domain.dtos.CreateNotificationEntityDTO;
import com.johannag.tapup.notifications.domain.dtos.FindNotificationEntitiesDTO;
import com.johannag.tapup.notifications.presentation.dtos.queries.FindNotificationsQuery;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class NotificationApplicationMapperImpl implements NotificationApplicationMapper {

    private final TypeMap<CreateNotificationDTO, CreateNotificationEntityDTO.Builder> createDTOMapper;
    private final TypeMap<FindNotificationsQuery, FindNotificationsDTO.Builder> findDTOMapper;
    private final TypeMap<FindNotificationsDTO, FindNotificationEntitiesDTO.Builder> findEntitiesDTOMapper;


    public NotificationApplicationMapperImpl() {
        createDTOMapper = builderTypeMapper(CreateNotificationDTO.class, CreateNotificationEntityDTO.Builder.class);
        findDTOMapper = builderTypeMapper(FindNotificationsQuery.class, FindNotificationsDTO.Builder.class);
        findEntitiesDTOMapper = builderTypeMapper(FindNotificationsDTO.class,
                FindNotificationEntitiesDTO.Builder.class);
    }

    @Override
    public List<CreateNotificationEntityDTO> toCreateDTO(List<CreateNotificationDTO> dtos) {
        return dtos.stream()
                .map(this::toCreateDTO)
                .toList();
    }

    @Override
    public FindNotificationsDTO toFindDTO(FindNotificationsQuery query, UUID userUuid) {
        return findDTOMapper
                .map(query)
                .userUuid(userUuid)
                .build();
    }

    @Override
    public FindNotificationEntitiesDTO toFindEntitiesDTO(FindNotificationsDTO dto) {
        return findEntitiesDTOMapper
                .map(dto)
                .build();
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
