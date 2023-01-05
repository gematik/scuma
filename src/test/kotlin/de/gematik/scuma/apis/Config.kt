package de.gematik.scuma.apis

import de.gematik.kether.crypto.AccountStore
import de.gematik.kether.crypto.accountStore
import de.gematik.kether.eth.types.Address
import de.gematik.kether.rpc.Rpc
import java.util.*

/**
 * Created by rk on 07.11.2022.
 * gematik.de
 */
val testResource = UUID(0, 1)
val contractId = Address("0xba1a4f08001416a630e19e34abd260f039874e92")
val ownerId = accountStore.getAccount(AccountStore.TEST_ACCOUNT_1).address
val userId = accountStore.getAccount(AccountStore.TEST_ACCOUNT_2).address
val protectionAuthorizationId = accountStore.getAccount(AccountStore.TEST_ACCOUNT_4).address
val rpc = Rpc("http://ethereum1.lab.gematik.de:8545", "ws://ethereum1.lab.gematik.de:8546")
val controlApi = ControlApi(contractId, ownerId, rpc)
val protectionApi = ProtectionApi(contractId, protectionAuthorizationId, rpc)

