# FairECom
The price discrimination has been empirically exposed where ecommercial platforms aim to  gain additional profits by charging customers different prices for the same product/service at the same time.

By relying on a newly designed cryptographic accumulator and public bullet board, a system, named as FairECom, is developed, to allow an auditor (i.e., a customer or third-party auditor) to verify if customers are  experiencing price discrimination.

## Introduction
There are four types of users in the system, namely `Customer`, `Vendor`, `Manager` and `Bank`. Customer, Vendor, Manager and Bank interact to establish transactions, and Customer and Manager interact to verify whether the transaction price is fair. 

Customer, Vendor, and Bank each have a project, which are placed in the `FairCustomer`, `FairVendor`and `FairBank`folders respectively. There are two projects on the Manager side, which are placed in the `FairManager` and `FairPBB`folders. 

Our FairECom is implemented by Java, with the built-in random number generator, cryptographic hash (i.e., SHA), and asymmetric cryptography (i.e., RSA). The socket is used to realize the communication among customers, the vendor, the PBB Manager, and the bank system. The Manager and the Bank store the metadata information associated with each transaction in a local MySQL database. The cryptographic accumulator(Merkle Tree)  is stored in a file via java's serialization.

The FairPBB is a project based on Spring Boot, and uses web3j library to provide Ethereum related support. The FairPBB, or ledger, allows a third party (i.e., a manager) to publish compactly  statistical information of customers’ transactions in a privacy-preserving way  and any parties can access it. For each payment item, the PBB publishes the number of transactions and a  digest, i.e., Merkle Tree root, for each price. With the help of the published  information, a customer can attest that he/she had been treated fairly (i.e., with fair price charge/payment) on specific transaction.

We use a real-world public Brazilian e-commercial dataset including orders generously provided by Olist Store. This dataset includes about 100k orders/transactions placed at multiple marketplaces in Brazil from 2016 to 2018. From which, we focus on the Order Items Dataset, which includes the detailed information about the items purchased within
each order.

## Environmental requirements

- JDK 1.8+
- Maven 3.3+
- web3j
- Spring Boot 2.5.6
- mysql 5.7
- Intellij IDEA

## Install&Configure 
We need  four computers to simulate`Customer`, `Vendor`, `Manager` and `Bank` respectively.
>Download the `FairCustomer`, `FairVendor` and `FairBank` folders respectively in Customer, Vendor, and Bank. Download `FairManager` and `FairPBB`folders in Manager.

>Modify the `ip address` and `The port number `of the socket connection in the code( if you deploy on one computer, you don't need to do this step).

```java
 Socket socketManager = new Socket("127.0.0.1", 8086);
 Socket socketVendor = new Socket("127.0.0.1", 8089);
 Socket socketBank = new Socket("127.0.0.1", 8087);
```

>Create a new database test in the MYSQL database of `Customer`, `Vendor`, `Manager` and `Bank` respectively. Run list_order.sql, transaction.sql in Customer and Bank respectively. Run node.sql, t_manager.sql in Manager.  The database generation statements at each side are in the corresponding folders.

>Modify the database related configuration  in `"src\main\resources\mybatis-config"` to correspond to your own database connection. 

```java
 <dataSource type="POOLED">
     <property name="driver" value="com.mysql.jdbc.Driver"/>
     <property name="url" value="jdbc:mysql://localhost:3306/test?useSSL=false&amp;useUnicode=true&amp;characterEncoding=utf8"/>
     <property name="username" value="root"/>
     <property name="password" value="123456"/>
  </dataSource>
```


## Usage&Result
Run five projects and run FairCustomer at last. Next, I will show the usage and result of FairCustomer and FairPBB. The  other projects are simple to use and will not be described here. 
### FairPBB
> Run FairPBB. If the situation of the console is the same as that shown in the figure below, it indicates that the project is running successfully.

![Schematic diagram of successful operation ](./image/fig1.png "Schematic diagram of successful operation ")

> After the project runs successfully, access  http://localhost:8081/  you can access the project locally.
>
> After accessing the project, the home page information is as shown in the figure below. Enter the product_id you want to query in the input box to query the information about changing the product.

![Schematic diagram of viewing product ](./image/fig2.png "Schematic diagram of viewing product ")

> We take a product in an experimental dataset as an example. In the sidebar of the page, we can clearly see the five properties of the product. 

![Schematic diagram of product detail ](./image/fig3.png "Schematic diagram of product detail ")

> The *Product ID* represents the ID of the product and uniquely identifies a product in the entire dataset. The *Total No. Of Trans.* represents the total number of transactions for this product in a time epoch. The *Price Quantity* denotes the number of different prices for this product during this time epoch. The *Merkle Tree Root* represents the root hash of a Merkel tree whose leaf node value is composed of the Merkle root hash of each price. We have created a smart contract for each product to manage, and The *Contract Address* represents the address of the smart contract. In the table,  we show the relevant information of five different prices of this product in this time epoch. We can see the number of transactions and a digest, for example, *HASH OF MTROOT*, for each price. *HASH OF MTROOT*  is a root hash of a Merkle tree composed of all transactions under this price. We call the function of the smart contract of this product and store the hash value on Ethereum (rinkeby test network). As shown in the table, *TXN HASH* represents the Ethereum transaction hash generated in the stored procedure.

> Relevant information about Ethereum storage can also be verified in https://rinkeby.etherscan.io/

![Schematic diagram of  Ethereum](./image/fig4.png "Schematic diagram of  Ethereum ")
### FairCustomer

```
Please enter an action to perform ：1.Create transactions.  2.Verify transactions.  3.Quit
1
Order-fulfill:create transactions.
Please enter the product id:
bc76f0b0323c7a007739b72cfc9277bb
C send sig. to V.
C read sig. of V and B.
B's sig. verf. success. tid=585579aed1c5241bcec5569704f086
V's sig. verf. success. tid=585579aed1c5241bcec5569704f086
Order-fulfill success!
```
```
Please enter an action to perform ：1.Create transactions.  2.Verify transactions.  3.Quit
2
Attest-verify: verify transactions.
Please enter verification type: 1. Membership verification.  2.Cardinality verification.  3.Quit
1
Proof of membership start.
Please enter the transaction id to be verified(You can choose the transaction id in table list_order,
i.e.b2e54b3ccbc9c423893aa9dbe19dcd73):
b2e54b3ccbc9c423893aa9dbe19dcd73
C send attestation to M.
C read Acc from Ethereum.
ACC=e9b107c41422ad8f336e97e4fc3407c7c9686f1e85dfced28682630fa9d1d09f
C read proof from M.
Acc verf. success.
Membership verf. success.
```
```
Please enter verification type: 1. Membership verification.  2.Cardinality verification.  3.Quit
2
Proof of cardinality start.
Please enter the product id(i.e.ac6c3623068f30de03045865e4e10089):
ac6c3623068f30de03045865e4e10089
Please enter the price(i.e. 199.9):
199.9
Please enter the number of transactions to verify(1-9)
1
C send attestation to M.
C read proof from M.
C read Acc from Ethereum.
ACC=e9b107c41422ad8f336e97e4fc3407c7c9686f1e85dfced28682630fa9d1d09f
Size verf. success.
Pos. verf. success. pos=0
V's and B's sig. verf. success. tid=00042b26cf59d7ce69dfabb4e55b4fd9
MTRoot verf. success. MTRoot=af6c4cbc1524b29ff38e35d5b7a2171654d1b18c845ba42cd0f05528385fb0b8
Acc verf. success.
Open Node verf. success
Open Node verf. success
Open Node verf. success
Cardinality verification success.
```



