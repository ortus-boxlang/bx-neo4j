# ⚡︎ BoxLang Module: Neo4J JDBC Driver

```
|:------------------------------------------------------:|
| ⚡︎ B o x L a n g ⚡︎
| Dynamic : Modular : Productive
|:------------------------------------------------------:|
```

<blockquote>
	Copyright Since 2023 by Ortus Solutions, Corp
	<br>
	<a href="https://www.boxlang.io">www.boxlang.io</a> |
	<a href="https://www.ortussolutions.com">www.ortussolutions.com</a>
</blockquote>

<p>&nbsp;</p>


----

This module brings Neo4J JDBC Drivers to BoxLang.  Just install this module and you will be able to connect to Neo4J databases via JDBC datasource connections.

```bash
install-bx-module bx-neo4j
```

Then you can define your datasources in your `boxlang.json`, in your `Application.bx|cfc` or inline.

```json
// https://boxlang.ortusbooks.com/getting-started/configuration/datasources
// boxlang.json
{
	"datasources": {
		"neo4j": {
			"driver" : "neo4j",
			"database": "myDatabase",
			"host": "localhost",
			"port": 7687,
			"username": "neo4j",
			"password": "password"
		}
	}
}
```


## What is Neo4J?

Neo4j is a graph database management system developed by Neo4j, Inc. Described by its developers as an ACID-compliant transactional database with native graph storage and processing, Neo4j is the most popular graph database according to DB-Engines ranking, and the 22nd most popular database overall.

High-speed graph database with unbounded scale, security, and data integrity for mission-critical intelligent applications.

More information can be found at [Neo4J](https://neo4j.com/product/neo4j-graph-database/)

## Ortus Sponsors

BoxLang is a professional open-source project and it is completely funded by the [community](https://patreon.com/ortussolutions) and [Ortus Solutions, Corp](https://www.ortussolutions.com). Ortus Patreons get many benefits like a cfcasts account, a FORGEBOX Pro account and so much more. If you are interested in becoming a sponsor, please visit our patronage page: [https://patreon.com/ortussolutions](https://patreon.com/ortussolutions)

### THE DAILY BREAD

> "I am the way, and the truth, and the life; no one comes to the Father, but by me (JESUS)" Jn 14:1-12
