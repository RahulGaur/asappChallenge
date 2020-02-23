# asappChallenge
 REST API for a chat backend.

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

# Message Service

This is a Eureka Client used for Sending and reciveing messages for the chat application. This service has two Api's

1. /message : POST request to send message from sender to the recipient. Please note it requires an authentication token as a header, in order to send message which was returned by the login request. Returns message id and the time it was sent. 
2. /message : GET request to receive messages. Please note it requires an authentication token as a header. Returns list of messages. 

DB Name: Message.db

schema:
1. message 
2. content
3. text
4. image
5. video



