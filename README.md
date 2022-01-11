# FairECom
 Prototype implementation of FairECom 

## Background
We have five programs `FairCustomer`, `FairVendor`, `FairManager`, `FairPBB`, `FairBank`. Among them, `FairCustomer` works on the `Customer` side, `FairVendor` works on the `Vendor` side, `FairManager` and `FairPBB` work on the `Manager` side, and `FairBank` works on the `Bank` side. `Customer, Vendor, Manager and Bank` interact to establish transactions, and `Customer and Manager` interact to verify whether the transaction price is fair. <br>
![Network model of FairECom](./image/fig1.png "Network model of FairECom")

## Install
The above five programs can be installed on four computers or on one computer for testing. If you want to deploy five programs on four computers, you need to modify the `ip address` of the socket connection in the code, and if you deploy on one computer, you don't need to do anything. 
We used the `Mysql database`, and the database generation statements at each side are in the corresponding folders. There is a table list_order on the Customer side, two tables nodes and t_manager on the Manager side, and a table translation on the Bank side. Here, `username="root", password="123456"`. You can  modify the database related configuration  in `"src\main\resources\mybatis-config"` to correspond to your own database connection. 

## Usage
    V's sig. verf. success. tid=93d336256cb2fc7b61c9030d24d0f031
    B's sig. verf. success. tid=93d336256cb2fc7b61c9030d24d0f031
    V's sig. verf. success. tid=94be6b672a642c5c33415e7ccc13c00c
    B's sig. verf. success. tid=94be6b672a642c5c33415e7ccc13c00c
    
    Please enter verification type: 1. Membership verification 2.Cardinality verification 3.quit
    1
    Proof of membership start.
    Please enter the transaction id to be verified(You can choose the transaction id in table list_order,
    such as 0006ec9db01a64e59a68b2c340bf65a7,08bf457a7ecf8ba28efb48f0add11cca...):
    0006ec9db01a64e59a68b2c340bf65a7
    C send attestation to M.
    C read Acc from Ethereum.
    acc=f83fa70122be19ebfcd64b87d46d1e8c71b9be6a9789e7730c178ac8ad7ceeb0
    C read proof from M.
    Acc verf. success.
    Membership verf. success.
## License
