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
package org.web3j.corda.model

import java.time.Duration

data class SignedTransaction(
    val signatures: List<String>,
    val references: List<String>,
    val networkParametersHash: String,
    val coreTransaction: CoreTransaction,
    val notaryChangeTransaction: Boolean,
    val missingSigners: List<PublicKey>
)

data class CoreTransaction(
    val componentGroups: List<ComponentGroup>,
    val privacySalt: String,
    val attachments: List<String>,
    val inputs: List<String>,
    val references: List<String>,
    val outputs: List<Output?>,
    val commands: List<Commands>,
    val notary: Party,
    val timeWindow: TimeWindow,
    val networkParametersHash: String,
    val id: String,
    val requiredSigningKeys: List<String>,
    val `groupHashes$core`: List<String>,
    val `groupsMerkleRoots$core`: Map<String, String>,
    val `availableComponentNonces$core`: Map<String, List<String>>,
    val `availableComponentHashes$core`: Map<String, List<String>>,
    val availableComponentGroups: List<List<Any?>?>
)
data class TimeWindow(
    val fromTime: Long,
    val untilTime: Long,
    val midpoint: Long,
    val length: Duration
)
data class Commands(
    val value: Value?,
    val signers: List<String>
)
data class Value(val value: String?)
data class ComponentGroup(
    val groupIndex: Int,
    val components: List<String>
)

data class Output(
    val data: Data?,
    val contract: String?,
    val notary: Party?,
    val encumbrance: String?,
    val constraint: Constraint?
)
data class Constraint(val attachmentId: String)
data class Data(
    val amount: Money,
    val lender: Party,
    val borrower: Party,
    val paid: Money,
    val linearId: LinearId,
    val participants: List<Party>
)

data class LinearId(
    val externalId: String?,
    val id: String
)

data class Money(
    val quantity: Int,
    val displayTokenSize: Float,
    val token: String
)
