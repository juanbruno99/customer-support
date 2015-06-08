package com.tikcom.domain;

/**
 * @author juanm
 * @since 21-11-2014
 * 
 * Basic attachment object, composed primarily of a name to describe it briefly and the byte contents
 * */
public class Attachment
{
    private String name;

    private byte[] contents;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public byte[] getContents()
    {
        return contents;
    }

    public void setContents(byte[] contents)
    {
        this.contents = contents;
    }
}
