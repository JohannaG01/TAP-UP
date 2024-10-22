package com.johannag.tapup.notifications.domain.mappers;

import com.johannag.tapup.notifications.domain.dtos.CreateNotificationEntityDTO;
import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.notifications.infrastructure.db.entities.NotificationEntity;
import com.johannag.tapup.users.infrastructure.db.entities.UserEntity;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class NotificationDomainMapperImpl implements NotificationDomainMapper {

    private final TypeMap<CreateNotificationEntityDTO, NotificationEntity.Builder> entityMapper;
    private final TypeMap<NotificationEntity, NotificationModel.Builder> modelMapper;


    public NotificationDomainMapperImpl() {
        entityMapper = builderTypeMapper(CreateNotificationEntityDTO.class, NotificationEntity.Builder.class);
        modelMapper = builderTypeMapper(NotificationEntity.class, NotificationModel.Builder.class);
    }

    @Override
    public List<NotificationEntity> toEntity(List<CreateNotificationEntityDTO> dtos, List<UserEntity> users) {

        Map<UUID, UserEntity> usersByUuid = users.stream()
                .collect(Collectors.toMap(UserEntity::getUuid, Function.identity()));

        return dtos.stream()
                .map(dto -> toEntity(dto, usersByUuid.get(dto.getUserUuid())))
                .toList();
    }

    @Override
    public List<NotificationModel> toModel(List<NotificationEntity> entities) {
        return List.of();
    }

    private NotificationEntity toEntity(CreateNotificationEntityDTO dto, UserEntity user) {
        return entityMapper
                .map(dto)
                .user(user)
                .build();
    }

    private NotificationModel toModel(NotificationEntity entity) {
        return modelMapper
                .map(entity)
                .build();
    }
}
