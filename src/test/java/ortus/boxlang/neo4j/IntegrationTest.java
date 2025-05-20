/**
 * [BoxLang]
 *
 * Copyright [2023] [Ortus Solutions, Corp]
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package ortus.boxlang.neo4j;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ortus.boxlang.runtime.config.segments.DatasourceConfig;
import ortus.boxlang.runtime.scopes.Key;
import ortus.boxlang.runtime.types.Struct;

/**
 * This loads the module and runs an integration test on the module.
 */
public class IntegrationTest extends BaseIntegrationTest {

	@DisplayName( "Test module load and query execution" )
	@Test
	public void testModuleLoads() {
		// Then
		assertThat( moduleService.getRegistry().containsKey( moduleName ) ).isTrue();
		// Verify things got registered
		assertThat( runtime.getDataSourceService().hasDriver( Key.of( "neo4j" ) ) ).isTrue();
		// Register a named datasource
		runtime.getConfiguration().datasources.put(
		    Key.of( "neo4j" ),
		    new DatasourceConfig( moduleName ).process(
		        Struct.of(
		            "driver", "neo4j",
		            "database", "neo4j",
		            "username", "neo4j",
		            "password", "boxlangRocks"
		        )
		    )
		);

		// @formatter:off
		runtime.executeSource(
		    """
				result = queryExecute(
					"MATCH (n) RETURN n LIMIT 1",
					{},
					{ "datasource" : "neo4j" }
				);
			""",
		    context
		);
		// @formatter:on
	}
}
