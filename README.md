# 310-AI Evaluation Project

## Description
The goal of this project is to track the tweets of specific users and extract them.

### Tracking Tweets
A scheduled job is created to track the tweets using Java and the Spring Boot framework. Every 'n' minutes, the job runs and calls the python scraper endpoint to get and save the tweets in a Postgres DB. The python scraper uses the Snscrape library to get the tweets and exposes them through a REST API endpoint.

### Tracking Replies
To track the replies, a python script uses the Twint library to fetch the replies for a specific user and publishes them on a RabbitMQ message broker(each user has its queue.) To consume the events (replies), a listener is written in Java. After receiving and mapping the message, the Java consumer finds the main conversation, which was already saved into the DB (tip: so the main discussion should exist in advance. This is done by the scheduled job, which constantly runs in the Java app.)
The below diagram is a high-level demonstration.


![alt text](https://github.com/ehsansasanian/310-ai/blob/main/diagram.jpg)

## Getting started

### Prerequisites
- Docker
- Docker Compose
- pip
- python

#### Tip!

Everything for the core app (Java, and Rabbit) is configured in the docker-compose file. Therefore, having docker, and docker-compose is sufficient to start.


#### Clone the repo:

```
 git clone https://github.com/ehsansasanian/310-ai.git 
 ```

#### Build the app:
```
cd 310-ai/core
```
```
docker-compose build
```

#### Run the containers

```
docker-compose up
```

#### Run the python app

```
cd ../scraper
```

```
python3 main.py
```

## How it works:

The app works in two phases:
1. Tweets must be fetched for a certain account.
2. Replies must be fetched and mapped for each tweet.

### Phase 1: 
After running above commands, the system will start fetching the tweets for the main accounts. It is possible to add unlimited accounts and track the tweets, replies and analysis. To do so, the account should be added to the accounts table. For now, there is no API to add an account, therefore, it should be inserted directly to the DB.

Sample insert query:
```
INSERT INTO accounts(id, last_processed_date, username) VALUES (1, '2023-02-01', 'elonmusk');
```

As soon as an account added to the DB, the system starts fetching the tweets since last_processed_date as of today.

### Phase 2:
To fetch replies, reply-streamer.py script should be run. Here is the run command:

```
python3 reply-streamer.py <account-name> <until>
```
Example:

```
python3 reply-streamer.py elonmusk 2023-03-12
```

After running the command, the python app will start streaming the replies on the AMQ broker. On the other side, the Java app is listening to the proper queues and is ready to consume the replies.
The Java app, receives, maps, and processes the replies, and finally saves them into the DB for further use cases.

## Seeing the result:

To see the result, different REST API endpoints are exposed. Below you can find sample requests.

### Request Samples:
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

Thank you for checking this App!