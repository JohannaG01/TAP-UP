package com.johannag.tapup.globals.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;

public class ModelMapperUtils {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <Source, DestinationBuilder> TypeMap<Source, DestinationBuilder> builderTypeMapper(
            Class<Source> sourceClass,
            Class<DestinationBuilder> destinationBuilderClass
    ) {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        Configuration builderConfiguration = modelMapper.getConfiguration().copy()
                .setDestinationNameTransformer(NameTransformers.builder())
                .setDestinationNamingConvention(NamingConventions.builder());

        return modelMapper.createTypeMap(sourceClass, destinationBuilderClass, builderConfiguration);
    }

}