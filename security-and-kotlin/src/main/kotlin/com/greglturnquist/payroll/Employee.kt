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

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Version

import com.fasterxml.jackson.annotation.JsonIgnore

import java.util.Objects

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Entity
class Employee {

    @Id
    @GeneratedValue
    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var description: String? = null

    @Version
    @JsonIgnore
    var version: Long? = null

    @ManyToOne
    var manager: Manager? = null

    private constructor() {}

    constructor(firstName: String, lastName: String, description: String, manager: Manager) {
        this.firstName = firstName
        this.lastName = lastName
        this.description = description
        this.manager = manager
    }

    override fun toString(): String {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\''.toString() +
                ", lastName='" + lastName + '\''.toString() +
                ", description='" + description + '\''.toString() +
                ", version=" + version +
                ", manager=" + manager +
                '}'.toString()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val employee = o as Employee?
        return id == employee!!.id &&
                firstName == employee.firstName &&
                lastName == employee.lastName &&
                description == employee.description &&
                version == employee.version &&
                manager == employee.manager
    }

    override fun hashCode(): Int {

        return Objects.hash(id, firstName, lastName, description, version, manager)
    }
}
// end::code[]