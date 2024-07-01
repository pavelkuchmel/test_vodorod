GET request:
    http://localhost:8080/currencies/load
    Params:
    - date (In the format: "yyyy-MM-dd")
    Returns the number of Rate objects loaded into the database.
    Possible status codes:
    - 502: no access to API (https://api.nbrb.by/exrates/rates)
    - 404: exchange rates for this date have already been loaded or the date is incorrect
Example:
  Request:
  http://localhost:8080/currencies/load?date=2024-07-01
  Response:
  Status = 200
  Body = Currencies have been successfully loaded into the database. Total:  31

GET request:
    http://localhost:8080/currencies/get
    Params:
    - date (In the format: "yyyy-MM-dd")
    - curCode (Three currency symbols (ISO 4217) (USD, EUR, CNY))
    Returns a JSON object containing fields:
    - date
    - name of the currency
    - currency scale
    - rate
    Possible status codes:
    - 502: no access to API (https://api.nbrb.by/exrates/rates)
    - 406: invalid parameters
Example:
  Request:
  http://localhost:8080/currencies/get?date=2024-07-01&curCode=USD
  Response:
  Status = 200
  Body = {"Date":"2024-07-01","Cur_Scale":1,"Cur_Name":"Доллар США","Cur_OfficialRate":3.1662}
