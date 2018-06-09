/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.greglturnquist.payroll

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

/**
 * @author Greg Turnquist
 */
@Component
class DatabaseLoader @Autowired
constructor(val employees: EmployeeRepository,
            val managers: ManagerRepository) : CommandLineRunner {

    @Throws(Exception::class)
    override fun run(vararg strings: String) {

        val greg = managers.save(
                Manager(name = "greg", password = SecurityConfiguration.PASSWORD_ENCODER.encode("turnquist"), roles = listOf("ROLE_MANAGER"))
        )

        val oliver = managers.save(
                Manager(name = "oliver", password = SecurityConfiguration.PASSWORD_ENCODER.encode("gierke"), roles = listOf("ROLE_MANAGER"))
        )

        SecurityContextHolder.getContext().setAuthentication(
                UsernamePasswordAuthenticationToken("greg", "doesn't matter",
                        AuthorityUtils.createAuthorityList("ROLE_MANAGER")))

        employees.save(Employee(firstName = "Frodo", lastName = "Baggins", description = "ring bearer", manager = greg))
        employees.save(Employee(firstName = "Bilbo", lastName = "Baggins", description = "burglar", manager = greg))
        employees.save(Employee(firstName = "Gandalf", lastName = "the Grey", description = "wizard", manager = greg))

        SecurityContextHolder.getContext().setAuthentication(
                UsernamePasswordAuthenticationToken("oliver", "doesn't matter",
                        mapToAuthorities(listOf("ROLE_MANAGER"))
                ))

        employees.save(Employee(firstName = "Samwise", lastName = "Gamgee", description = "gardener", manager = oliver))
        employees.save(Employee(firstName = "Merry", lastName = "Brandybuck", description = "pony rider", manager = oliver))
        employees.save(Employee(firstName = "Peregrin", lastName = "Took", description = "pipe smoker", manager = oliver))

        SecurityContextHolder.clearContext()
    }

}

fun mapToAuthorities(y: List<String>) = y.map { SimpleGrantedAuthority(it) }