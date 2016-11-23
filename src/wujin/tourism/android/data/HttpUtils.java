package wujin.tourism.android.data;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpUtils {
	public HttpUtils() {
		// TODO Auto-generated constructor stub
	}

	public static String getJsonContent(String url_path) {
		HttpGet httpRequest = new HttpGet(url_path);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = null;
				try {
					entity = httpResponse.getEntity();
				} catch (Exception e) {
				}
				if (entity != null) {
					strResult = EntityUtils.toString(entity);
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strResult;
	}

	public static String HttpPostData(String uri, JSONObject obj) {
		HttpPost post = new HttpPost(uri);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
			post.setEntity(new StringEntity(obj.toString(), HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = null;
				try {
					entity = httpResponse.getEntity();
				} catch (Exception e) {
				}
				if (entity != null) {
					return changeInputStream(entity.getContent());
				}
			}
		} catch (Exception e) {
		}
		return strResult;
	}

	public static String HttpPostData1(String uri, JSONObject obj) {
		HttpPost post = new HttpPost(uri);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
			// httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
			// 300000);
			post.setEntity(new StringEntity(obj.toString(), HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = null;
				try {
					entity = httpResponse.getEntity();
				} catch (Exception e) {
				}
				if (entity != null) {
					return changeInputStream(entity.getContent());
				}
			}
		} catch (Exception e) {
		}
		return strResult;
	}

	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			// conn.setConnectTimeout(30000);
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("Content-Type", "text/html");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String submitPostData(String url_path, List<NameValuePair> params) {
		HttpPost post = new HttpPost(url_path);
		String strResult = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = null;
				try {
					entity = httpResponse.getEntity();
				} catch (Exception e) {
				}
				if (entity != null) {
					return changeInputStream(entity.getContent());
				}
			}
		} catch (Exception e) {
		}
		return strResult;
	}

	public static String getInputStreamByPost(String urlPath, Map<String, Object> params) {
		try {
			StringBuffer sb = new StringBuffer();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
				sb.append("&");
			}
			String data = sb.deleteCharAt(sb.length() - 1).toString();
			URL url = new URL(urlPath);
			// 打开连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置提交方式
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			// post方式不能使用缓存
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			// 设置连接超时时间
			conn.setConnectTimeout(6 * 1000);
			// 配置本次连接的Content-Type，配置为application/x-www-form-urlencoded
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 维持长连接
			conn.setRequestProperty("Connection", "Keep-Alive");
			// 设置浏览器编码
			conn.setRequestProperty("Charset", "UTF-8");
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			// 将请求参数数据向服务器端发送
			dos.writeBytes(data);
			dos.flush();
			dos.close();
			if (conn.getResponseCode() == 200) {
				// 获得服务器端输出流
				return changeInputStream(conn.getInputStream());
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}

	private static String changeInputStream(InputStream inputStream) {
		String jsonString = "";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = 0;
		byte[] data = new byte[1024];
		try {
			while ((len = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}
			jsonString = new String(outputStream.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonString;
	}
}
