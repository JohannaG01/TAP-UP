package com.johannag.tapup.bets.presentation.mappers;

import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.presentation.dtos.responses.BetResponseDTO;
import org.springframework.data.domain.Page;

public interface BetPresentationMapper {

    /**
     * Converts a {@link BetModel} instance into a {@link BetResponseDTO}.
     *
     * @param model the {@link BetModel} instance to convert
     * @return a {@link BetResponseDTO} representation of the provided {@link BetModel}
     */
    BetResponseDTO toResponseDTO(BetModel model);

    /**
     * Converts a {@link Page} of {@link BetModel} to a {@link Page} of {@link BetResponseDTO}.
     *
     * <p>This method takes a paginated list of bet models and maps them to a paginated list
     * of response DTOs, which can be used to return data to the client.</p>
     *
     * @param models the {@link Page} of {@link BetModel} objects to convert
     * @return a {@link Page} of {@link BetResponseDTO} representing the converted models,
     * or an empty page if the input page is null or empty
     */
    Page<BetResponseDTO> toResponseDTO(Page<BetModel> models);
}
