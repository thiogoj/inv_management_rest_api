## Item API Spec

# Create Item

- Endpoint : POST /api/item

Request Header

- X-API-TOKEN : Token (Mandatory)

Request Body

- noPart -> string = not blank, max 200
- description -> string = not blank
- hsCode -> int = max 10 digit
- itemType -> string = not blank, max 100
- unit -> Unit = name -> string = not blank
- **unit name must be exist in unit table**

```json
{
  "noPart": "95PR",
  "description": "Iphone 14 XR",
  "hsCode": 1234567890,
  "itemType": "Barang Jadi",
  "unit": {
    "name": "Pcs"
  }
}
```

Response Body (Success) 

```json
{
  "data": {
    "id": "08ade319-7dc3-445d-9caa-c1b8e9f01d73",
    "noPart": "95PR",
    "description": "Iphone 14 XR",
    "hsCode": 1234567890,
    "itemType": "Barang Jadi",
    "unit": {
      "id": 90,
      "name": "Pcs"
    }
  },
  "errors": null,
  "paging": null
}
```

# Find All Item

- Endpoint : GET /api/item?pageNumber=0

Request Header

- X-API-TOKEN : Token (Mandatory)

Request Param

- pageNumber={pageNumber}

Response Body (Success)

```json
{
  "data": [
    {
      "id": "08ade319-7dc3-445d-9caa-c1b8e9f01d73",
      "noPart": "95PR",
      "description": "Iphone 14 XR",
      "hsCode": 1234567890,
      "itemType": "Barang Jadi",
      "unit": {
        "id": 90,
        "name": "Pcs"
      }
    },
    {
      "id": "c06b37e4-37c9-43f2-a8fc-0723600050bc",
      "noPart": "84DF",
      "description": "Xiaomi 14 Pro",
      "hsCode": 343527,
      "itemType": "Barang Jadi",
      "unit": {
        "id": 90,
        "name": "Pcs"
      }
    },
  ],
  "errors": null,
  "paging": {
    "currentPage": 0,
    "totalPage": 1,
    "size": 10
  }
}
```

# Find By Id Item

- Endpoint : GET /api/item/{itemId}

Request Header

- X-API-TOKEN : Token (Mandatory)

Response Body (Success)

```json
{
  "data": {
    "id": "08ade319-7dc3-445d-9caa-c1b8e9f01d73",
    "noPart": "95PR",
    "description": "Iphone 14 XR",
    "hsCode": 1234567890,
    "itemType": "Barang Jadi",
    "unit": {
      "id": 90,
      "name": "Pcs"
    }
  },
  "errors": null,
  "paging": null
}
```

# Update Item

- Endpoint : PUT /api/item/{itemId}

Request Header

- X-API-TOKEN : Token (Mandatory)

Request Body

- noPart -> string = not blank, max 200
- description -> string = not blank
- hsCode -> int = max 10 digit
- itemType -> string = not blank, max 100
- unit -> Unit = name -> string = not blank
- **unit name must be exist in unit table**

```json
{
  "noPart": "95PR",
  "description": "Iphone 14 XR Update",
  "hsCode": 123456,
  "itemType": "Barang Mentah",
  "unit": {
    "name": "Pcs"
  }
}
```

Response Body (Success) 

```json
{
  "data": {
    "id": "08ade319-7dc3-445d-9caa-c1b8e9f01d73",
    "noPart": "95PR",
    "description": "Iphone 14 XR Update",
    "hsCode": 123456,
    "itemType": "Barang Mentah",
    "unit": {
      "id": 90,
      "name": "Pcs"
    }
  },
  "errors": null,
  "paging": null
}
```

# Delete Item

- Endpoint : DELETE /api/item/{itemId}

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

