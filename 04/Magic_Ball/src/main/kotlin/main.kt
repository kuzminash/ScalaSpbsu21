import me.ivmg.telegram.bot
import me.ivmg.telegram.dispatch
import me.ivmg.telegram.dispatcher.command
import me.ivmg.telegram.dispatcher.text
import kotlin.random.Random.Default.nextInt

fun main(args: Array<String>) {
    val answers: Array<String> = arrayOf("100%", "MaybeBaby", "Hmmm.. Ask me one more time", "Definitely", "Wow Yas!!", "Maybe next time?", "I think no")
    //2
    val bot = bot {
        //1
        dispatch {
            text { bot, update ->
                if (update.message?.text == "/start") {
                    update.message?.let {
                        bot.sendMessage(
                            chatId = it.chat.id,
                            text = "Ask me a question and I will predict your future"
                        )
                    }
                }
                else if (update.message?.text?.last() == '?') {
                    update.message?.let {
                        val position = nextInt(0, answers.size - 1)
                        bot.sendMessage(
                            chatId = update.message!!.chat.id,
                            text = answers[position]
                        )
                    }
                }
                else {
                    update.message?.let {
                        bot.sendMessage(
                            chatId = update.message!!.chat.id,
                            text = "It's not a question, question should ends with a ?"
                        )
                    }
                }
            }
        }
    }
    bot.startPolling()
}