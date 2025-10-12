organizer can input event name, date, time and venue
organizer can set mulitiple ticket types with different prices
organizer can specify total available tickets per type
event appears on the platform after creation

purchase event ticket
want to perchase the correct ticket for and event

[ Database Design and Entity RelationShips ](https://miro.com/app/board/uXjVJC_Ymcs=/?share_link_id=897811772590)

## Module Structure

- Design the event organizer's REST API endpoints
- Design the event attendee's REST API endpoints
- Design the event staff's REST API endpoints

| Methord | Endpoints                                                               | Usage                      | Request Body                       |
| ------- | ----------------------------------------------------------------------- | -------------------------- | ---------------------------------- |
| POST    | /api/events                                                             | Create Event               | Request Body:event                 |
| GET     | /api/events                                                             | List Events                |                                    |
| PUT     | /api/events/{event_id}                                                  | Update Event               |                                    |
| GET     | /api/events/{event_id}                                                  | Retrive Event              |                                    |
| GET     | /api/events/{event_id}/tickets                                          | List Ticket Sales          |                                    |
| GET     | /api/events/{event_id}/tickets/{ticket_id}                              | Retrive Ticket Sale        | Request Body : Partial Event       |
| PATCH   | /api/events/{event_id}/tickets                                          | Partial Update             |                                    |
| Get     | /api/events/{event_id}/ticket-types                                     | List Ticket Type           |                                    |
| Get     | /api/events/{event_id}/ticket-types/{ticket_type_id}                    | Retrive Ticket Type        |                                    |
| PATCH   | /api/events/{event_id}/ticket-types/{ticket_type_id}                    | Partial Update Ticket Type | Request Body : Partial Ticket Type |
| GET     | /api/published-event/{published_event_id}                               | Search Published Event     |                                    |
| POST    | /api/published-event/{published_event_id}/ticket-types/{ticket_type_id} | Purchase Ticket            |                                    |
| GET     | /api/tickets                                                            | List Tickets (for user)    |                                    |
| GET     | /api/tickets/{ticket_id}                                                | Retrive Ticket (for user)  |                                    |
