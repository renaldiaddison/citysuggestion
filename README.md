# City Suggestion

Link: https://citysuggestion-production.up.railway.app

## City API Spec

### Create City

Endpoint : POST /api/cities

Request Body :

```json
{
    "id": 134,
    "name": "Sample City",
    "asciiName": "Sample City",
    "alternateNames": "Example City, City Example",
    "latitude": 37.7749,
    "longitude": -122.4194,
    "featureClass": "P",
    "featureCode": "PPL",
    "country": "US",
    "cc2": "CA",
    "admin1": "California",
    "admin2": "San Francisco",
    "admin3": "",
    "admin4": "",
    "population": 884363,
    "elevation": 15,
    "dem": 20,
    "timezone": "America/Los_Angeles",
    "modifiedAt": "2022-10-20"
}
```

Response Body (Success) :

```json
{
    "data": {
        "id": 134,
        "name": "Sample City",
        "asciiName": "Sample City",
        "alternateNames": "Example City, City Example",
        "latitude": 37.7749,
        "longitude": -122.4194,
        "featureClass": "P",
        "featureCode": "PPL",
        "country": "US",
        "cc2": "CA",
        "admin1": "California",
        "admin2": "San Francisco",
        "admin3": "",
        "admin4": "",
        "population": 884363,
        "elevation": 15,
        "dem": 20,
        "timezone": "America/Los_Angeles",
        "modifiedAt": "2022-10-20"
    },
    "errors": null
}
```

Response Body (Failed) :

```json
{
    "errors": "City with this ID already exists"
}
```

### Update City

Endpoint : PUT /api/cities/{id}

Request Body :

```json
{
    "name": "Sample City 2",
    "asciiName": "Sample City",
    "alternateNames": "Example City, City Example",
    "latitude": 37.7749,
    "longitude": -122.4194,
    "featureClass": "P",
    "featureCode": "PPL",
    "country": "US",
    "cc2": "CA",
    "admin1": "California",
    "admin2": "San Francisco",
    "admin3": "",
    "admin4": "",
    "population": 884363,
    "elevation": 15,
    "dem": 20,
    "timezone": "America/Los_Angeles",
    "modifiedAt": "2022-10-20"
}
```

Response Body (Success) :

```json
{
    "data": {
        "id": 134,
        "name": "Sample City 2",
        "asciiName": "Sample City",
        "alternateNames": "Example City, City Example",
        "latitude": 37.7749,
        "longitude": -122.4194,
        "featureClass": "P",
        "featureCode": "PPL",
        "country": "US",
        "cc2": "CA",
        "admin1": "California",
        "admin2": "San Francisco",
        "admin3": "",
        "admin4": "",
        "population": 884363,
        "elevation": 15,
        "dem": 20,
        "timezone": "America/Los_Angeles",
        "modifiedAt": "2022-10-20"
    },
    "errors": null
}
```

Response Body (Failed) :

```json
{
    "data": null,
    "errors": "City not found"
}
```

### Get City

Endpoint : GET /api/cities/{id}

Response Body (Success) :

```json
{
    "data": {
        "id": 123,
        "name": "Sample City",
        "asciiName": "Sample City",
        "alternateNames": "Example City, City Example",
        "latitude": 37.7749,
        "longitude": -122.4194,
        "featureClass": "P",
        "featureCode": "PPL",
        "country": "US",
        "cc2": "CA",
        "admin1": "California",
        "admin2": "San Francisco",
        "admin3": "",
        "admin4": "",
        "population": 884363,
        "elevation": 15,
        "dem": 20,
        "timezone": "America/Los_Angeles",
        "modifiedAt": "2024-11-26"
    },
    "errors": null
}
```

Response Body (Failed) :

```json
{
    "data": null,
    "errors": "City not found"
}
```

### Remove City

Endpoint : DELETE /api/cities/{id}

Response Body (Success) :

```json
{
    "data": "OK",
    "errors": null
}
```

Response Body (Failed) :

```json
{
    "data": null,
    "errors": "City not found"
}
```

### Suggest City

Endpoint : GET /api/cities/suggest

Query Parameter :

- q : String (Mandatory)
- latitude : Double
- longitude : Double

Response Body (Success) :

```json
{
    "data": [
        {
            "id": 4361094,
            "name": "Londontowne, MD, US",
            "latitude": 38.93345,
            "longitude": -76.54941,
            "score": 0.8
        }
    ],
    "errors": null
}
```

Response Body (Failed) :

```json
{
    "data": null,
    "errors": "An error occurred: Required request parameter 'q' for method parameter type String is not present, Type: class org.springframework.web.bind.MissingServletRequestParameterException"
}
```
