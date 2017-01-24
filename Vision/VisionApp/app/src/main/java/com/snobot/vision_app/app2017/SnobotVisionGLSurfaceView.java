package com.snobot.vision_app.app2017;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.snobot.vision_app.opengl_renderer.VisionTrackerGLSurfaceView;

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

        texOut = texIn;
    }

    @Override
    protected TextView getFpsTextView() {
        return null;
    }
}
