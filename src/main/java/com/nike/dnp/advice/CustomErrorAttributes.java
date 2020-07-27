package com.nike.dnp.advice;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Class Custom error attributes.
 *
 * @author [오지훈]
 * @CreatedOn 2020. 7. 27. 오전 11:33:10
 * @Description
 */
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {


	/**
	 * Gets error attributes.
	 *
	 * @param webRequest        the web request
	 * @param includeStackTrace the include stack trace
	 * @return the error attributes
	 * @author [오지훈]
	 * @CreatedOn 2020. 7. 27. 오전 11:33:10
	 * @Description
	 */
	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
		Map<String, Object> errorAttributes = new LinkedHashMap<>();
		addStatus(errorAttributes, webRequest);
		addErrorMessage(errorAttributes, webRequest);
		addPath(errorAttributes, webRequest);
		return errorAttributes;
	}

	/**
	 * Add status.
	 *
	 * @param errorAttributes   the error attributes
	 * @param requestAttributes the request attributes
	 * @author [오지훈]
	 * @CreatedOn 2020. 7. 27. 오전 11:33:10
	 * @Description
	 */
	private void addStatus(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
		Integer status = getAttribute(requestAttributes, "javax.servlet.error.status_code");
		if(status == null){
			errorAttributes.put("status", 999);
			errorAttributes.put("error", "None");
			return;
		}
		errorAttributes.put("status", status);
	}


	/**
	 * Add error message.
	 *
	 * @param errorAttributes the error attributes
	 * @param webRequest      the web request
	 * @author [오지훈]
	 * @CreatedOn 2020. 7. 27. 오전 11:33:10
	 * @Description
	 */
	private void addErrorMessage(Map<String, Object> errorAttributes, WebRequest webRequest) {
		Throwable error = getError(webRequest);
		BindingResult result = extractBindingResult(error);
		if(result == null){
			errorAttributes.put("message", error.getMessage());
			return;
		}
		if(result.hasErrors()){
			errorAttributes.put("errors", result.getAllErrors());
			errorAttributes.put("message", "Validation failed for object='" + result.getObjectName() + "'. Error count: " + result.getErrorCount());
		}else{
			errorAttributes.put("message", "No errors");
		}
	}

	/**
	 * Extract binding result binding result.
	 *
	 * @param error the error
	 * @return the binding result
	 * @author [오지훈]
	 * @CreatedOn 2020. 7. 27. 오전 11:33:10
	 * @Description
	 */
	private BindingResult extractBindingResult(Throwable error) {
		if(error instanceof BindingResult){
			return (BindingResult) error;
		}
		if(error instanceof MethodArgumentNotValidException){
			return ((MethodArgumentNotValidException) error).getBindingResult();
		}
		return null;
	}

	/**
	 * Add path.
	 *
	 * @param errorAttributes   the error attributes
	 * @param requestAttributes the request attributes
	 * @author [오지훈]
	 * @CreatedOn 2020. 7. 27. 오전 11:33:10
	 * @Description
	 */
	private void addPath(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
		String path = getAttribute(requestAttributes, "javax.servlet.error.request_uri");
		if(path != null){
			errorAttributes.put("path", path);
		}
	}

	/**
	 * Gets attribute.
	 *
	 * @param <T>               the type parameter
	 * @param requestAttributes the request attributes
	 * @param name              the name
	 * @return the attribute
	 * @author [오지훈]
	 * @CreatedOn 2020. 7. 27. 오전 11:33:10
	 * @Description
	 */
	private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
		return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}
}
