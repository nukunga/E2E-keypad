package kr.bob.e2ekeypad.repository

import kr.bob.e2ekeypadver2.model.Keypad
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface KeypadRepository : CrudRepository<Keypad, Long>
