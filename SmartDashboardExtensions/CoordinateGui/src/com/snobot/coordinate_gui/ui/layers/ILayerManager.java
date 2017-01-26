package com.snobot.coordinate_gui.ui.layers;

public interface ILayerManager
{
    public interface IFieldClickListener
    {
        public void onClicked(double aXFeet, double aYFeet);

        public void onDrag(double aXFeet, double aYFeet);

        public void onHover(double aXFeet, double aYFeet);
    }

    public void addLayer(ILayer aLayer);

    public void addFieldClickListener(IFieldClickListener aListener);

    public void removeFieldClickListener(IFieldClickListener aListener);

    public void render();
}
