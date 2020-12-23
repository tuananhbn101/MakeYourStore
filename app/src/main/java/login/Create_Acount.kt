package login

import Activity.Admin_Main_Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.makeyourstore.R
import com.example.makeyourstore.SQLite_Manage_Your_Store
import com.google.android.gms.common.util.CollectionUtils.isEmpty
import com.gun0912.tedpermission.util.ObjectUtils.isEmpty
import kotlinx.android.synthetic.main.activity_create__acount.*
import object_App.Account

class Create_Acount : AppCompatActivity() {
    var sqLite_manage_your_store: SQLite_Manage_Your_Store? = null
    var accountList: List<Account>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create__acount)
        sqLite_manage_your_store = SQLite_Manage_Your_Store(applicationContext)
        val selectedYear = 2000
        val selectedMonth = 5
        val selectedDayOfMonth = 10
        tvDateOfBirth.setOnClickListener(View.OnClickListener {
            val dateSetListener = OnDateSetListener { view, year, monthOfYear, dayOfMonth -> tvDateOfBirth.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year) }
            val datePickerDialog = DatePickerDialog(this,
                    dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth)
            datePickerDialog.show()
        })
        btnCancle.setOnClickListener(View.OnClickListener() {
            fun onClick(v: View?) {
                val intent = Intent(baseContext, Login_Activity::class.java)
                startActivity(intent)
            }
        })
        btnCreateAccount.setOnClickListener(View.OnClickListener {
            var dem = 0
            val userName: String = etNewUserName.text.toString()
            val password: String = etNewPassword.text.toString()
            val fullName: String = etNewFullName.text.toString()
            val dateOfBirth: String = tvDateOfBirth.text.toString()
            val phone: String = etNewPhone.text.toString()
            val question: String = etQuestion.text.toString()
            val answer: String = etAnswer.text.toString()
            val newAccount = Account(0, userName, password, fullName, dateOfBirth, phone, question, answer, 1)
            if (userName.isEmpty() || password.isEmpty() || fullName.isEmpty() || dateOfBirth.isEmpty()  || question.isEmpty() || answer.isEmpty()) {
                Toast.makeText(baseContext, "Bạn nhập thiếu thông tin", Toast.LENGTH_LONG).show()
            } else if (newAccount.checkUserName() && newAccount.checkPassword()) {
                accountList = sqLite_manage_your_store!!.allAccounts
                for (account in (accountList as MutableList<Account>?)!!) {
                    if (userName.equals(account.userName, ignoreCase = true)) {
                        dem++
                    }
                }
                if (dem != 0) {
                    Toast.makeText(baseContext, "Tên đăng nhập đã tồn tại", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(baseContext, "Tạo tài khoản thành công", Toast.LENGTH_LONG).show()
                    sqLite_manage_your_store!!.insertAccount(newAccount)
                    val intent = Intent(baseContext, Admin_Main_Activity::class.java)
                    startActivity(intent)
                }
            }
            if (newAccount.checkUserName() == false) etNewUserName.setError("Tài khoản có từ 6 ký tự bao gồm chữ hoa, chữ thường và số")
            if (newAccount.checkPassword() == false) etNewPassword.setError("Mật khẩu có từ 8 ký tự bao gồm chữ hoa, chữ thường và số")
        })
    }
}