package kr.bob.e2ekeypadver2.model

import jakarta.persistence.*

@Entity
class Keypad(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ElementCollection
    @CollectionTable(name = "key_mappings", joinColumns = [JoinColumn(name = "keypad_id")])
    @MapKeyColumn(name = "hash")
    @Column(name = "key_value")
    var keyMappings: Map<String, String> = HashMap()
)
