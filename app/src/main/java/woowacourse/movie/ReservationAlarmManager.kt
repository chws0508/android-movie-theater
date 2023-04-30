package woowacourse.movie

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import woowacourse.movie.receiver.ReservationNotificationReceiver
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.TicketsUiModel
import java.sql.Date
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class ReservationAlarmManager() {

    fun registerAlarm(
        context: Context, ticketsUiModel: TicketsUiModel, movieUiModel: MovieUiModel
    ) {
        val receiverIntent = ReservationNotificationReceiver.generateReceiverIntent(
            context, movieUiModel, ticketsUiModel
        )
        val pendingIntent = generatedPendingIntent(context, receiverIntent)
        val alarmManager: AlarmManager = generateAlarmManager(context)
        val timeMillis = getTimeInMillis(ticketsUiModel.list.first().date)
        setAlarmManagerOption(alarmManager, timeMillis, pendingIntent)
    }

    private fun generatedPendingIntent(context: Context, receiverIntent: Intent): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            receiverIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun generateAlarmManager(context: Context): AlarmManager {
        return context.getSystemService(ALARM_SERVICE) as AlarmManager
    }

    private fun getTimeInMillis(localDateTime: LocalDateTime): Long {
        val sqlDate = getAlarmDateTime(localDateTime, ALARM_TIME)
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = sqlDate
        return calendar.timeInMillis
    }

    private fun getAlarmDateTime(localDateTime: LocalDateTime, minute: Long): Date {
        val milliSeconds: Long = localDateTime
            .atZone(ZoneId.systemDefault())
            .minusMinutes(minute)
            .toInstant()
            .toEpochMilli()
        return Date(milliSeconds)
    }

    private fun setAlarmManagerOption(
        alarmManager: AlarmManager,
        timeMillis: Long,
        pendingIntent: PendingIntent
    ) {
        alarmManager[AlarmManager.RTC, timeMillis] = pendingIntent
    }

    companion object {
        private const val REQUEST_CODE = 125
        private const val ALARM_TIME = 30L
    }
}
