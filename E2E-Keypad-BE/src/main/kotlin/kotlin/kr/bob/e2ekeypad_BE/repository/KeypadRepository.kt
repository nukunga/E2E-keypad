package kr.bob.e2ekeypad_BE.repository

import kr.bob.e2ekeypad_BE.model.Keypad
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface KeypadRepository : CrudRepository<Keypad, Long>
