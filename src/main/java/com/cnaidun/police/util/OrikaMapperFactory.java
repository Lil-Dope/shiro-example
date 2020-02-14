package com.cnaidun.police.util;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.List;

/**
 * 描    述:   对象复制工具类
 * 类    名： OrikaMapperFactory
 */
public class OrikaMapperFactory {

    public static final MapperFactory mapperFactory;

    public static final MapperFacade mapper;

    static{
        mapperFactory=new DefaultMapperFactory.Builder().build();
        mapper=mapperFactory.getMapperFacade();
    }

    /**
     * 通用bean copy方法
     * 传入源对象和拷贝目标对象
     *
     * @param source
     * @param destination
     * @param <A>
     * @param <B>
     * @return
     */
    public static <A, B> B map(A source, B destination) {
        mapper.map(source, destination);
        return destination;
    }

    /**
     * 通用bean copy方法
     * 传入源对象和拷贝目标对象Class
     *
     * @param source
     * @param destination
     * @param <A>
     * @param <B>
     * @return
     */
    public static <A, B> B map(A source, Class<B> destination) {
        return mapper.map(source, destination);
    }

    /**
     *  Maps the source Iterable into a new List parameterized by destinationClass
     * @param source
     * @param destinationType
     * @return
     */
    public static <D> List<D> mapAsList(List source, Class<D> destinationType) {
        return mapper.mapAsList(source, destinationType);
    }
}
