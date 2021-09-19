import requests
import json

data = {
    "userName": "Ashish",
    "bountiesAccepted": [],
    "bountiesCreated": [],
    "totalCash": 0
}

# backend-rqj26lvvaa-uc.a.run.app

x = requests.get(
    url="http://127.0.0.1:8080/api/bounties/accepted/6146fa2de7398e93b883997c")

print(x)
print(x.json())

# ,
#     headers={'Content-Type': 'application/json'},
#     data=json.dumps(data)
