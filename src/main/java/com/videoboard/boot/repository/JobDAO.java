package com.videoboard.boot.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.videoboard.boot.domain.CatalogResultVO;
import com.videoboard.boot.domain.JobVO;
import com.videoboard.boot.domain.PagingVO;

@Mapper
public interface JobDAO {
	long insertJob(JobVO jvo);
	List<JobVO> selectJobs(@Param(value = "mno") long mno,
			@Param(value = "pgvo") PagingVO pgvo);
	int updateJob(JobVO jvo);
	int finishJob(long jobSeq);
	JobVO selectJob(long jobSeq);
	int updateProgress(@Param(value = "progress") String progress, 
						@Param(value = "vno") long vno);
	int selectTotalCount(long mno);
	List<JobVO> selectJobsByVno(long vno);
}
