package com.wow.webapp.domain.pojo;

public class Vitals {

	
	private String value;

    private String property;

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    public String getProperty ()
    {
        return property;
    }

    public void setProperty (String property)
    {
        this.property = property;
    }
	
}
