## CSV-File API Spec

# Upload File

- Endpoint : POST /api/upload/csv

Request Header

- X-API-TOKEN : Token (Mandatory)

Request Body (Form Data)

- csv -> file = csv file

```json
{
  "csv": "itemcsv.csv"
}
```

Response Body (Success)

```json
{
  "data": {
    "fileName": "itemcsv_2024-02-12_11-18-32.csv"
  },
  "errors": null,
  "paging": null
}
```

# Read CSV

- Endpoint : GET /api/csv/{fileName}

Request Header

- X-API-TOKEN : Token (Mandatory)


Response Body (Success)

```json
{
  "data": [
    {
      "id": null,
      "noPart": "25PR",
      "description": "Turbo Galaxy",
      "hsCode": 123121243,
      "itemType": "Barang Mentah",
      "unit": {
        "id": null,
        "name": "Kg"
      }
    },
    {
      "id": null,
      "noPart": "32ST",
      "description": "Iphone 14",
      "hsCode": 12432123,
      "itemType": "Barang Jadi",
      "unit": {
        "id": null,
        "name": "Pcs"
      }
    }
  ],
  "errors": null,
  "paging": null
}
```

# Find All CSV File

- Endpoint : GET /api/csv

Request Header

- X-API-TOKEN : Token (Mandatory)

Response Body (Success)

```json
{
  "data": [
    {
      "fileName": "itemcsv_2024-02-09_20-11-27.csv"
    },
    {
      "fileName": "itemcsv_2024-02-12_11-18-32.csv"
    }
  ],
  "errors": null,
  "paging": null
}
```

# Insert to Item

- Endpoint : POST /api/csv/{fileName}

Request Header

- X-API-TOKEN : Token (Mandatory)

Response Body (Success)

```json
{
  "data": [
    {
      "id": "0a51c1f6-2543-4a9a-aec9-43aea76bba97",
      "noPart": "25PR",
      "description": "Turbo Galaxy",
      "hsCode": 123121243,
      "itemType": "Barang Mentah",
      "unit": {
        "id": 94,
        "name": "Kg"
      }
    },
    {
      "id": "af39ae26-0562-469c-8d0e-f92b6e9cb363",
      "noPart": "32ST",
      "description": "Iphone 14",
      "hsCode": 12432123,
      "itemType": "Barang Jadi",
      "unit": {
        "id": 90,
        "name": "Pcs"
      }
    }
  ],
  "errors": null,
  "paging": null
}
```

# Delete CSV File

- Endpoint : DELETE /api/csv/{fileName}

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