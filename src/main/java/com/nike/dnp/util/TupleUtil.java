package com.nike.dnp.util;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.DateTimePath;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * TupleUtil
 *
 * @author [윤태호]
 * @CreatedOn 2020. 7. 2. 오전 11:06:13
 * @Description
 */
@UtilityClass
@Slf4j
public class TupleUtil {

	/**
	 * List Tuple > List HashMap 변환 <br />
	 * key 는 exprs 값.<br />
	 * 조회에 DateTimePath 가 있을 경우 dateFormat 을 null이면 기본 Format("yyyy.mm.dd HH:mm")
	 * <a href="https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html">dateFormat 참고</a>
	 * - Pattern for Formatting and Parsing 참고
	 *
	 * @param tupleList  the tuple list
	 * @param dateFormat the date format
	 * @param exprs      the exprs
	 * @return the list
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 2. 오전 11:06:10
	 * @Description
	 */
	public static List<HashMap<String,Object>> listTupleToListHashMap(final List<Tuple> tupleList,
																	  final String dateFormat,
																	  final Expression<?>... exprs) {
		Iterator<Tuple> iterator = tupleList.iterator();
		List<HashMap<String, Object>> resultList = new ArrayList<>();
		while(iterator.hasNext()){
			Tuple temp = iterator.next();
			resultList.add(tupleToHashMap(temp,dateFormat,exprs));
		}
		return resultList;
	}

	/**
	 * List Tuple > List HashMap 변환 <br />
	 * key 는 exprs 값.<br />
	 *
	 * @param tupleList the tuple list
	 * @param exprs     the exprs
	 * @return the list
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 2. 오전 11:06:13
	 * @Description
	 */
	public static List<HashMap<String, Object>> listTupleToListHashMap(final List<Tuple> tupleList,
																	   final Expression<?>... exprs) {
		return listTupleToListHashMap(tupleList, null, exprs);
	}


	/**
	 * Tuple > HashMap  변경<br />
	 * key 는 exprs 값 <br />
	 * 조회에 DateTimePath 가 있을 경우 dateFormat 을 null이면 기본 Format("yyyy.mm.dd HH:mm")
	 * <a href="https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html">dateFormat 참고</a>
	 * - Pattern for Formatting and Parsing 참고
	 *
	 * @param tuple      the tuple
	 * @param dateFormat the date format
	 * @param exprs      the exprs
	 * @return the hash map
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 2. 오전 11:20:41
	 * @Description
	 */
	public static HashMap<String, Object> tupleToHashMap(final Tuple tuple,
														 final String dateFormat,
														 final Expression<?>... exprs) {
		HashMap<String, Object> result = new HashMap<>();
		for(Expression<?> expr : exprs){
			String key = StringUtils.getFilenameExtension(expr.toString());
			if(expr instanceof DateTimePath){
				String defaultFormat = "yyyy.MM.dd HH:mm";
				if(!ObjectUtils.isEmpty(dateFormat)){
					defaultFormat = dateFormat;
				}
				LocalDateTime date = (LocalDateTime) tuple.get(expr);
				result.put(key, date.format(DateTimeFormatter.ofPattern(defaultFormat)));
			}else{
				result.put(key, tuple.get(expr));
			}
		}
		return result;
	}

	/**
	 * Tuple > HashMap  변경<br />
	 * key 는 exprs 값 <br />
	 *
	 * @param tuple the tuple
	 * @param exprs the exprs
	 * @return the hash map
	 * @author [윤태호]
	 * @CreatedOn 2020. 7. 2. 오전 11:20:41
	 * @Description
	 */
	public static HashMap<String, Object> tupleToHashMap(final Tuple tuple,
														 final Expression<?>... exprs) {
		return tupleToHashMap(tuple,null,exprs);
	}

}
