# ASBoet

Very small API to fetch next duties from [lv.svs](https://lv.svs-system.at/).

## Entities

Here a list of all entities.

**DutyType**

```graphql
enum DutyType {
    VEHICLE_340
    VEHICLE_341
    VEHICLE_342
    VEHICLE_343
    VEHICLE_344
    VEHICLE_345
    OFFICE
    JOURNAL
    OTHER
    UNKNOWN
}
```

**DutyTime**

```graphql
enum DutyTime {
    DAY
    NIGHT
    UNKNOWN
}
```

**DutyWorker**

```graphql
type DutyWorker {
    name: String!
    begin: String!  # Time in HH:mm
    end: String!  # Time in HH:mm
}
```

**Duty**

```graphql
type Duty {
    date: String!  # Date in yyyy-MM-dd
    dutyType: DutyType!
    dutyTime: DutyTime!
    drivers: [DutyWorker]!
    firstParamedics: [DutyWorker]!
    secondParamedics: [DutyWorker]!
}
```

## Endpoints

List of endpoints (so far ...). (())

**/duty**

Used to get a list of duties for a specific day.

Example: `https://localhost:62461/api/v1/duty?username=flastname&password=secret&date=2022-06-30`

| Query Param | Description                 |
|-------------|-----------------------------|
| `username`  | Simply the username         |
| `password`  | The right password          |
| `date`      | A date in yyyy-MM-dd format |

Returns `200 OK` with a body: `[Duty]!` or `400 Bad Request` or `403 Forbidden`.

**/personal**

Get your personal duty list.

Example: `https://localhost:62461/api/v1/personal?username=flastname&password=secret`

| Query Param | Description                 |
|-------------|-----------------------------|
| `username`  | Simply the username         |
| `password`  | The right password          |

Returns `200 OK` with a body: `[Duty]!` or `400 Bad Request` or `403 Forbidden`.

**/range**

List all duties between two dates.

Example: `https://localhost:62461/api/v1/range?username=flastname&password=secret&from=2022-06-28&to=2022-07-10`

| Query Param | Description                        |
|-------------|------------------------------------|
| `username`  | Simply the username                |
| `password`  | The right password                 |
| `from`      | The beginning in yyyy-MM-dd format |
| `to`        | The end date in yyyy-MM-dd format  |

Returns `200 OK` with a body: `[Duty]!` or `400 Bad Request` or `403 Forbidden`.

## Swagger

// TBD
