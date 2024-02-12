# Currency API Spec

## Create Currency

- Endpoint : POST /api/currency

Request Header

- X-API-TOKEN : Token (Mandatory)

Request Body

- currency -> string = not blank, max 100
- rate -> string = not blank, 0-9, digit and dot(.) only
- remark -> string = not blank

```json
{
  "currency": "IDR",
  "rate": "15000",
  "remark": "Indonesia Rupiah"
}
```

Response Body (Success) 

```json
{
  "data": {
    "id": 41,
    "currency": "IDR",
    "rate": "15000",
    "remark": "Indonesia Rupiah",
    "lastUpdatedAt": "2024-02-12T10:23:53.0148544"
  },
  "errors": null,
  "paging": null
}
```

## Find All Currency

- Endpoint : GET /api/currency?pageNumber=0

Request Header

- X-API-TOKEN : Token (Mandatory)

Request Param

- pageNumber={pageNumber}

Response Body (Success)

```json
{
  "data": [
    {
      "id": 40,
      "currency": "INI TEST",
      "rate": "10.00",
      "remark": "Ini Test Dollar",
      "lastUpdatedAt": "2024-02-09T20:31:59"
    },
    {
      "id": 41,
      "currency": "IDR",
      "rate": "15000",
      "remark": "Indonesia Rupiah",
      "lastUpdatedAt": "2024-02-12T10:23:53"
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

## Find By Id Currency

- Endpoint : GET /api/currency/{currencyId}

Request Header

- X-API-TOKEN : Token (Mandatory)

Response Body (Success)

```json
{
  "data": {
    "id": 41,
    "currency": "IDR",
    "rate": "15000",
    "remark": "Indonesia Rupiah",
    "lastUpdatedAt": "2024-02-12T10:23:53"
  },
  "errors": null,
  "paging": null
}
```

## Update Currency

- Endpoint : PUT /api/currency/{currencyId}

Request Header

- X-API-TOKEN : Token (Mandatory)

Request Body

- currency -> string = not blank, max 100
- rate -> string = not blank, 0-9, digit and dot(.) only
- remark -> string = not blank

```json
{
  "currency": "IDR",
  "rate": "15000",
  "remark": "Indonesia Rupiah"
}
```

Response Body (Success)

```json
{
  "data": {
    "id": 41,
    "currency": "IDR Updated",
    "rate": "15000",
    "remark": "Indonesia Rupiah",
    "lastUpdatedAt": "2024-02-12T10:23:53"
  },
  "errors": null,
  "paging": null
}
```

## Delete Currency

- Endpoint : DELETE /api/currency/{currencyId}

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



