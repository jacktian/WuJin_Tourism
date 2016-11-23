package wujin.tourism.android.common;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import wujin.tourism.android.childFragment.pubu.DuitangInfo;
import wujin.tourism.android.tpc.Wzglbean;
import wujin.tourism.android.tpc.jsondatabean;
import wujin.tourism.android.tpc.jsonsubdata;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class GsonTools {
	static ArrayList<HashMap<String, Object>> userlist, detaillist;

	public GsonTools() {
	}

	public static ArrayList<mainnewsbean> retrunmainnewlist(String jsonData) throws Exception {
		ArrayList<mainnewsbean> spotlsit = new ArrayList<mainnewsbean>();
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				mainnewsbean mainnewsbean = new mainnewsbean();
				reader.beginObject();
				while (reader.hasNext()) {
					String tagName = reader.nextName();
					if (tagName.equals("id"))
						mainnewsbean.setId(reader.nextString());
					else if (tagName.equals("title"))
						mainnewsbean.setTitle(reader.nextString());
					else if (tagName.equals("phone"))
						mainnewsbean.setTime(reader.nextString());
					else {
						reader.skipValue();
					}
				}
				reader.endObject();
				spotlsit.add(mainnewsbean);
			}
			reader.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return spotlsit;
	}

	public static ArrayList<jsondatabean> readdatajson(String jsonData) throws Exception {
		ArrayList<jsondatabean> spotlsit = new ArrayList<jsondatabean>();
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				jsondatabean wzglbean = new jsondatabean();
				reader.beginObject();
				while (reader.hasNext()) {
					String tagName = reader.nextName();
					if (tagName.equals("id"))
						wzglbean.setId(reader.nextString());
					else if (tagName.equals("hotsposts") && reader.peek() == JsonToken.BEGIN_ARRAY) {
						reader.beginArray();
						ArrayList<jsonsubdata> spotjsonsubdata = new ArrayList<jsonsubdata>();
						while (reader.hasNext()) {
							reader.beginObject();
							jsonsubdata jsonsubdata = new jsonsubdata();
							while (reader.hasNext()) {
								String tagName1 = reader.nextName();
								if (tagName1.equals("id"))
									jsonsubdata.setSubid(reader.nextString());
								else if (tagName1.equals("point")) {
									jsonsubdata.setPoint(reader.nextString());
								} else {
									reader.skipValue();
								}
							}
							spotjsonsubdata.add(jsonsubdata);
							reader.endObject();
						}
						wzglbean.setListObject(spotjsonsubdata);
						reader.endArray();
					} else {
						reader.skipValue();
					}
				}
				reader.endObject();
				spotlsit.add(wzglbean);
			}
			reader.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
			}
		}
		return spotlsit;
	}

	public static ArrayList<Wzglbean> readspotinformation(String jsonData) throws Exception {
		ArrayList<Wzglbean> spotlsit = new ArrayList<Wzglbean>();
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				Wzglbean wzglbean = new Wzglbean();
				reader.beginObject();
				while (reader.hasNext()) {
					String tagName = reader.nextName();
					if (tagName.equals("album"))
						wzglbean.setAlbum(reader.nextString());
					else if (tagName.equals("id"))
						wzglbean.setId(reader.nextString());
					else if (tagName.equals("intro"))
						wzglbean.setIntro(reader.nextString());
					else if (tagName.equals("name"))
						wzglbean.setName(reader.nextString());
					else if (tagName.equals("images") && reader.peek() == JsonToken.BEGIN_ARRAY) {
						reader.beginArray();
						String strlist = "";
						while (reader.hasNext()) {
							strlist = strlist + reader.nextString() + ",";
						}
						wzglbean.setImagelist(strlist);
						reader.endArray();
					} else {
						reader.skipValue();
					}
				}
				reader.endObject();
				String[] stringlist = { "11", "29", "30", "31", "32", "9", "26" };
				for (int i = 0; i < stringlist.length; i++) {
					if (wzglbean.getId().equals(stringlist[i])) {
						spotlsit.add(wzglbean);
						break;
					}
				}
			}
			reader.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return spotlsit;
	}

	public static ArrayList<HashMap<String, Object>> listKeyMaps(String jsonString) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
			}.getType());
		} catch (Exception e) {
		}
		return list;
	}

	public static int posting(String jsonData) throws Exception {
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		int status = -1;
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String tagName = reader.nextName();
				if (tagName.equals("data")) {
					reader.beginObject();
					while (reader.hasNext()) {
						String tagName2 = reader.nextName();
						if (tagName2.equals("status")) {
							status = Integer.parseInt(reader.nextString());
						} else {
							reader.skipValue();
						}
					}
					reader.endObject();
				} else {
					reader.skipValue();
				}
			}
			reader.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	public static Boolean postingstatus(String jsonData) throws Exception {
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		Boolean status = false;
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String tagName = reader.nextName();
				if (tagName.equals("status")) {
					status = reader.nextBoolean();
				} else {
					reader.skipValue();
				}
			}
			reader.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	public static HashMap<String, Object> readloginJsonData(String jsonData) throws Exception {
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		HashMap<String, Object> item = new HashMap<String, Object>();
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String tagName = reader.nextName();
				item.put("user.tel", "");
				if (tagName.equals("status")) {
					item.put("login_sate", reader.nextInt());
				} else if (tagName.equals("data")) {
					reader.beginObject();
					while (reader.hasNext()) {
						String tagName2 = reader.nextName();
						if (tagName2.equals("id")) {
							item.put("user.id", reader.nextString());
						} else if (tagName2.equals("username")) {
							item.put("user.name", reader.nextString());
						} else if (tagName2.equals("tel") && reader.peek() != JsonToken.NULL) {
							item.put("user.tel", reader.nextString());
						} else if (tagName2.equals("grade")) {
							item.put("user.grade", reader.nextString());
						} else if (tagName2.equals("s_profile_image_url")) {
							item.put("user.s_profile_image_url", reader.nextString());
							;
						} else {
							reader.skipValue();
						}
					}
					reader.endObject();
				} else {
					reader.skipValue();
				}
			}
			reader.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return item;
	}

	public static int readregisterJsonData(String jsonData) throws Exception {
		int m_ndata = 0;
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String tagName = reader.nextName();
				if (tagName.equals("status")) {
					m_ndata = reader.nextInt();
				} else {
					reader.skipValue();
				}
			}
			reader.endObject();
		} catch (Exception e) {
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return m_ndata;
	}

	public static ArrayList<HashMap<String, Object>> readregisterJsonData1(String jsonData) throws Exception {
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		ArrayList<HashMap<String, Object>> gsomList = new ArrayList<HashMap<String, Object>>();
		try {
			reader.beginObject();
			HashMap<String, Object> item = new HashMap<String, Object>();
			while (reader.hasNext()) {
				String tagName = reader.nextName();
				if (tagName.equals("title")) {
					item.put("title", reader.nextString());
				} else if (tagName.equals("tid")) {
					item.put("tid", reader.nextString());
				} else if (tagName.equals("type")) {
					item.put("type", reader.nextString());
				} else {
					reader.skipValue();
				}
			}
			gsomList.add(item);
			reader.endObject();
		} catch (Exception e) {
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return gsomList;
	}

	public static int readpersonreplyJsonData(String jsonData) throws Exception {
		int m_ndata = 0;
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				while (reader.hasNext()) {
					String tagName = reader.nextName();
					if (tagName.equals("uid")) {
						m_ndata = Integer.parseInt(reader.nextString());
					} else {
						reader.skipValue();
					}
				}
				reader.endObject();
			}
			reader.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return m_ndata;
	}

	public static ArrayList<HashMap<String, Object>> readJsonData(String jsonData) throws Exception {
		userlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> item;
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				item = new HashMap<String, Object>();
				item.put("s_image_url", "");
				item.put("fid", "");
				item.put("user.tel", "");
				while (reader.hasNext()) {
					String tagName = reader.nextName();
					if (tagName.equals("tid")) {
						item.put("id", reader.nextString());
					} else if (tagName.equals("fid") && reader.peek() != JsonToken.NULL) {
						item.put("fid", reader.nextString());
					} else if (tagName.equals("title")) {
						item.put("title", reader.nextString());
					} else if (tagName.equals("lastreplydate")) {
						item.put("created_at", reader.nextString());
					} else if (tagName.equals("replies_count")) {
						item.put("replies_count", reader.nextString());
					} else if (tagName.equals("views_count")) {
						item.put("views_count", reader.nextString());
					} else if (tagName.equals("s_image_url") && reader.peek() == JsonToken.BEGIN_ARRAY) {
						reader.beginArray();
						while (reader.hasNext()) {
							reader.beginObject();
							while (reader.hasNext()) {
								String tagName3 = reader.nextName();
								if (tagName3.equals("bigpath")) {
									item.put("s_image_url", reader.nextString());
								} else {
									reader.skipValue();
								}
							}
							reader.endObject();
						}
						reader.endArray();
					} else if (tagName.equals("user")) {
						reader.beginObject();
						while (reader.hasNext()) {
							String tagName2 = reader.nextName();
							if (tagName2.equals("id")) {
								item.put("user.id", reader.nextString());
							} else if (tagName2.equals("username")) {
								item.put("user.name", reader.nextString());
							} else if (tagName2.equals("tel") && reader.peek() != JsonToken.NULL) {
								item.put("user.tel", reader.nextString());
							} else if (tagName2.equals("grade")) {
								item.put("user.grade", reader.nextString());
							} else if (tagName2.equals("s_profile_image_url")) {
								item.put("user.s_profile_image_url", reader.nextString());
								;
							} else {
								reader.skipValue();
							}
						}
						reader.endObject();
					} else {
						reader.skipValue();
					}
				}
				userlist.add(item);
				reader.endObject();
			}
			reader.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userlist;
	}

	public static ArrayList<HashMap<String, Object>> readJsonDatahuan(String jsonData) throws Exception {
		userlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> item;
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				item = new HashMap<String, Object>();
				item.put("s_image_url", "");
				item.put("fid", "");
				item.put("user.tel", "");
				while (reader.hasNext()) {
					String tagName = reader.nextName();
					if (tagName.equals("tid")) {
						item.put("id", reader.nextString());
					} else if (tagName.equals("fid") && reader.peek() != JsonToken.NULL) {
						item.put("fid", reader.nextString());
					} else if (tagName.equals("title")) {
						item.put("title", reader.nextString());
					} else if (tagName.equals("created_at")) {
						item.put("created_at", reader.nextString());
					} else if (tagName.equals("replies_count")) {
						item.put("replies_count", reader.nextString());
					} else if (tagName.equals("views_count")) {
						item.put("views_count", reader.nextString());
					} else if (tagName.equals("s_image_url")) {
						item.put("s_image_url", reader.nextString());
					} else if (tagName.equals("user")) {
						reader.beginObject();
						while (reader.hasNext()) {
							String tagName2 = reader.nextName();
							if (tagName2.equals("id")) {
								item.put("user.id", reader.nextString());
							} else if (tagName2.equals("username")) {
								item.put("user.name", reader.nextString());
							} else if (tagName2.equals("tel") && reader.peek() != JsonToken.NULL) {
								item.put("user.tel", reader.nextString());
							} else if (tagName2.equals("grade")) {
								item.put("user.grade", reader.nextString());
							} else if (tagName2.equals("s_profile_image_url")) {
								item.put("user.s_profile_image_url", reader.nextString());
								;
							} else {
								reader.skipValue();
							}
						}
						reader.endObject();
					} else {
						reader.skipValue();
					}
				}
				userlist.add(item);
				reader.endObject();
			}
			reader.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userlist;
	}

	public static ArrayList<HashMap<String, Object>> readcasualookJsonData(String jsonData) throws Exception {
		userlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> item;
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				item = new HashMap<String, Object>();
				item.put("s_image_url", "");
				item.put("typeid", "");
				item.put("user.tel", "");
				while (reader.hasNext()) {
					String tagName = reader.nextName();
					if (tagName.equals("tid")) {
						item.put("id", reader.nextString());
					} else if (tagName.equals("fid")) {
						item.put("fid", reader.nextString());
					}
					// else if(tagName.equals("authorid"))
					// {
					// item.put("typeid",reader.nextString());
					// }
					else if (tagName.equals("title")) {
						item.put("title", reader.nextString());
					} else if (tagName.equals("lastreplydate")) {
						item.put("created_at", reader.nextString());
					} else if (tagName.equals("replies_count")) {
						item.put("replies_count", reader.nextString());
					} else if (tagName.equals("views_count")) {
						item.put("views_count", reader.nextString());
					} else if (tagName.equals("s_image_url") && reader.peek() == JsonToken.BEGIN_ARRAY) {
						int pathString = 0;
						reader.beginArray();
						while (reader.hasNext()) {
							reader.beginObject();
							while (reader.hasNext()) {
								String tagName3 = reader.nextName();
								if (tagName3.equals("bigpath")) {
									if (pathString == 0)
										item.put("s_image_url", reader.nextString());
									else
										reader.nextString();
									pathString++;
								} else {
									reader.skipValue();
								}
							}
							reader.endObject();
						}
						reader.endArray();
					} else if (tagName.equals("user")) {
						reader.beginObject();
						while (reader.hasNext()) {
							String tagName2 = reader.nextName();
							if (tagName2.equals("id")) {
								item.put("user.id", reader.nextString());
							} else if (tagName2.equals("username")) {
								item.put("user.name", reader.nextString());
							} else if (tagName2.equals("tel") && reader.peek() != JsonToken.NULL) {
								item.put("user.tel", reader.nextString());
							} else if (tagName2.equals("grade")) {
								item.put("user.grade", reader.nextString());
							} else if (tagName2.equals("s_profile_image_url")) {
								item.put("user.s_profile_image_url", reader.nextString());
								;
							} else {
								reader.skipValue();
							}
						}
						reader.endObject();
					} else {
						reader.skipValue();
					}
				}
				userlist.add(item);
				reader.endObject();
			}
			reader.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userlist;
	}

	public static ArrayList<HashMap<String, Object>> myposting(String jsonData) throws Exception {
		userlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> item;
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				item = new HashMap<String, Object>();
				item.put("s_image_url", "");
				while (reader.hasNext()) {
					String tagName = reader.nextName();
					if (tagName.equals("tid")) {
						item.put("id", reader.nextString());
					} else if (tagName.equals("fid")) {
						item.put("fid", reader.nextString());
					} else if (tagName.equals("flag")) {
						item.put("typeid", reader.nextString());
					} else if (tagName.equals("title")) {
						item.put("title", reader.nextString());
					} else if (tagName.equals("created_at")) {
						item.put("created_at", reader.nextString());
					} else if (tagName.equals("replies_count")) {
						item.put("replies_count", reader.nextString());
					} else if (tagName.equals("views_count")) {
						item.put("views_count", reader.nextString());
					}
					// else if(tagName.equals("user"))
					// {
					// reader.beginObject();
					// while(reader.hasNext())
					// {
					// String
					// tagName2=reader.nextName();
					// if(tagName2.equals("s_profile_image_url"))
					// {
					// item.put("s_image_url","");
					// }
					//
					// else
					// {
					// reader.skipValue();
					// }
					// }
					// reader.endObject();
					// }
					else {
						reader.skipValue();
					}
				}
				userlist.add(item);
				reader.endObject();
			}
			reader.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userlist;
	}

	public static ArrayList<HashMap<String, Object>> mybackposting(String jsonData) throws Exception {
		userlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> item;
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				item = new HashMap<String, Object>();
				item.put("fid", "");
				item.put("s_image_url", "");
				item.put("typeid", "");
				while (reader.hasNext()) {
					String tagName = reader.nextName();
					if (tagName.equals("tid")) {
						item.put("id", reader.nextString());
					} else if (tagName.equals("title")) {
						item.put("title", reader.nextString());
					} else if (tagName.equals("created_at")) {
						item.put("created_at", reader.nextString());
					} else if (tagName.equals("replies_count")) {
						item.put("replies_count", reader.nextString());
					} else if (tagName.equals("views_count")) {
						item.put("views_count", reader.nextString());
					} else {
						reader.skipValue();
					}
				}
				userlist.add(item);
				reader.endObject();
			}
			reader.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userlist;
	}

	public static ArrayList<HashMap<String, Object>> readfriendsJsonData(String jsonData) throws Exception {
		userlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> item;
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				item = new HashMap<String, Object>();
				while (reader.hasNext()) {
					String tagName = reader.nextName();
					if (tagName.equals("uid")) {
						item.put("uid", reader.nextString());
					} else if (tagName.equals("friendgroupid")) {
						item.put("friendgroupid", reader.nextString());
					} else if (tagName.equals("friend")) {
						reader.beginObject();
						while (reader.hasNext()) {
							String tagName2 = reader.nextName();
							if (tagName2.equals("id")) {
								item.put("friendid", reader.nextString());
							} else if (tagName2.equals("username")) {
								item.put("friendusername", reader.nextString());
							} else if (tagName2.equals("tel") && reader.peek() != JsonToken.NULL) {
								item.put("friendtel", reader.nextString());
							} else if (tagName2.equals("grade")) {
								item.put("friendgrade", reader.nextString());
							} else if (tagName2.equals("s_profile_image_url")) {
								item.put("friends_profile_image_url", reader.nextString());
								;
							} else {
								reader.skipValue();
							}
						}
						reader.endObject();
					} else {
						reader.skipValue();
					}
				}
				userlist.add(item);
				reader.endObject();
			}
			reader.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userlist;
	}

	public static ArrayList<HashMap<String, Object>> readpoplistplate(String jsonData) throws Exception {
		userlist = new ArrayList<HashMap<String, Object>>();
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		HashMap<String, Object> item = null;
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				while (reader.hasNext()) {
					String tagName = reader.nextName();
					if (tagName.equals("subplates")) {
						reader.beginArray();
						while (reader.hasNext()) {
							reader.beginObject();
							item = new HashMap<String, Object>();
							while (reader.hasNext()) {
								String tagName2 = reader.nextName();
								if (tagName2.equals("fid")) {
									item.put("fid", reader.nextString());
								} else if (tagName2.equals("news")) {
									item.put("news", reader.nextString());
								} else {
									reader.skipValue();
								}
							}
							userlist.add(item);
							reader.endObject();
						}
						reader.endArray();
					} else {
						reader.skipValue();
					}
				}
				reader.endObject();
			}
			reader.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
			}
		}
		return userlist;
	}

	public static ArrayList<HashMap<String, Object>> readpersonJsonData(String jsonData) throws Exception {
		userlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> item;
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				while (reader.hasNext()) {
					String tagName = reader.nextName();
					if (tagName.equals("data")) {
						reader.beginArray();
						while (reader.hasNext()) {
							item = new HashMap<String, Object>();
							reader.beginObject();
							while (reader.hasNext()) {
								String tagName2 = reader.nextName();
								if (tagName2.equals("message")) {
									item.put("message", reader.nextString());
								} else if (tagName2.equals("subject")) {
									item.put("subject", reader.nextString());
								} else if (tagName2.equals("dateline")) {
									item.put("dateline", reader.nextString());
								} else if (tagName2.equals("uid")) {
									item.put("uid", reader.nextString());
								} else if (tagName2.equals("msgfrom")) {
									item.put("msgfrom", reader.nextString());
								} else if (tagName2.equals("msgfromid")) {
									item.put("msgfromid", reader.nextString());
								} else {
									reader.skipValue();
								}
							}
							reader.endObject();
							userlist.add(item);
						}
						reader.endArray();
					} else {
						reader.skipValue();
					}
				}
				reader.endObject();
			}
			reader.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userlist;
	}

	public static ArrayList<HashMap<String, Object>> readJsonDatadetail(String jsonData) throws Exception {
		ArrayList<HashMap<String, Object>> detaillist1 = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> item = null;
		String tid = "";
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String tagName = reader.nextName();
				if (tagName.equals("tid")) {
					tid = reader.nextString();
				} else if (tagName.equals("replyarray") && reader.peek() == JsonToken.BEGIN_ARRAY) {
					reader.beginArray();
					while (reader.hasNext() && reader.peek() == JsonToken.BEGIN_OBJECT) {
						reader.beginObject();
						item = new HashMap<String, Object>();
						item.put("tid", tid);
						item.put("user.tel", "");
						item.put("images_urls", "");
						while (reader.hasNext()) {
							String tagName2 = reader.nextName();
							if (tagName2.equals("pid")) {
								item.put("id", reader.nextString());
							} else if (tagName2.equals("fid")) {
								item.put("fid", reader.nextString());
							} else if (tagName2.equals("content")) {
								item.put("content", reader.nextString());
							} else if (tagName2.equals("created_at")) {
								item.put("created_at", reader.nextString());
							} else if (tagName2.equals("replypicarray") && reader.peek() == JsonToken.BEGIN_ARRAY) {
								List<String> strlit = new ArrayList<String>();
								reader.beginArray();
								while (reader.hasNext()) {
									reader.beginObject();
									while (reader.hasNext()) {
										String tagName3 = reader.nextName();
										if (tagName3.equals("bigpath")) {
											strlit.add(reader.nextString());
										} else {
											reader.skipValue();
										}
									}
									reader.endObject();
								}
								reader.endArray();
								item.put("images_urls", strlit);
							} else if (tagName2.equals("user")) {
								reader.beginObject();
								while (reader.hasNext()) {
									String tagName4 = reader.nextName();
									if (tagName4.equals("id")) {
										item.put("user.id", reader.nextString());
									} else if (tagName4.equals("username")) {
										item.put("user.name", reader.nextString());
									} else if (tagName4.equals("tel") && reader.peek() != JsonToken.NULL) {
										item.put("user.tel", reader.nextString());
									} else if (tagName4.equals("grade")) {
										item.put("user.grade", reader.nextString());
									} else if (tagName4.equals("s_profile_image_url")) {
										item.put("user.s_profile_image_url", reader.nextString());
										;
									} else {
										reader.skipValue();
									}
								}
								reader.endObject();
							} else {
								reader.skipValue();
							}
						}
						detaillist1.add(item);
						reader.endObject();
					}
					reader.endArray();
				} else {
					reader.skipValue();
				}
			}
			reader.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return detaillist1;
	}

	public static HashMap<String, Object> readJsonDatadetaillouzhu(String jsonData) throws Exception {
		HashMap<String, Object> item = new HashMap<String, Object>();
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			item.put("images_urls", "");
			item.put("user.tel", "");
			reader.beginObject();
			while (reader.hasNext()) {
				String tagName = reader.nextName();
				if (tagName.equals("content")) {
					item.put("content", reader.nextString());
				} else if (tagName.equals("tid")) {
					item.put("tid", reader.nextString());
				} else if (tagName.equals("created_at")) {
					item.put("publishdate", reader.nextString());
				} else if (tagName.equals("topicpicarray") && reader.peek() == JsonToken.BEGIN_ARRAY) {
					List<String> strlit = new ArrayList<String>();
					reader.beginArray();
					while (reader.hasNext()) {
						reader.beginObject();
						while (reader.hasNext()) {
							String tagName3 = reader.nextName();
							if (tagName3.equals("bigpath")) {
								strlit.add(reader.nextString());
							} else {
								reader.skipValue();
							}
						}
						reader.endObject();
					}
					item.put("images_urls", strlit);
					reader.endArray();
				} else if (tagName.equals("user")) {
					reader.beginObject();
					while (reader.hasNext()) {
						String tagName2 = reader.nextName();
						if (tagName2.equals("id")) {
							item.put("user.id", reader.nextString());
						} else if (tagName2.equals("username")) {
							item.put("user.name", reader.nextString());
						} else if (tagName2.equals("tel") && reader.peek() != JsonToken.NULL) {
							item.put("user.tel", reader.nextString());
						} else if (tagName2.equals("grade")) {
							item.put("user.grade", reader.nextString());
						} else if (tagName2.equals("s_profile_image_url")) {
							item.put("user.s_profile_image_url", reader.nextString());
							;
						} else {
							reader.skipValue();
						}
					}
					reader.endObject();
				} else {
					reader.skipValue();
				}
			}
			reader.endObject();
		} catch (Exception e) {
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return item;
	}

	public static List<String> readDoublesArray(JsonReader reader) throws IOException {
		List<String> strlit = new ArrayList<String>();
		reader.beginArray();
		while (reader.hasNext()) {
			strlit.add(reader.nextString());
		}
		reader.endArray();
		return strlit;
	}

	public static ArrayList<HashMap<String, Object>> wzwujinitem(String jsonData) throws Exception {
		userlist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> item;
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				item = new HashMap<String, Object>();
				while (reader.hasNext()) {
					String tagName = reader.nextName();
					if (tagName.equals("address")) {
						item.put("address", reader.nextString());
					} else if (tagName.equals("description")) {
						item.put("description", reader.nextString());
					} else if (tagName.equals("id")) {
						item.put("id", reader.nextString());
					} else if (tagName.equals("imageUrl")) {
						item.put("imageUrl", reader.nextString());
					} else if (tagName.equals("phone")) {
						item.put("phone", reader.nextString());
					} else if (tagName.equals("title")) {
						item.put("title", reader.nextString());
					} else {
						reader.skipValue();
					}
				}
				userlist.add(item);
				reader.endObject();
			}
			reader.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userlist;
	}

	public static String postimagejson(String jsonData) throws Exception {
		String m_ndata = "";
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String tagName = reader.nextName();
				if (tagName.equals("state")) {
					m_ndata = reader.nextString();
				} else {
					reader.skipValue();
				}
			}
			reader.endObject();
		} catch (Exception e) {
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return m_ndata;
	}

	public static List<DuitangInfo> wzpubujson(String jsonData) throws Exception {
		List<DuitangInfo> duitangs = new ArrayList<DuitangInfo>();
		DuitangInfo newsInfo1;
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				newsInfo1 = new DuitangInfo();
				while (reader.hasNext()) {
					String tagName = reader.nextName();
					if (tagName.equals("id")) {
						newsInfo1.setAlbid(reader.nextString());
					} else if (tagName.equals("originUrl")) {
						newsInfo1.setIsrc(reader.nextString());
					} else if (tagName.equals("thumbnailUrl")) {
						newsInfo1.setisrcsmall(reader.nextString());
					} else if (tagName.equals("favouriteCount")) {
						newsInfo1.setMsg(reader.nextString());
					} else {
						reader.skipValue();
					}
				}
				duitangs.add(newsInfo1);
				reader.endObject();
			}
			reader.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return duitangs;
	}
}
