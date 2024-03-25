package io.hhplus.cleanarchitecture.jpa

import jakarta.annotation.PostConstruct
import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.Table
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DbCleanup(
    private val em: EntityManager,
) {

    private val tableNames = mutableListOf<String>()

    @PostConstruct
    fun init() {
        em.metamodel.entities
            .filter { it.javaType.getAnnotation(Entity::class.java) != null }
            .forEach {
                val tableName = it.javaType.getAnnotation(Table::class.java).name
                tableNames.add(tableName)
            }
    }

    @Transactional
    fun execute() {
        em.flush()
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
        tableNames.forEach {
            em.createNativeQuery("TRUNCATE TABLE %s".format(it)).executeUpdate()
            em.createNativeQuery("ALTER TABLE %s ALTER COLUMN id RESTART WITH 1".format(it)).executeUpdate()
        }
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }
}