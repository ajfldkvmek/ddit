package kr.or.ddit.free.service;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.FreeVO;
import kr.or.ddit.vo.PaginationInfoVO;

public interface IFreeService {

	ServiceResult insertFree(FreeVO freeVO);

	int selectFreeCount(PaginationInfoVO<FreeVO> pagingVO);

	List<FreeVO> selectFreeList(PaginationInfoVO<FreeVO> pagingVO);

	FreeVO selectFree(int freeNo);

	ServiceResult updateFree(FreeVO freeVO);

	ServiceResult deleteFree(int freeNo);

}