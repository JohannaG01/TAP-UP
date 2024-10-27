package com.johannag.tapup.globals.application.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;

/**
 * Utility class for ModelMapper configurations and operations.
 */
public class ModelMapperUtils {

    private static final ModelMapper modelMapper = new ModelMapper();

    /**
     * Creates a TypeMap for mapping between a source class and a destination builder class.
     *
     * @param sourceClass             the class of the source object to be mapped
     * @param destinationBuilderClass the class of the destination builder object
     * @param <Source>                the type of the source object
     * @param <DestinationBuilder>    the type of the destination builder object
     * @return a TypeMap for the specified source and destination builder classes
     */
    public static <Source, DestinationBuilder> TypeMap<Source, DestinationBuilder> builderTypeMapper(
            Class<Source> sourceClass,
            Class<DestinationBuilder> destinationBuilderClass
    ) {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setAmbiguityIgnored(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        Configuration builderConfiguration = modelMapper.getConfiguration().copy()
                .setDestinationNameTransformer(NameTransformers.builder())
                .setDestinationNamingConvention(NamingConventions.builder());

        return modelMapper.createTypeMap(sourceClass, destinationBuilderClass, builderConfiguration);
    }

}