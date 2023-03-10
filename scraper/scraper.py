import snscrape.modules.twitter as sntwitter
import pandas as pd


"""
Function: getTweets

This function fetches tweets from Twitter based on a given query and returns a list of extracted tweet information.

Parameters:
- query (str): The query to search for tweets on Twitter.

Returns:
- tweets (list): A list of extracted tweet information.
"""
def getTweets(query):
    tweets = []
    print(f"fetching with query {query}")
    fetchedTweets = sntwitter.TwitterSearchScraper(query).get_items()
    for tweet in fetchedTweets:
        tweets.append(extract_tweet_info(tweet))
    return tweets

"""
Function: getReplies

This function fetches the replies for a given Twitter user and conversation, and returns a list of extracted tweet information.

Parameters:
- username (str): The username of the Twitter user whose replies are to be fetched.
- conversationId (str): The ID of the conversation for which replies are to be fetched.

Returns:
- tweets (list): A list of extracted tweet information for the replies.
"""
def getReplies(username : str , conversationId : str):
    print(f"request received to process the replies for username {username} and conv {conversationId}.")
    query=f"to:{username} since_id:{conversationId} filter:safe"
    tweets = []
    fetchedTweets = sntwitter.TwitterSearchScraper(query).get_items()
    for tweet in fetchedTweets:
        tweets.append(extract_tweet_info(tweet))
    return tweets


def getTweetByIdAndUsername(username, tweet_id):
    tweet_url = f"https://twitter.com/{username}/status/{tweet_id}"
    return sntwitter.TwitterSearchScraper(tweet_url).get_items()


def extract_tweet_info(tweet):
    return {'date': tweet.date,
            'username': tweet.user.username,
            'content': tweet.rawContent,
            'retweet_count': tweet.retweetCount,
            'tweet_id': tweet.id,
            'like_count':tweet.likeCount,
            'reply_count':tweet.replyCount,
            'in_reply_to_tweet_id':tweet.inReplyToTweetId,
            'conversation_id':tweet.conversationId,
            'view_count':tweet.viewCount}