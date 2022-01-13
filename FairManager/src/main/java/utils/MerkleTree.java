package utils;


import msg.LastLeaf;

import msg.SiblingsMsg;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


/*     WARNING! If you're reading this because you're learning about crypto
       and/or designing a new system that will use merkle trees, keep in mind
       that the following merkle tree algorithm has a serious flaw related to
       duplicate txids, resulting in a vulnerability (CVE-2012-2459).

       The reason is that if the number of hashes in the list at a given level
       is odd, the last one is duplicated before computing the next level (which
       is unusual in Merkle trees). This results in certain sequences of
       transactions leading to the same merkle root. For example, these two
       trees:

                    A               A
                  /  \            /   \
                B     C         B       C
               / \    |        / \     / \
              D   E   F       D   E   F   F
             / \ / \ / \     / \ / \ / \ / \
             1 2 3 4 5 6     1 2 3 4 5 6 5 6

       for transaction lists [1,2,3,4,5,6] and [1,2,3,4,5,6,5,6] (where 5 and
       6 are repeated) result in the same root hash A (because the hash of both
       of (F) and (F,F) is C).

       The vulnerability results from being able to send a block with such a
       transaction list, with the same merkle root, and the same block hash as
       the original without duplication, resulting in failed validation. If the
       receiving node proceeds to mark that block as permanently invalid
       however, it will fail to accept further unmodified (and thus potentially
       valid) versions of the same block. We defend against this by detecting
       the case where we would hash two identical hashes at the end of the list
       together, and treating that identically to the block having an invalid
       merkle root. Assuming no double-SHA256 collisions, this will detect all
       known ways of changing the transactions without affecting the merkle
       root.
*/
public class MerkleTree implements Serializable {
    private static final long serialVersionUID = 1L;
    private byte[] hash;
    private MerkleTree left;
    private MerkleTree right;

    public MerkleTree(byte[] hash) {
        this.hash = hash;
        //this.data = data;
    }

    public MerkleTree() {

    }

    public static MerkleTree merkleTree(List<String> list) throws NoSuchAlgorithmException, IOException {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("NOT EMPTY");
        }

        Sha256Hash sha256 = new Sha256Hash();
        ArrayList<MerkleTree> trees = new ArrayList<>();
        int listLen = list.size();
      //  int a = (int) Math.sqrt(listLen) + 1;
        double a=Math.log(listLen)/Math.log(2);
       // int len = (int) Math.pow(2, a);
        int len=0;
        int b=(int)a;
        if(b==a){
            len=(int)Math.pow(2,b);
        }else{
            len=(int)Math.pow(2,b+1);
        }
        for (int i = 0; i < len; i++) {
            if (i < listLen) {
                trees.add(new MerkleTree(sha256.hash(list.get(i).getBytes())));
            } else {
                byte[] data = Integer.toString(i).getBytes();
                trees.add(new MerkleTree(sha256.hash(data)));
            }
            //System.out.print(new String(trees.get(i).data) + " ");
//            System.out.print(new BigInteger(trees.get(i).hash).toString() + " ");

        }

        for (int i = 0; i < len - 1; i++) {
            trees.get(i).setRight(trees.get(i + 1));
        }
        //int height = 0;
        while (len > 1) {
            for (int i = 0; i < len; i += 2) {
                int next = i + 1;
                MerkleTree node = new MerkleTree();
                node.left = trees.get(i);
                node.right = trees.get(next);
                node.hash = hash(node.left.hash, node.right.hash);
                //System.out.print(new BigInteger(node.left.hash).toString() + "   ");
                //System.out.print(new BigInteger(node.right.hash).toString() + "   ");
//                System.out.print(new BigInteger(node.hash).toString() + "   ");
                trees.set(i / 2, node);
            }
            len = (len + 1) / 2;
            //height++;
        }
        //trees.get(0).data = Integer.toString(height).getBytes();
        return trees.get(0);
    }

    public MerkleTree addTree(ArrayList<String> list) throws NoSuchAlgorithmException, IOException {
        MerkleTree merkle = this;
        MerkleTree newtree = merkleTree(list);
        MerkleTree combineTree = new MerkleTree();
        combineTree.left = newtree;
        combineTree.right = merkle;
        combineTree.hash = hash(combineTree.left.hash, combineTree.right.hash);
        //int height = Integer.parseInt(new String(merkle.getData())) + 1;
        //combineTree.data = Integer.toString(height).getBytes();
        return combineTree;
    }

//    public int  addLeaf(byte[] newLeaf) throws IOException {
//        MerkleTree merkle = this;
//        merkle.setHash(new byte[1]);
//        //int height = Integer.parseInt(new String(merkle.getData()));
////		for (int i = 0; i < height; i++) {
////			merkle = merkle.getLeft();
////		}
//        while(merkle.getLeft()!=null) {
//            merkle = merkle.getLeft();
//        }
//        int pos = 0;
//        int j = 1;
//        while (merkle != null) {
//            if (new BigInteger(merkle.getRight().getHash()).equals(new BigInteger(hash(Integer.toString(j).getBytes())))) {
//                //merkle.getRight().setData(newLeaf);
//                merkle.getRight().setHash(hash(newLeaf));
//                break;
//            }
//            merkle = merkle.getRight();
//            pos++;
//            j++;
//
//        }
//        ArrayList<MerkleTree> path=getPath(pos+1);
//        int len=path.size();
//        for(int i=0;i<len;i++) {
//

//        }
//        //path.get(0).setHash(new byte[1]);
//        for(int i=len-2;i>-1;i--) {
//            path.get(i).setHash(hash(path.get(i).getLeft().getHash(),path.get(i).getRight().getHash()));
//
//        }
//        return pos+1;
//
//    }

//	public byte[] generateRootbysiblings(ArrayList<MerkleTree> provem, String userId) {
//		byte[] rootHash = hash(userId.getBytes());
//		int siblingslen = provem.size() - 1;
//		for (int i = siblingslen - 1; i > -1; i--) {
//			if (new String(provem.get(i).getData()).equals("1")) {
//				rootHash = hash(provem.get(i).getHash(), rootHash);
//			} else {
//				rootHash = hash(rootHash, provem.get(i).getHash());
//			}
//		}
//		return rootHash;
//
//	}

//    public ProvemMsg proveM(byte[] userIdhash) throws IOException {
//        MerkleTree merkle = this;
//        int pos = merkle.findPosition(userIdhash);
//        ArrayList<SiblingsMsg> siblings = merkle.siblings(pos);
//        ProvemMsg provemMsg = new ProvemMsg(siblings, merkle.getHash());
//        return provemMsg;
//    }

    public byte[] findLeaf(int pos) {

        MerkleTree merkle = this;
        int height=0;
        while(merkle.getLeft()!=null) {
            merkle = merkle.getLeft();
            height++;
        }
        merkle=this;
        int len = (int) Math.pow(2, height);
        //System.out.println("len:" + len);
        if (pos >= len) {

            return null;
        }
        len = len / 2;
        int position = 0;
        while (len > 0) {
            if (pos < len + position) {
                merkle = merkle.getLeft();
            } else {
                merkle = merkle.getRight();
                position = position + len;
            }
            len = len / 2;
        }
        //System.out.println("??????????" + new String(merkle.getData()));
        return merkle.getHash();
    }

    public ArrayList<SiblingsMsg> siblings(int pos) throws IOException {

        MerkleTree merkle = this;
        int height=0;
        while(merkle.getLeft()!=null) {
            merkle = merkle.getLeft();
            height++;
        }
        merkle=this;
        int len = (int) Math.pow(2, height);
        //System.out.println("len:" + len);
        if (pos >= len) {

            return null;
        }
        ArrayList<SiblingsMsg> siblings = new ArrayList<SiblingsMsg>();
        int position = 0;
        len = len / 2;
        while (len > 0) {
            if (pos < len + position) {
                SiblingsMsg msg = new SiblingsMsg(merkle.getRight().getHash(), 2);
                siblings.add(msg);
                merkle = merkle.getLeft();
            } else {
                position = position + len;
                SiblingsMsg msg = new SiblingsMsg(merkle.getLeft().getHash(), 1);
                siblings.add(msg);
                merkle = merkle.getRight();

            }
            len = len / 2;

        }
        return siblings;
    }
    public ArrayList<MerkleTree> getPath(int pos) throws IOException {
        // System.out.println("???????????¦Ë???" + pos);
        MerkleTree merkle = this;
        int height=0;
        while(merkle.getLeft()!=null) {
            merkle = merkle.getLeft();
            height++;
        }
        merkle=this;
        int len = (int) Math.pow(2, height);
        //System.out.println("len:" + len);
        if (pos >= len) {
            System.out.println("???????????¦¶");
            return null;
        }

        //this.setHash(new byte[1]);
        ArrayList<MerkleTree> path = new ArrayList<MerkleTree>();
        path.add(merkle);
        int position = 0;
        len = len / 2;
        while (len > 0) {
            //System.out.println("lalala:"+len);
            if (pos < len + position) {
                path.add(merkle.getLeft());
                merkle = merkle.getLeft();
            } else {
                path.add(merkle.getRight());
                merkle = merkle.getRight();
                position=position+len;

            }
            len = len / 2;

        }
        return path;
    }
    public LastLeaf findLastLeaf() {
        MerkleTree merkle = this;
        int height=0;
        while(merkle.getLeft()!=null) {
            merkle = merkle.getLeft();
            height++;
        }
        merkle=this;
        int len = (int) Math.pow(2, height);
        for (int i = 0; i < height; i++) {
            merkle = merkle.getLeft();
        }
        int pos = 0;
        int j = 1;
        while (merkle != null) {
            if (new BigInteger(merkle.getRight().getHash()).equals(new BigInteger(hash(Integer.toString(j).getBytes())))) {
                LastLeaf lastLeaf = new LastLeaf(merkle.getHash(), pos);
                return lastLeaf;
            }
            merkle = merkle.getRight();
            pos++;
            j++;
        }
        return null;
    }

    public int findPosition(byte[] data) {
        MerkleTree merkle = this;
        int height=0;
        while(merkle.getLeft()!=null) {
            merkle = merkle.getLeft();
            height++;
        }
        merkle=this;
        for (int i = 0; i < height; i++) {
            merkle = merkle.getLeft();
        }
        int pos = 0;
        while (merkle != null) {
            if (new BigInteger(merkle.getHash()).equals(new BigInteger(data))) {
                return pos;
            }
            merkle = merkle.getRight();
            pos++;
        }
        return 0;
    }

    public byte[] acc() {
        //System.out.println("acchash:" + new BigInteger(this.hash));
        //System.out.println(new BigInteger(this.data));
        MerkleTree merkle = this;
        int height=0;
        while(merkle.getLeft()!=null) {
            merkle = merkle.getLeft();
            height++;
        }
        return hash(this.hash, Integer.toString(height).getBytes());
    }

    public byte[] getHash() {
        return hash;
    }

    public MerkleTree setHash(byte[] hash) {
        this.hash = hash;
        return this;
    }

    public MerkleTree getLeft() {
        return left;
    }

    public MerkleTree setLeft(MerkleTree left) {
        this.left = left;
        return this;
    }

    public MerkleTree getRight() {
        return right;
    }

    public MerkleTree setRight(MerkleTree right) {
        this.right = right;
        return this;
    }


    public String stringHash() {
        return toString(this.hash);
    }

    public static String toString(Object data) {
        if (data == null) {
            return "N-U-L-L";
        }
        if (data instanceof byte[]) {
            return new BigInteger(1, (byte[]) data).toString(16);
        }
        return data.toString();
    }

    @Override
    public String toString() {
        return this.toString("").toString();
    }



    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

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
}
