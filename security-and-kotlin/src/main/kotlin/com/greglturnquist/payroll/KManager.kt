/*
package com.greglturnquist.payroll

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Manager(

        @Id
        @GeneratedValue
        val id: Int = 0,

        val name: String,

        @JsonIgnore
        val password: String? = null,

        @ElementCollection
        val roles: List<String>
)

@Entity
data class Employee(

        @Id
        @GeneratedValue
        val id: Long? = null,

        val firstName: String,

        val lastName: String,

        val description: String,

        @Version
        @JsonIgnore
        val version: Long? = null,

        @ManyToOne
        var manager: Manager // FIXME BOOOH! I WANT IT VAL NOT VAR!!!

)
*/
