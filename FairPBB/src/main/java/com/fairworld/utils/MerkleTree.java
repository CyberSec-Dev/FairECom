package com.fairworld.utils;


import java.io.IOException;
import java.io.ObjectOutputStream;
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
        double a=Math.log(listLen)/Math.log(2);
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
        while (len > 1) {
            for (int i = 0; i < len; i += 2) {
                int next = i + 1;
                MerkleTree node = new MerkleTree();
                node.left = trees.get(i);
                node.right = trees.get(next);
                node.hash = hash(node.left.hash, node.right.hash);
                trees.set(i / 2, node);
            }
            len = (len + 1) / 2;
        }
        return trees.get(0);
    }


    public byte[] acc() {
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
