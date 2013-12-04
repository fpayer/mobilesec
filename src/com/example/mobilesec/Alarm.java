package com.example.mobilesec;


public class Alarm implements AlarmListItem {
	public final static int HIGH_SENSITIVITY=7;
	public final static int MEDIUM_SENSITIVITY=5;
	public final static int LOW_SENSITIVITY=2;
	public final static int ON=1;
	public final static int NOT_IMPLEMENTED=-1;
	public final static int OFF=0;
	private int icon;
	private static int triggerTotal=0;
	static int totalAlarms=0;
	private int id;
	private int enabled;
	private int info;
	private String title;
	private int sensitivity;
	private String description;
	private int timeout;
	private int triggerValue;
	public Alarm(){
		super();
	}
	public Alarm(int id,int sensitivity, int isEnabled,String title,String description) {
		super();
		totalAlarms++;
		this.setId(id);
		this.setIcon(icon);
		this.setEnabled(isEnabled);
		if (isEnabled==ON) {
			icon = R.drawable.on;
		} else if (isEnabled==OFF) {
			icon = R.drawable.off;
		} else {
			icon = R.drawable.nuetral;
		}
		this.setTitle(title);
		this.setInfo(R.drawable.icon);
		this.setDescription(description);
		this.setSensitivity(sensitivity);
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getInfo() {
		return info;
	}

	public void setInfo(int info) {
		this.info = info;
	}

	public int isEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getTriggerValue() {
		return triggerValue;
	}

	public void setTriggerValue(int triggerValue) {
		this.triggerValue = triggerValue;
	}
	public String toString() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public static int getTriggerTotal() {
		return triggerTotal;
	}
	public static void setTriggerTotal(int triggerTotal) {
		Alarm.triggerTotal = triggerTotal;
	}
	
}