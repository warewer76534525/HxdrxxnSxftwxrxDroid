package com.triplelands.HidreenSoftware.model;

import java.io.Serializable;

import com.triplelands.HidreenSoftware.R;

@SuppressWarnings("serial")
public class EconomicCalendar implements Serializable {
	private String date;
	private String time;
	private String timezone;
	private String currency;
	private String description;
	private String importance;
	private String actual;
	private String forecast;
	private String previous;

	public EconomicCalendar(String date, String time, String timezone,
			String currency, String description, String importance,
			String actual, String forecast, String previous) {
		this.date = date;
		this.time = time;
		this.timezone = timezone;
		this.currency = currency;
		this.description = description;
		this.importance = importance;
		this.actual = actual;
		this.forecast = forecast;
		this.previous = previous;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public String getTimezone() {
		return timezone;
	}

	public String getCurrency() {
		return currency;
	}

	public String getDescription() {
		return description;
	}

	public String getImportance() {
		return importance;
	}

	public String getActual() {
		return actual;
	}

	public String getForecast() {
		return forecast;
	}

	public String getPrevious() {
		return previous;
	}
	
	public int getImageResource(){
		if (currency.toLowerCase().contains("aud")) {
			return R.drawable.aud;
		}
		if (currency.toLowerCase().contains("cad")) {
			return R.drawable.cad;
		}
		if (currency.toLowerCase().contains("chf")) {
			return R.drawable.chf;
		}
		if (currency.toLowerCase().contains("cny")) {
			return R.drawable.cny;
		}
		if (currency.toLowerCase().contains("eur")) {
			return R.drawable.eur;
		}
		if (currency.toLowerCase().contains("gbp")) {
			return R.drawable.gbp;
		}
		if (currency.toLowerCase().contains("jpy")) {
			return R.drawable.jpy;
		}
		if (currency.toLowerCase().contains("nzd")) {
			return R.drawable.nzd;
		}
		if (currency.toLowerCase().contains("usd")) {
			return R.drawable.usd;
		}
		return -1;
	}

}
