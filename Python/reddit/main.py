import praw
import json
import config

SUBREDDIT = "gaming"

reddit = praw.Reddit(
    client_id = config.CLIENT_ID,
    client_secret = config.CLIENT_SECRET,
    user_agent = config.USER_AGENT,
    username = config.USERNAME,
    password = config.PASSWORD

)

subreddit = reddit.subreddit(SUBREDDIT)
data = []

for submission in subreddit.new(limit=100):
    data.append({
        "title": submission.title,
        "score": submission.score,
        "id": submission.id,
        "url": submission.url,
        "num_comments": submission.num_comments,
        "created": submission.created,
        "selftext": submission.selftext,
        "author": str(submission.author)
    })

with open("results.json", "w") as file:
    json.dump(data, file, indent=4)