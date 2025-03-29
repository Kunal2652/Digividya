package com.example.digividya

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class Task(val id: Int, var name: String, var quantity: Int, var timerDuration: Int? = null)

class todo : AppCompatActivity() {

    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var tasksAdapter: TaskAdapter
    private var taskList = mutableListOf<Task>()
    private var taskIdCounter = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasksm)

        tasksRecyclerView = findViewById(R.id.tasks_recycler_view)
        tasksAdapter = TaskAdapter(taskList) { task -> editTask(task) }
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksRecyclerView.adapter = tasksAdapter

        findViewById<View>(R.id.add_task_button).setOnClickListener {
            showAddTaskDialog()
            createNotificationChannel() // Create notification channel for sound
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showAddTaskDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val taskNameEditText = dialogView.findViewById<EditText>(R.id.task_name_edit_text)
        val taskQuantityEditText = dialogView.findViewById<EditText>(R.id.task_quantity_edit_text)
        val timerSpinner = dialogView.findViewById<Spinner>(R.id.timer_spinner)
        val customTimerEditText = dialogView.findViewById<EditText>(R.id.custom_timer_edit_text)

        timerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                customTimerEditText.visibility = if (position == 3) View.VISIBLE else View.GONE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .setPositiveButton("Save") { _, _ ->
                val taskName = taskNameEditText.text.toString()
                val taskQuantity = taskQuantityEditText.text.toString().toIntOrNull() ?: 1
                var timerDuration: Int? = null

                when (timerSpinner.selectedItem.toString()) {
                    "5 min" -> timerDuration = 5 * 60 * 1000
                    "10 min" -> timerDuration = 10 * 60 * 1000
                    "Custom" -> {
                        val customTime = customTimerEditText.text.toString().toIntOrNull()
                        if (customTime != null) timerDuration = customTime * 60 * 1000
                    }
                }

                val newTask = Task(++taskIdCounter, taskName, taskQuantity, timerDuration)
                taskList.add(newTask)
                tasksAdapter.notifyItemInserted(taskList.size - 1)

                timerDuration?.let { startTaskTimer(newTask) }
            }
            .setNegativeButton("Cancel", null)
            .create().show()
    }

    private fun editTask(task: Task) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val taskNameEditText = dialogView.findViewById<EditText>(R.id.task_name_edit_text)
        val taskQuantityEditText = dialogView.findViewById<EditText>(R.id.task_quantity_edit_text)

        taskNameEditText.setText(task.name)
        taskQuantityEditText.setText(task.quantity.toString())

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .setPositiveButton("Save") { _, _ ->
                task.name = taskNameEditText.text.toString()
                task.quantity = taskQuantityEditText.text.toString().toIntOrNull() ?: 1
                tasksAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .create().show()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val soundUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.sam)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val channel = NotificationChannel(
                "task_channel",
                "Task Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifies when task timer ends"
                setSound(soundUri, audioAttributes)
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startTaskTimer(task: Task) {
        task.timerDuration?.let { duration ->
            object : CountDownTimer(duration.toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    showNotification(task)
                    playRingtone()
                }
            }.start()
        }
    }

    private fun showNotification(task: Task) {
        runOnUiThread {
            AlertDialog.Builder(this)
                .setTitle("Task Timer Ended")
                .setMessage("Time is up for '${task.name}'. What do you want to do?")

                .setNeutralButton("Stop & Close") { dialog, _ ->
                    stopRingtone()
                    dialog.dismiss() // Close the dialog
                }
                .setCancelable(false)
                .show()
        }
    }

    private var ringtone: Ringtone? = null

    private fun playRingtone() {
        val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(applicationContext, ringtoneUri)
        ringtone?.play()
    }

    // Function to stop the ringtone
    private fun stopRingtone() {
        ringtone?.stop()
    }

    // Separate function to send the notification
    private fun sendNotification(task: Task) {
        val soundUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.sam)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this, "task_channel")
            .setSmallIcon(R.drawable.baseline_close_24)
            .setContentTitle("Task Timer Ended")
            .setContentText("Time is up for '${task.name}'!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(soundUri)
            .setAutoCancel(true)

        notificationManager.notify(task.id, builder.build())
    }



}
