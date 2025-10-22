```bash
pgcli -h localhost -p 5434 -U postgres -d tickets
```

```bash
curl -X POST http://localhost:8080/api/v1/events \
  -H "Content-Type: application/json" \
  -d '{
    "name": "BroFest 2025",
    "start": "2025-11-10T18:00:00",
    "end": "2025-11-10T23:00:00",
    "venue": "Cyber Stadium",
    "salesStart": "2025-10-20T00:00:00",
    "salesEnd": "2025-11-09T23:59:59",
    "status": "PUBLISHED",
    "ticketTypes": [
      {
        "name": "VIP",
        "price": 2999.99,
        "description": "Unlimited Red Bull and regret",
        "totalAvalaible": 100
      },
      {
        "name": "General",
        "price": 999.99,
        "description": "Entry + free panic attack",
        "totalAvalaible": 500
      }
    ]
  }' | jq
```

```bash
ACCESS_TOKEN=$(curl -s -X POST "http://localhost:9090/realms/bro-realm/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "client_id=springboot-api" \
  -d "username=bro" \
  -d "password=1234" \
  -d "grant_type=password" | jq -r '.access_token')
```
