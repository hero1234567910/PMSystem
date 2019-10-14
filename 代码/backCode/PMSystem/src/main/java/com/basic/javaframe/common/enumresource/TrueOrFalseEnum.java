package com.basic.javaframe.common.enumresource;


public enum TrueOrFalseEnum{
	TRUE(0,true),
	FALSE(1,false);
	
	private final int code;
    private final boolean value;
    private TrueOrFalseEnum(int code, boolean value) {
        this.code = code;
        this.value = value;
    }
	
	public int getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	public boolean getValue() {
		// TODO Auto-generated method stub
		return value;
	}
	
	public static String getValueByCode(int code){
		if (code == 0) {
			return "true";
		}else if(code == 1){
			return "false";
		}else {
			return "";
		}
	}
}
