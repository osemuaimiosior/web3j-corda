package org.web3j.corda.protocol

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.Annotations
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder

class CordaService(val uri: String) : AutoCloseable {
    internal val client: Client by lazy {
        val jsonProvider = JacksonJaxbJsonProvider(jacksonObjectMapper(), arrayOf(Annotations.JACKSON))
        ClientBuilder.newClient().register(jsonProvider)
    }

    override fun close() {
        client.close()
    }
}
