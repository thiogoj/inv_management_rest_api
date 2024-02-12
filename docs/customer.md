## Customer API Spec

# Create Customer

- Endpoint = POST /api/customer

Request Header

- X-API-TOKEN : Token (Mandatory)

Request Body 

- name -> string = not blank, max 200
- address -> string
- phone -> string = max 15, must be digit

```json
{
  "name": "PT Singapore Airline",
  "address": "Singapore",
  "phone": "082178274067"
}
```

Response Body (Success)

```json
{
    "data": {
        "id": "a945d0a5-336d-4151-833a-38bf99526563",
        "name": "PT Singapore Airline",
        "address": "Singapore",
        "phone": "082178274067"
    },
    "errors": null,
    "paging": null
}
```

# Find All Customer

- Endpoint = GET /api/customer?pageNumber=0

Request Header

- X-API-TOKEN : Token (Mandatory)

Request Param

- pageNumber={pageNumber}

Response Body (Success)

```json
{
  "data": [
    {
      "id": "a945d0a5-336d-4151-833a-38bf99526563",
      "name": "PT Singapore Airline",
      "address": "Singapore",
      "phone": "082178274067"
    },
    {
      "id": "e9c2bba0-06a5-4054-a0cc-facb4a6a9df5",
      "name": "Test",
      "address": "Test",
      "phone": "08123"
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

# Find By Id Customer

- Endpoint = GET /api/customer/{customerId}

Request Header

- X-API-TOKEN : Token (Mandatory)

Response Body (Success)

```json
{
  "data": {
    "id": "a945d0a5-336d-4151-833a-38bf99526563",
    "name": "PT Singapore Airline",
    "address": "Singapore",
    "phone": "082178274067"
  },
  "errors": null,
  "paging": null
}
```

# Update Customer

- Endpoint = PUT /api/customer/{customerId}

Request Header

- X-API-TOKEN : Token (Mandatory)

Request Body

- name -> string = not blank, max 200
- address -> string
- phone -> string = max 15, must be digit

```json
{
  "name": "PT Singapore Airline Updated",
  "address": "Singapore Update",
  "phone": "082178274067"
}
```

Response Body (Success)

```json
{
  "data": {
    "id": "a945d0a5-336d-4151-833a-38bf99526563",
    "name": "PT Singapore Airline Updated",
    "address": "Singapore Update",
    "phone": "082178274067"
  },
  "errors": null,
  "paging": null
}
```

# Delete Customer

- Endpoint = DELETE /api/customer/{customerId}

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