package br.com.neillon.home.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.neillon.home.rules.TestCoroutineRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
abstract class BaseCoroutineTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

}