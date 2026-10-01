import com.apollographql.apollo.ApolloClient
import com.apollographql.mockserver.MockServer
import com.apollographql.mockserver.enqueueString
import com.example.GetUserFragmentedQuery
import com.example.GetUserStandardQuery
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExceptionPathTest {
    private val nullStringPayload = """
        {
          "data": {
            "user": {
              "id": null
            }
          }
        }
    """.trimIndent()

    private lateinit var client: ApolloClient

    @Before
    fun setup() = runBlocking {
        val mockServer = MockServer()
        mockServer.enqueueString(nullStringPayload)
        client = ApolloClient.Builder().serverUrl(mockServer.url()).build()
    }

    @Test
    fun `Standard query reports null String type and JSON path`() =
        runTest {
            val response = client.query(GetUserStandardQuery()).execute()

            assertEquals(
                "Expected a string but was NULL at path data.user.id",
                response.exception?.message,
            )
        }

    @Test
    fun `Fragmented query drops null String error details`() =
        runTest {
            val response = client.query(GetUserFragmentedQuery()).execute()

            assertEquals(
                "Expected a string but was NULL at path data.user.id",
                response.exception?.message,
            )
        }
}
