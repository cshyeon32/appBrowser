package com.example.webbrowser

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.lang.Exception

class Extension {
}
////////////Context.sendSMS////////////
/*문자를 전송하는 암묵적 인텐트 매개변수로 문자를 수신할 전화번호(number),SMS 본문 내용(text) 기본값은 빈 문자열("")로 설정*/
fun Context.sendSMS(number :String, text:String =""): Boolean{
    return try {
        val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse("sms:$number"))
        intent.putExtra("sms_body", text)
        startActivity(intent)
        true
    }catch(e:Exception){
        e.printStackTrace()
        false
    }
}
////////////Context.sendSMS////////////

////////////Context.email////////////
/*이메일 전송하는 암묵적 인텐트 매개변수로 이메일주소(email),제목(subject),본문(text)을 전달받아 처리*/
fun Context.email(email:String, subject:String="", text:String=""):Boolean{
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto")
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    if(subject.isNotEmpty())
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    if(text.isNotEmpty())
        intent.putExtra(Intent.EXTRA_TEXT, text)
    if(intent.resolveActivity(packageManager) != null){
        startActivity(intent)
        return true
    }
    return false
}
////////////Context.email////////////

////////////Context.share////////////
/*페이지를 공유하는 암묵적 인텐트 매개변수로 본문(text),제목(subject)을 전달 받아처리*/
fun Context.share(text: String, subject: String = ""):Boolean{
    return try{
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT,subject)
        intent.putExtra(Intent.EXTRA_TEXT,text)
        startActivity(Intent.createChooser(intent,null))
        true
    }catch (e:ActivityNotFoundException){
        e.printStackTrace()
        false
    }
}
////////////Context.share////////////

////////////Context.browser////////////
/*기본 브라우저를 여는 암묵적 인텐트 매개변수 */
fun Context.browser(url:String, newTask: Boolean = false):Boolean{
    return try{
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if(newTask){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        true
    }catch(e:ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}
////////////Context.browser////////////