import twint
import sys
import publisher as pb

module = sys.modules["twint.storage.write"]
account = sys.argv[1]
until = sys.argv[2]

"""
Uses Publisher library to publish the reply on a specific queue 
"""

def Json(obj, config):
    tweet = obj.__dict__
    pb.publish(tweet,account)


module.Json = Json

c = twint.Config()
c.Conversation = True

c.Since='2023-02-01'
c.Until=until
c.To=account
c.Replies=True
c.Store_json = True
c.Output="dummy.json"
c.Hide_output=True

twint.run.Search(c)
