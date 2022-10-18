# The Friend Management RESTFul Api

### Project Features

* Build Api endpoints for Friend management project
* Write unit test for API endpoints

### Project Requirement
1. Java 11+
2. Maven 3.x.x
3. Install and run PostgreSQL on your localhost for storing data
4. ...

### Test coverage
    Class: 76%
    Method: 67%
    Line: 72%

### How to use from this sample project
##### Clone the repository
```
git clone https://github.com/clipdoi/FiendsManagemenWebFlux.git
```
##### Run project

* Docker
```
docker-compose up --build
```

##### Test api with postman
```
https://www.postman.com/downloads/
```

### RestApi Enpoints

* Create a friend connection between two email addresses: http://localhost:8080/api/emails/add
 ````
  Example Request:
     {
        "friends":[
            "hongson@gmail.com",
            "minhthong@gmail.com"
        ]
     }
  Success Response Example:
    {
      "success": true
    }
  Error Response Example
   {
      "success": false
   }
   ````
  -------------------------------------------------------------

* Retrieve the friends list for an email address: http://localhost:8080/api/emails/friends
````
 Example Request:
    {
        "email":"hongson@gmail.com"
    }
 Success Response Example:
    {
        "success": true,
        "friends": [
            "minhthong@gmail.com",
            "saomai@gmail.com",
            "nguyenphi@gmail.com"
        ],
        "count": 3
    }

 ````
 -------------------------------------------------------------

* Retrieve the common friends list between two email addresses: http://localhost:8080/api/emails/common
 ````
  Example Request:
      {
        "friends":[
            "hongson@gmail.com",
            "minhthong@gmail.com"
        ]
      }
 Success Response Example:
      {
        "success": true,
        "friends": [
            "saomai@gmail.com"
        ],
        "count": 1
      }

  ````
  -------------------------------------------------------------

* Subscribe to updates from an email address: http://localhost:8080/api/emails/subscribe
 ````
  Example Request:
        {
            "requester":"nguyenphi@gmail.com",
            "target":"nguyenquang@gmail.com"
        }
  Success Response Example:
       {
            "success": true
       }

  -------------------------------------------------------------
````
* Block updates from an email address(PUT method): http://localhost:8080/api/emails/block
````
  Example Request:
    {   
        "requester":"hongson@gmail.com",
        "target":"nguyenquang@gmail.com"
    } 
  Success Response Example:
   {
        "success": true
   }

   ````
  -------------------------------------------------------------

* Retrieve all email addresses that can receive updates from an email address: http://localhost:8080/api/emails/retrieve
````
  Example Request:
    {
        "sender": "hongson@gmail.com",
        "text": "Hello World! nguyenvu@gmail.com"
    }
  Success Response Example:
    {
        "success": true,
        "recipients": [
            "nguyenvu@gmail.com",
            "minhthong@gmail.com",
            "saomai@gmail.com",
            "nguyenphi@gmail.com"
        ]
    }
 
````