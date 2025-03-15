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
 * https://neo4j.com/docs/jdbc-manual/current/configuration/
 */
public class Neo4jDriver extends GenericJDBCDriver {

	protected static final String				DEFAULT_PROTOCOL			= "";
	protected static final Map<String, String>	AVAILABLE_PROTOCOLS			= Map.of( "bolt", "Bolt Protocol" );
	protected static final String				DEFAULT_HOST				= "localhost";
	protected static final String				DEFAULT_PORT				= "7687";

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
		this.defaultCustomParams	= Struct.of(
		    "enableSQLTranslation", "true",
		    "debug", true
		);
		this.defaultProperties		= DEFAULT_HIKARI_PROPERTIES;
		this.protocol				= DEFAULT_PROTOCOL;
	}

	@Override
	public String buildConnectionURL( DatasourceConfig config ) {
		// Validate the database
		String database = ( String ) config.properties.getOrDefault( "database", "" );
		if ( database.isEmpty() ) {
			throw new IllegalArgumentException( "The database property is required for the Neo4j JDBC Driver" );
		}

		// Validate the host
		String host = ( String ) config.properties.getOrDefault( "host", DEFAULT_HOST );
		if ( host.isEmpty() ) {
			host = DEFAULT_HOST;
		}

		// Verify if we have a protocol
		this.protocol = ( String ) config.properties.getOrDefault( "protocol", DEFAULT_PROTOCOL );
		if ( !this.protocol.isEmpty() && !AVAILABLE_PROTOCOLS.containsKey( this.protocol ) ) {
			throw new IllegalArgumentException(
			    String.format(
			        "The protocol '%s' is not valid for the Neo4j Driver. Available protocols are %s",
			        this.protocol,
			        AVAILABLE_PROTOCOLS.keySet().toString()
			    )
			);
		}
		// Append the : to the protocol if it exists

		if ( !protocol.isEmpty() ) {
			protocol += ":";
		}

		// Port
		String port = StringCaster.cast( config.properties.getOrDefault( "port", DEFAULT_PORT ) );
		if ( port.isEmpty() || port.equals( "0" ) ) {
			port = DEFAULT_PORT;
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
