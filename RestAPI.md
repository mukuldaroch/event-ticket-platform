## Create Event

POST /api/events

Request Body:event

## List Events

GET /api/events

## Update Event

PUT /api/events/{event_id}

## Retrive Event

GET /api/events/{event_id}

## List Ticket Sales

GET /api/events/{event_id}/tickets

## Retrive Ticket Sale

GET /api/events/{event_id}/tickets/{ticket_id}

## Partial Update

PATCH /api/events/{event_id}/tickets
Request Body : Partial Event

## List Ticket Type

Get /api/events/{event_id}/ticket-types

## Retrive Ticket Type

Get /api/events/{event_id}/ticket-types/{ticket_type_id}

## Partial Update Ticket Type

PATCH /api/events/{event_id}/ticket-types/{ticket_type_id}
Request Body : Partial Ticket Type

## Search Published Event

GET /api/published-event/{published_event_id}

## Purchase Ticket

POST /api/published-event/{published_event_id}/ticket-types/{ticket_type_id}

## List Tickets (for user)

GET /api/tickets

## Retrive Ticket (for user)

GET /api/tickets/{ticket_id}
