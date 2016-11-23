package wujin.tourism.android.data;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class GsonUtils {

	public enum JSON_TYPE {
		/** JSONObject */
		JSON_TYPE_OBJECT,
		/** JSONArray */
		JSON_TYPE_ARRAY,
		/** 不是JSON格式的字符串 */
		JSON_TYPE_ERROR
	}

	public final static String Tag = "JsonUtils";
	private static int connectTimeout = 30 * 1000;
	private static int readTimeout = 30 * 1000;

	public static String getHttpServiceUrl() {
		return "http://218.93.12.210:8080/";
	}

	public static String SendMessage(Context context, String Url, String msg) throws Exception {
		String result = "";
		URL url;
		try {
			DialogUtils.showDialog(context, "", "正在请求数据...");
			url = new URL(Url);

			HttpURLConnection urlConn;

			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(connectTimeout);
			urlConn.setReadTimeout(readTimeout);

			urlConn.setDoOutput(true);
			urlConn.setDoInput(true);
			urlConn.setRequestMethod("POST");
			urlConn.setUseCaches(false);
			urlConn.setInstanceFollowRedirects(true);

			DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
			out.write(msg.getBytes("UTF-8"));
			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			String inputLine = null;
			while ((inputLine = reader.readLine()) != null) {
				result += inputLine;
			}

			reader.close();
			urlConn.disconnect();
			// Log.v("wj", "JSON_result===>" + result);
			DialogUtils.DismissProgressDialog1();
			System.out.println("JSON_result===>" + result);
			return result;
			// return analyJson(context, result);
		} catch (Exception ex) {
			Log.d(Tag, ex.toString());
			// ToastUtil.showLong(context, "连接服务器时发生异常：" + ex.getMessage());
			DialogUtils.DismissProgressDialog1();
			Toast toast = Toast.makeText(context, "连接服务器时发生异常：" + ex.getMessage(), Toast.LENGTH_SHORT);
			toast.show();
			return ex.getMessage();
		}
	}

	// /**
	// * 发送请求并接收返回数据 根据sucess等判断
	// */
	// public static String SendMessage1(Context context, String Url, String
	// msg)
	// throws Exception {
	// String result = "";
	// Log.v("wj", "JSON_reqest---->" + msg);
	// URL url;
	// try {
	// url = new URL(Url);
	//
	// HttpURLConnection urlConn;
	//
	// urlConn = (HttpURLConnection) url.openConnection();
	// urlConn.setConnectTimeout(connectTimeout);
	// urlConn.setReadTimeout(readTimeout);
	//
	// urlConn.setDoOutput(true);
	// urlConn.setDoInput(true);
	// urlConn.setRequestMethod("POST");
	// urlConn.setUseCaches(false);
	// urlConn.setInstanceFollowRedirects(true);
	//
	// DataOutputStream out = new DataOutputStream(
	// urlConn.getOutputStream());
	// out.write(msg.getBytes("UTF-8"));
	// out.flush();
	// out.close();
	//
	// BufferedReader reader = new BufferedReader(new InputStreamReader(
	// urlConn.getInputStream()));
	// String inputLine = null;
	// while ((inputLine = reader.readLine()) != null) {
	// result += inputLine;
	// }
	//
	// reader.close();
	// urlConn.disconnect();
	// Log.v("wj", "JSON_result===>" + result);
	//
	// return analyJson(context, result);
	// } catch (Exception ex) {
	// Log.d(Tag, ex.toString());
	// // ToastUtil.showLong(context, "连接服务器时发生异常：" + ex.getMessage());
	// Toast toast = Toast.makeText(context,
	// "连接服务器时发生异常：" + ex.getMessage(), Toast.LENGTH_SHORT);
	// toast.show();
	// return ex.getMessage();
	// }
	// }

	// /**
	// * 解析JSON,返回data
	// *
	// * @param json
	// */
	// public static String analyJson(Context context, String json)
	// throws Exception {
	// String result = "";
	// Pubu dto = (Pubu) jsonToObj(Pubu.class, json);
	// if (dto.isSuccess()) {
	// result = dto.getData().toString();
	// } else {
	// result = "返回错误";
	// // result = dto.getMessage();
	// // ToastUtil.showLong(context, dto.getMessage());
	// }
	// return result;
	// }

	/**
	 * 组装请求的JSON,根据数组条件
	 */
	public static String combinJson(String action, String params) {
		JsonObject comjson = new JsonObject();
		try {
			comjson.addProperty("Action", action);
			comjson.addProperty("Params", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comjson.toString();
	}

	/**
	 * 组装请求的JSON,根据数组条件
	 */
	public static String combinJsonByArray(String action, String[] keys, String[] values) {
		JsonObject comjson = new JsonObject();
		try {
			comjson.addProperty("Action", action);
			JsonObject bodyjson = new JsonObject();
			for (int i = 0; i < keys.length; i++) {
				bodyjson.addProperty(keys[i], values[i]);
			}
			comjson.addProperty("Params", bodyjson.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comjson.toString();
	}

	/**
	 * 获取GSON对象
	 * 
	 * @return
	 */
	private static Gson getGsonObj() {
		Gson gson = new Gson();
		return gson;
		// GsonBuilder builder = new GsonBuilder();
		// // 不转换没�?@Expose 注解的字�?
		// builder.excludeFieldsWithoutExposeAnnotation();
		// Gson gson = builder.create();
		// return gson;
	}

	/**
	 * 对象转JSON
	 */
	public static String objToJson(Object obj) {

		return getGsonObj().toJson(obj);
	}

	/**
	 * JSON转对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object jsonToObj(Class obClass, String json) {
		Gson gson = getGsonObj();
		return gson.fromJson(json, obClass);
	}

	/**
	 * list对象转JSON
	 */
	public static String listToJson(List listBean) {
		Gson gson = getGsonObj();
		Type type = new TypeToken<List>() {
		}.getType(); // 指定集合对象属�?
		String listToJson = gson.toJson(listBean, type);
		return listToJson;
	}

	/**
	 * json转LIST Type type = new TypeToken<List<�?>(){}.getType()
	 */
	@SuppressWarnings("rawtypes")
	public static List jsonToList(String json, Type type) {
		Gson gson = getGsonObj();
		List listFromJson = gson.fromJson(json, type);
		return listFromJson;
	}

	/**
	 * HashMap转成json
	 */
	public static String hashMapToJson(Map<String, String> map) {
		Gson gson = getGsonObj();
		String mapToJson = gson.toJson(map);
		return mapToJson;
	}

	/**
	 * JSON转HashMap
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> jsonToMap(String json) {
		Gson gson = getGsonObj();
		Map<String, String> map = (Map<String, String>) gson.fromJson(json, new TypeToken<Map<String, String>>() {
		}.getType());
		return map;
	}

	/***
	 * 
	 * 获取JSON类型 判断规则 判断第一个字母是否为{或[ 如果都不是则不是�?��JSON格式的文�?
	 * 
	 * @param str
	 * @return
	 */
	public static JSON_TYPE getJSONType(String str) {
		if (str == "") {
			return JSON_TYPE.JSON_TYPE_ERROR;
		}

		final char[] strChar = str.substring(0, 1).toCharArray();
		final char firstChar = strChar[0];
		if (firstChar == '{') {
			return JSON_TYPE.JSON_TYPE_OBJECT;
		} else if (firstChar == '[') {
			return JSON_TYPE.JSON_TYPE_ARRAY;
		} else {
			return JSON_TYPE.JSON_TYPE_ERROR;
		}
	}
}
