package com.snobot.vision_app.app2017;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.snobot.vision_app.opengl_renderer.VisionTrackerGLSurfaceView;
import com.snobot.vision_app.utils.MjpgServer;

import java.io.ByteArrayOutputStream;

/**
 * Created by PJ on 1/23/2017.
 */

public class SnobotVisionGLSurfaceView extends VisionTrackerGLSurfaceView{

    public SnobotVisionGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void processTexture(int texIn, int texOut, int width, int height, long image_timestamp) {
        Log.i(LOGTAG, "Processing texture");
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        this.draw(c);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, os);

        MjpgServer.getInstance().update(os.toByteArray());
    }

    @Override
    protected TextView getFpsTextView() {
        return null;
    }
}
