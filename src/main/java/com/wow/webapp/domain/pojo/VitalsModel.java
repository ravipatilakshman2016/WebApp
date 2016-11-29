package com.wow.webapp.domain.pojo;

public class VitalsModel {

	
	private Vitals[] vitals;

    private String bookingId;

    public Vitals[] getVitals ()
    {
        return vitals;
    }

    public void setVitals (Vitals[] vitals)
    {
        this.vitals = vitals;
    }

    public String getBookingId ()
    {
        return bookingId;
    }

    public void setBookingId (String bookingId)
    {
        this.bookingId = bookingId;
    }
}
