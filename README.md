# CMS Server
The idea of this project is to learn how to build rest api and how to use spring boot. The technologies that i am using for this project are: java, spring boot, junit, mysql. 

Current status for this project is: writting tests for services.

Later i am gonna make front by using html, css, js, reactjs.

This server works on the heroku.

Url: https://spring-cms-server.herokuapp.com/


# Endpoints

## User

### Add

/api/users/add

Required data using json and send as POST request: 

```yaml
{

  "firstName": String(length: 2-50),
  
  "lastName":  String(length: 2-50),
  
  "email": String(length: 5-50),
  
  "newPassword": String(length: 10-128),
  
  "repeatNewPassword": String(length: 10-128)
  
}
```

### Get

/api/users/{id}

It will return json with data:

```yaml
{

  "firstName": String,
  
  "lastName":  String,
  
  "email":  String,
}
```

### Update

/api/users/update

Update data using json and send as PUT request: 
Note: if you want update password, there are required values oldPassword, newPassword, repeatNewPassword.

```yaml
{

  "firstName": String(length: 2-50),
  
  "lastName":  String(length: 2-50),
  
  "email": String(length: 5-50),
  
  "oldPassowrd": String(length: 10-128),
  
  "newPassword": String(length: 10-128),
  
  "repeatNewPassword": String(length: 10-128)
  
}
```


### Delete

Both requests require DELETE method.

/api/users/delete/{id}

 &nbsp; Description: It's soft delete. It means that the user's data are not deleted from db but user can't sign in to his account.


/api/users/delete/{id}/hard

 &nbsp; Description: It's hard delete. It means that all of the user's data are deleted from db. 


### Login

/login

Required data using json and send request POST. 
Note: username is email that user gave in sign up.

```yaml
{
 
  "username": String(length: 5-50),
  
  "password": String(length: 10-128),
  
}
```

## Article

### Add

/api/article/add


```yaml
{

  "title": String(length: 2-25),
  
  "content":  String(length: 30-2000),
  
  "authorName": String(length: 3-100)
}
```

### Get

/api/article/all/author/{id}

 &nbsp; Description: Returns list o articles by author's id.

/api/article/all/author/{name}

 &nbsp; Description: Returns list o articles by author's name.

/api/article/{id}

 &nbsp; Returns article by article's id.

/api/article/all/team/{id}

 &nbsp; Description: Returns list o articles by team's id.


### Update

/api/article/update

Update data using json and send request with PUT method.

```yaml
{

  "title": String(length: 2-25),
  
  "content":  String(length: 30-2000),
  
  "authorName": String(length: 3-100)
}
```

### Delete 

/api/article/soft/{id}

 &nbsp; Description: If article is soft deleted then it is not available for everyone.

/api/article/hard/{id}

 &nbsp; Description: Article is deleted from db.
