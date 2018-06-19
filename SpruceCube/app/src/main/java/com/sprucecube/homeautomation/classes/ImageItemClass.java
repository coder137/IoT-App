package com.sprucecube.homeautomation.classes;

public class ImageItemClass
{
    String text;
    int imageId;
    public ImageItemClass(int imageId, String text)
    {
        this.imageId = imageId;
        this.text = text;
    }

    public String getText()
    {
        return text;
    }

    public int getImageId()
    {
        return imageId;
    }
}
