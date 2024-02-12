## Unit API Spec

# Create Unit

- Endpoint : POST /api/unit

Request Header

- X-API-TOKEN : Token (Mandatory)

Request Body

- name -> string -> unique = not blank, max 200

```json
{
  "name": "Lusin"
}
```

Response Body (Success)

```json
{
    "data": {
        "id": 96,
        "name": "Lusin"
    },
    "errors": null,
    "paging": null
}
```

# Find All Unit

- Endpoint : GET /api/unit?pageNumber=0

Request Header

- X-API-TOKEN : Token (Mandatory)

Request Param

- pageNumber={pageNumber}

Response Body (Success)

```json
{
  "data": [
    {
      "id": 90,
      "name": "Pcs"
    },
    {
      "id": 91,
      "name": "Test"
    },
    {
      "id": 94,
      "name": "Kg"
    },
    {
      "id": 96,
      "name": "Lusin"
    }
  ],
  "errors": null,
  "paging": {
    "currentPage": 0,
    "totalPage": 1,
    "size": 10
  }
}
```

# Find By Id Unit

- Endpoint : GET /api/unit/{unitId}

Request Header

- X-API-TOKEN : Token (Mandatory)

Response Body (Success)

```json
{
  "data": {
    "id": 96,
    "name": "Lusin"
  },
  "errors": null,
  "paging": null
}
```

# Update Unit

- Endpoint : PUT /api/unit/{unitId}

Request Header

- X-API-TOKEN : Token (Mandatory)

Request Body

- name -> string -> unique = not blank, max 200

```json
{
  "name": "Lusin"
}
```

Response Body (Success)

```json
{
  "data": {
    "id": 96,
    "name": "Lusin Updated"
  },
  "errors": null,
  "paging": null
}
```

# Delete Unit

- Endpoint : DELETE /api/unit/{unitId}

**Unit can be delete if items with this unit is empty**

Request Header

- X-API-TOKEN : Token (Mandatory)


Response Body (Success)

```json
{
  "data": "OK",
  "errors": null,
  "paging": null
}
```
