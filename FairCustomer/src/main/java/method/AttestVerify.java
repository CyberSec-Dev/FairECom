package method;

import msg.*;
import pojo.list_order;
import utils.Sha256Hash;
import utils.query;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

public class AttestVerify {

	public AttestVerify() {
		// TODO Auto-generated constructor stub
	}

	public void attestVerify(String managerIp,int managerPort)
			throws Exception {
		//oos.writeObject(new String("start verification"));
		//oos.flush();
		System.out.println("Attest-verify: verify transactions.");
		FileInputStream fin=new FileInputStream("./customerRSAKey");
		ObjectInputStream in=new ObjectInputStream(fin);
		ArrayList<RSAKey1> keys=(ArrayList<RSAKey1>) in.readObject();
		RSAPublicKey publicKeyBank=keys.get(2).getPublicKey();
		RSAPublicKey publicKeyVendor=keys.get(1).getPublicKey();

		//List<String> lists=query.getList(serviceId,price);
		//int size=lists.size();
		while(true) {
			System.out.println("Please enter verification type: 1. Membership verification.  2.Cardinality verification.  3.Quit");
			Scanner scan = new Scanner(System.in);
			String str1 = "0";
			if (scan.hasNext()) {
				str1 = scan.next();
				//System.out.println(str1);
			}

			if(str1.equals("1")) {
				Socket socketManager = new Socket(managerIp, managerPort);
				if (socketManager == null)
					return;
				ObjectOutputStream oos= new ObjectOutputStream(socketManager.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socketManager.getInputStream());
				System.out.println("Proof of membership start.");
				//System.out.println("Transactions: ");
//				for (int i = 0; i < size; i++) {
//					System.out.println(i + 1 + "." + lists.get(i));
//				}
				System.out.println("Please enter the transaction id to be verified(You can choose the transaction id in table list_order," );
				System.out.println("i.e.b2e54b3ccbc9c423893aa9dbe19dcd73):");
				String orderId = scan.next();

				list_order order=query.getOrder(orderId);
				byte[] verifyhash=hash(order.getOrderId().getBytes());
				//int p = Integer.parseInt(order.get) - 1;
				String serviceId1=order.getProductId();
				double price1=order.getPrice();
				//List<String> lists=query.getList(serviceId1,price1);
				//int size=lists.size();
				//System.out.println();
				//byte[] verifyhash = hash(lists.get(p).getBytes());
				AttestmMsg attestmMsg = new AttestmMsg(verifyhash, serviceId1, price1);
				oos.writeObject(attestmMsg);
				System.out.println("C send attestation to M.");
				oos.flush();
				oos.reset();
				System.out.println("C read Acc from Ethereum.");
				String acc = query.getPriceRoot(serviceId1, price1);
				System.out.println("ACC=" + acc);
				ProvemMsg provemMsg = (ProvemMsg) ois.readObject();
				System.out.println("C read proof from M.");
				ArrayList<SiblingsMsg> siblingsm = provemMsg.getSiblings();
				byte[] rootHash = generateRootbysiblings(siblingsm, verifyhash);
				byte[] accverify = hash(rootHash, Integer.toString(siblingsm.size()).getBytes());
				boolean a = acc.equals(new BigInteger(1,accverify).toString(16));
				if (a) {
					System.out.println("Acc verf. success.");
					System.out.println("Membership verf. success.");
				} else {
					System.out.println("Acc verf. fail.");
					System.out.println("Membership verf. fail.");
				}
			}else if(str1.equals("2")) {
				Boolean b=true;
				Socket socketManager = new Socket(managerIp, managerPort);
				if (socketManager == null)
					return;
				ObjectOutputStream oos= new ObjectOutputStream(socketManager.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socketManager.getInputStream());
				System.out.println("Proof of cardinality start.");
				System.out.println("Please enter the product id(i.e.ac6c3623068f30de03045865e4e10089):");
				String productId=scan.next();
				System.out.println("Please enter the price(i.e. 199.9):");
				String s=scan.next();
				double price2=Double.parseDouble(s);
				List<String> lists=query.getList(productId,price2);
				int size=lists.size();
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
				AttestcMsg attestcMsg = new AttestcMsg(productId, price2, rands);
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
				String acc1 = query.getPriceRoot(productId, price2);
				System.out.println("ACC=" + acc1);
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
					byte[] msg1 = DERSA(publicKeyVendor, transactionRand.get(i).getSignV());
					byte[] msg2 = DERSA(publicKeyBank, transactionRand.get(i).getSignB());
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
				if (acc1.equals(new BigInteger(1,accverify1).toString(16))) {
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
