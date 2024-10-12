package com.johannag.tapup.horses.application.useCases;

import com.johannag.tapup.globals.infrastructure.utils.Logger;
import com.johannag.tapup.horses.application.dtos.FindHorsesDTO;
import com.johannag.tapup.horses.domain.models.HorseModel;
import com.johannag.tapup.horses.infrastructure.db.adapters.HorseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindHorsesUseCase {

    private static final Logger logger = Logger.getLogger(FindHorsesUseCase.class);
    private final HorseRepository horseRepository;

    public Page<HorseModel> execute(FindHorsesDTO dto){
        logger.info("Starting findHorses process");

        Page<HorseModel> horses = horseRepository.findAll(dto);

        logger.info("Finished findHorses process");
        return horses;
    }
}
