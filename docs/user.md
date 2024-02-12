# User Api Spec

## Login User

- Endpoint : POST /api/auth/login

Request Body

- username -> string = not blank, max 100
- password -> string = not blank, max 100

```json
{
  "username": "admin",
  "password": "rahasia"
}
```

Response Body (Success)

```json
{
  "data": {
    "token": "697371ee-1e79-45be-a5e2-f6d135fa19a2",
    "expiredAt": 1708312545152
  },
  "errors": null,
  "paging": null
}
```

## Logout User

- Endpoint : DELETE /api/auth/logout

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success)

```json
{
  "data": "OK",
  "errors": null,
  "paging": null
}
```

