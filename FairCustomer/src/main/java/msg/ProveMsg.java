package msg;

import java.util.ArrayList;

public class ProveMsg extends Message {

	/**
	 *
	 */
	private static final long serialVersionUID = 8652062226350919071L;
	//private  ArrayList<SiblingsMsg> siblingsm;
	private int size;
	private byte[] lastLeaf;
	private ArrayList<byte[]> randLeaves;
	private ArrayList<SiblingsMsg> siblings;
	private ArrayList<ArrayList<SiblingsMsg>> randLeavesSiblings;
	//private byte[] rootHash;
	private ArrayList<TransactionSign> transactionRand;
	public ProveMsg(int size, byte[] lastLeaf, ArrayList<SiblingsMsg> siblings, ArrayList<byte[]> randLeaves, ArrayList<ArrayList<SiblingsMsg>> randLeavesSiblings, ArrayList<TransactionSign> transactionRand) {
		//this.siblingsm=siblingsm;
		this.size=size;
		this.lastLeaf=lastLeaf;
		this.siblings=siblings;
		this.randLeavesSiblings=randLeavesSiblings;
		this.randLeaves=randLeaves;
		//this.rootHash=rootHash;
		this.transactionRand=transactionRand;
	}

//	public ArrayList<SiblingsMsg> getSiblingsm() {
//		return siblingsm;
//	}
//
//	public void setSiblingsm(ArrayList<SiblingsMsg> siblingsm) {
//		this.siblingsm = siblingsm;
//	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getLastLeaf() {
		return lastLeaf;
	}

	public void setLastLeaf(byte[] lastLeaf) {
		this.lastLeaf = lastLeaf;
	}

	public ArrayList<byte[]> getRandLeaves() {
		return randLeaves;
	}

	public void setRandLeaves(ArrayList<byte[]> randLeaves) {
		this.randLeaves = randLeaves;
	}

	public ArrayList<SiblingsMsg> getSiblings() {
		return siblings;
	}

	public void setSiblings(ArrayList<SiblingsMsg> siblings) {
		this.siblings = siblings;
	}

	public ArrayList<ArrayList<SiblingsMsg>> getRandLeavesSiblings() {
		return randLeavesSiblings;
	}

	public void setRandLeavesSiblings(ArrayList<ArrayList<SiblingsMsg>> randLeavesSiblings) {
		this.randLeavesSiblings = randLeavesSiblings;
	}


	public ArrayList<TransactionSign> getTransactionRand() {
		return transactionRand;
	}

	public void setTransactionRand(ArrayList<TransactionSign> transactionRand) {
		this.transactionRand = transactionRand;
	}
}



