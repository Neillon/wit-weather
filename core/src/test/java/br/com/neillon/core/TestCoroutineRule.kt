package br.com.neillon.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TestCoroutineRule : TestRule {
    private val testCoroutineRuleDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineRuleDispatcher)

    override fun apply(base: Statement?, description: Description?) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(testCoroutineRuleDispatcher)
            base?.evaluate()

            Dispatchers.resetMain()
            testCoroutineRuleDispatcher.cleanupTestCoroutines()
        }
    }

    fun runBlockingTest(block: suspend CoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest { block() }
}