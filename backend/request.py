import requests
import json

data = {
    "userName": "Ashish",
    "bountiesAccepted": [],
    "bountiesCreated": [],
    "totalCash": 0
}

# backend-rqj26lvvaa-uc.a.run.app

x = requests.post(
    url="http://127.0.0.1:8080/api/users/",
    headers={'Content-Type': 'application/json'},
    data=json.dumps(data))

print(x)
print(x.json())
