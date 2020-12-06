package com.github.yungyu16.framework.toolkit;

import org.modelmapper.*;
import org.modelmapper.Module;
import org.modelmapper.config.Configuration;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * CreatedDate: 2020/9/17
 * Author: songjialin
 */
public class BeanKit {
    private static ModelMapper modelMapper = new ModelMapper();

    private BeanKit() {
    }

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }

    public static <S, D> void addConverter(Converter<S, D> converter) {
        modelMapper.addConverter(converter);
    }

    public static <S, D> void addConverter(Converter<S, D> converter, Class<S> sourceType, Class<D> destinationType) {
        modelMapper.addConverter(converter, sourceType, destinationType);
    }

    public static <S, D> TypeMap<S, D> addMappings(PropertyMap<S, D> propertyMap) {
        return modelMapper.addMappings(propertyMap);
    }

    public static <S, D> TypeMap<S, D> createTypeMap(Class<S> sourceType, Class<D> destinationType) {
        return modelMapper.createTypeMap(sourceType, destinationType);
    }

    public static <S, D> TypeMap<S, D> createTypeMap(Class<S> sourceType, Class<D> destinationType, Configuration configuration) {
        return modelMapper.createTypeMap(sourceType, destinationType, configuration);
    }

    public static <S, D> TypeMap<S, D> createTypeMap(Class<S> sourceType, Class<D> destinationType, String typeMapName) {
        return modelMapper.createTypeMap(sourceType, destinationType, typeMapName);
    }

    public static <S, D> TypeMap<S, D> createTypeMap(Class<S> sourceType, Class<D> destinationType, String typeMapName, Configuration configuration) {
        return modelMapper.createTypeMap(sourceType, destinationType, typeMapName, configuration);
    }

    public static <S, D> TypeMap<S, D> createTypeMap(S source, Class<D> destinationType) {
        return modelMapper.createTypeMap(source, destinationType);
    }

    public static <S, D> TypeMap<S, D> createTypeMap(S source, Class<D> destinationType, Configuration configuration) {
        return modelMapper.createTypeMap(source, destinationType, configuration);
    }

    public static <S, D> TypeMap<S, D> createTypeMap(S source, Class<D> destinationType, String typeMapName) {
        return modelMapper.createTypeMap(source, destinationType, typeMapName);
    }

    public static <S, D> TypeMap<S, D> createTypeMap(S source, Class<D> destinationType, String typeMapName, Configuration configuration) {
        return modelMapper.createTypeMap(source, destinationType, typeMapName, configuration);
    }

    public static Configuration getConfiguration() {
        return modelMapper.getConfiguration();
    }

    public static <S, D> TypeMap<S, D> getTypeMap(Class<S> sourceType, Class<D> destinationType) {
        return modelMapper.getTypeMap(sourceType, destinationType);
    }

    public static <S, D> TypeMap<S, D> getTypeMap(Class<S> sourceType, Class<D> destinationType, String typeMapName) {
        return modelMapper.getTypeMap(sourceType, destinationType, typeMapName);
    }

    public static <S, D> TypeMap<S, D> typeMap(Class<S> sourceType, Class<D> destinationType) {
        return modelMapper.typeMap(sourceType, destinationType);
    }

    public static <S, D> TypeMap<S, D> typeMap(Class<S> sourceType, Class<D> destinationType, String typeMapName) {
        return modelMapper.typeMap(sourceType, destinationType, typeMapName);
    }

    public static <S, D> TypeMap<S, D> emptyTypeMap(Class<S> sourceType, Class<D> destinationType) {
        return modelMapper.emptyTypeMap(sourceType, destinationType);
    }

    public static Collection<TypeMap<?, ?>> getTypeMaps() {
        return modelMapper.getTypeMaps();
    }

    public static <D> D map(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public static <D> D map(Object source, Class<D> destinationType, String typeMapName) {
        return modelMapper.map(source, destinationType, typeMapName);
    }

    public static void map(Object source, Object destination) {
        modelMapper.map(source, destination);
    }

    public static void map(Object source, Object destination, String typeMapName) {
        modelMapper.map(source, destination, typeMapName);
    }

    public static <D> D map(Object source, Type destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public static <D> D map(Object source, Type destinationType, String typeMapName) {
        return modelMapper.map(source, destinationType, typeMapName);
    }

    public static void validate() {
        modelMapper.validate();
    }

    public static ModelMapper registerModule(Module module) {
        return modelMapper.registerModule(module);
    }
}
