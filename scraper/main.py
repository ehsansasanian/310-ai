import scraper as s
import uvicorn
from fastapi import FastAPI

app = FastAPI()

@app.get("/tweets")
def getTweets(query : str):
    return s.getTweets(query)

@app.get("/replies")
def getReplies(username : str , conversationId : str):
    return s.getReplies(username,conversationId)

@app.get("/byId")
def getTweetByIdAndUsername(username:str, id:str):
    return s.getTweetByIdAndUsername(username, id)

if __name__ == "__main__":
    uvicorn.run("main:app", port= 8000, reload=True)