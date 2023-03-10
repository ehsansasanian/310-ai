import twint
import sys
module = sys.modules["twint.storage.write"]

def Json(obj, config):
    tweet = obj.__dict__
    print(tweet)

module.Json = Json

c = twint.Config()
c.Conversation = True

c.Since='2023-02-01'
c.Until='2023-03-10'
c.To="ylecun"
c.Replies=True
c.Store_json = True
c.Output="./ylecun-combined.json"
c.Hide_output=True

twint.run.Search(c)
