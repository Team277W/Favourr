import requests
import json

data = {
    "title": '4 Loonies',
    "body": 'zsdfxghj',
    "contact": '234567',
    "city": 'qwdfgbn',
    "cash": 20
}

# backend-rqj26lvvaa-uc.a.run.app

x = requests.post(
    url="http://127.0.0.1:3000/api/bounties/",
    headers={'Content-Type': 'application/json'},
    data=json.dumps(data))

print(x)
print(x.json())
