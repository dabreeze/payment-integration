# payment-integration

# A payment integration to paystack test environment

this is a middle-ware service that accepts payment request and sends to paystack for processing.

=======================
 END POINTS================

1. create payment plan for product
2. initialize payment
3. verify payment
4. basic customer crud operations

NOTE: assumptions are you have already existing client facing application or postman
and can communicate with the middle ware.

also that you have already existing customers if not using the basic customer api provided will enable you create new customer
then proceed to create different payment plan on paystact.

SETUP
======
clone the project and ensure you have java 21 installed and any mysql database and using any IDE of tour choice 
run this command "mvn clean install" 