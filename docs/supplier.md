## Supplier API Spec

# Create Supplier

- Endpoint : POST /api/supplier

Request Header

- X-API-TOKEN = Token (Mandatory)

Request Body

- name -> string = not blank, max 200
- address -> string = not blank
- phone -> string = max 15, must be digit

```json
{
  "name": "Jono",
  "address": "Jalan Kaldera 12",
  "phone": "0813672621"
}
```

Response Body (Success)

```json
{
    "data": {
        "id": "e9f9507e-2de8-4592-a0ec-b22ad834dadb",
        "name": "Jono",
        "address": "Jalan Kaldera 12",
        "phone": "0813672621"
    },
    "errors": null,
    "paging": null
}
```

# Find All Supplier

- Endpoint : GET /api/supplier?pageNumber=0

Request Header

- X-API-TOKEN = Token (Mandatory)

Request Param 
- pageNumber={pageNumber}

Response Body (Success)

```json
{
  "data": [
    {
      "id": "14c04927-18a0-40b4-84d6-87039640667f",
      "name": "United Airlines",
      "address": "Singapore",
      "phone": "1234"
    },
    {
      "id": "e9f9507e-2de8-4592-a0ec-b22ad834dadb",
      "name": "Jono",
      "address": "Jalan Kaldera 12",
      "phone": "0813672621"
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

# Find By Id Supplier

- Endpoint : GET /api/supplier/{supplierId}

Request Header

- X-API-TOKEN = Token (Mandatory)

Response Body (Success)

```json
{
  "data": {
    "id": "e9f9507e-2de8-4592-a0ec-b22ad834dadb",
    "name": "Jono",
    "address": "Jalan Kaldera 12",
    "phone": "0813672621"
  },
  "errors": null,
  "paging": null
}
```

# Update Supplier

- Endpoint : PUT /api/supplier/{supplierId}

Request Header

- X-API-TOKEN = Token (Mandatory)

Request Body

- name -> string = not blank, max 200
- address -> string = not blank
- phone -> string = max 15, must be digit

```json
{
  "name": "Jono Updated",
  "address": "Jalan Kaldera 12 Updated",
  "phone": "0813672621"
}
```

Response Body (Success)

```json
{
  "data": {
    "id": "e9f9507e-2de8-4592-a0ec-b22ad834dadb",
    "name": "Jono Updated",
    "address": "Jalan Kaldera 12 Updated",
    "phone": "0813672621"
  },
  "errors": null,
  "paging": null
}
```

# Delete Supplier

- Endpoint : DELETE /api/supplier/{supplierId}

Request Header

- X-API-TOKEN = Token (Mandatory)

Response Body (Success)

```json
{
  "data": "OK",
  "errors": null,
  "paging": null
}
```
