import me.ivmg.telegram.Bot
import me.ivmg.telegram.bot
import me.ivmg.telegram.dispatch
import me.ivmg.telegram.dispatcher.text


fun main() {
    val replies: Array<String> = arrayOf("Скорее да, чем нет", "100%", "Да да и еще раз да!", "MaybeBaby", "Хммм.. Спроси еще раз", "Однозначно", "WOW ДА!!!!!", "Может в следующий раз", "Я не думаю")
    val myBot: Bot = bot {
        token = "1239026618:AAFQFSOdPJcIWEpwW-jj8zXndLqqiLQ3weQ"
        dispatch {
            text { bot, update ->

                if (update.message?.text == "/start") {
                    update.message?.let {
                        bot.sendMessage(
                            chatId = it.chat.id,
                            text = "Спроси меня что-нибудь и я предскажу твое будущее"
                        )
                    }
                }

                else if (update.message?.text?.last() == '?') {
                    val position = (0 until replies.size).random()
                    update.message?.let {
                        bot.sendMessage(
                            chatId = it.chat.id,
                            text = replies[position]
                        )
                    }
                }

                else {
                    update.message?.let {
                        bot.sendMessage(
                            chatId = it.chat.id,
                            text = "Я не понял вопрос"
                        )
                    }
                }
            }
        }

    }

    myBot.startPolling()

}