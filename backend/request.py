import requests

data = {
    "title": '4 Loonies',
    "body": 'zsdfxghj',
    "contact": '234567',
    "city": 'qwdfgbn',
    "cash": 20
}

x = requests.post(
    url="https://backend-rqj26lvvaa-uc.a.run.app/api/bounties/", data=data)

print(x)
print(x.json())
