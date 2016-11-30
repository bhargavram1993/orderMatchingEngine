# Order Matching Engine

##Overview

This Document describes the concept, System Overview, files and database design overview, input formats, output layouts overview, design and processing logic.

##Purpose and Scope 

The objective is to build a basic FIFO/Time Priority/Price-Time bid matching engine.

##Project Executive Summary

The most commonly applied algorithm for auction trading would be Price/Time priority and Pro-Rata. Both have been adapted and extended for various types of products and use cases, but for in short, I'll only use the basics at high level of Price/Time priority i.e. FIFO engine.

##System Overview

The FIFO algorithm uses price and time as the only criteria for filling an order. In this algorithm, all orders at the same price level are filled according to time priority. It is important to note that a user loses order priority when the order is changed in any of the following ways. Fills orders on a strict price and time priority; the first order at a price level is the first order matched.
Price/Time priority, aka FIFO, ensures that all orders at the same price level are filled according to time priority; the first order at a price level is the first order matched.

##Design
Rules

1. Build order queues buy or sell side and by price/time priority

2. A buy order is matched against a sell order and vice versa, at the same price level

##Algorithm

1. Queue incoming orders into two sides: buy and sell

2. Each side is ordered according to price and time. Price takes precedence for the same time.

3. Sort order by side, sell ascending and buy descending

4. Match incoming order with pending orders in the database

5. A new buy order is matched against a sell pending order and vice versa, at the same price level

6. Matched orders are removed from queue and the order status is updated to match from pending. 

7. Every matched order has a list of matching orders.

8. Unmatched orders are created new with status pending.

9. Cancelled orders are removed from pending orders. 

10. A testing unit with sample data.

![image](https://cloud.githubusercontent.com/assets/17102225/20771445/6779fa7c-b717-11e6-9e8f-df0de6078da5.png)

##Time Priority defined

![1](https://cloud.githubusercontent.com/assets/17102225/20771278/c7ac37da-b716-11e6-92e5-ed9ab681b9e9.PNG)




