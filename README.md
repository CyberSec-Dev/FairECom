# FairECom
 Prototype implementation of FairECom 

## Background
We have five programs `FairCustomer`, `FairVendor`, `FairManager`, `FairPBB`, `FairBank`. Among them, `FairCustomer` works on the `Customer` side, `FairVendor` works on the `Vendor` side, `FairManager` and `FairPBB` work on the `Manager` side, and `FairBank` works on the `Bank` side. `Customer, Vendor, Manager and Bank` interact to establish transactions, and `Customer and Manager` interact to verify whether the transaction price is fair. <br>
![Network model of FairECom](./image/fig1.png "Network model of FairECom")

## Install
The above five programs can be installed on four computers or on one computer for testing. If you want to deploy five programs on four computers, you need to modify the `ip address` of the socket connection in the code, and if you deploy on one computer, you don't need to do anything. 
We used the `Mysql database`, and the database generation statements at each side are in the corresponding folders. There is a table list_order on the Customer side, two tables nodes and t_manager on the Manager side, and a table translation on the Bank side. Here, `username="root", password="123456"`. You can  modify the database related configuration  in `"src\main\resources\mybatis-config"` to correspond to your own database connection. 

## Usage&Result
    Please enter verification type: 1. Membership verification 2.Cardinality verification 3.quit
    `1 `
    Proof of membership start.
    Please enter the transaction id to be verified(You can choose the transaction id in table list_order,
    such as 0006ec9db01a64e59a68b2c340bf65a7,08bf457a7ecf8ba28efb48f0add11cca...):
    `0006ec9db01a64e59a68b2c340bf65a7`
    C send attestation to M.
    C read Acc from Ethereum.
    acc=f83fa70122be19ebfcd64b87d46d1e8c71b9be6a9789e7730c178ac8ad7ceeb0
    C read proof from M.
    Acc verf. success.
    Membership verf. success.
    
    Please enter verification type: 1. Membership verification 2.Cardinality verification 3.quit
    `2`
    Proof of cardinality start.
    Please enter the product id:
    `99a4788cb24856965c36a24e339b6058`
    Please enter the price:
    `74`
    Please enter the number of transactions to verify(1-9)
    `2`
    C send attestation to M.
    C read proof from M.
    C read Acc from Ethereum.
    acc=f83fa70122be19ebfcd64b87d46d1e8c71b9be6a9789e7730c178ac8ad7ceeb0
    Size verf. success.
    Pos. verf. success. pos=8
    V's and B's sig. verf. success. tid=edddb585a5c894ef488568334c567e2e
    Pos. verf. success. pos=1
    V's and B's sig. verf. success. tid=08bf457a7ecf8ba28efb48f0add11cca
    MTRoot verf. success. MTRoot=159dff853f506d02579796d2c442c86bae44f4666b26ba85a1ef0ff33129a94c
    MTRoot verf. success. MTRoot=159dff853f506d02579796d2c442c86bae44f4666b26ba85a1ef0ff33129a94c
    Acc verf. success.
    Open Node verf. success
    Open Node verf. success
    Open Node verf. success
    Cardinality verification success.
## License

# FairPBB

## Introduction

The FairPbb is a project based on Spring Boot, and uses web3j library to provide Ethereum related support. The FairPbb, or ledger, allows a third party (i.e., a manager) to publish compactly  statistical information of customers’ transactions in a privacy-preserving way  and any parties can access it. For each payment item, the PBB publishes the number of transactions and a  digest, i.e., Merkle Tree root, for each price. With the help of the published  information, a customer can attest that he/she had been treated fairly (i.e., with fair price charge/payment) on specific transaction.

## Environmental requirements

- JDK 1.8+
- Maven 3.3+
- web3j
- Spring Boot 2.5.6
- mysql 5.7
- Intellij IDEA

## Operation process

> After opening the project with the Intellij IDEA, you can start running the project as shown in the following figure.

<img src="../AppData/Roaming/Typora/typora-user-images/image-20220112181616696.png" alt="image-20220112181616696" style="zoom: 50%;" />

> If the situation of the console is the same as that shown in the figure below, it indicates that the project is running successfully.

<img src="../AppData/Roaming/Typora/typora-user-images/image-20220112181715110.png" alt="image-20220112181715110" style="zoom:50%;" />

> After the project runs successfully, access  http://localhost:8081/  you can access the project locally.
>
> After accessing the project, the home page information is as shown in the figure below. Enter the product_id you want to query in the input box to query the information about changing the product.

![image-20220112182216180](../AppData/Roaming/Typora/typora-user-images/image-20220112182216180.png)

> We take a product in an experimental dataset as an example. In the sidebar of the page, we can clearly see the five properties of the product. 

![image-20220112185617142](../AppData/Roaming/Typora/typora-user-images/image-20220112185617142.png)

> The *Product ID* represents the ID of the product and uniquely identifies a product in the entire dataset. The *Total No. Of Trans.* represents the total number of transactions for this product in a time epoch. The *Price Quantity* denotes the number of different prices for this product during this time epoch. The *Merkle Tree Root* represents the root hash of a Merkel tree whose leaf node value is composed of the Merkle root hash of each price. We have created a smart contract for each product to manage, and The *Contract Address* represents the address of the smart contract. In the table,  we show the relevant information of five different prices of this product in this time epoch. We can see the number of transactions and a digest, for example, *HASH OF MTROOT*, for each price. *HASH OF MTROOT*  is a root hash of a Merkle tree composed of all transactions under this price. We call the function of the smart contract of this product and store the hash value on Ethereum (rinkeby test network). As shown in the table, *TXN HASH* represents the Ethereum transaction hash generated in the stored procedure.

> Relevant information about Ethereum storage can also be verified in https://rinkeby.etherscan.io/

![image-20220112190413162](../AppData/Roaming/Typora/typora-user-images/image-20220112190413162.png)

