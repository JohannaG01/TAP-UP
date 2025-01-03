package com.johannag.tapup.bets.presentation.mappers;

import com.johannag.tapup.bets.domain.models.BetModel;
import com.johannag.tapup.bets.domain.models.BetStatisticsModel;
import com.johannag.tapup.bets.domain.models.BetSummaryModel;
import com.johannag.tapup.bets.presentation.dtos.responses.BetResponseDTO;
import com.johannag.tapup.bets.presentation.dtos.responses.BetStatisticsDTO;
import com.johannag.tapup.bets.presentation.dtos.responses.BetSummaryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

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

    /**
     * Converts a list of {@link BetSummaryModel} instances to a list of {@link BetSummaryDTO} instances.
     *
     * @param models the list of {@link BetSummaryModel} to convert
     * @return a list of {@link BetSummaryDTO} instances representing the corresponding models
     */
    List<BetSummaryDTO> toResponseDTO(List<BetSummaryModel> models);

    /**
     * Converts a {@link BetStatisticsModel} to a {@link BetStatisticsDTO}.
     *
     * <p>This method transforms the data model containing betting statistics into a
     * Data Transfer Object (DTO) suitable for sending as a response in a web service.
     * The DTO may contain only the relevant fields required by the client,
     * ensuring a clean and efficient data exchange.</p>
     *
     * @param model the {@link BetStatisticsModel} containing the betting statistics to be converted
     * @return a {@link BetStatisticsDTO} representing the converted betting statistics
     */
    BetStatisticsDTO toResponseDTO(BetStatisticsModel model);
}
