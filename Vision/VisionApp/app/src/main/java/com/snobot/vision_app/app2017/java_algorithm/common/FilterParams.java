package com.snobot.vision_app.app2017.java_algorithm.common;

public class FilterParams
{
	public double minArea = 125.0;
	public double minPerimeter = 0.0;

	public double minWidth = 28.0;
	public double maxWidth = 60.0;

	public double minHeight = 0.0;
	public double maxHeight = 250.0;

    public double minContoursSolidity = 0;
    public double maxContoursSolidity = 100;

	public double maxVertices = 1000000.0;
	public double minVertices = 0.0;

	public double minRatio = 0.0;
	public double maxRatio = 1000.0;
}