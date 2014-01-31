package com.goraksh.http.client;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.goraksh.http.resource.KbLogin;

/**
 * 
 * @author niteshk
 * 
 */
public class KbBaseHttpClient {
	private String loginUser = "kbagent1";
	private String loginPwd = "egain123";

	protected static String KEY_X_EGAIN_HEADER = "X-egain-session";
	private String X_EGAIN_HEADER;
	private String baseUrl;
	private String loginPath;
	private String logoutPath;

	private static String base = "/system/ws/v12";

	public String getXEgainKey() {
		return KEY_X_EGAIN_HEADER;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public KbBaseHttpClient(String host, String port, String user, String pwd,
			String loginPath, String logoutPath) {
		this.baseUrl = "http://" + host + ":" + port + base;
		this.loginUser = user;
		this.loginPwd = pwd;
		this.loginPath = loginPath;
		this.logoutPath = logoutPath;
	}

	public static void main(String[] args) throws IOException {
		String path = "/kb/attachment/internal";
		String host = "ggnv64w4";
		String port = "9001";

		String loginUser = "kbagent1";
		String loginPwd = "egain123";

		String loginPath = "/user/login";

		String logoutPath = "/user/logout";

		KbBaseHttpClient client = new KbBaseHttpClient(host, port, loginUser,
				loginPwd, loginPath, logoutPath);

		client.login();

		client.logout();
	}

	public void login() throws IOException {

		KbLogin loginEntity = new KbLogin();
		loginEntity.setUserName(loginUser);
		loginEntity.setPassword(loginPwd);

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {

			String urlLogin = baseUrl + "/authentication";
			String loginXml = loginEntity.toString();

			HttpPost postLogin = new HttpPost(urlLogin + loginPath);
			postLogin.setHeader("Content-Type", "application/xml");

			postLogin.setEntity(new StringEntity(loginXml));

			CloseableHttpResponse response = httpclient.execute(postLogin);

			try {
				System.out.println(response.getStatusLine());

				assert ("204".equals(response.getStatusLine()));

				processHeaders(response);
				// HttpEntity entity2 = response.getEntity();
				// do something useful with the response body
				// and ensure it is fully consumed
				// EntityUtils.consume(entity2);

				// System.out.println(entity2.toString());
			} catch (Exception e) {
				e.printStackTrace();
				response.close();

			}

			// header
			// String egain_session = map.get(KEY_X_EGAIN_HEADER).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			httpclient.close();
		}

	}

	private Header[] headers;

	/**
	 * @throws IOException
	 * 
	 */
	public void login0() throws IOException {

		StringBuilder sb = new StringBuilder();
		sb.append(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
				.append("<login xmlns=\"http://bindings.egain.com/ws/model/v12/gen/platform\">")
				.append("<userName>").append(loginUser).append("</userName>")
				.append("<password>").append(loginPwd).append("</password>")
				.append("</login>");

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {

			String urlLogin = baseUrl + "/authentication";
			String loginXml = sb.toString();

			HttpPost postLogin = new HttpPost(urlLogin + loginPath);
			postLogin.setHeader("Content-Type", "application/xml");

			postLogin.setEntity(new StringEntity(loginXml));

			CloseableHttpResponse response = httpclient.execute(postLogin);

			try {
				System.out.println(response.getStatusLine());

				assert ("204".equals(response.getStatusLine()));

				processHeaders(response);

				// HttpEntity entity2 = response.getEntity();
				// do something useful with the response body
				// and ensure it is fully consumed
				// EntityUtils.consume(entity2);

				// System.out.println(entity2.toString());
			} catch (Exception e) {
				e.printStackTrace();
				response.close();

			}

			// header
			// String egain_session = map.get(KEY_X_EGAIN_HEADER).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			httpclient.close();
		}

	}

	private void processHeaders(CloseableHttpResponse response) {

		this.headers = response.getHeaders(KEY_X_EGAIN_HEADER);
		for (Header h : headers) {
			System.out.println(h.getValue());
			System.out.println(h.getName());

			System.out.println(" -------------------");

			if (KEY_X_EGAIN_HEADER.equals(h.getName()))
				setXEgainHeader(h.getValue());
		}
	}

	public void logout() throws IOException {

		StringBuilder sb = new StringBuilder();
		sb.append(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
				.append("<login xmlns=\"http://bindings.egain.com/ws/model/v12/gen/platform\">")
				.append("<userName>").append(loginUser).append("</userName>")
				.append("<password>").append(loginPwd).append("</password>")
				.append("</login>");

		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {

			String urlLogin = baseUrl + "/authentication";
			String loginXml = sb.toString();

			HttpDelete delete = new HttpDelete(urlLogin + logoutPath);
			delete.setHeader(KEY_X_EGAIN_HEADER, getXEgainHeader());

			CloseableHttpResponse response = httpclient.execute(delete);

			try {
				System.out.println(response.getStatusLine());

				assert ("204".equals(response.getStatusLine()));
				// HttpEntity entity2 = response.getEntity();
				// do something useful with the response body
				// and ensure it is fully consumed
				// EntityUtils.consume(entity2);

				// System.out.println(entity2.toString());
			} catch (Exception e) {
				e.printStackTrace();
				response.close();

			}

			// header
			// String egain_session = map.get(KEY_X_EGAIN_HEADER).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			httpclient.close();
		}

	}

	public void setXEgainHeader(String header) {
		this.X_EGAIN_HEADER = header;
	}

	public String getXEgainHeader() {
		return this.X_EGAIN_HEADER;
	}

}
