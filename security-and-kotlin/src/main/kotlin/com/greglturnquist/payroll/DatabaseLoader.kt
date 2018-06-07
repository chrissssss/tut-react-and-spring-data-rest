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
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Component
class DatabaseLoader @Autowired
constructor(val employees: EmployeeRepository,
            val managers: ManagerRepository) : CommandLineRunner {

    @Throws(Exception::class)
    override fun run(vararg strings: String) {

        val greg = managers.save(
                Manager("greg", SecurityConfiguration.PASSWORD_ENCODER.encode("turnquist"), "ROLE_MANAGER")
        )

        val oliver = managers.save(
                Manager("oliver", SecurityConfiguration.PASSWORD_ENCODER.encode("gierke"), "ROLE_MANAGER")
        )

        SecurityContextHolder.getContext().setAuthentication(
                UsernamePasswordAuthenticationToken("greg", "doesn't matter",
                        AuthorityUtils.createAuthorityList("ROLE_MANAGER")))

        employees.save(Employee("Frodo", "Baggins", "ring bearer", greg))
        employees.save(Employee("Bilbo", "Baggins", "burglar", greg))
        employees.save(Employee("Gandalf", "the Grey", "wizard", greg))

        SecurityContextHolder.getContext().setAuthentication(
                UsernamePasswordAuthenticationToken("oliver", "doesn't matter",
                        AuthorityUtils.createAuthorityList("ROLE_MANAGER")))

        employees.save(Employee("Samwise", "Gamgee", "gardener", oliver))
        employees.save(Employee("Merry", "Brandybuck", "pony rider", oliver))
        employees.save(Employee("Peregrin", "Took", "pipe smoker", oliver))

        SecurityContextHolder.clearContext()
    }
}
// end::code[]