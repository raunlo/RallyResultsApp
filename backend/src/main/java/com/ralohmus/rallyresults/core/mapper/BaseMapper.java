package com.ralohmus.rallyresults.core.mapper;



/**
 * Base Mapper
 * @param <FromT> Object type from to map
 * @param <ToT> Object type to map
 */
public interface BaseMapper<FromT, ToT> {
    ToT map (FromT fromT);
}

