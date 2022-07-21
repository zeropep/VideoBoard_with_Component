package com.videoboard.boot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.videoboard.boot.domain.CatalogResultVO;

@Mapper
public interface CatalogerDAO {
	int insertCatalogerResult(CatalogResultVO resultvo);
	List<CatalogResultVO> selectResults(long vno);
}
