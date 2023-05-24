package com.backend.project.system.fiter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

public class DecodeRequestWrapper extends HttpServletRequestWrapper {

	private Map<String,String> params;

	/**
	 * Creates a ServletRequest adaptor wrapping the given request object.
	 *
	 * @param request The request to wrap
	 * @throws IllegalArgumentException if the request is null
	 */
	public DecodeRequestWrapper(HttpServletRequest request, Map map) {
		super(request);
		this.params = map;
	}

	@Override
	public Map getParameterMap(){
		return this.params;
	}

	@Override
	public Enumeration<String> getParameterNames(){
		return Collections.enumeration(this.params.keySet());
	}

	/*@Override
	public String[] getParameterValues(String name) {
		return this.params.get(name);
	}*/

	@Override
	public String getParameter(String name) {
		return this.params.get(name);
	}
}
