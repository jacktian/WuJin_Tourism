package wujin.tourism.android.childactivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import wujin.tourism.android.R;
import wujin.tourism.android.common.GsonTools;
import wujin.tourism.android.data.FileHelper;
import wujin.tourism.android.tpc.jsondatabean;
import wujin.tourism.android.tpc.jsonsubdata;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ZoomControls;
import com.panoramagl.PLICamera;
import com.panoramagl.PLIView;
import com.panoramagl.PLView;
import com.panoramagl.PLViewListener;
import com.panoramagl.hotspots.PLIHotspot;
import com.panoramagl.ios.structs.CGPoint;
import com.panoramagl.loaders.PLILoader;
import com.panoramagl.loaders.PLJSONLoader;
import com.panoramagl.structs.PLPosition;
import com.panoramagl.transitions.PLITransition;
import com.panoramagl.transitions.PLTransitionBlend;

public class QjtyActivity extends PLView {
	private ZoomControls mZoomControls;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setListener(new PLViewListener() {
			public void onDidClickHotspot(PLIView view, PLIHotspot hotspot, CGPoint screenPoint,
					PLPosition scene3DPoint) {
			}

			public void onDidBeginLoader(PLIView view, PLILoader loader) {
				setControlsEnabled(false);
			}

			public void onDidCompleteLoader(PLIView view, PLILoader loader) {
				setControlsEnabled(true);
			}

			public void onDidErrorLoader(PLIView view, PLILoader loader, String error) {
				setControlsEnabled(true);
			}

			public void onDidBeginTransition(PLIView view, PLITransition transition) {
				setControlsEnabled(false);
			}

			public void onDidStopTransition(PLIView view, PLITransition transition, int progressPercentage) {
				setControlsEnabled(true);
			}

			public void onDidEndTransition(PLIView view, PLITransition transition) {
				setControlsEnabled(true);
			}
		});
	}

	protected View onContentViewCreated(View contentView) {
		ViewGroup mainView = (ViewGroup) this.getLayoutInflater().inflate(R.layout.qjtylayout, null);
		mainView.addView(contentView, 0);
		mZoomControls = (ZoomControls) mainView.findViewById(R.id.zoom_controls);
		mZoomControls.setOnZoomInClickListener(new OnClickListener() {
			public void onClick(View view) {
				PLICamera camera = getCamera();
				if (camera != null)
					camera.zoomIn(true);
			}
		});
		mZoomControls.setOnZoomOutClickListener(new OnClickListener() {
			public void onClick(View view) {
				PLICamera camera = getCamera();
				if (camera != null)
					camera.zoomOut(true);
			}
		});
		loadPanoramaFromJSON(getIntent().getStringExtra("id"));
		return super.onContentViewCreated(mainView);
	}

	private void setControlsEnabled(final boolean isEnabled) {
		this.runOnUiThread(new Runnable() {
			public void run() {
				mZoomControls.setIsZoomInEnabled(isEnabled);
				mZoomControls.setIsZoomOutEnabled(isEnabled);
			}
		});
	}

	public String readSDFile(String fileName) {
		StringBuffer sb = new StringBuffer();
		File file = new File(fileName);
		try {
			FileInputStream fis = new FileInputStream(file);
			int c;
			while ((c = fis.read()) != -1) {
				sb.append((char) c);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private void loadPanoramaFromJSON(String index) {
		try {
			String jsondataString = readSDFile("sdcard//wujinly//panorama_" + index + "//sightspots.json");
			ArrayList<jsondatabean> jsonArrayList = GsonTools.readdatajson(jsondataString);
			FileHelper filehelper = new FileHelper();
			String idString = jsonArrayList.get(0).getId() + ".data";
			if (!filehelper.isFileDirExsit("sdcard//wujinly//panorama_" + index + "//" + idString)) {
				for (int i = 0; i < jsonArrayList.size(); i++) {
					String pathString = jsonArrayList.get(i).getId() + ".data";
					filehelper.CreateFile("sdcard//wujinly//panorama_" + index + "//" + pathString);
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("urlBase", "file:///sdcard/wujinly/panorama_" + index);
					jsonObj.put("type", "spherical2");
					jsonObj.put("keep", "all");
					JSONObject subjsonObj = new JSONObject();
					subjsonObj.put("preview",
							"file:///sdcard/wujinly/panorama_" + index + "/" + jsonArrayList.get(i).getId() + ".jpg");
					subjsonObj.put("image",
							"file:///sdcard/wujinly/panorama_" + index + "/" + jsonArrayList.get(i).getId() + ".jpg");
					jsonObj.put("images", subjsonObj);
					JSONObject subjsonObj1 = new JSONObject();
					subjsonObj1.put("keep", "all");
					jsonObj.put("camera", subjsonObj1);
					JSONArray jsonArr = new JSONArray();
					ArrayList<jsonsubdata> spotjsonsubdata = (ArrayList<jsonsubdata>) jsonArrayList.get(i)
							.getListObject();
					for (int j = 0; j < spotjsonsubdata.size(); j++) {
						jsonsubdata jsonsubdata = spotjsonsubdata.get(j);
						String pointString = jsonsubdata.getPoint().replace("{", "").replace("}", "");
						String arrpointString[] = pointString.split(",");
						JSONObject jsonObjdemo = new JSONObject();
						String subidString = jsonsubdata.getSubid();
						jsonObjdemo.put("id", Integer.parseInt(spotjsonsubdata.get(j).getSubid()));
						jsonObjdemo.put("atv", 0.0);
						jsonObjdemo.put("ath", Float.parseFloat(arrpointString[0]) / 2048 * 360 - 180);
						jsonObjdemo.put("width", 0.05);
						jsonObjdemo.put("height", 0.05);
						jsonObjdemo.put("image", "res://raw/hotspot");
						for (jsondatabean hhsondatabean : jsonArrayList) {
							if (hhsondatabean.getId().equals(spotjsonsubdata.get(j).getSubid())) {
								ArrayList<jsonsubdata> spotjsonsubdatademo = (ArrayList<jsonsubdata>) hhsondatabean
										.getListObject();
								if (spotjsonsubdata.size() == 1) {
									if (spotjsonsubdatademo.size() > 1) {
										jsonsubdata jsonsubdatasub = spotjsonsubdatademo.get(1);
										String pointStringsub = jsonsubdatasub.getPoint().replace("{", "").replace("}",
												"");
										String arrpointStringsub[] = pointStringsub.split(",");
										jsonObjdemo.put("onClick", "lookAt(0.0, "
												+ String.valueOf(Float.parseFloat(arrpointString[0]) / 2048 * 360 - 180)
												+ ", true); load('file:///sdcard/wujinly/panorama_" + index + "/"
												+ subidString + ".data', true, BLEND(2.0, 1.0), 0.0, "
												+ String.valueOf(
														Float.parseFloat(arrpointStringsub[0]) / 2048 * 360 - 180)
												+ ")");
									} else {
										jsonsubdata jsonsubdatasub = spotjsonsubdatademo.get(0);
										String pointStringsub = jsonsubdatasub.getPoint().replace("{", "").replace("}",
												"");
										String arrpointStringsub[] = pointStringsub.split(",");
										jsonObjdemo.put("onClick", "lookAt(0.0, "
												+ String.valueOf(Float.parseFloat(arrpointString[0]) / 2048 * 360 - 180)
												+ ", true); load('file:///sdcard/wujinly/panorama_" + index + "/"
												+ subidString + ".data', true, BLEND(2.0, 1.0), 0.0, "
												+ String.valueOf(
														Float.parseFloat(arrpointStringsub[0]) / 2048 * 360 - 180)
												+ ")");
									}
								} else {
									if (j == 0) {
										jsonsubdata jsonsubdatasub = spotjsonsubdatademo.get(0);
										String pointStringsub = jsonsubdatasub.getPoint().replace("{", "").replace("}",
												"");
										String arrpointStringsub[] = pointStringsub.split(",");
										jsonObjdemo.put("onClick", "lookAt(0.0, "
												+ String.valueOf(Float.parseFloat(arrpointString[0]) / 2048 * 360 - 180)
												+ ", true); load('file:///sdcard/wujinly/panorama_" + index + "/"
												+ subidString + ".data', true, BLEND(2.0, 1.0), 0.0, "
												+ String.valueOf(
														Float.parseFloat(arrpointStringsub[0]) / 2048 * 360 - 180)
												+ ")");
									}
									if (j == 1) {
										if (spotjsonsubdatademo.size() > 1) {
											jsonsubdata jsonsubdatasub = spotjsonsubdatademo.get(1);
											String pointStringsub = jsonsubdatasub.getPoint().replace("{", "")
													.replace("}", "");
											String arrpointStringsub[] = pointStringsub.split(",");
											jsonObjdemo.put("onClick", "lookAt(0.0, "
													+ String.valueOf(
															Float.parseFloat(arrpointString[0]) / 2048 * 360 - 180)
													+ ", true); load('file:///sdcard/wujinly/panorama_" + index + "/"
													+ subidString + ".data', true, BLEND(2.0, 1.0), 0.0, "
													+ String.valueOf(
															Float.parseFloat(arrpointStringsub[0]) / 2048 * 360 - 180)
													+ ")");
										} else {
											jsonsubdata jsonsubdatasub = spotjsonsubdatademo.get(0);
											String pointStringsub = jsonsubdatasub.getPoint().replace("{", "")
													.replace("}", "");
											String arrpointStringsub[] = pointStringsub.split(",");
											jsonObjdemo.put("onClick", "lookAt(0.0, "
													+ String.valueOf(
															Float.parseFloat(arrpointString[0]) / 2048 * 360 - 180)
													+ ", true); load('file:///sdcard/wujinly/panorama_" + index + "/"
													+ subidString + ".data', true, BLEND(2.0, 1.0), 0.0, "
													+ String.valueOf(
															Float.parseFloat(arrpointStringsub[0]) / 2048 * 360 - 180)
													+ ")");
										}
									}
									if (j == 2) {
										if (spotjsonsubdatademo.size() > 1) {
											jsonsubdata jsonsubdatasub = spotjsonsubdatademo.get(1);
											String pointStringsub = jsonsubdatasub.getPoint().replace("{", "")
													.replace("}", "");
											String arrpointStringsub[] = pointStringsub.split(",");
											jsonObjdemo.put("onClick", "lookAt(0.0, "
													+ String.valueOf(
															Float.parseFloat(arrpointString[0]) / 2048 * 360 - 180)
													+ ", true); load('file:///sdcard/wujinly/panorama_" + index + "/"
													+ subidString + ".data', true, BLEND(2.0, 1.0), 0.0, "
													+ String.valueOf(
															Float.parseFloat(arrpointStringsub[0]) / 2048 * 360 - 180)
													+ ")");
										} else {
											jsonsubdata jsonsubdatasub = spotjsonsubdatademo.get(0);
											String pointStringsub = jsonsubdatasub.getPoint().replace("{", "")
													.replace("}", "");
											String arrpointStringsub[] = pointStringsub.split(",");
											jsonObjdemo.put("onClick", "lookAt(0.0, "
													+ String.valueOf(
															Float.parseFloat(arrpointString[0]) / 2048 * 360 - 180)
													+ ", true); load('file:///sdcard/wujinly/panorama_" + index + "/"
													+ subidString + ".data', true, BLEND(2.0, 1.0), 0.0, "
													+ String.valueOf(
															Float.parseFloat(arrpointStringsub[0]) / 2048 * 360 - 180)
													+ ")");
										}
									}
								}
								break;
							}
						}
						jsonArr.put(jsonObjdemo);
					}
					jsonObj.put("hotspots", jsonArr);
					filehelper.print(String.valueOf(jsonObj), "sdcard//wujinly//panorama_" + index + "//" + pathString);
				}
			}
			PLILoader loader = null;
			String pathluString = "file:///sdcard/wujinly/panorama_" + index + "/" + idString;
			loader = new PLJSONLoader(pathluString);
			if (loader != null)
				this.load(loader, true, new PLTransitionBlend(2.0f));
		} catch (Exception e) {
		}
	}
}
