# 310-AI Evaluation Project

## Description
The goal of this project is to track the tweets of specific users and extract them.

### Tracking Tweets
A scheduled job is created to track the tweets using Java and the Spring Boot framework. Every 'n' minutes, the job runs and calls the python scraper endpoint to get and save the tweets in a Postgres DB. The python scraper uses the Snscrape library to get the tweets and exposes them through a REST API endpoint.

### Tracking Replies
To track the replies, a python script uses the Twint library to fetch the replies for a specific user and publishes them on a RabbitMQ message broker(each user has its queue.) To consume the events (replies), a listener is written in Java. After receiving and mapping the message, the Java consumer finds the main conversation, which was already saved into the DB (tip: so the main discussion should exist in advance. This is done by the scheduled job, which constantly runs in the Java app.)
The below diagram is a high-level demonstration.


![alt text](https://github.com/ehsansasanian/310-ai/blob/main/diagram.jpg)



## Request Samples:
1. Get tacked users
```
curl --location --request GET 'localhost:8080/accounts'
```

2. Get a conversation thread from start

```
curl --location --request GET 'http://localhost:8080/tweets/{conversation-id}'
```
3. Get a list of accounts which the most interaction
```
curl --location --request GET 'http://localhost:8080/audience/{username}'
```