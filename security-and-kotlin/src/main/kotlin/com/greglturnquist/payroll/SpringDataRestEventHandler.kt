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
import org.springframework.data.rest.core.annotation.HandleBeforeCreate
import org.springframework.data.rest.core.annotation.HandleBeforeSave
import org.springframework.data.rest.core.annotation.RepositoryEventHandler
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Component
@RepositoryEventHandler(Employee::class)
class SpringDataRestEventHandler @Autowired
constructor(private val managerRepository: ManagerRepository) {

    @HandleBeforeCreate
    @HandleBeforeSave
    fun applyUserInformationUsingSecurityContext(employee: Employee) {

        val name = SecurityContextHolder.getContext().getAuthentication().getName()
        var manager = this.managerRepository.findByName(name)
        if (manager == null) {
            val newManager = Manager(name, "ROLE_MANAGER")
            manager = this.managerRepository.save(newManager)
        }
        employee.manager = manager
    }
}
// end::code[]
