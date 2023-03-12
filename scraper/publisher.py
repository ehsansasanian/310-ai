import pika
import json


"""
publishes a message to a RabbitMQ message queue. The function takes two parameters: msg, which is 
the message to be published, and queue, which is the name of the message queue to publish to.
"""
def publish(msg,queue):
    connection_params = pika.ConnectionParameters('localhost')
    connection = pika.BlockingConnection(connection_params)

    channel = connection.channel()

    channel.exchange_declare(exchange='reply_exchange', exchange_type='direct',durable=False)
    channel.queue_declare(queue=queue, durable=False, exclusive=False, auto_delete=False)
    channel.basic_publish(
        exchange= '',
        routing_key= queue,
        body=json.dumps(msg)
    )
    connection.close()
