package com.backend.project.system.fiter;

import com.backend.common.utils.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DecodeParamWrapper extends HttpServletRequestWrapper {

	private byte[] body;
	public DecodeParamWrapper(HttpServletRequest request, String jsonData) throws IOException {
		super(request);
		//StreamUtil.readBytes(request.getReader(), "utf-8");
		//由于request并没有提供现成的获取json字符串的方法，所以我们需要将body中的流转为字符串
		//String json = new String(StreamUtil.readText(request.getReader(), "utf-8"));
		//String json = new String(StreamUtil.readText(request.getReader()));
		//body = getData(jsonData).getBytes();
		if (StringUtils.isNotBlank(jsonData)){
			body = jsonData.getBytes();
		}
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	/**
	 * 在使用@RequestBody注解的时候，其实框架是调用了getInputStream()方法，所以我们要重写这个方法
	 * @return
	 * @throws IOException
	 */
	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);

		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {

			}

			@Override
			public int read() throws IOException {
				return bais.read();
			}
		};
	}

	/*private String getData(String json){
		if (!json.contains("\"data\"")){
			return "";
		}
		ObjectMapper mapper = new ObjectMapper();
		String data = null;
		try {
			JsonNode jsonNode = mapper.readTree(json);
			data = jsonNode.get("data").toString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}*/
}
