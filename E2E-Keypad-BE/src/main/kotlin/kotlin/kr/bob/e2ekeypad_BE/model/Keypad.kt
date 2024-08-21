package kr.bob.e2ekeypad_BE.model

import jakarta.persistence.*
import java.util.*
import kotlin.collections.HashMap

@Entity
class Keypad(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:String = UUID.randomUUID().toString(),

    @ElementCollection
    @CollectionTable(name = "key_mappings", joinColumns = [JoinColumn(name = "keypad_id")])
    @MapKeyColumn(name = "hash")
    @Column(name = "key_value")
    var keyMappings: Map<String, String> = HashMap()
)
