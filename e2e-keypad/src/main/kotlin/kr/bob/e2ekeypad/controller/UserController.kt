package kr.bob.e2ekeypad.controller

import kr.bob.e2ekeypadver2.model.User
import kr.bob.e2ekeypadver2.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "User API", description = "API for managing users")
@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

    @Operation(summary = "Add a new user")
    @PostMapping
    fun addUser(
        @RequestParam id: String,
        @RequestParam password: String
    ): ResponseEntity<Void> {
        userService.saveUser(User(id, password))
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "Get user password")
    @GetMapping("/{id}")
    fun getUserPassword(@PathVariable id: String): ResponseEntity<String> {
        val password = userService.getUserPassword(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(password)
    }

    @Operation(summary = "Get all users")
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }
}
