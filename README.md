### Glad to see you here!  

# Problem Statement
The module is developed to design discount calculation workflow based on different category.

Discount categories can be seen below,
1.	If the user is an employee of the store, he gets a 30% discount
2.	If the user is an affiliate of the store, he gets a 10% discount
3.	If the user has been a customer for over 2 years, he gets a 5% discount.
4.	For every $100 on the bill, there would be a $ 5 discount (e.g. for $ 990, you get $ 45 as a discount).
5.	The percentage based discounts do not apply on groceries.
6.	A user can get only one of the percentage based discounts on a bill.

# Solution

Current working solution handles following thing,
- List down availble filters to be applied
- Calculate their total discount number
- Order available discounts in descending order
- Display available discounts with reason

## Sample input-output (From console pretty print).

```
=========================================
Customer: Test Store user
Cart items: 
-------------------
P4 [GROCERY]: 6 x 80.0 = 480.0
P1 [BAKERY]: 2 x 250.0 = 500.0
-------------
Total: 980.0
Discount 150.0 (Reason: Store emplyee discount 30 percentage of non-grocery product value)
Discount 25.0 (Reason: Existing customer (more than 2 years) gets 5 percentage discount)
Additional Discount: 45.0 (Reason: Discount of 5 for all category items on every 100 bill value)
Maximum discount: 195.0
==========================================
```

# Solution Design

## Entities
1. Product: name, category, price
2. Customer: name, registration date, dob, is_affiliate, is_employee
3. CartItem: Product, quantity
4. Cart: List of CartItem, Customer

Class diagram for the inter relation ship can be shown in below diagram.

![Class Diagram drawio](https://github.com/user-attachments/assets/6a4eba4d-812e-4c5c-847b-1f1351266119)


## Filter implementation
Class diagram for the filter implementation.
![Class Diagram drawio](https://github.com/user-attachments/assets/cae3e514-1add-4b88-8cd0-72ca6de529c5)



## Coverage Report
Code is covering 100% business lines from service layer. And overall 60+ % due to POJO/DTO etc has not covered fully.

<img width="559" alt="image" src="https://github.com/user-attachments/assets/1c1ec487-d7b1-4916-99d8-9a91337adfa5">
