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

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.security.access.prepost.PreAuthorize

/**
 * @author Greg Turnquist
 */
// tag::code[]
@PreAuthorize("hasRole('ROLE_MANAGER')")
interface EmployeeRepository : PagingAndSortingRepository<Employee, Long> {

    @PreAuthorize("#employee?.manager == null or #employee?.manager?.name == authentication?.name")
    fun save(@Param("employee") employee: Employee): Employee

    @PreAuthorize("@employeeRepository.findOne(#id)?.manager?.name == authentication?.name")
    override fun delete(@Param("id") id: Long)

    @PreAuthorize("#employee?.manager?.name == authentication?.name")
    override fun delete(@Param("employee") employee: Employee)

}
// end::code[]
