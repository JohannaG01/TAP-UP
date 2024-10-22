package com.johannag.tapup.notifications.presentation.mappers;

import com.johannag.tapup.notifications.domain.models.NotificationModel;
import com.johannag.tapup.notifications.presentation.dtos.responses.NotificationResponseDTO;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static com.johannag.tapup.globals.application.utils.ModelMapperUtils.builderTypeMapper;

@Component
public class NotificationPresentationMapperImpl implements NotificationPresentationMapper {

    private final TypeMap<NotificationModel, NotificationResponseDTO.Builder> responseDTOMapper;

    public NotificationPresentationMapperImpl() {
        responseDTOMapper = builderTypeMapper(NotificationModel.class, NotificationResponseDTO.Builder.class);
    }

    @Override
    public Page<NotificationResponseDTO> toResponseDTO(Page<NotificationModel> models) {
        return models.map(this::toResponseDTO);
    }

    private NotificationResponseDTO toResponseDTO(NotificationModel model) {
        return responseDTOMapper
                .map(model)
                .build();
    }
}
