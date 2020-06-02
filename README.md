# TimescaleDb

TimescaleDB is an open-source time-series database which is optimized for fast ingest and complex queries. We can use it like a traditional relational database, but it also gives competence to all NOSQL databases. It leverages PostgreSQL's foundation[Timescale](https://docs.timescale.com/latest/introduction).

## Installation

To install the Timescale db, we should have a postgre installation setup

```bash
# `lsb_release -c -s` should return the correct codename of your OS
echo "deb http://apt.postgresql.org/pub/repos/apt/ $(lsb_release -c -s)-pgdg main" | sudo tee /etc/apt/sources.list.d/pgdg.list
wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -
sudo apt-get update

#Add TimescaleDB's third party repositry and install Timescale
# Add our PPA
sudo add-apt-repository ppa:timescale/timescaledb-ppa
sudo apt-get update

# Now install appropriate package for PG version
sudo apt install timescaledb-postgresql-12
#configure Timescale
sudo timescaledb-tune
#After installation restart the service
# Restart PostgreSQL instance
sudo service postgresql restart
```

Once the TimescaleDB successfully gets installed, we have to connect to PostgreSQL and create a database with Timescale extension
```bash
psql -U postgres -h localhost
```
```sql
CREATE database tutorial;
-- Connect to the database
\c tutorial
-- Extend the database with TimescaleDB
CREATE EXTENSION IF NOT EXISTS timescaledb CASCADE;
```
```bash
#Now you can directly connect to the selected database
psql -U postgres -h localhost -d tutorial
```
After the successfully connecting to the TimescaleDB database.We can create the [(Hyper)Tables](https://docs.timescale.com/latest/getting-started/creating-hypertables) and indexes on them.
```sql
--Create a normal table
CREATE TABLE "newbornsensex"(
    origintime         TIMESTAMP WITH TIME ZONE NOT NULL,
    newborn    NUMERIC,
    city  TEXT,
    country     TEXT
);
--create respective required indexes
CREATE INDEX ON "newbornsensex"(origintime DESC);
CREATE INDEX ON "newbornsensex"(country, origintime DESC);
--create hypertable on the normal table
SELECT create_hypertable('newbornsensex', 'origintime', chunk_time_interval => 86400000000);
```

The TimeScaleDB is successfully installed as an extension of PostgreSQL,and also we have a hypertable in the timescaledb database.

# Running the applications
To ingest the data into the hypertable , we have a created some dummy timeseries data [data.txt](https://github.com/jayanthbaswa/timescale/blob/master/populationdata.txt)
  - Download the data file into the respective location
  - Download the datasample project and run the [Ingestingtsdb.java](https://github.com/jayanthbaswa/timescale/blob/master/datasample/src/main/java/data/Ingestingtsdb.java)

Now we have the timeseries in the TimescaleDB hypertable.

If you pre-existed GRAFANA , we can create some of the dashboards like below:

   - Metric Column:City
![](https://github.com/jayanthbaswa/timescale/blob/master/examples/city.png)
  
   - Metric Column:Country
![](https://github.com/jayanthbaswa/timescale/blob/master/examples/country.png)

   - Metric Column:None
![](https://github.com/jayanthbaswa/timescale/blob/master/examples/none.png)


We can perform all the standard SQL operatins(like group by,join etc..,) and also the bucketing of timeseries into intervals in the shell.
   - Buckceting and using aggreagations function on timeseries and country
       ![](https://github.com/jayanthbaswa/timescale/blob/master/examples/citytimeseries.png)
   - Buckceting and using aggreagations function on timeseries and city
       ![](https://github.com/jayanthbaswa/timescale/blob/master/examples/countrytimescale.png)
   - Basic Operations like grouping and aggreagations on numeric column
       ![](https://github.com/jayanthbaswa/timescale/blob/master/examples/grouping.png)


## Spark
We can perform all of the operations (reading and writing) onto the Timescale DB by using spark.
Spark just needs the postgresql connection driver to connect to the existing TimescaleDb and to perform all the operations.

   - You can read the data from the hypertable by running the [TimeSeriesReader](https://github.com/jayanthbaswa/timescale/blob/master/SparkProject/src/main/scala/dataframes/TimeSeriesReader.scala)
   - You can perform all the opeartions on the generated dataframe.
   

For benchmarks and differences between PostgreSQL vs TimescaleDb [here](https://docs.timescale.com/latest/introduction/timescaledb-vs-postgres).


