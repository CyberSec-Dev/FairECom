package msg;

import java.util.ArrayList;

public class ProvemMsg extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4704063242338417529L;
	private  ArrayList<SiblingsMsg> siblings;
	//private  byte[] rootHash;

	public ProvemMsg(ArrayList<SiblingsMsg> siblings) {
		// TODO Auto-generated constructor stub
		this.siblings=siblings;
		//this.rootHash=rootHash;
	}

	public ArrayList<SiblingsMsg> getSiblings() {
		return siblings;
	}

	public void setSiblings(ArrayList<SiblingsMsg> siblings) {
		this.siblings = siblings;
	}



	
}