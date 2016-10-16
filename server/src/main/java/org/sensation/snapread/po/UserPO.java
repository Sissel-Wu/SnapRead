package org.sensation.snapread.po;

import java.io.Serializable;

public class UserPO implements Serializable{
	private String userID;

	public UserPO(){}

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID.substring(0,10);
    }
}
