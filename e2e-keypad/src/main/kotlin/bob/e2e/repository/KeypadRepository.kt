package bob.e2e.repository

import bob.e2e.model.Keypad
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface KeypadRepository : CrudRepository<Keypad, String>