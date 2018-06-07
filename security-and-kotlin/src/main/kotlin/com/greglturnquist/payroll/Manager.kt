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

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import java.util.Arrays
import java.util.Objects

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Entity
class Manager {

    @Id
    @GeneratedValue
    var id: Long? = null

    var name: String? = null

    @JsonIgnore
    var password: String? = null

    var roles: Array<out String> = emptyArray()

    override fun toString(): String {
        return "Manager{" +
                "id=" + id +
                ", name='" + name + '\''.toString() +
                ", password='" + password + '\''.toString() +
                ", roles=" + Arrays.toString(roles) +
                '}'.toString()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val manager = o as Manager?
        return id == manager!!.id &&
                name == manager.name &&
                password == manager.password &&
                Arrays.equals(roles, manager.roles)
    }

    override fun hashCode(): Int {

        var result = Objects.hash(id, name, password)
        result = 31 * result + Arrays.hashCode(roles)
        return result
    }

    protected constructor() {}

    constructor(name: String, password: String, vararg roles: String) {

        this.name = name
        this.password = password
        this.roles = roles
    }

}
// end::code[]