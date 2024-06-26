package kr.or.ddit.book.dao;

import java.util.List;
import java.util.Map;

public interface BookDAO {

	int insert(Map<String, Object> map);

	Map<String, Object> selectBook(Map<String, Object> map);

	int update(Map<String, Object> map);

	int delete(Map<String, Object> map);

	List<Map<String, Object>> selectBookList(Map<String, Object> map);

	
}
