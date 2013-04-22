package com.acme.doktoric;

public class FileObj {
	private String text = "";
	private int num = 0;

	public FileObj(String text, int num) {
		this.text = text;
		this.num = num;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getNum() {
		return num;
	}
	
	public String NumasString()
	{
		Integer number=new Integer(this.num); 
		return number.toString();
	}

	public void setNum(int num) {
		this.num = num;
	}

}
