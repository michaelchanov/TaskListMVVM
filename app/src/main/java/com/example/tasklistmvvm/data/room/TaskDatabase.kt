package com.example.tasklistmvvm.data.room

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false
)

abstract class TaskDatabase: RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object {
        private var INSTANCE: TaskDatabase? = null
        fun getAppDatabase(context: Context): TaskDatabase? {

            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder<TaskDatabase>(
                    context.applicationContext, TaskDatabase::class.java, "AppDBB"
                )
                    .allowMainThreadQueries()
                    .build()

            }
            return INSTANCE
        }

//        fun destroyInstance() {
//            INSTANCE = null
//        }
    }


}
//private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
//            instance ?: buildDatabase(context).also {
//                instance = it
//            }
//        }
//
//        private fun buildDatabase(context: Context) = Room.databaseBuilder(
//            context.applicationContext,
//            TaskDatabase::class.java,
//            DB_NAME
//        ).build()