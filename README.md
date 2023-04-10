# Getting Started

First of all, you need to start the Postgres instance and Redis DB, and change application.yml for your postgres credential.

### Steps

1. Create level 
.../api/create-level
2. Create work places
.../api/create-place
3. Create causes
.../api/create-cause
4. Create fixers
.../api/create-fixer
5. Create downtimes (1 request will create 100_000 rows)
.../api/create-dwt

6. You can use .../graphiql for fetch data

Example

````
```
   query FIND_BY_CAUSE {
   findFirstByCauseSql(causeId: "01GT29SSVXMFCSJPTJR1780AN8") {
       id
       beginDate
       endDate
       }
   }

    query GET_ALL{
        searchSql(filter: {workPlace: ["01GT29PEHGZ088YSH2PJMYA95D"],
        beginDate: ["2021-09-28T00:00:00.000000", "2021-09-29T00:00:00.000000"],
        cause: [ "01GT29SSVXMFCSJPTJR1780AN8"]}) {
            id
            beginDate
            endDate
            area {
                id
                name
            }
        }
    }
```
````