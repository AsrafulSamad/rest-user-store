# REST User Store
## Problem Statement
Link: https://hackmd.io/@FS0ejO9xRXOnOkdbQAdemg/SJ3KU8X3W?type=view

# Sample API Request Response

## User Creation

* URL: http://localhost:8080/users
* Method: POST
* Sample Request Body:
    ```
    {
        "firstName": "asraful",
        "lastName": "samad",
        "password": "pass"
    }
    ```
* Sample Response Body:
    ```
    {
        "id": 1
    }
    ```
## Get All Users
* URL: http://localhost:8080/users
* Method: GET
* Sample Response Body:
    ```
    [
    {
    "id": 1,
    "firstName": "asraful",
    "lastName": "samad"
    },
    {
    "id": 2,
    "firstName": "asraful",
    "lastName": "forhad"
    }
    ]
    ```

## Get Single User

* URL: http://localhost:8080/users/{id} (ex. http://localhost:8080/users/1)
* Method: POST
* Sample Response Body:
    ```
    {
    "id": 1,
    "firstName": "asraful",
    "lastName": "samad"
    }
    ```

## Tag Posting

* URL: http://localhost:8080/users/{id}/tags (ex. http://localhost:8080/users/1/tags)
* Method: POST
* 'expiry' param is in milliseconds
* Sample Request Body:
    ```
    {
    "tags": ["tag1", "tag2"],
    "expiry": 12336554       
    }
    ```
* Response (void)


## Get Users by Tags

* URL: http://localhost:8080/users/get-by-tags?tags=tag1,tag3 (ex. http://localhost:8080/users/get-by-tags?tags=tag1,tag3)
* Method: POST
* Sample Response Body:
     ```
    {
      "users": [
          {
              "id": 1,
              "name": "asraful samad",
              "tags": [
                  "user1",
                  "tag1",
                  "beshi"
                  ],
          },
          {
              "id": 4,
              "name": "asraful forhad",
              "tags": [
                     "tag1",
                     "user2",
                     "user4"
              ],
          }
      ]
    }
    ```

# Error Case 
#### HTTP Status Code: 404 Not Found (User Not Found)
