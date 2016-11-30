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

##Time Priority defined


