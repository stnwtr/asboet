# ASBoet

Very small API to fetch next duties from [lv.svs](https://lv.svs-system.at/).

## Entities

Here a list of all entities.

### DutyType

The type (car, office, ...) the worker is on.

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
}
```

### DutyTime

Day or night shift.

```graphql
enum DutyTime {
    DAY
    NIGHT
}
```

### DutyWorker

A worker represented by name and the working times.

```graphql
type DutyWorker {
    name: String!
    begin: String!  # Time in HH:mm
    end: String!  # Time in HH:mm
}
```

### Duty

A whole duty with all possible properties.

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

A list of endpoints (so far ...).

### /duties?date={date}

Used to get a list of duties for a specific day.

| Query Param | Description                   |
|-------------|-------------------------------|
| `date`      | The date in yyyy-MM-dd format |

Example: `https://localhost:62461/api/v1/duties?date=2022-07-01`

Returns `200 OK` with a body: `[Duty]!` or `400 Bad Request` or `403 Forbidden`.

### /range?from={from}&to={to}

List all duties between two dates.

| Query Param | Description                        |
|-------------|------------------------------------|
| `from`      | The beginning in yyyy-MM-dd format |
| `to`        | The end date in yyyy-MM-dd format  |

Example: `https://localhost:62461/api/v1/range?from=2022-06-27&to=2022-07-03`

Returns `200 OK` with a body: `[Duty]!` or `400 Bad Request` or `403 Forbidden`.

### /personal

Get your personal duty list.

Example: `https://localhost:62461/api/v1/personal`

Returns `200 OK` with a body: `[Duty]!` or `400 Bad Request` or `403 Forbidden`.

## Authentication

Basic authentication is needed in order to fetch any duties. The header field `Authorization` is doing this job.

Example: `Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=`
