package method;

import msg.*;
import utils.Sha256Hash;
import utils.query;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

public class AttestVerify {

	public AttestVerify() {
		// TODO Auto-generated constructor stub
	}

	public void attestVerify(ArrayList<RSAPublicKey> publickeys)
			throws Exception {
		//oos.writeObject(new String("start verification"));
		//oos.flush();
		System.out.println("Attest-verify: verify transactions.");
		//512
		//String serviceId="aca2eb7d00ea1a7b8ebd4e68314663af";
		//Double price=69.9;
		//String tid="00a870c6c06346e85335524935c600c0";

		//256
//		String serviceId="154e7e31ebfa092203795c972e5804a6";
//	Double price=23.99;
//		String tid="041cba819a99569f87996b65b73ea82e";

		//128
		//Double price=19.99;
		//String tid="05bb540accb9a3fc966280b894023c9c";


		//64
		//String serviceId="437c05a395e9e47f9762e677a7068ce7";
//		Double price=47.65;
//		String tid="0199115a1cbfc272c5bd53117772a64a";

		//8
//		Double price=53.79;
//		String tid="0b87d9e59c7c19b041fe36e77aa33a42";

		//4
		//Double price=53.5;
		//String tid="28e2f8073d78153ccf2053de91fc4db3";

		//2
		//Double price=50.21;
		//String tid="00b8d354b36820e9d6131fd5173c5581";

		//32
		 String serviceId="99a4788cb24856965c36a24e339b6058";
//		Double price=79.9;
//		String tid="00c763284c0056eed753352f5559ff0a";

		//Double price=89.9;
		//String tid="01be661b8196707ef60f062632d6d1bd";

//       16
		Double price=74.0;
		String tid="0006ec9db01a64e59a68b2c340bf65a7";


		List<String> lists=query.getList(serviceId,price);
		int size=lists.size();
		while(true) {
			System.out.println("Please enter verification type: 1. Membership verification 2.Cardinality verification 3.quit");
			Scanner scan = new Scanner(System.in);
			String str1 = "0";
			if (scan.hasNext()) {
				str1 = scan.next();
				//System.out.println(str1);
			}

			if(str1.equals("1")) {
				Socket socketManager = new Socket("127.0.0.1", 8086);
				if (socketManager == null)
					return;
				ObjectOutputStream oos= new ObjectOutputStream(socketManager.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socketManager.getInputStream());
				System.out.println("Proof of membership start.");
				System.out.println("Transactions: ");
				for (int i = 0; i < size; i++) {
					System.out.println(i + 1 + "." + lists.get(i));
				}
				System.out.println("Please enter the transaction id to be verified:");
				String pos = scan.next();
				//byte[] verifyhash=hash(tid.getBytes());
				int p = Integer.parseInt(pos) - 1;
				byte[] verifyhash = hash(lists.get(p).getBytes());
				AttestmMsg attestmMsg = new AttestmMsg(p, serviceId, price);
				oos.writeObject(attestmMsg);
				System.out.println("C send attestation to M.");
				oos.flush();
				oos.reset();
				System.out.println("C read Acc from Ethereum.");
				String acc = query.getPriceRoot(serviceId, price);
				System.out.println("acc=" + acc);
				ProvemMsg provemMsg = (ProvemMsg) ois.readObject();
				System.out.println("C read proof from M.");
				ArrayList<SiblingsMsg> siblingsm = provemMsg.getSiblings();
				byte[] rootHash = generateRootbysiblings(siblingsm, verifyhash);
				byte[] accverify = hash(rootHash, Integer.toString(siblingsm.size()).getBytes());
				boolean a = acc.equals(new BigInteger(accverify).toString(16));
				if (a) {
					System.out.println("Acc verf. success.");
					System.out.println("Membership verf. success.");
				} else {
					System.out.println("Acc verf. fail.");
					System.out.println("Membership verf. fail.");
				}
			}else if(str1.equals("2")) {
				Boolean b=true;
				Socket socketManager = new Socket("127.0.0.1", 8086);
				if (socketManager == null)
					return;
				ObjectOutputStream oos= new ObjectOutputStream(socketManager.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socketManager.getInputStream());
				System.out.println("Proof of cardinality start.");
				System.out.println("Please enter the number of transactions to verify(1-" + lists.size() + ")");
				int randNumber = Integer.parseInt(scan.next());
				//System.out.println(randNumber);
				HashSet<Integer> randsset = new HashSet<>();
				ArrayList<Integer> rands = new ArrayList<>();
				for (int i = 0; i < randNumber; i++) {
					Random rd = new Random();
					int j = rd.nextInt(size);
					Boolean bb = randsset.add(j);
					if (bb.equals(false)) {
						i--;
					} else {
						rands.add(j);
					}

				}
				AttestcMsg attestcMsg = new AttestcMsg(serviceId, price, rands);
				oos.writeObject(attestcMsg);
				System.out.println("C send attestation to M.");
				oos.flush();
				oos.reset();
				ProveMsg proveMsg = (ProveMsg) ois.readObject();
				System.out.println("C read proof from M.");
				int setSize = proveMsg.getSize();
				//byte[] rootHash1 = provecMsg.getRootHash();
				ArrayList<SiblingsMsg> siblings1 = proveMsg.getSiblings();
				byte[] lastLeaf = proveMsg.getLastLeaf();
				int height = siblings1.size();

				ArrayList<TransactionSign> transactionRand = proveMsg.getTransactionRand();
				System.out.println("C read Acc from Ethereum.");
				String acc1 = query.getPriceRoot(serviceId, price);
				System.out.println("acc=" + acc1);
				if (Math.pow(2, height - 1) < setSize && Math.pow(2, height) >= setSize) {
					System.out.println("Size verf. success.");
				} else {
					b=false;
					System.out.println("Size verf. fail.");
				}


				for (int i = 0; i < transactionRand.size(); i++) {
					if (transactionRand.get(i).getPosition() == rands.get(i)) {
						System.out.println("Pos. verf. success. pos="+rands.get(i));
					} else {
						b=false;
						System.out.println("Pos. verf. fail. pos="+rands.get(i));
					}
					byte[] msg1 = DERSA(publickeys.get(0), transactionRand.get(i).getSignV());
					byte[] msg2 = DERSA(publickeys.get(1), transactionRand.get(i).getSignB());
					String[] array=new String(msg1).split(",");
					String msg3 = transactionRand.get(i).toString();
					if (new String(msg2).equals(msg3) && new String(msg1).equals(msg3)) {
						System.out.println("V's and B's sig. verf. success. tid="+array[0]);
					} else {
						b=false;
						System.out.println("V's and B's sig. verf. fail. tid="+array[0]);
					}
				}


				byte[] rootHash2 = generateRootbysiblings(siblings1, lastLeaf);
				int randleavesnum = proveMsg.getRandLeaves().size();
				ArrayList<byte[]> randLeaves = proveMsg.getRandLeaves();
				ArrayList<ArrayList<SiblingsMsg>> randLeavesSiblings = proveMsg.getRandLeavesSiblings();
				for (int i = 0; i < randleavesnum; i++) {
					byte[] rootHash3 = generateRootbysiblings(randLeavesSiblings.get(i), randLeaves.get(i));

					if (new BigInteger(rootHash3).equals(new BigInteger(rootHash2))) {
							System.out.println("MTRoot verf. success. MTRoot="+new BigInteger(1,rootHash2).toString(16));
					} else {
						b=false;
						System.out.println("MTRoot verf. fail.");
					}

				}
				byte[] accverify1 = hash(rootHash2, Integer.toString(height).getBytes());
				if (acc1.equals(new BigInteger(accverify1).toString(16))) {
					System.out.println("Acc verf. success.");
				} else {
					b=false;
					System.out.println("Acc verf. fail.");
				}
				Boolean bool=verifyOpenNode(setSize - 1, height, siblings1);
				if(bool.equals(false)){
					b=false;
				}
				if(b.equals(true)){
					System.out.println("Cardinality verification success.");
				}else{
					System.out.println("Cardinality verification fail.");
				}

			}else{
				break;
			}
		}
		}


	/**
	 * RSA verify
	 *
	 * @param PUBK
	 * @param encrypted
	 * @return
	 * @throws Exception
	 */
	public static byte[] DERSA(PublicKey PUBK, byte[] encrypted) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, PUBK);
		return cipher.doFinal(encrypted);
	}

	public static byte[] generateRootbysiblings(ArrayList<SiblingsMsg> siblings, byte[] userIdhash) {
		byte[] rootHash = userIdhash;
		int siblingslen = siblings.size();
		// System.out.println("proveM length：" + siblingslen);
		for (int i = siblingslen - 1; i > -1; i--) {
			// System.out.println(i);
			if (siblings.get(i).getDirection() == 1) {
				rootHash = hash(siblings.get(i).getHash(), rootHash);
			} else {
				rootHash = hash(rootHash, siblings.get(i).getHash());
			}
		}
		return rootHash;

	}

	public static Boolean verifyOpenNode(int nodepos, int height, ArrayList<SiblingsMsg> siblings) {
		Boolean bool=true;
		int position = 0;
		int totalSize = (int) Math.pow(2, height);
		totalSize = totalSize / 2;
		int siblingsNo = 0;
		while (totalSize > 0) {
			if (nodepos < totalSize + position) {
				//if (setSize < (totalSize + position)) {
					// System.out.println("totalSize:" + totalSize);
					// System.out.println("position" + position);
					// System.out.println("open node");
					// totalSize+position position+totalSize*2
					if (totalSize == 1) {
						if (new BigInteger(siblings.get(siblingsNo).getHash())
								.equals(new BigInteger(hash(Integer.toString(totalSize + position).getBytes())))) {
							System.out.println("Open Node verf. success");
						} else {
							bool=false;
							System.out.println("Open Node verf. fail.");
						}
					} else {
						ArrayList<byte[]> list = new ArrayList<>();
						for (int i = totalSize + position; i < position + totalSize * 2; i++) {
							list.add(hash(Integer.toString(i).getBytes()));

						}

						byte[] hash2 = hash(list.get(0), list.get(1));
						int len = totalSize;
						while (len > 1) {
							for (int i = 0; i < len; i += 2) {
								int next = i + 1;
								// System.out.println("i"+Integer.parseInt(new String(list.get(i))));

								// System.out.println("next"+new String(list.get(next)));
								byte[] hash = hash(list.get(i), list.get(next));
								// System.out.print(new BigInteger(node.hash).toString() + " ");
								list.set(i / 2, hash);
							}
							len = (len + 1) / 2;
						}

						if (new BigInteger(siblings.get(siblingsNo).getHash()).equals(new BigInteger(list.get(0)))) {
							System.out.println("Open Node verf. success");
						} else {
							bool=false;
							System.out.println("Open Node verf. fail.");
						}
					}
				//}
			} else {
				position = position + totalSize;
			}
			totalSize = totalSize / 2;
			siblingsNo++;

		}
		return bool;
	}

	public static int findZeroNum(int setSize, int totalSize) {
		int zeroNum = 0;
		int position = 0;
		totalSize = totalSize / 2;
		while (totalSize > 0) {
			if (setSize < totalSize + position) {
				zeroNum++;
			} else {
				position = position + totalSize;
			}
			totalSize = totalSize / 2;

		}
		return zeroNum;

	}



	public static byte[] hash(byte[] left, byte[] right) {
		Sha256Hash sha256 = new Sha256Hash();
		byte[] a = addBytes(left, right);
		byte[] b = sha256.hash(a);
		return b;
	}

	public static byte[] hash(byte[] data) {
		Sha256Hash sha256 = new Sha256Hash();
		byte[] b = sha256.hash(data);
		return b;
	}

	public static byte[] addBytes(byte[] data1, byte[] data2) {
		byte[] data3 = new byte[data1.length + data2.length];
		System.arraycopy(data1, 0, data3, 0, data1.length);
		System.arraycopy(data2, 0, data3, data1.length, data2.length);
		return data3;

	}
}
