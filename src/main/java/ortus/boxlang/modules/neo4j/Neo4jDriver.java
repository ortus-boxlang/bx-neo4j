/**
 * [BoxLang]
 *
 * Copyright [2023] [Ortus Solutions, Corp]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ortus.boxlang.modules.neo4j;

import java.util.Map;

import ortus.boxlang.runtime.config.segments.DatasourceConfig;
import ortus.boxlang.runtime.dynamic.casters.StringCaster;
import ortus.boxlang.runtime.jdbc.drivers.DatabaseDriverType;
import ortus.boxlang.runtime.jdbc.drivers.GenericJDBCDriver;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.types.IStruct;
import ortus.boxlang.runtime.types.Struct;

/**
 * The Neo4jDriver
 * https://github.com/neo4j/neo4j-jdbc
 */
public class Neo4jDriver extends GenericJDBCDriver {

	protected static final String				DEFAULT_PROTOCOL			= "";
	protected static final Map<String, String>	AVAILABLE_PROTOCOLS			= Map.of();

	/**
	 * Default Hikari Properties For Performance
	 * https://cdn.oreillystatic.com/en/assets/1/event/21/Connector_J%20Performance%20Gems%20Presentation.pdf
	 */
	protected static final IStruct				DEFAULT_HIKARI_PROPERTIES	= Struct.of(

	);

	/**
	 * The protocol in use for the jdbc connection
	 */
	protected String							protocol					= DEFAULT_PROTOCOL;

	/**
	 * Constructor
	 */
	public Neo4jDriver() {
		super();
		this.name					= new Key( "neo4j" );
		this.type					= DatabaseDriverType.OTHER;
		this.driverClassName		= "org.neo4j.jdbc.Neo4jDriver";
		this.defaultDelimiter		= "&";
		this.defaultCustomParams	= Struct.of();
		this.defaultProperties		= DEFAULT_HIKARI_PROPERTIES;
	}

	@Override
	public String buildConnectionURL( DatasourceConfig config ) {
		// Validate the database
		String database = ( String ) config.properties.getOrDefault( "database", "" );
		if ( database.isEmpty() ) {
			throw new IllegalArgumentException( "The database property is required for the MySQL JDBC Driver" );
		}

		// Validate the host
		String host = ( String ) config.properties.getOrDefault( "host", "localhost" );
		if ( host.isEmpty() ) {
			host = "localhost";
		}

		// Verify if we have a protocol
		this.protocol = ( String ) config.properties.getOrDefault( "protocol", "" );
		if ( protocol.length() > 0 && !AVAILABLE_PROTOCOLS.containsKey( protocol ) ) {
			throw new IllegalArgumentException(
			    String.format(
			        "The protocol '%s' is not valid for the MySQL Driver. Available protocols are %s",
			        this.protocol,
			        AVAILABLE_PROTOCOLS.keySet().toString()
			    )
			);
		}
		// Append the : to the protocol if it exists

		if ( protocol.length() > 0 ) {
			protocol += ":";
		}

		// Port
		String port = StringCaster.cast( config.properties.getOrDefault( "port", "7687" ) );
		if ( port.isEmpty() || port.equals( "0" ) ) {
			port = "7687";
		}

		// Build the connection URL with no host info
		return String.format(
		    "jdbc:neo4j:%s//%s:%s/%s?%s",
		    protocol,
		    host,
		    port,
		    database,
		    customParamsToQueryString( config )
		);
	}

}
