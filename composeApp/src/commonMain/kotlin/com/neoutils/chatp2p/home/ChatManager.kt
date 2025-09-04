package com.neoutils.chatp2p.home

import com.neoutils.chatp2p.protocols.Chat
import com.neoutils.chatp2p.protocols.ChatBinding
import com.neoutils.chatp2p.protocols.ChatController
import io.libp2p.core.PeerId
import io.libp2p.core.dsl.host
import io.libp2p.discovery.MDnsDiscovery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.Inet4Address

class ChatManager(
    private val scope: CoroutineScope,
) {

    private val _onMessage = Channel<Message>(Channel.UNLIMITED)
    val onMessage = _onMessage.receiveAsFlow()

    private val chatProtocol = Chat(
        onMessage = { peerId, message ->
            _onMessage.trySend(
                Message(
                    myself = false,
                    author = peerId.toBase58(),
                    body = message,
                )
            )
        }
    )

    private val address = Inet4Address.getByName("0.0.0.0")

    private val host = host {
        protocols {
            +chatProtocol
        }
        network {
            listen("/ip4/${address.hostAddress}/tcp/0")
        }
    }

    private val connections
        get() = host
            .network
            .connections
            .distinctBy { it.secureSession().remoteId }

    private val knownNodes = mutableSetOf<PeerId>()

    init {
        host.start().thenAccept {
            _onMessage.trySend(
                Message(
                    myself = false,
                    author = "ChatP2P: Connected",
                    body = "${host.listenAddresses().first()}",
                )
            )

            knownNodes.add(host.peerId)
        }

        host.addConnectionHandler {

            val peerId = it.secureSession().remoteId

            if (knownNodes.contains(peerId)) return@addConnectionHandler

            knownNodes.add(peerId)

            _onMessage.trySend(
                Message(
                    myself = false,
                    author = "ChatP2P: ${if (it.isInitiator) "New Peer" else "Found Peer"}",
                    body = "$peerId",
                )
            )
        }

        scope.launch {
            val peerFinder = MDnsDiscovery(host, address = address)

            peerFinder.newPeerFoundListeners += newPeed@{ peerInfo ->

                if (knownNodes.contains(peerInfo.peerId)) return@newPeed

                host.network.connect(
                    peerInfo.peerId,
                    peerInfo.addresses.first()
                )
            }

            peerFinder.start()
        }
    }

    fun sendMessage(message: String) {
        connections.map { connection ->
            host.newStream<ChatController>(
                listOf(ChatBinding.ANNOUNCE),
                connection
            )
        }.forEach { (_, controller) ->
            controller.thenAccept {
                it.send(message)
            }
        }
    }
}