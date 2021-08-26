package com.udacity.asteroidradar

import android.content.Context
import androidx.work.testing.TestWorkerBuilder
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.WorkManager
import androidx.work.Worker
import com.udacity.asteroidradar.work.RefreshDataWorker
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import androidx.work.ListenableWorker.Result
import androidx.work.testing.TestListenableWorkerBuilder
import kotlinx.coroutines.runBlocking


@RunWith(AndroidJUnit4::class)
class RefreshWorkerTest {
    private lateinit var context: Context
    private lateinit var executor: Executor

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }


    @Test
    fun testRefreshWorker() {

        // Unit test vs android test, what is the difference ?
        val worker = TestListenableWorkerBuilder<RefreshDataWorker>(
            context = context
        ).build()

        // runs in parallele with other coroutines?
        runBlocking {
            val result = worker.doWork()
            assertThat(result,`is` (Result.success()))
        }


    }

    // "Android test" for a specific class or sum lie
    // da
}