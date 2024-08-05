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
P4 [GROCERY]: 5 x 80.0 = 400.0
P1 [BAKERY]: 2 x 250.0 = 500.0
-------------------
Total: 900.0

Discount 270.0 (Reason: Store emplyee discount 30 percentage of total value)
Discount 45.0 (Reason: Existing customer (more than 2 years) gets 5 discount)
Discount 20.0 (Reason: Discount of 5 for grocery category items for every 100 bill)

==========================================
```

# Solution Design

## Entities
1. Product: name, category, price
2. Customer: name, registration date, dob, is_affiliate, is_employee
3. CartItem: Product, quantity
4. Cart: List of CartItem, Customer

## Filter implementation

