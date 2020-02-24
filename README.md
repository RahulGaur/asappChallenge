# asappChallenge
 REST API for a chat backend.
 
# Tech Stack
1. Java 
2. Spring Boot
3. Eureka
4. Sqlite3

# SERVICES

There are three services which need to run in parallel 

1. Discovery Service
2. Auth Service
3. Message Service

Bring up these services in the following order as mentioned above. 

# Discovery Service

This is Eureka Service Registry Server – This microservice provides the service registry and discovery server. 

Port Number: 8761

To bring up this service:
1. cd ~/discovery
1. mvn package (make sure to have maven installed and added to the PATH )
2. java -jar ./target/discovery-0.0.1-SNAPSHOT.jar

To check the health and the status of all the services which have registered visit: http://localhost:8761/

# Auth Service

This is a Eureka Client used for the authentication operations. This service has two Api's:

1. /users : POST request to create a new user. Returns the user id for the created user
2. /login : POST request to login. Returns the user id of the logged in user and the authentication token unique to this user.

Port Number: 8080

DB NAME: Auth.db

Schema:
1. user
2. user_token

To bring up this service:
1. cd ~/auth
2. mvn package (make sure to have maven installed and added to the PATH )
3. java -jar ./target/auth-0.0.1-SNAPSHOT.jar


# Message Service

This is a Eureka Client used for Sending and reciveing messages for the chat application. This service has two Api's

1. /message : POST request to send message from sender to the recipient. Please note it requires an authentication token as a header, in order to send message which was returned by the login request. Returns message id and the time it was sent. 

Sample body for the POST reques (besideds the header):

```
{
  "sender": 2,
  "recipient": 1,
  "content": {
  	"type": "text",
  	"text":{
  		"type":"string",
  		"text":"hey how are you"
  	}
  	
  }
}
```

2. /message : GET request to receive messages. Please note it requires an authentication token as a header. Returns list of messages. 

Port Number: 8081

DB Name: Message.db

Schema:
1. message 
2. content
3. text
4. image
5. video

Content Table:

Name       | Type            | Example
content_id |Integer          | 1
type       |Varchar          | text/string/image/video
text_id    |Integer(Nullable)| 1
image_id   |Integer(Nullable)| 2
video_id   |Integer(Nullable)| 2

Each content has a content_id which can be mapped to a text_id or image_id or video_id

Each message would have a content_id attached to it. 

To bring up this service:
1. cd ~/message
2. mvn package (make sure to have maven installed and added to the PATH )
3. java -jar ./target/message-0.0.1-SNAPSHOT.jar
