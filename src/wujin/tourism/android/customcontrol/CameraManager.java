package wujin.tourism.android.customcontrol;

import java.io.IOException;
import android.app.ProgressDialog;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Handler;
import android.view.SurfaceHolder;

public final class CameraManager {
	private static CameraManager cameraManager;
	private Camera camera;
	private boolean previewing;

	public static void init(Context context) {
		if (cameraManager == null) {
			cameraManager = new CameraManager(context);
		}
	}

	public static CameraManager get() {
		return cameraManager;
	}

	private CameraManager(Context context) {
		camera = null;
		previewing = false;
	}

	public String openDriver(SurfaceHolder holder) throws IOException {
		String result = null;
		if (camera == null) {
			camera = Camera.open();
			camera.setPreviewDisplay(holder);
			camera.setDisplayOrientation(90);
		}
		return result;
	}

	public void closeDriver() {
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}

	public void startPreview() {
		if (camera != null && !previewing) {
			camera.startPreview();
			previewing = true;
		}
	}

	public void stopPreview() {
		if (camera != null && previewing) {
			camera.stopPreview();
			previewing = false;
		}
	}

	public void requestPreviewFrame(Handler handler, int message) {
		if (camera != null && previewing) {
			camera.takePicture(null, null, jpegCallback);
		}
	}

	public void requestAutoFocus(Handler handler, int message) {
	}

	ProgressDialog alertDialog;
	private PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
		}
	};
}
