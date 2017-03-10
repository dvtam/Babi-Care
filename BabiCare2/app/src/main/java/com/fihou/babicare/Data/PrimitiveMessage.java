/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fihou.babicare.Data;

/**
 * 
 * @author PHAN TIEN
 */
public class PrimitiveMessage {

	private short MessageID;
	private byte[] Data;

	public PrimitiveMessage() {
		Data = null;
	}

	public void setData(byte[] mData) {
		int length = mData.length;
		this.Data = new byte[length];
		System.arraycopy(mData, 0, this.Data, 0, length);
	}

	public byte[] getData() {
		return Data;
	}

	public void setMessageID(short mMessageID) {
		this.MessageID = mMessageID;
	}

	public short getMessageID() {
		return MessageID;
	}
}
