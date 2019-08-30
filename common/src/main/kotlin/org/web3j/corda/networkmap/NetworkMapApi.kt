/*
 * Copyright 2019 Web3 Labs LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.web3j.corda.networkmap

import org.web3j.corda.protocol.CordaService
import org.web3j.corda.protocol.ProxyBuilder
import javax.ws.rs.Path

/**
 * Network Map Service client.
 *
 * **Please note:** The protected parts of this API require JWT authentication.
 *
 * @see [AdminApi.login]
 */
interface NetworkMapApi {

    @get:Path("network-map")
    val networkMap: NetworkMap

    @get:Path("certificate")
    val certificate: Certificate

    @get:Path("certman/api")
    val certMan: CertManApi

    @get:Path("admin/api")
    val admin: AdminApi

    companion object {

        @JvmStatic
        fun build(service: CordaService): NetworkMapApi {
            return ProxyBuilder.build(NetworkMapApi::class.java, service)
        }

        @JvmStatic
        fun build(service: CordaService, token: String): NetworkMapApi {
            return ProxyBuilder.build(NetworkMapApi::class.java, service, token)
        }
    }
}
